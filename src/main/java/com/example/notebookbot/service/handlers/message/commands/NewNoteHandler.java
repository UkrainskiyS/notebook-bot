package com.example.notebookbot.service.handlers.message.commands;

import com.example.notebookbot.persist.chat.ChatManager;
import com.example.notebookbot.persist.chat.ChatMode;
import com.example.notebookbot.persist.note.UpdateMod;
import com.example.notebookbot.persist.note.model.Note;
import com.example.notebookbot.persist.note.repository.NoteRepository;
import com.example.notebookbot.service.handlers.message.AbstractMessageHandler;
import com.example.notebookbot.txtmark.MarkdownCorrector;
import com.example.notebookbot.txtmark.TypeText;
import com.example.notebookbot.utilits.DefaultMessage;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Slf4j
public class NewNoteHandler extends AbstractMessageHandler {
	private final ChatMode mode;

	public NewNoteHandler(Message message, ChatManager chatManager, NoteRepository noteRepository, ChatMode mode) {
		super(message, chatManager, noteRepository);
		this.mode = mode;
	}

	@Override
	public List<PartialBotApiMethod<Message>> execute() {
		switch (mode) {
			case IGNORED: return ignoredMode();
			case NEW_SET_NAME: return setNameMode();
			case NEW_SET_TEXT: return setTextMode();
			default: return Collections.emptyList();
		}
	}

	// переводит чат в режим создания новой заметки
	private List<PartialBotApiMethod<Message>> ignoredMode() {
		chatManager.setMode(message.getChatId(), ChatMode.NEW_SET_NAME);
		return DefaultMessage.setNameForNewNote(message.getChatId());
	}

	// проверяет имя новой заметки и переводит чат в режим добавления текста к ней
	private List<PartialBotApiMethod<Message>> setNameMode() {
		MarkdownCorrector corrector = new MarkdownCorrector(message.getText(), TypeText.NAME);
		String nameNote = corrector.correct();

		if (noteRepository.existsByChatIdAndName(message.getChatId(), nameNote)) {
			// проверка повтора названия
			chatManager.setMode(message.getChatId(), ChatMode.IGNORED);
			return DefaultMessage.noteNameExist(message.getChatId(), nameNote);
		} else if (nameNote.length() > 150) {
			// проверка длинны названия
			chatManager.setMode(message.getChatId(), ChatMode.IGNORED);
			return DefaultMessage.longNoteName(message.getChatId());
		} else {
			// добавляет имя в бд
			chatManager.setMode(message.getChatId(), ChatMode.NEW_SET_TEXT);
			noteRepository.save(new Note(message.getChatId(), nameNote, UpdateMod.NOT));
			log.debug("Add {} note to database", nameNote);
			return DefaultMessage.setTextForNewNote(message.getChatId(), nameNote);
		}
	}

	// добавляет текст к созданной заметке и выставляет режим игнора
	private List<PartialBotApiMethod<Message>> setTextMode() {
		chatManager.setMode(message.getChatId(), ChatMode.IGNORED);

		// Обработка максимальной длинны текста
		if (message.getText().length() > 4000) {
			return DefaultMessage.veryLongText(message.getChatId());
		}

		Note note = noteRepository.getAllByChatId(message.getChatId()).stream()
				.max(Comparator.comparing(Note::getId))
				.orElseThrow();

		MarkdownCorrector corrector = new MarkdownCorrector(message.getText(), TypeText.TEXT);
		note.setText(corrector.correct());
		noteRepository.save(note);

		log.debug("Note {} saves to database, chatId = {}", note.getName(), note.getChatId());
		return DefaultMessage.newNoteCreated(message.getChatId(), note.getName());
	}
}

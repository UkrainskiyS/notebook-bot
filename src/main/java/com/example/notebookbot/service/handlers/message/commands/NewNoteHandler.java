package com.example.notebookbot.service.handlers.message.commands;

import com.example.notebookbot.persist.chat.ChatManager;
import com.example.notebookbot.persist.chat.ChatMode;
import com.example.notebookbot.persist.note.UpdateMod;
import com.example.notebookbot.persist.note.model.Note;
import com.example.notebookbot.persist.note.repository.NoteRepository;
import com.example.notebookbot.service.handlers.message.AbstractMessageHandler;
import com.example.notebookbot.utilits.DefaultMessage;
import com.example.notebookbot.utilits.TextCorrector;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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
		if (noteRepository.existsByChatIdAndName(message.getChatId(), message.getText())) {
			// проверка повтора названия
			chatManager.setMode(message.getChatId(), ChatMode.IGNORED);
			return DefaultMessage.noteNameExist(message.getChatId(), message.getText());
		} else if (message.getText().length() > 150) {
			// проверка длинны названия
			chatManager.setMode(message.getChatId(), ChatMode.IGNORED);
			return DefaultMessage.longNoteName(message.getChatId());
		} else {
			// добавляет имя в бд
			chatManager.setMode(message.getChatId(), ChatMode.NEW_SET_TEXT);
			noteRepository.save(new Note(message.getChatId(), message.getText(), LocalDateTime.now(), UpdateMod.NOT));
			return DefaultMessage.setTextForNewNote(message.getChatId(), message.getText());
		}
	}

	// добавляет текст к созданной заметке и выставляет режим игнора
	private List<PartialBotApiMethod<Message>> setTextMode() {
		chatManager.setMode(message.getChatId(), ChatMode.IGNORED);
		Note note = noteRepository.getAllByChatId(message.getChatId()).stream()
				.max(Comparator.comparing(Note::getId))
				.orElseThrow();

		note.setText(TextCorrector.correct(message.getText()));

		try {
			noteRepository.save(note);
		} catch (Exception e) {
			return DefaultMessage.veryLongText(message.getChatId());
		}

		return DefaultMessage.newNoteCreated(message.getChatId(), note.getName());
	}
}

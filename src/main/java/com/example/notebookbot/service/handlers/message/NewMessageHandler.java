package com.example.notebookbot.service.handlers.message;

import com.example.notebookbot.persist.chat.ChatManager;
import com.example.notebookbot.persist.chat.ChatMode;
import com.example.notebookbot.persist.note.model.Note;
import com.example.notebookbot.persist.note.repository.NoteRepository;
import com.example.notebookbot.service.handlers.AbstractHandler;
import com.example.notebookbot.utilits.DefaultMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class NewMessageHandler extends AbstractHandler {
	private final NoteRepository noteRepository;
	private final ChatMode mode;

	public NewMessageHandler(Message message, ChatManager chatManager, NoteRepository noteRepository, ChatMode mode) {
		super(message, chatManager);
		this.noteRepository = noteRepository;
		this.mode = mode;
	}

	@Override
	public List<SendMessage> execute() {
		switch (mode) {
			case IGNORED: return ignoredMode();
			case NEW_SET_NAME: return setNameMode();
			case NEW_SET_TEXT: return setTextMode();
			default: return Collections.emptyList();
		}
	}

	// добавляет текст к созданной саметке и выставляет режим игнора
	private List<SendMessage> setTextMode() {
		chatManager.setMode(message.getChatId(), ChatMode.IGNORED);
		List<Note> notes = noteRepository.findAllByChatId(message.getChatId()).stream()
				.sorted(Comparator.comparing(Note::getId))
				.collect(Collectors.toList());

		Note note = notes.get(notes.size() - 1);
		note.setText(message.getText());
		noteRepository.save(note);

		return DefaultMessage.newNoteCreated(message.getChatId(), note.getName());
	}

	// проверяет имя новой заметки и переводит чат в режим добавления текста к ней
	private List<SendMessage> setNameMode() {
		if (noteRepository.existsByChatIdAndName(message.getChatId(), message.getText())) {
			chatManager.setMode(message.getChatId(), ChatMode.IGNORED);

			return DefaultMessage.badNoteName(message.getChatId(), message.getText());
		} else {
			chatManager.setMode(message.getChatId(), ChatMode.NEW_SET_TEXT);
			noteRepository.save(new Note(message.getChatId(), message.getText()));

			return DefaultMessage.setTextForNewNote(message.getChatId(), message.getText());
		}
	}

	// переводит чат в режим создания новой заметки
	private List<SendMessage> ignoredMode() {
		chatManager.setMode(message.getChatId(), ChatMode.NEW_SET_NAME);
		return DefaultMessage.setNameForNewNote(message.getChatId());
	}
}

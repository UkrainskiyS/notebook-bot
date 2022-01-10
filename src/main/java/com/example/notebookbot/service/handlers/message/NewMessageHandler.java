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
		if (mode.equals(ChatMode.IGNORED)) {
			chatManager.setMode(message.getChatId(), ChatMode.NEW_SET_NAME);
			return DefaultMessage.setNameForNewNote(message.getChatId());
		} else if (mode.equals(ChatMode.NEW_SET_NAME)) {
			if (noteRepository.existsByChatIdAndName(message.getChatId(), message.getText())) {
				chatManager.setMode(message.getChatId(), ChatMode.IGNORED);
				return DefaultMessage.badNoteName(message.getChatId(), message.getText());
			} else {
				chatManager.setMode(message.getChatId(), ChatMode.NEW_SET_TEXT);
				noteRepository.save(new Note(message.getChatId(), message.getText()));
				return DefaultMessage.setTextForNewNote(message.getChatId(), message.getText());
			}
		} else if (mode.equals(ChatMode.NEW_SET_TEXT)) {
			chatManager.setMode(message.getChatId(), ChatMode.IGNORED);
			List<Note> notes = noteRepository.findAllByChatId(message.getChatId()).stream()
					.sorted(Comparator.comparing(Note::getId))
					.collect(Collectors.toList());
			Note note = notes.get(notes.size() - 1);
			note.setText(message.getText());
			noteRepository.save(note);
			return DefaultMessage.newNoteCreated(message.getChatId(), note.getName());
		} else {
			return Collections.emptyList();
		}
	}
}

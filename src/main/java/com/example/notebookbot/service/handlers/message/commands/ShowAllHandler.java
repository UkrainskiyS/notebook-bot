package com.example.notebookbot.service.handlers.message.commands;

import com.example.notebookbot.persist.chat.ChatManager;
import com.example.notebookbot.persist.note.model.Note;
import com.example.notebookbot.persist.note.repository.NoteRepository;
import com.example.notebookbot.service.handlers.AbstractHandler;
import com.example.notebookbot.telegram.MessageBuilder;
import com.example.notebookbot.utilits.DefaultMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ShowAllHandler extends AbstractHandler {
	private final NoteRepository noteRepository;

	public ShowAllHandler(Message message, ChatManager chatManager, NoteRepository noteRepository) {
		super(message, chatManager);
		this.noteRepository = noteRepository;
	}

	@Override
	public List<SendMessage> execute() {
		Optional<List<Note>> optionalNotes = noteRepository.findAllByChatId(message.getChatId());
		MessageBuilder messageBuilder = new MessageBuilder(message.getChatId());

		if (optionalNotes.isEmpty() || optionalNotes.get().size() == 0) {
			return DefaultMessage.noteListEmpty(message.getChatId());

		} else {
			return List.of(messageBuilder.setText(optionalNotes.get().stream()
							.map(Note::getName)
							.collect(Collectors.joining("```\n- ```", "*Все заметки:*\n- ```", "```")))
							.build());
		}
	}
}
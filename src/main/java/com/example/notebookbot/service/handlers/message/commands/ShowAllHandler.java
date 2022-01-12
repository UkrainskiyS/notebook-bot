package com.example.notebookbot.service.handlers.message.commands;

import com.example.notebookbot.persist.chat.ChatManager;
import com.example.notebookbot.persist.note.model.Note;
import com.example.notebookbot.persist.note.repository.NoteRepository;
import com.example.notebookbot.service.handlers.message.AbstractMessageHandler;
import com.example.notebookbot.utilits.DefaultMessage;
import com.example.notebookbot.utilits.NotePrinter;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;
import java.util.Optional;

public class ShowAllHandler extends AbstractMessageHandler {

	public ShowAllHandler(Message message, ChatManager chatManager, NoteRepository noteRepository) {
		super(message, chatManager, noteRepository);
	}

	@Override
	public List<PartialBotApiMethod<Message>> execute() {
		Optional<List<Note>> optionalNotes = noteRepository.findAllByChatId(message.getChatId());

		if (optionalNotes.isEmpty() || optionalNotes.get().size() == 0) {
			return DefaultMessage.noteListEmpty(message.getChatId());

		} else {
			return NotePrinter.getMessageAllNotes(message.getChatId(), optionalNotes.get());
		}
	}
}

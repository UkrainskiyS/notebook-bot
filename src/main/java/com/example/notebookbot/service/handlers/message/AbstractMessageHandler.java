package com.example.notebookbot.service.handlers.message;

import com.example.notebookbot.persist.chat.ChatManager;
import com.example.notebookbot.persist.note.repository.NoteRepository;
import com.example.notebookbot.service.handlers.AbstractHandler;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;

public abstract class AbstractMessageHandler extends AbstractHandler {
	protected final NoteRepository noteRepository;

	public AbstractMessageHandler(Message message, ChatManager chatManager, NoteRepository noteRepository) {
		super(message, chatManager);
		this.noteRepository = noteRepository;
	}

	@Override
	public abstract List<SendMessage> execute();
}

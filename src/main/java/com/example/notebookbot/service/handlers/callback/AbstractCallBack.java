package com.example.notebookbot.service.handlers.callback;

import com.example.notebookbot.persist.chat.ChatManager;
import com.example.notebookbot.persist.note.repository.NoteRepository;
import com.example.notebookbot.service.handlers.AbstractHandler;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;

public abstract class AbstractCallBack extends AbstractHandler {
	protected final NoteRepository noteRepository;
	protected final int noteId;

	public AbstractCallBack(Message message, ChatManager chatManager, NoteRepository noteRepository, String data) {
		super(message, chatManager);
		this.noteRepository = noteRepository;
		this.noteId = Integer.parseInt(data);
	}

	@Override
	public abstract List<BotApiMethod<Message>> execute();
}

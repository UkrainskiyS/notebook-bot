package com.example.notebookbot.service.handlers.message;

import com.example.notebookbot.config.BotConfig;
import com.example.notebookbot.persist.chat.ChatManager;
import com.example.notebookbot.persist.chat.ChatMode;
import com.example.notebookbot.persist.note.repository.NoteRepository;
import com.example.notebookbot.service.handlers.AbstractHandler;
import com.example.notebookbot.service.handlers.AbstractHandlerFactory;
import com.example.notebookbot.service.handlers.message.commands.*;
import org.telegram.telegrambots.meta.api.objects.Message;


public class MessageHandlersFactory extends AbstractHandlerFactory {
	private final BotConfig config;
	private final ChatMode mode;

	public MessageHandlersFactory(ChatManager chatManager, NoteRepository noteRepository, Message message,
								  BotConfig config, ChatMode mode) {
		super(chatManager, noteRepository, message, mode);
		this.config = config;
		this.mode = mode;
	}

	@Override
	public AbstractHandler getHandler() {
		if (mode.equals(ChatMode.IGNORED) && config.getCommands().contains(message.getText())) {
			return getIgnoredHandler(mode);
		} else {
			return getLongHandler(mode);
		}
	}

	private AbstractHandler getIgnoredHandler(ChatMode mode) {
		if (message.getText().startsWith("/newnote")) {
			return new NewNoteHandler(message, chatManager, noteRepository, mode);
		} else if (message.getText().startsWith("/deletenote")) {
			return new DeleteHandler(message, chatManager, noteRepository);
		} else if (message.getText().startsWith("/showall")) {
			return new ShowAllHandler(message, chatManager, noteRepository);
		} else if (message.getText().startsWith("/getnote")) {
			return new GetNoteHandler(message, chatManager, noteRepository);
		} else if (message.getText().startsWith("/getfile")) {
			return new GetFileHandler(message, chatManager, noteRepository);
		} else if (message.getText().startsWith("/help")) {
			return null;
		} else {
			return null;
		}
	}

	private AbstractHandler getLongHandler(ChatMode mode) {
		switch (mode) {
			case NEW_SET_NAME:
			case NEW_SET_TEXT:
				return new NewNoteHandler(message, chatManager, noteRepository, mode);
			default: return null;
		}
	}
}

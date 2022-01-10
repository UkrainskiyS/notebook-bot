package com.example.notebookbot.service.handlers;

import com.example.notebookbot.config.BotConfig;
import com.example.notebookbot.persist.chat.ChatManager;
import com.example.notebookbot.persist.chat.ChatMode;
import com.example.notebookbot.persist.note.repository.NoteRepository;
import com.example.notebookbot.service.handlers.message.NewNoteHandler;
import com.example.notebookbot.service.handlers.message.ShowAllHandler;
import lombok.AllArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Collections;
import java.util.Set;

@AllArgsConstructor
public class MessageHandlersFactory {
	private final ChatManager chatManager;
	private final NoteRepository noteRepository;
	private final Message message;
	private final BotConfig config;

	public AbstractHandler getHandler(ChatMode mode) {
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
			return null;
		} else if (message.getText().startsWith("/showall")) {
			return new ShowAllHandler(message, chatManager, noteRepository);
		} else if (message.getText().startsWith("/shownote")) {
			return null;
		} else if (message.getText().startsWith("/getfile")) {
			return null;
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

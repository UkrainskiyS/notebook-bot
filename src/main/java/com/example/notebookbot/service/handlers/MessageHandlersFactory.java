package com.example.notebookbot.service.handlers;

import com.example.notebookbot.persist.chat.ChatManager;
import com.example.notebookbot.persist.chat.ChatMode;
import com.example.notebookbot.persist.note.repository.NoteRepository;
import com.example.notebookbot.service.handlers.message.NewNoteHandler;
import com.example.notebookbot.service.handlers.message.ShowAllHandler;
import lombok.AllArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Set;

@AllArgsConstructor
public class MessageHandlersFactory {
	private final ChatManager chatManager;
	private final NoteRepository noteRepository;
	private final Message message;
	private final Set<String> commands;

	public AbstractHandler getHandler(ChatMode mode) {
		if (mode.equals(ChatMode.IGNORED)) {
			return getIgnoredHandler(mode);
		} else {
			return getLongHandler(mode);
		}
	}

	private AbstractHandler getIgnoredHandler(ChatMode mode) {
		switch (message.getText()) {
			case "/newnote": return new NewNoteHandler(message, chatManager, noteRepository, mode);
			case "/deletenote": return null;
			case "/showall": return new ShowAllHandler(message, chatManager, noteRepository);
			case "/shownote": return null;
			case "/help": return null;
			default: return null;
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

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

	public MessageHandlersFactory(ChatManager chatManager, NoteRepository noteRepository, Message message, BotConfig config) {
		super(chatManager, noteRepository, message);
		this.config = config;
	}

	@Override
	public AbstractHandler getHandler() {
		if (chatManager.getMode(message.getChatId()).equals(ChatMode.IGNORED) && config.getCommands().contains(message.getText())) {
			return getIgnoredHandler();
		} else {
			return null;
		}
	}

	// Метод обрабатывает команды бота
	private AbstractHandler getIgnoredHandler() {
		if (message.getText().startsWith("/newnote")) {
			return new NewNoteHandler(message, config);
		} else if (message.getText().startsWith("/deletenote")) {
			return new DeleteHandler(message, chatManager, noteRepository);
		} else if (message.getText().startsWith("/showall")) {
			return new ShowAllHandler(message, chatManager, noteRepository);
		} else if (message.getText().startsWith("/getnote")) {
			return new GetNoteHandler(message, chatManager, noteRepository);
		} else if (message.getText().startsWith("/getfile")) {
			return new GetFileHandler(message, chatManager, noteRepository);
		} else if (message.getText().startsWith("/help")) {
			return new HelpHandler(message);
		} else if (message.getText().startsWith("/start")) {
			return new StartHandler(message, chatManager);
		} else if (message.getText().startsWith("/editnote")) {
			return new EditNoteHandler(message, chatManager, noteRepository);
		}else {
			return null;
		}
	}
}

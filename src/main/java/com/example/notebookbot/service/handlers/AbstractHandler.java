package com.example.notebookbot.service.handlers;

import com.example.notebookbot.persist.chat.ChatManager;
import lombok.AllArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;

@AllArgsConstructor
public abstract class AbstractHandler {
	protected final Message message;
	protected final ChatManager chatManager;

	public abstract List<SendMessage> execute();
}
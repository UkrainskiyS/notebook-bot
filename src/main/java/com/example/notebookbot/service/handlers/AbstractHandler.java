package com.example.notebookbot.service.handlers;

import com.example.notebookbot.persist.chat.ChatManager;
import lombok.AllArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;

@AllArgsConstructor
public abstract class AbstractHandler {
	protected Message message;
	protected ChatManager chatManager;

	public AbstractHandler(Message message) {
		this.message = message;
	}

	public abstract List<PartialBotApiMethod<Message>> execute();
}

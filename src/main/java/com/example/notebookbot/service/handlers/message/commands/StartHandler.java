package com.example.notebookbot.service.handlers.message.commands;

import com.example.notebookbot.persist.chat.ChatManager;
import com.example.notebookbot.service.handlers.AbstractHandler;
import com.example.notebookbot.telegram.MessageBuilder;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;

public class StartHandler extends AbstractHandler {

	public StartHandler(Message message, ChatManager chatManager) {
		super(message, chatManager);
	}

	public List<SendMessage> execute() {
		chatManager.saveChat(message.getChatId());
		return List.of(
				new MessageBuilder().fastBuild(message.getChatId(), "Приветствую, " + message.getChat().getFirstName() + "!"),
				new MessageBuilder().fastBuild(message.getChatId(), "Используй /help чтобы посмотреть инструкцию.")
		);
	}
}

package com.example.notebookbot.service.handlers.message.commands;

import com.example.notebookbot.persist.chat.ChatManager;
import com.example.notebookbot.service.handlers.AbstractHandler;
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
				SendMessage.builder().text("Приветствую, " + message.getChat().getFirstName() + "!").chatId(String.valueOf(message.getChatId())).build(),
				SendMessage.builder().text("Используй /help чтобы посмотреть инструкцию.").chatId(String.valueOf(message.getChatId())).build()
		);
	}
}

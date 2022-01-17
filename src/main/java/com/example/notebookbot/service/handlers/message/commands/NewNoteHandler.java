package com.example.notebookbot.service.handlers.message.commands;

import com.example.notebookbot.config.BotConfig;
import com.example.notebookbot.persist.chat.ChatManager;
import com.example.notebookbot.service.handlers.AbstractHandler;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;


import java.util.List;

@Slf4j
public class NewNoteHandler extends AbstractHandler {
	private final BotConfig config;

	public NewNoteHandler(Message message, ChatManager chatManager, BotConfig config) {
		super(message, chatManager);
		this.config = config;
	}

	@Override
	public List<PartialBotApiMethod<Message>> execute() {
		String text = "Для создания новой заметки откройте ссылку " +
				config.getHost() + "/new?chat=" + chatManager.getChat(message.getChatId()).getUuid() +
				" в браузере!";

		return List.of(SendMessage.builder()
				.chatId(String.valueOf(message.getChatId()))
				.text(text)
				.parseMode(ParseMode.HTML)
				.build()
		);
	}
}

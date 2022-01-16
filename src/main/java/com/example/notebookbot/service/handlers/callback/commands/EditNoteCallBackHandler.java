package com.example.notebookbot.service.handlers.callback.commands;

import com.example.notebookbot.config.BotConfig;
import com.example.notebookbot.persist.chat.ChatManager;
import com.example.notebookbot.persist.chat.ChatMode;
import com.example.notebookbot.service.handlers.AbstractHandler;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;

public class EditNoteCallBackHandler extends AbstractHandler {
	private final String data;
	private final BotConfig config;

	public EditNoteCallBackHandler(Message message, ChatManager chatManager, BotConfig botConfig, String data) {
		super(message);
		this.chatManager = chatManager;
		this.data = data;
		this.config = botConfig;
	}

	@Override
	public List<PartialBotApiMethod<Message>> execute() {
		String text = "Для редактирования заметки откройте ссылку <code>" +
				config.getHost() + "/edit?id=" + data + "</code> в браузере!";

		chatManager.setMode(message.getChatId(), ChatMode.IGNORED);
		return List.of(SendMessage.builder()
				.chatId(String.valueOf(message.getChatId()))
				.text(text)
				.parseMode(ParseMode.HTML)
				.build()
		);
	}
}

package com.example.notebookbot.service.handlers.message.commands;

import com.example.notebookbot.config.BotConfig;
import com.example.notebookbot.service.handlers.AbstractHandler;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;


import java.util.List;

@Slf4j
public class NewNoteHandler extends AbstractHandler {
	private final BotConfig config;

	public NewNoteHandler(Message message, BotConfig config) {
		super(message);
		this.config = config;
	}

	@Override
	public List<PartialBotApiMethod<Message>> execute() {
		String url = config.getHost() + ":" + config.getPort() + "/new?chat=" + message.getChatId();

		return List.of(
				SendMessage.builder()
						.chatId(String.valueOf(message.getChatId()))
						.text("Создайте заметку в редакторе по ссылке ниже!\n\n" + url)
						.build()
		);
	}
}

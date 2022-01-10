package com.example.notebookbot.utilits;

import com.example.notebookbot.telegram.MessageBuilder;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;

public class DefaultMessage {
	public static List<SendMessage> notBotInitMessage(Long chatId) {
		return List.of(
				new MessageBuilder().fastBuild(chatId, "Enter /start for initial notebook bot!")
		);
	}
}

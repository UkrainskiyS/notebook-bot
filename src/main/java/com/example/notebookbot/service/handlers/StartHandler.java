package com.example.notebookbot.service.handlers;

import com.example.notebookbot.persist.chat.ChatManager;
import com.example.notebookbot.telegram.MessageBuilder;
import lombok.Data;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;

@Data
public class StartHandler {
	private final Message message;
	private final ChatManager chatManager;

	public List<SendMessage> execute() {
		chatManager.saveChat(message.getChatId());
		return List.of(
				new MessageBuilder().fastBuild(message.getChatId(), "Hello, " + message.getChat().getFirstName() + "!"),
				new MessageBuilder().fastBuild(message.getChatId(), "Enter /help to get instructions on how to use me.")
		);
	}
}

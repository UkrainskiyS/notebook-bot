package com.example.notebookbot.service.handlers.message.commands;

import com.example.notebookbot.service.handlers.AbstractHandler;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class HelpHandler extends AbstractHandler {

	public HelpHandler(Message message) {
		super(message);
	}

	@Override
	public List<PartialBotApiMethod<Message>> execute() {
		return List.of(SendMessage.builder()
				.text(readHelpFile())
				.chatId(String.valueOf(message.getChatId()))
				.parseMode(ParseMode.MARKDOWN)
				.build());
	}

	private String readHelpFile() {
		String pathToHelp = "src/main/resources/static/help.txt";

		String help = null;
		try (BufferedInputStream stream = new BufferedInputStream(new FileInputStream(pathToHelp))) {

			help = new String(stream.readAllBytes());

		} catch (IOException e) {
			e.printStackTrace();
		}
		return help;
	}
}
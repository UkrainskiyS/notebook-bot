package com.example.notebookbot.service.handlers.message.commands;

import com.example.notebookbot.service.handlers.AbstractHandler;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

/*
* Класс для чтения help.txt and markdown.txt из /resource/static
 */

@Slf4j
public class HelpHandler extends AbstractHandler {

	public HelpHandler(Message message) {
		super(message);
	}

	@Override
	public List<PartialBotApiMethod<Message>> execute() {
		log.debug("Command /help execute");

		return List.of(
				SendMessage.builder().chatId(String.valueOf(message.getChatId()))
						.text(readFile("help")).parseMode(ParseMode.MARKDOWN).build(),

				SendMessage.builder().chatId(String.valueOf(message.getChatId()))
						.text(readFile("markdown")).build()
		);
	}

	private String readFile(String file) {
		String pathToHelp = String.format("src/main/resources/static/%s.txt", file);

		String help = null;
		try (BufferedInputStream stream = new BufferedInputStream(new FileInputStream(pathToHelp))) {
			help = new String(stream.readAllBytes());
		} catch (IOException e) {
			log.error(e.getMessage());
		}
		return help;
	}
}

package com.example.notebookbot.utilits;

import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;

/*
* Класс со статическими методами с заготовками стандартных сообщений
 */

public class DefaultMessage {

	public static List<PartialBotApiMethod<Message>> noteDeleted(Long chatId, String name) {
		return List.of(SendMessage.builder().chatId(String.valueOf(chatId)).text(String.format("Заметка _%s_ успешно удалена!", name))
				.parseMode(ParseMode.MARKDOWN).build());
	}

	public static List<PartialBotApiMethod<Message>> noteListEmpty(Long chatId) {
		return List.of(SendMessage.builder().chatId(String.valueOf(chatId)).text("Ваш список заметок пуст!\n\nСоздать новую заметку можно с помощью команды /newnote").build());
	}

	public static List<PartialBotApiMethod<Message>> notBotInitMessage(Long chatId) {
		return List.of(SendMessage.builder().chatId(String.valueOf(chatId)).text("Используй /start чтобы начать использовать бота!").build());
	}

	public static List<PartialBotApiMethod<Message>> noteWasDeleted(Long chatId) {
		return List.of(SendMessage.builder().text("Эта заметка удалена!").chatId(String.valueOf(chatId)).build());
	}
}

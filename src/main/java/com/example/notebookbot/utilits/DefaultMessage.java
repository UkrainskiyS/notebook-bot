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

	public static List<PartialBotApiMethod<Message>> setTextForNewNote(Long chatId, String name) {
		return List.of(SendMessage.builder().chatId(String.valueOf(chatId))
				.text(String.format("Хорошо, а теперь добавь содержание для _%s_!", name))
				.parseMode(ParseMode.MARKDOWN).build());
	}

	public static List<PartialBotApiMethod<Message>> noteNameExist(Long chatId, String name) {
		return List.of(SendMessage.builder().chatId(String.valueOf(chatId))
				.text(String.format("Хмм... Кажется заметка с именем _%s_ уже существует.", name))
				.parseMode(ParseMode.MARKDOWN).build());
	}

	public static List<PartialBotApiMethod<Message>> newNoteCreated(Long chatId, String name) {
		return List.of(SendMessage.builder().chatId(String.valueOf(chatId))
				.text(String.format("Новая заметка _%s_ успешно создана!", name))
				.parseMode(ParseMode.MARKDOWN).build());
	}

	public static List<PartialBotApiMethod<Message>> noteDeleted(Long chatId, String name) {
		return List.of(SendMessage.builder().chatId(String.valueOf(chatId)).text(String.format("Заметка _%s_ успешно удалена!", name))
				.parseMode(ParseMode.MARKDOWN).build());
	}

	public static List<PartialBotApiMethod<Message>> veryLongText(Long chatId) {
		return List.of(SendMessage.builder().chatId(String.valueOf(chatId)).text("Заметка слишком длинная! Попробуйте сократить!").build());
	}

	public static List<PartialBotApiMethod<Message>> noteListEmpty(Long chatId) {
		return List.of(SendMessage.builder().chatId(String.valueOf(chatId)).text("Ваш список заметок пуст!\n\nСоздать новую заметку можно с помощью команды /newnote").build());
	}

	public static List<PartialBotApiMethod<Message>> longNoteName(Long chatId) {
		return List.of(SendMessage.builder().chatId(String.valueOf(chatId)).text("Название заметки должно быть не более 150 символов!").build());
	}

	public static List<PartialBotApiMethod<Message>> notBotInitMessage(Long chatId) {
		return List.of(SendMessage.builder().chatId(String.valueOf(chatId)).text("Используй /start чтобы начать использовать бота!").build());
	}

	public static List<PartialBotApiMethod<Message>> setNameForNewNote(Long chatId) {
		return List.of(SendMessage.builder().chatId(String.valueOf(chatId)).text("Как назовем новую заметку?").build());
	}

	public static List<PartialBotApiMethod<Message>> noteWasDeleted(Long chatId) {
		return List.of(SendMessage.builder().text("Эта заметка удалена!").chatId(String.valueOf(chatId)).build());
	}
}

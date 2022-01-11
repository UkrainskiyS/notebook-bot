package com.example.notebookbot.utilits;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;

public class DefaultMessage {

	public static List<BotApiMethod<Message>> setTextForNewNote(Long chatId, String name) {
		SendMessage message = SendMessage.builder().chatId(String.valueOf(chatId)).text(String.format("Хорошо, а теперь добавь содержание для _%s_!", name)).build();
		message.enableMarkdown(true);
		return List.of(message);
	}

	public static List<BotApiMethod<Message>> noteNameExist(Long chatId, String name) {
		SendMessage message = SendMessage.builder().chatId(String.valueOf(chatId)).text(String.format("Хмм... Кажется заметка с именем _%s_ уже существует.", name)).build();
		message.enableMarkdown(true);
		return List.of(message);
	}

	public static List<BotApiMethod<Message>> newNoteCreated(Long chatId, String name) {
		SendMessage message = SendMessage.builder().chatId(String.valueOf(chatId)).text(String.format("Новая заметка _%s_ успешно создана!", name)).build();
		message.enableMarkdown(true);
		return List.of(message);
	}

	public static List<BotApiMethod<Message>> noteDeleted(Long chatId, String name) {
		SendMessage message = SendMessage.builder().text("Заметка _" + name + "_ успешно удалена!").chatId(String.valueOf(chatId)).build();
		message.enableMarkdown(true);
		return List.of(message);
	}

	public static List<BotApiMethod<Message>> veryLongText(Long chatId) {
		return List.of(SendMessage.builder().chatId(String.valueOf(chatId)).text("Заметка слишком длинная! Попробуйте сократить!").build());
	}

	public static List<BotApiMethod<Message>> noteListEmpty(Long chatId) {
		return List.of(SendMessage.builder().chatId(String.valueOf(chatId)).text("Ваш список заметок пуст!\n\nСоздать новую заметку можно с помощью команды /newnote").build());
	}

	public static List<BotApiMethod<Message>> longNoteName(Long chatId) {
		return List.of(SendMessage.builder().chatId(String.valueOf(chatId)).text("Название заметки должно быть не более 60 символов!").build());
	}

	public static List<BotApiMethod<Message>> notBotInitMessage(Long chatId) {
		return List.of(SendMessage.builder().chatId(String.valueOf(chatId)).text("Используй /start чтобы начать использовать бота!").build());
	}

	public static List<BotApiMethod<Message>> setNameForNewNote(Long chatId) {
		return List.of(SendMessage.builder().chatId(String.valueOf(chatId)).text("Как назовем новую заметку?").build());
	}
}

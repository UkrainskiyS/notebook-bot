package com.example.notebookbot.utilits;

import com.example.notebookbot.telegram.MessageBuilder;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;

public class DefaultMessage {
	public static List<SendMessage> notBotInitMessage(Long chatId) {
		return List.of(new MessageBuilder().fastBuild(chatId, "Используй /start чтобы начать использовать бота!"));
	}

	public static List<SendMessage> setNameForNewNote(Long chatId) {
		return List.of(new MessageBuilder().fastBuild(chatId, "Как назовем новую заметку?"));
	}

	public static List<SendMessage> setTextForNewNote(Long chatId, String name) {
		return List.of(
				new MessageBuilder().fastBuild(chatId, String.format("Хорошо, а теперь добавь содержание для _%s_!", name))
		);
	}

	public static List<SendMessage> badNoteName(Long chatId, String name) {
		return List.of(
				new MessageBuilder().fastBuild(chatId, String.format("Хмм... Кажется заметка с именем _%s_ уже существует.", name))
		);
	}

	public static List<SendMessage> newNoteCreated(Long chatId, String name) {
		return List.of(
				new MessageBuilder().fastBuild(chatId, String.format("Новая заметка _%s_ успешно создана!", name))
		);
	}

	public static List<SendMessage> veryLongText(Long chatId) {
		return List.of(new MessageBuilder().fastBuild(chatId, "Заметка слишком длинная! Попробуйте сократить!"));
	}
}

package com.example.notebookbot.utilits;

import com.example.notebookbot.persist.note.model.Note;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;
import java.util.stream.Collectors;

/*
* Класс для вывод /getnote, /showall
 */

public class NotePrinter {

	public static List<PartialBotApiMethod<Message>> getMessageAllNotes(Long chatId, List <Note> noteList) {
		return List.of(
				SendMessage.builder().chatId(String.valueOf(chatId))
						.parseMode(ParseMode.MARKDOWN)
						.text(noteList.stream()
								.map(note -> "- `" + note.getName() + "`,\t\tобновлена `" + note.getDateString() + "`")
								.collect(Collectors.joining("\n", "*Все заметки*:\n", "")))
						.build()
		);
	}

	public static List<PartialBotApiMethod<Message>> getMessageOneNote(Long chatId, Note note) {
		return List.of(
				SendMessage.builder()
						.parseMode(ParseMode.HTML)
						.chatId(String.valueOf(chatId))
						.text(String.format("<code>%s</code>:\n\n%s", note.getName(), note.getText()))
						.build()
		);
	}
}

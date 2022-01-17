package com.example.notebookbot.service.handlers.callback.commands;

import com.example.notebookbot.config.BotConfig;
import com.example.notebookbot.persist.chat.ChatManager;
import com.example.notebookbot.persist.chat.ChatMode;
import com.example.notebookbot.persist.note.model.Note;
import com.example.notebookbot.persist.note.repository.NoteRepository;
import com.example.notebookbot.service.handlers.callback.AbstractCallBack;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;

public class EditNoteCallBackHandler extends AbstractCallBack {
	private final BotConfig config;

	public EditNoteCallBackHandler(Message message, ChatManager chatManager, NoteRepository noteRepository, BotConfig botConfig, String data) {
		super(message, chatManager, noteRepository, data);
		this.config = botConfig;
	}

	@Override
	public List<PartialBotApiMethod<Message>> execute() {
		Note note = super.noteRepository.findById(super.noteId);
		String text = "Для редактирования заметки откройте ссылку " +
				config.getHost() + "/edit?note=" + note.getUuid() + " в браузере!";

		chatManager.setMode(message.getChatId(), ChatMode.IGNORED);
		return List.of(SendMessage.builder()
				.chatId(String.valueOf(message.getChatId()))
				.text(text)
				.parseMode(ParseMode.HTML)
				.disableWebPagePreview(true)
				.build()
		);
	}
}

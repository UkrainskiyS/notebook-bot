package com.example.notebookbot.service.handlers.message.commands;

import com.example.notebookbot.persist.chat.ChatManager;
import com.example.notebookbot.persist.chat.ChatMode;
import com.example.notebookbot.persist.note.model.Note;
import com.example.notebookbot.persist.note.repository.NoteRepository;
import com.example.notebookbot.service.handlers.message.AbstractMessageHandler;
import com.example.notebookbot.utilits.DefaultMessage;
import com.example.notebookbot.utilits.TmeButtons;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.List;
import java.util.Optional;

public class DeleteHandler extends AbstractMessageHandler {

	public DeleteHandler(Message message, ChatManager chatManager, NoteRepository noteRepository) {
		super(message, chatManager, noteRepository);
	}

	@Override
	public List<BotApiMethod<Message>> execute() {
		Optional<List<Note>> optionalNotes = noteRepository.findAllByChatId(message.getChatId());
		// создание ответа для пустого списка заметок
		List<SendMessage> sendMessage = DefaultMessage.noteListEmpty(message.getChatId());

		if (optionalNotes.isPresent() && !optionalNotes.get().isEmpty()) {
			// если заметки есть, конвертируем их в кнопки и отправляем
			InlineKeyboardMarkup markup = new InlineKeyboardMarkup(new TmeButtons().convertToListButtons(optionalNotes.get()));
			sendMessage.get(0).setReplyMarkup(markup);
			sendMessage.get(0).setText("Какую заметку удалить?");
			chatManager.setMode(message.getChatId(), ChatMode.DEL_NOTE);
		}

		return sendMessage;
	}
}

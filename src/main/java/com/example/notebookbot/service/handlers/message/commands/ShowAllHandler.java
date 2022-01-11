package com.example.notebookbot.service.handlers.message.commands;

import com.example.notebookbot.persist.chat.ChatManager;
import com.example.notebookbot.persist.note.model.Note;
import com.example.notebookbot.persist.note.repository.NoteRepository;
import com.example.notebookbot.service.handlers.message.AbstractMessageHandler;
import com.example.notebookbot.utilits.DefaultMessage;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ShowAllHandler extends AbstractMessageHandler {

	public ShowAllHandler(Message message, ChatManager chatManager, NoteRepository noteRepository) {
		super(message, chatManager, noteRepository);
	}

	@Override
	public List<BotApiMethod<Message>> execute() {
		Optional<List<Note>> optionalNotes = noteRepository.findAllByChatId(message.getChatId());

		if (optionalNotes.isEmpty() || optionalNotes.get().size() == 0) {
			return DefaultMessage.noteListEmpty(message.getChatId());

		} else {
			SendMessage sendMessage = SendMessage.builder()
					.text(optionalNotes.get().stream()
									.map(Note::getName)
									.collect(Collectors.joining("```\n- ```", "*Все заметки:*\n- ```", "```")))
					.chatId(String.valueOf(message.getChatId())).build();

			sendMessage.enableMarkdown(true);
			return List.of(sendMessage);
		}
	}
}

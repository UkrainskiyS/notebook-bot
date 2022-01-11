package com.example.notebookbot.service.handlers.callback.commands;

import com.example.notebookbot.persist.chat.ChatManager;
import com.example.notebookbot.persist.chat.ChatMode;
import com.example.notebookbot.persist.note.model.Note;
import com.example.notebookbot.persist.note.repository.NoteRepository;
import com.example.notebookbot.service.handlers.callback.AbstractCallBack;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class GetFileCallBackHandler extends AbstractCallBack {

	public GetFileCallBackHandler(Message message, ChatManager chatManager, NoteRepository noteRepository, String data) {
		super(message, chatManager, noteRepository, data);
	}

	@Override
	public List<PartialBotApiMethod<Message>> execute() {
		Note note = noteRepository.findById(noteId);
		chatManager.setMode(message.getChatId(), ChatMode.IGNORED);

		InputStream inputStream = new ByteArrayInputStream(note.getText().getBytes(StandardCharsets.UTF_8));

		return List.of(SendDocument.builder()
				.chatId(String.valueOf(message.getChatId()))
				.document(new InputFile(inputStream, note.getName() + ".txt"))
				.build());
	}
}

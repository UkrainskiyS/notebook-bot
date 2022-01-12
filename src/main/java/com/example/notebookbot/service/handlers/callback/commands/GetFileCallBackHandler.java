package com.example.notebookbot.service.handlers.callback.commands;

import com.example.notebookbot.persist.chat.ChatManager;
import com.example.notebookbot.persist.chat.ChatMode;
import com.example.notebookbot.persist.note.model.Note;
import com.example.notebookbot.persist.note.repository.NoteRepository;
import com.example.notebookbot.service.handlers.callback.AbstractCallBack;
import com.example.notebookbot.utilits.DefaultMessage;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
public class GetFileCallBackHandler extends AbstractCallBack {

	public GetFileCallBackHandler(Message message, ChatManager chatManager, NoteRepository noteRepository, String data) {
		super(message, chatManager, noteRepository, data);
	}

	@Override
	public List<PartialBotApiMethod<Message>> execute() {
		Note note = noteRepository.findById(noteId);
		// Если note = null, значит юзер нажимает на кнопку с этой заметкой после ее удаления
		if (note == null) {
			return DefaultMessage.noteWasDeleted(message.getChatId());
		}

		chatManager.setMode(message.getChatId(), ChatMode.IGNORED);
		// Преобразование заметки в поток и создание обЪекта документа
		InputStream inputStream = new ByteArrayInputStream(note.getText().getBytes(StandardCharsets.UTF_8));

		log.debug("Command /getfile execute");
		return List.of(SendDocument.builder()
				.chatId(String.valueOf(message.getChatId()))
				.document(new InputFile(inputStream, note.getName() + ".txt"))
				.build());
	}
}

package com.example.notebookbot.service.handlers.callback.commands;

import com.example.notebookbot.persist.chat.ChatManager;
import com.example.notebookbot.persist.chat.ChatMode;
import com.example.notebookbot.persist.note.model.Note;
import com.example.notebookbot.persist.note.repository.NoteRepository;
import com.example.notebookbot.service.handlers.callback.AbstractCallBack;
import com.example.notebookbot.utilits.DefaultMessage;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;

@Slf4j
public class DeleteCallBackHandler extends AbstractCallBack {

	public DeleteCallBackHandler(Message message, ChatManager chatManager, NoteRepository noteRepository, String data) {
		super(message, chatManager, noteRepository, data);
	}

	@Override
	public List<PartialBotApiMethod<Message>> execute() {
		Note note = noteRepository.findById(noteId);
		noteRepository.delete(note);

		chatManager.setMode(message.getChatId(), ChatMode.IGNORED);
		log.debug("Command /deletenote execute");
		return DefaultMessage.noteDeleted(message.getChatId(), note.getName());
	}
}

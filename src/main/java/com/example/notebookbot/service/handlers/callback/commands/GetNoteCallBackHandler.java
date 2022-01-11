package com.example.notebookbot.service.handlers.callback.commands;

import com.example.notebookbot.persist.chat.ChatManager;
import com.example.notebookbot.persist.chat.ChatMode;
import com.example.notebookbot.persist.note.model.Note;
import com.example.notebookbot.persist.note.repository.NoteRepository;
import com.example.notebookbot.service.handlers.callback.AbstractCallBack;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;

public class GetNoteCallBackHandler extends AbstractCallBack {

    public GetNoteCallBackHandler(Message message, ChatManager chatManager, NoteRepository noteRepository, String data) {
        super(message, chatManager, noteRepository, data);
    }

    @Override
    public List<SendMessage> execute() {
        Note note = noteRepository.findById(noteId);
        chatManager.setMode(message.getChatId(), ChatMode.IGNORED);

        SendMessage sendMessage = SendMessage.builder().chatId(String.valueOf(message.getChatId()))
                .text(String.format("*%s*:\n\n%s", note.getName(), note.getText())).build();
        sendMessage.enableMarkdown(true);

        return List.of(sendMessage);
    }
}

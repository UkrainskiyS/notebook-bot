package com.example.notebookbot.service.handlers.callback.commands;

import com.example.notebookbot.persist.chat.ChatManager;
import com.example.notebookbot.persist.chat.ChatMode;
import com.example.notebookbot.persist.note.model.Note;
import com.example.notebookbot.persist.note.repository.NoteRepository;
import com.example.notebookbot.service.handlers.AbstractHandler;
import com.example.notebookbot.telegram.MessageBuilder;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;

public class GetNoteCallBackHandler extends AbstractHandler {
    private final NoteRepository noteRepository;
    private final String data;

    public GetNoteCallBackHandler(Message message, ChatManager chatManager, NoteRepository noteRepository, String data) {
        super(message, chatManager);
        this.noteRepository = noteRepository;
        this.data = data;
    }

    @Override
    public List<SendMessage> execute() {
        Note note = noteRepository.findById(Integer.parseInt(data));
        chatManager.setMode(message.getChatId(), ChatMode.IGNORED);

        return List.of(
                new MessageBuilder().fastBuild(message.getChatId(), String.format("*%s*:\n\n%s", note.getName(), note.getText()))
        );
    }
}

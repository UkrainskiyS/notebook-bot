package com.example.notebookbot.service.handlers;

import com.example.notebookbot.persist.chat.ChatManager;
import com.example.notebookbot.persist.note.repository.NoteRepository;
import lombok.AllArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.Message;

@AllArgsConstructor
public abstract class AbstractHandlerFactory {
    protected final ChatManager chatManager;
    protected final NoteRepository noteRepository;
    protected final Message message;

    public abstract AbstractHandler getHandler();
}

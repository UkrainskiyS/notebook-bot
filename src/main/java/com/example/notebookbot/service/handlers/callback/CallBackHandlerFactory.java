package com.example.notebookbot.service.handlers.callback;

import com.example.notebookbot.persist.chat.ChatManager;
import com.example.notebookbot.persist.chat.ChatMode;
import com.example.notebookbot.persist.note.repository.NoteRepository;
import com.example.notebookbot.service.handlers.AbstractHandler;
import com.example.notebookbot.service.handlers.AbstractHandlerFactory;
import com.example.notebookbot.service.handlers.callback.commands.DeleteCallBackHandler;
import com.example.notebookbot.service.handlers.callback.commands.GetFileCallBackHandler;
import com.example.notebookbot.service.handlers.callback.commands.GetNoteCallBackHandler;
import com.example.notebookbot.service.handlers.callback.commands.editnote.EditNoteCallBackHandler;
import org.telegram.telegrambots.meta.api.objects.Message;

public class CallBackHandlerFactory extends AbstractHandlerFactory {
    private final String data;

    public CallBackHandlerFactory(ChatManager chatManager, NoteRepository noteRepository, Message message, ChatMode mode, String data) {
        super(chatManager, noteRepository, message, mode);
        this.data = data;
    }

    @Override
    public AbstractHandler getHandler() {
        switch (mode) {
            case GET_NOTE:
            case IGNORED:
                return new GetNoteCallBackHandler(message, chatManager, noteRepository, data);

            case GET_FILE: return new GetFileCallBackHandler(message, chatManager, noteRepository, data);
            case DEL_NOTE: return new DeleteCallBackHandler(message, chatManager, noteRepository, data);
            case EDIT_MODE: return new EditNoteCallBackHandler(message, chatManager, noteRepository, data);
            default: return null;
        }
    }
}

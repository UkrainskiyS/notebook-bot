package com.example.notebookbot.service.handlers.callback;

import com.example.notebookbot.config.BotConfig;
import com.example.notebookbot.persist.chat.ChatManager;
import com.example.notebookbot.persist.chat.ChatMode;
import com.example.notebookbot.persist.note.repository.NoteRepository;
import com.example.notebookbot.service.handlers.AbstractHandler;
import com.example.notebookbot.service.handlers.AbstractHandlerFactory;
import com.example.notebookbot.service.handlers.callback.commands.DeleteCallBackHandler;
import com.example.notebookbot.service.handlers.callback.commands.GetFileCallBackHandler;
import com.example.notebookbot.service.handlers.callback.commands.GetNoteCallBackHandler;
import com.example.notebookbot.service.handlers.callback.commands.EditNoteCallBackHandler;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

public class CallBackHandlerFactory extends AbstractHandlerFactory {
    private final String data;
    private final BotConfig config;
    private final ChatMode mode;

    public CallBackHandlerFactory(ChatManager chatManager, NoteRepository noteRepository, CallbackQuery callback, BotConfig config) {
        super(chatManager, noteRepository, callback.getMessage());
        this.config = config;
        this.data = callback.getData();
        this.mode = chatManager.getMode(callback.getMessage().getChatId());
    }

    @Override
    public AbstractHandler getHandler() {
        switch (mode) {
            case GET_NOTE:
            case IGNORED:
                return new GetNoteCallBackHandler(message, chatManager, noteRepository, data);

            case GET_FILE: return new GetFileCallBackHandler(message, chatManager, noteRepository, data);
            case DEL_NOTE: return new DeleteCallBackHandler(message, chatManager, noteRepository, data);
            case EDIT_MODE: return new EditNoteCallBackHandler(message, chatManager, noteRepository, config, data);
            default: return null;
        }
    }
}

package com.example.notebookbot.service;

import com.example.notebookbot.persist.chat.ChatManager;
import com.example.notebookbot.persist.note.repository.NoteRepository;
import com.example.notebookbot.service.handlers.AbstractHandler;
import com.example.notebookbot.service.handlers.MessageHandlersFactory;
import com.example.notebookbot.service.handlers.StartHandler;
import com.example.notebookbot.utilits.DefaultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
public class BotService {
    private final ChatManager chatManager;
    private final NoteRepository noteRepository;

    @Autowired
    public BotService(ChatManager chatManager, NoteRepository noteRepository) {
        this.chatManager = chatManager;
        this.noteRepository = noteRepository;
    }

    public List<SendMessage> messageHandler(Message message, Set<String> commands) {
        // условие, при котором бот в текущем чате инициализирован
        if (chatManager.chatExist(message.getChatId())) {
            MessageHandlersFactory factory = new MessageHandlersFactory(chatManager, noteRepository, message, commands);
            AbstractHandler handler = factory.getHandler(chatManager.getMode(message.getChatId()));
            return handler.execute();

            // бот не инициализирован, но пользователь отправляет команду
        } else if (commands.contains(message.getText())) {
            // если команда /start - инициализация бота
            if (message.getText().equals("/start")) {
                StartHandler startHandler = new StartHandler(message, chatManager);
                return startHandler.execute();
            } else {
                // в ином случае отправляем просьбу ввести команду /start
                return DefaultMessage.notBotInitMessage(message.getChatId());
            }
        } else {
            // бот игнорирует сообщение
            return Collections.emptyList();
        }
    }

    public List<SendMessage> callBackQueryHandler(CallbackQuery callbackQuery) {
        return null;
    }
}
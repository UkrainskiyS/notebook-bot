package com.example.notebookbot.service;

import com.example.notebookbot.persist.chat.ChatManager;
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

    @Autowired
    public BotService(ChatManager chatManager) {
        this.chatManager = chatManager;
    }

    public List<SendMessage> messageHandler(Message message, Set<String> commands) {
        if (chatManager.chatExist(message.getChatId())) {

            return null;

        } else if (commands.contains(message.getText())) {

            if (message.getText().equals("/start")) {
                StartHandler startHandler = new StartHandler(message, chatManager);
                return startHandler.execute();
            } else {
                return DefaultMessage.notBotInitMessage(message.getChatId());
            }

        } else {
            return Collections.emptyList();
        }
    }

    public List<SendMessage> callBackQueryHandler(CallbackQuery callbackQuery) {
        return null;
    }
}
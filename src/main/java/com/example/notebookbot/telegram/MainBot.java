package com.example.notebookbot.telegram;

import com.example.notebookbot.config.BotConfig;
import com.example.notebookbot.service.BotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@Component
public class MainBot extends TelegramLongPollingBot {
    private final BotService service;

    // Переменные из файла application.yml
    private final BotConfig config;

    @Autowired
    public MainBot(BotService botService, BotConfig botConfig) {
        this.service = botService;
        this.config = botConfig;
    }

    @Override
    public void onUpdateReceived(Update update) {
        List<SendMessage> messages;
        if (update.hasMessage()) {
            messages = service.messageHandler(update.getMessage(), config.getCommands());
        } else {
            messages = service.callBackQueryHandler(update.getCallbackQuery());
        }

        messages.forEach(this::send);
    }

    private void send(SendMessage message) {
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return config.getName();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }
}

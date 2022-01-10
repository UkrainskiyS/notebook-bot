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
import java.util.Optional;

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
        Optional<List<SendMessage>> messages;
        if (update.hasMessage()) {
            // обрабатывает ввод пользователей и команды
            messages = Optional.ofNullable(service.messageHandler(update.getMessage()));
        } else {
            // обрабатывает кнопки
            messages = Optional.ofNullable(service.callBackQueryHandler(update.getCallbackQuery()));
        }

        messages.ifPresent((list) -> list.forEach(this::send));
    }

    private void send(SendMessage message) {
        try {
            if (message != null) {
                execute(message);
            }
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

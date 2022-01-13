package com.example.notebookbot.telegram;

import com.example.notebookbot.config.BotConfig;
import com.example.notebookbot.service.BotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
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

        // обрабатывает ввод пользователей и команды
        if (update.hasMessage()) {
            Optional.ofNullable(service.messageHandler(update.getMessage()))
                    .ifPresent((list) -> list.forEach(this::send));

            // обрабатывает кнопки
        } else if (update.hasCallbackQuery()){
            Optional.ofNullable(service.callBackQueryHandler(update.getCallbackQuery()))
                    .ifPresent((list) -> list.forEach(this::send));
        }
    }

    private void send(PartialBotApiMethod<Message> messageBotApiMethod) {
        try {
            if (messageBotApiMethod instanceof SendDocument) {
                execute((SendDocument) messageBotApiMethod);
            } else if (messageBotApiMethod instanceof SendMessage) {
                execute((SendMessage) messageBotApiMethod);
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

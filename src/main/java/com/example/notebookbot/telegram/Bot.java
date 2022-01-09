package com.example.notebookbot.telegram;

import com.example.notebookbot.config.BotConfig;
import com.example.notebookbot.model.MessageBuilder;
import com.example.notebookbot.service.BotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class Bot extends TelegramLongPollingBot {
    private final BotService service;

    // Переменные из файла application.yml
    private final BotConfig config;
    // Поле для сохранения имени новой заметки
    private String nameNeNote;
    private boolean newNoteMode;

    @Autowired
    public Bot(BotService botService, BotConfig botConfig) {
        this.service = botService;
        this.config = botConfig;
    }

    @Override
    public void onUpdateReceived(Update update) {
        SendMessage message;
        if (newNoteMode && update.hasMessage()) {
            message = createNewNote(update.getMessage());
        } else {
            message = checkUpdate(update);
        }
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private SendMessage checkUpdate(Update update) {
        SendMessage message;
        if (update.hasMessage()) {
            String[] text = update.getMessage().getText().split("\\s+");
            if (config.getCommands().contains(text[0])) {
                if (text[0].equalsIgnoreCase("/newnote"))
                    message = newMessageMode(text, update.getMessage());
                else
                    message = service.command(update.getMessage());
            } else {
                message = new MessageBuilder().fastBuild(update.getMessage().getChatId(), "noCommand");
            }
        } else {
            message = new MessageBuilder().fastBuild(update.getMessage().getChatId(), "callBack");
        }
        return message;
    }

    private SendMessage newMessageMode(String[] text, Message message) {
        nameNeNote = text[1];
        newNoteMode = true;
        return new MessageBuilder().fastBuild(message.getChatId(),
                "Что будет в заметке _" + nameNeNote + "_?\n\n" +
                        "/cancel для отмены."
        );
    }

    private SendMessage createNewNote(Message message) {
        String text = message.getText();
        newNoteMode = false;
        if (text.strip().equalsIgnoreCase("/cancel")) {
            nameNeNote = null;
            return new MessageBuilder()
                    .fastBuild(message.getChatId(), "Отмена так отмена(");
        } else {
            nameNeNote = null;
            return service.newNote(
                    message.getChatId(), nameNeNote, text
            );
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

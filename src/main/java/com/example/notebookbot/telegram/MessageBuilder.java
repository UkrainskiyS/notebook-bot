package com.example.notebookbot.telegram;

import lombok.NoArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@NoArgsConstructor
public class MessageBuilder {

    /*
    Constructor for little messages
    */

    public SendMessage fastBuild(Long chatId, String text) {
        SendMessage message = new SendMessage(String.valueOf(chatId), text);
        message.enableMarkdown(true);
        return message;
    }

    /*
    Builder for big configuration SendMessage
    */

    private Long chatId;
    private String text;

    public MessageBuilder(Long chatId) {
        this.chatId = chatId;
    }

    public MessageBuilder setChatId(Long chatId) {
        this.chatId = chatId;
        return this;
    }

    public MessageBuilder setText(String text) {
        this.text = text;
        return this;
    }

    public SendMessage build() {
        SendMessage message = new SendMessage(String.valueOf(chatId), text);
        message.enableMarkdown(true);
        return message;
    }
}

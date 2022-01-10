package com.example.notebookbot.telegram;

import lombok.NoArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

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
    private InlineKeyboardMarkup markup;

    public MessageBuilder(Long chatId) {
        this.chatId = chatId;
    }

    public MessageBuilder setChatId(Long chatId) {
        this.chatId = chatId;
        return this;
    }

    public MessageBuilder setMarkup(InlineKeyboardMarkup markup) {
        this.markup = markup;
        return this;
    }

    public MessageBuilder setText(String text) {
        this.text = text;
        return this;
    }

    public SendMessage build() {
        SendMessage message = new SendMessage(String.valueOf(chatId), text);
        message.enableMarkdown(true);
        if (markup != null) {
            message.setReplyMarkup(markup);
        }
        return message;
    }
}

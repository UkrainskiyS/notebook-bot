package com.example.notebookbot.utilits;

import com.example.notebookbot.persist.note.model.Note;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;
import java.util.stream.Collectors;

public class TmeButtons {

    public static List<List<InlineKeyboardButton>> convertToListButtons(List<Note> noteList) {
        return noteList.stream()
                .map(note -> {
                    InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton(note.getName());
                    inlineKeyboardButton.setCallbackData(note.getName());
                    return List.of(inlineKeyboardButton);
                })
                .collect(Collectors.toList());
    }
}

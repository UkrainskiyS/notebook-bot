package com.example.notebookbot.utilits;


import com.example.notebookbot.persist.note.model.Note;

import java.util.List;
import java.util.stream.Collectors;

public class TextMaker {

    public static String messageAboutCreateNote(String name) {
        return String.format(
                "Новая заметка _%s_ успешно создана!", name
        );
    }

    public static String listNotesToString(List<Note> list) {
        return list.stream()
                .map(note -> "*" + note.getName() + "*\n" + note.getText())
                .collect(Collectors.joining("\n", "Все заметки:\n", ""));
    }
}

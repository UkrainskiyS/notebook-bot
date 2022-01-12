package com.example.notebookbot.utilits;

import com.example.notebookbot.persist.note.UpdateMod;
import com.example.notebookbot.persist.note.model.Note;
import one.util.streamex.StreamEx;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

/*
* Класс для создания листов с кнопками
 */

public class TmeButtons {

    public static List<List<InlineKeyboardButton>> getNoteUpdateModButtons(String id) {
        return List.of(List.of(
                InlineKeyboardButton.builder().text("Дописать в конец").callbackData(id + UpdateMod.ADD.name()).build(),
                InlineKeyboardButton.builder().text("Перезаписать").callbackData(id + UpdateMod.OVERWRITE.name()).build()
                ));
    }

    /*
    * Основной метод для создания кнопок с заметками. Сложность заключалась в том, чтобы сделать его универсальным.
    * Я хотел чтобы кнопки выводились в 2 столбца. Если их нечетное колличество - добавить одну кнопку в самый низ во всю ширину.
    * Для того чтобы не придумывать велосипед, решил использовать библиотеку для стримов StreamEx и интересный метод pairMap.
     */

    public static List<List<InlineKeyboardButton>> convertToListButtons(List<Note> noteList) {
        int size = noteList.size();

        if (size == 1) {
            return List.of(List.of(
                    InlineKeyboardButton.builder().text(noteList.get(0).getName()).callbackData(String.valueOf(noteList.get(0).getId())).build()
            ));
        } else if (size % 2 != 0) {
            Note note = noteList.get(size - 1);
            noteList.remove(note);

            var result =  StreamEx.of(noteList).pairMap((note1, note2) -> List.of(
                                InlineKeyboardButton.builder().text(note1.getName()).callbackData(String.valueOf(note1.getId())).build(),
                                InlineKeyboardButton.builder().text(note2.getName()).callbackData(String.valueOf(note2.getId())).build()
            )).toList();

            result.add(List.of(InlineKeyboardButton.builder().text(note.getName()).callbackData(String.valueOf(note.getId())).build()));
            return correct(result);
        } else {
            return correct(StreamEx.of(noteList).pairMap((note, note2) -> List.of(
                    InlineKeyboardButton.builder().text(note.getName()).callbackData(String.valueOf(note.getId())).build(),
                    InlineKeyboardButton.builder().text(note2.getName()).callbackData(String.valueOf(note2.getId())).build()
            )).toList());
        }
    }

    private static List<List<InlineKeyboardButton>> correct(List<List<InlineKeyboardButton>> list) {
        var result = new ArrayList<List<InlineKeyboardButton>>();

        for (int i = 0; i < list.size(); i++) {
            if (i % 2 == 0 || list.get(i).size() == 1) {
                result.add(list.get(i));
            }
        }
        return result;
    }
}

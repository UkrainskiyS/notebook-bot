package com.example.notebookbot.utilits;


import com.example.notebookbot.persist.note.UpdateMod;
import com.example.notebookbot.persist.note.model.Note;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TestUtils {
    private final String NAME = "Telegram";
    private final Long CHAT_ID = 999L;

    @Test
    public void testTextCorrector() {
        assertEquals(TextCorrector.correct("Hello\n <<code>>, <?????>"), "Hello\n `code`, ```???```");
        assertEquals(TextCorrector.correct("<<...>>"), "`...`");
        assertEquals(TextCorrector.correct("<<...?>"), "<<...?>");
    }

    @Test
    public void testNotePrinter() {
        Note[] notes = getNoteArray(3);

        String TEXT = "*Все заметки*:\n" +
                String.format("- `%s`,\t\tобновлена `%s`\n", notes[0].getName(), notes[0].getDateString()) +
                String.format("- `%s`,\t\tобновлена `%s`\n", notes[1].getName(), notes[1].getDateString()) +
                String.format("- `%s`,\t\tобновлена `%s`", notes[2].getName(), notes[2].getDateString());

        var testAllNotes = (SendMessage) NotePrinter.getMessageAllNotes(CHAT_ID, List.of(notes)).get(0);
        assertEquals(testAllNotes.getChatId(), String.valueOf(CHAT_ID));
        assertEquals(testAllNotes.getText(), TEXT);

        notes[0].setText(TEXT);
        var testOneNote = (SendMessage) NotePrinter.getMessageOneNote(CHAT_ID, notes[0]).get(0);
        assertEquals(testOneNote.getChatId(), String.valueOf(CHAT_ID));
        assertEquals(testOneNote.getText(), String.format("`%s`:\n\n%s", notes[0].getName(), notes[0].getText()));
    }

    @Test
    public void testDefaultMessage() {
        testDefMess((SendMessage) DefaultMessage.setTextForNewNote(CHAT_ID, NAME).get(0), "Хорошо, а теперь добавь содержание для _%s_!");
        testDefMess((SendMessage) DefaultMessage.noteNameExist(CHAT_ID, NAME).get(0), "Хмм... Кажется заметка с именем _%s_ уже существует.");
        testDefMess((SendMessage) DefaultMessage.newNoteCreated(CHAT_ID, NAME).get(0), "Новая заметка _%s_ успешно создана!");
        testDefMess((SendMessage) DefaultMessage.noteDeleted(CHAT_ID, NAME).get(0), "Заметка _%s_ успешно удалена!");
        testDefMes((SendMessage) DefaultMessage.veryLongText(CHAT_ID).get(0), "Заметка слишком длинная! Попробуйте сократить!");
        testDefMes((SendMessage) DefaultMessage.noteListEmpty(CHAT_ID).get(0), "Ваш список заметок пуст!\n\nСоздать новую заметку можно с помощью команды /newnote");
        testDefMes((SendMessage) DefaultMessage.longNoteName(CHAT_ID).get(0), "Название заметки должно быть не более 150 символов!");
        testDefMes((SendMessage) DefaultMessage.notBotInitMessage(CHAT_ID).get(0), "Используй /start чтобы начать использовать бота!");
        testDefMes((SendMessage) DefaultMessage.setNameForNewNote(CHAT_ID).get(0), "Как назовем новую заметку?");
        testDefMes((SendMessage) DefaultMessage.noteWasDeleted(CHAT_ID).get(0), "Эта заметка удалена!");
    }

    @Test
    public void testTmeButtons() {
        String ID = "999:";
        var listButtons = TmeButtons.getNoteUpdateModButtons(ID);
        assertEquals(listButtons.get(0).get(0).getText(), "Дописать в конец");
        assertEquals(listButtons.get(0).get(0).getCallbackData(), ID + UpdateMod.ADD.name());
        assertEquals(listButtons.get(1).get(0).getText(), "Перезаписать");
        assertEquals(listButtons.get(1).get(0).getCallbackData(), ID + UpdateMod.OVERWRITE.name());

        testButtons(1);
        testButtons(5);
        testButtons(10);
        testButtons(11);
        testButtons(50);
        testButtons(101);
    }

    void testButtons(int size) {
        Note[] notes = getNoteArray(size);
        var iterate = Arrays.stream(notes).iterator();
        var testButtons = TmeButtons.convertToListButtons(new ArrayList<>(List.of(notes)));

        if (size == 1) {
            testButton(testButtons.get(0).get(0), notes[0]);
            return;
        }

        for (int i = 0; i < size % 2; i++) {
            testButton(testButtons.get(i).get(0), iterate.next());
            testButton(testButtons.get(i).get(1), iterate.next());
        }

        if (size % 2 == 1) {
            InlineKeyboardButton button = testButtons.get(testButtons.size() - 1).get(0);
            testButton(button, notes[notes.length - 1]);
        }
    }

    void testButton(InlineKeyboardButton button, Note note) {
        assertEquals(button.getCallbackData(), String.valueOf(note.getId()));
        assertEquals(button.getText(), note.getName());
    }

    void testDefMess(SendMessage message, String text) {
        assertEquals(message.getText(), String.format(text, NAME));
        assertEquals(message.getChatId(), String.valueOf(CHAT_ID));
    }

    void testDefMes(SendMessage message, String text) {
        assertEquals(message.getText(), text);
        assertEquals(message.getChatId(), String.valueOf(CHAT_ID));
    }

    Note[] getNoteArray(int size) {
        return IntStream.range(0, size)
                .mapToObj(i -> new Note(i, NAME + i, LocalDateTime.now()))
                .toArray(Note[]::new);
    }
}

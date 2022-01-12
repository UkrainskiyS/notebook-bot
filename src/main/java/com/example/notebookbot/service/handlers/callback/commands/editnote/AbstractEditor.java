package com.example.notebookbot.service.handlers.callback.commands.editnote;

import com.example.notebookbot.persist.chat.ChatManager;
import com.example.notebookbot.persist.note.UpdateMod;
import com.example.notebookbot.persist.note.model.Note;
import com.example.notebookbot.persist.note.repository.NoteRepository;
import com.example.notebookbot.service.handlers.AbstractHandler;
import com.example.notebookbot.utilits.NotePrinter;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;

public abstract class AbstractEditor extends AbstractHandler {

    protected final NoteRepository noteRepository;

    public AbstractEditor(Message message, ChatManager chatManager, NoteRepository noteRepository) {
        super(message, chatManager);
        this.noteRepository = noteRepository;
    }

    @Override
    public abstract List<PartialBotApiMethod<Message>> execute();

    /*
    * Методы для отправки старого содержания заметки и сообщения в соответствии с форматом редактирования
     */

    List<PartialBotApiMethod<Message>> addMod(Note note) {
        note.setUpdateMod(UpdateMod.ADD);
        noteRepository.save(note);

        return List.of(
                NotePrinter.getMessageOneNote(message.getChatId(), note).get(0),
                SendMessage.builder().chatId(String.valueOf(message.getChatId())).text("Что добавить в заметку?").build()
        );
    }

    List<PartialBotApiMethod<Message>> overwriteMod(Note note) {
        note.setUpdateMod(UpdateMod.OVERWRITE);
        noteRepository.save(note);

        return List.of(
                NotePrinter.getMessageOneNote(message.getChatId(), note).get(0),
                SendMessage.builder().chatId(String.valueOf(message.getChatId())).text("Новое содержание заметки:").build()
        );
    }


}

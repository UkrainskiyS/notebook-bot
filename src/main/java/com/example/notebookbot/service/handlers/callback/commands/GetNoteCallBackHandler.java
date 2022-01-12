package com.example.notebookbot.service.handlers.callback.commands;

import com.example.notebookbot.persist.chat.ChatManager;
import com.example.notebookbot.persist.chat.ChatMode;
import com.example.notebookbot.persist.note.model.Note;
import com.example.notebookbot.persist.note.repository.NoteRepository;
import com.example.notebookbot.service.handlers.callback.AbstractCallBack;
import com.example.notebookbot.utilits.DefaultMessage;
import com.example.notebookbot.utilits.NotePrinter;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;

public class GetNoteCallBackHandler extends AbstractCallBack {

    public GetNoteCallBackHandler(Message message, ChatManager chatManager, NoteRepository noteRepository, String data) {
        super(message, chatManager, noteRepository, data);
    }

    @Override
    public List<PartialBotApiMethod<Message>> execute() {
        Note note = noteRepository.findById(noteId);
        // Если note = null, значит юзер нажимает на кнопку с этой заметкой после ее удаления
        if (note == null) {
            return DefaultMessage.noteWasDeleted(message.getChatId());
        }

        chatManager.setMode(message.getChatId(), ChatMode.IGNORED);
        return NotePrinter.getMessageOneNote(message.getChatId(), note);
    }
}

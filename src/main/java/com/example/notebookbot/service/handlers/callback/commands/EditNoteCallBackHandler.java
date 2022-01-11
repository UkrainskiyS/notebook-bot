package com.example.notebookbot.service.handlers.callback.commands;

import com.example.notebookbot.persist.chat.ChatManager;
import com.example.notebookbot.persist.chat.ChatMode;
import com.example.notebookbot.persist.note.UpdateMod;
import com.example.notebookbot.persist.note.model.Note;
import com.example.notebookbot.persist.note.repository.NoteRepository;
import com.example.notebookbot.service.handlers.callback.AbstractCallBack;
import com.example.notebookbot.utilits.DefaultMessage;
import com.example.notebookbot.utilits.TmeButtons;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.List;
import java.util.Optional;

public class EditNoteCallBackHandler extends AbstractCallBack {

    public EditNoteCallBackHandler(Message message, ChatManager chatManager, NoteRepository noteRepository, String data) {
        super(message, chatManager, noteRepository, data);
    }

    @Override
    public List<PartialBotApiMethod<Message>> execute() {
        Note note = noteRepository.findById(noteId);
        if (note == null) {
            return DefaultMessage.noteWasDeleted(message.getChatId());
        }

        if (!note.getUpdateMod().equals(UpdateMod.NOT)) {
            switch (note.getUpdateMod()) {
                case ADD: return addMod();
                case OVERWRITE: return overwriteMod();
            }
        }

        chatManager.setMode(message.getChatId(), ChatMode.EDIT_MODE);
        return List.of(SendMessage.builder().chatId(String.valueOf(message.getChatId()))
                .replyMarkup(new InlineKeyboardMarkup(TmeButtons.getNoteUpdateModButtons()))
                .text("Как обновляем?").build());
    }

    private List<PartialBotApiMethod<Message>> addMod() {
        return null;
    }

    private List<PartialBotApiMethod<Message>> overwriteMod() {
        return null;
    }


}

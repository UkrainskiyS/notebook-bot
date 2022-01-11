package com.example.notebookbot.service.handlers.message.commands;

import com.example.notebookbot.persist.chat.ChatManager;
import com.example.notebookbot.persist.chat.ChatMode;
import com.example.notebookbot.persist.note.model.Note;
import com.example.notebookbot.persist.note.repository.NoteRepository;
import com.example.notebookbot.service.handlers.AbstractHandler;
import com.example.notebookbot.utilits.DefaultMessage;
import com.example.notebookbot.utilits.TmeButtons;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.List;
import java.util.Optional;

public class GetNoteHandler extends AbstractHandler {
    private final NoteRepository noteRepository;

    public GetNoteHandler(Message message, ChatManager chatManager, NoteRepository noteRepository) {
        super(message, chatManager);
        this.noteRepository = noteRepository;
    }

    @Override
    public List<SendMessage> execute() {
        Optional<List<Note>> optionalNotes = noteRepository.findAllByChatId(message.getChatId());
        // создание ответа для пустого списка заметок
        List<SendMessage> sendMessage = DefaultMessage.noteListEmpty(message.getChatId());

        if (optionalNotes.isPresent() && !optionalNotes.get().isEmpty()) {
            // если заметки есть, конвертируем их в кнопки и отправляем
            InlineKeyboardMarkup markup = new InlineKeyboardMarkup(new TmeButtons().convertToListButtons(optionalNotes.get()));
            sendMessage.get(0).setReplyMarkup(markup);
            sendMessage.get(0).setText("Какую заметку показать?");
            chatManager.setMode(message.getChatId(), ChatMode.GET_NOTE);
        }

        return sendMessage;
    }
}

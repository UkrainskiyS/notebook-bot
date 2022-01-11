package com.example.notebookbot.service.handlers.message.commands;

import com.example.notebookbot.persist.chat.ChatManager;
import com.example.notebookbot.persist.chat.ChatMode;
import com.example.notebookbot.persist.note.model.Note;
import com.example.notebookbot.persist.note.repository.NoteRepository;
import com.example.notebookbot.service.handlers.message.AbstractMessageHandler;
import com.example.notebookbot.utilits.DefaultMessage;
import com.example.notebookbot.utilits.TmeButtons;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.List;
import java.util.Optional;

public class EditNoteHandler extends AbstractMessageHandler {

    public EditNoteHandler(Message message, ChatManager chatManager, NoteRepository noteRepository) {
        super(message, chatManager, noteRepository);
    }

    @Override
    public List<PartialBotApiMethod<Message>> execute() {
        Optional<List<Note>> optionalNotes = noteRepository.findAllByChatId(message.getChatId());

        if (optionalNotes.isPresent() && !optionalNotes.get().isEmpty()) {
            // если заметки есть, конвертируем их в кнопки и отправляем
            InlineKeyboardMarkup markup = new InlineKeyboardMarkup(TmeButtons.convertToListButtons(optionalNotes.get()));
            chatManager.setMode(message.getChatId(), ChatMode.GET_FILE);
            return List.of(SendMessage.builder().replyMarkup(markup).text("Какую заметку обновить?").chatId(String.valueOf(message.getChatId())).build());
        }

        return DefaultMessage.noteListEmpty(message.getChatId());
    }
}

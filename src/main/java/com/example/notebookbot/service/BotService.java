package com.example.notebookbot.service;

import com.example.notebookbot.telegram.MessageBuilder;
import com.example.notebookbot.persist.note.model.Note;
import com.example.notebookbot.persist.note.repository.NoteRepository;
import com.example.notebookbot.utilits.TextMaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;
import java.util.Optional;

@Service
public class BotService {
    private final NoteRepository repository;

    @Autowired
    public BotService(NoteRepository repository) {
        this.repository = repository;
    }

    public SendMessage command(Message message) {
        Optional<List<Note>> allByChatId = repository.findAllByChatId(message.getChatId());
        MessageBuilder sendMessage = new MessageBuilder(message.getChatId());

        if (allByChatId.isPresent()) {
            return sendMessage.setText(TextMaker.listNotesToString(allByChatId.get())).build();
        } else {
            return sendMessage.setText("У вас нет заметок!").build();
        }
    }

    public SendMessage newNote(Long chatId, String nameNeNote, String text) {
        repository.save(
                Note.builder()
                        .setName(nameNeNote)
                        .setText(text)
                        .setChatId(chatId)
                        .build()
        );
        return new MessageBuilder().fastBuild(chatId, TextMaker.messageAboutCreateNote(nameNeNote));
    }
}
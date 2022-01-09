package com.example.notebookbot.telegram.command;

import com.example.notebookbot.repository.NoteRepository;
import lombok.AllArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@AllArgsConstructor
public abstract class AbstractCommand {
    private final NoteRepository repository;

    abstract SendMessage execute();
}

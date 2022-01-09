package com.example.notebookbot.telegram.command;

import com.example.notebookbot.repository.NoteRepository;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class NewNoteCommand extends AbstractCommand{
    private String name;
    private String text;

    public NewNoteCommand(NoteRepository repository) {
        super(repository);
    }

    @Override
    SendMessage execute() {
        return null;
    }
}

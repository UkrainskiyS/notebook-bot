package com.example.notebookbot.controller;

import com.example.notebookbot.exceptions.NotFoundException;
import com.example.notebookbot.persist.chat.ChatManager;
import com.example.notebookbot.persist.note.model.Note;
import com.example.notebookbot.persist.note.repository.NoteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@AllArgsConstructor
public class MVCBot {
    private final NoteRepository noteRepository;
    private final ChatManager chatManager;

    @GetMapping("/new")
    public String add(@RequestParam String chat) {
        if (chatManager.existByUuid(chat)) {
            return "new";
        } else {
            throw new NotFoundException("Please, generate new link for create note!");
        }
    }

    @GetMapping("/edit")
    public String edit(@RequestParam String note, Model model) {
        Optional<Note> optionalNote = noteRepository.findByUuid(note);
        if (optionalNote.isPresent()) {
            model.addAttribute("note", optionalNote.get());
            return "edit";
        } else {
            throw new NotFoundException("Please, generate new link for edit note!");
        }
    }
}

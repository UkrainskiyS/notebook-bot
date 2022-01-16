package com.example.notebookbot.controller;

import com.example.notebookbot.persist.note.model.Note;
import com.example.notebookbot.persist.note.repository.NoteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
public class MVCBot {
    private final NoteRepository noteRepository;

    @GetMapping("/new")
    public String add() {
        return "new";
    }

    @GetMapping("/edit")
    public String edit(@RequestParam Integer id, Model model) {
        Note note = noteRepository.getById(id);
        model.addAttribute("note", note);
        return "edit";
    }
}

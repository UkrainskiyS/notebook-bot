package com.example.notebookbot.controller;

import com.example.notebookbot.persist.note.UpdateMod;
import com.example.notebookbot.persist.note.model.Note;
import com.example.notebookbot.persist.note.repository.NoteRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;
import java.util.Optional;

@RestController
@AllArgsConstructor
public class RESTBot {
    private final NoteRepository noteRepository;

    @PutMapping("/edit")
    public void edit(@RequestBody Map<String, String> body, HttpServletResponse response) {
        Optional<Note> note = Optional.ofNullable(noteRepository.findById(Integer.parseInt(body.get("id"))));
        if (note.isPresent()) {
            note.get().setText(body.get("text").replace("**", "*"));
            noteRepository.save(note.get());
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @PostMapping("/new")
    public void save(@RequestBody @Valid Note note, HttpServletResponse response) {
        if (noteRepository.existsByChatIdAndName(note.getChatId(), note.getName())) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } else {
            note.setText(note.getText().replace("**", "*"));
            note.setDate(LocalDateTime.now(ZoneId.of("Europe/Moscow")));
            note.setUpdateMod(UpdateMod.NOT);
            noteRepository.save(note);
            response.setStatus(HttpServletResponse.SC_OK);
        }
    }
}

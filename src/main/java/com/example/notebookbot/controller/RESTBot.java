package com.example.notebookbot.controller;

import com.example.notebookbot.persist.chat.ChatManager;
import com.example.notebookbot.persist.chat.model.Chat;
import com.example.notebookbot.persist.note.model.Note;
import com.example.notebookbot.persist.note.repository.NoteRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Map;
import java.util.Optional;

@RestController
@AllArgsConstructor
public class RESTBot {
    private final NoteRepository noteRepository;
    private final ChatManager chatManager;

    @PutMapping("/edit")
    public void edit(@RequestBody Map<String, String> params, HttpServletResponse response) {
        Optional<Note> note = noteRepository.findByUuid(params.get("uuid"));
        if (note.isPresent()) {
            noteRepository.save(note.get().update(params.get("text").replace("**", "*")));
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @PostMapping("/new")
    public void save(@RequestBody @Valid Map<String, String> param, HttpServletResponse response) {
        Optional<Chat> chat = chatManager.getByUuid(param.get("uuid"));
        if (chat.isPresent()) {
            if (noteRepository.existsByChatIdAndName(chat.get().getChatId(), param.get("name"))) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            } else {
                Note note = new Note(chat.get().getChatId(), param.get("name"));
                noteRepository.save(note.update(param.get("text").replace("**", "***")));
                chatManager.updateUuid(chat.get());
                response.setStatus(HttpServletResponse.SC_OK);
            }
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}

package com.example.notebookbot.repository;

import com.example.notebookbot.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NoteRepository extends JpaRepository<Note, Integer> {
    Optional<List<Note>> findAllByChatId(Long chatId);
    Optional<Note> findByChatIdAndName(Long chatId, String name);
    boolean existsByChatIdAndName(Long chatId, String name);
}

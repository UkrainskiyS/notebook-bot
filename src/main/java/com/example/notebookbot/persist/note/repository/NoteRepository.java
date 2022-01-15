package com.example.notebookbot.persist.note.repository;

import com.example.notebookbot.persist.note.UpdateMod;
import com.example.notebookbot.persist.note.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NoteRepository extends JpaRepository<Note, Integer> {
    List<Note> getAllByChatId(Long chatId);

    Optional<List<Note>> findAllByChatIdOrderById(Long chatId);

    Note findById(int id);

    Note findByNameAndChatId(String name, Long chatId);

    Optional<Note> findByChatIdAndUpdateModNot(Long chatId, UpdateMod mod);

    boolean existsByChatIdAndName(Long chatId, String name);
}

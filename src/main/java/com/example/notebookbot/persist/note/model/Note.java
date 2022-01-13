package com.example.notebookbot.persist.note.model;

import com.example.notebookbot.persist.note.UpdateMod;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "notes")
public class Note {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "chat_id")
    private Long chatId;
    private String name;
    private LocalDateTime date;
    private String text;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "update_mod")
    private UpdateMod updateMod;

    public Note(Long chatId, String name, UpdateMod updateMod) {
        this.chatId = chatId;
        this.name = name;
        this.updateMod = updateMod;
        this.date = LocalDateTime.now();
    }

    public String getDateString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm:ss dd.MM.yyyy");
        return date.format(formatter);
    }

    // for tests
    public Note(int id, String name, LocalDateTime date) {
        this.id = id;
        this.name = name;
        this.date = date;
    }
}

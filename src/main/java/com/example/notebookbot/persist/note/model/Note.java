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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "chat_id")
    private Long chatId;
    private String name;
    private LocalDateTime date;
    private String text;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "update_mod")
    private UpdateMod updateMod;

    public Note(Long chatId, String name, LocalDateTime date, UpdateMod updateMod) {
        this.chatId = chatId;
        this.name = name;
        this.date = date;
        this.updateMod = updateMod;
    }

    public String getDateString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss dd.MM.yyyy");
        return date.format(formatter);
    }
}

package com.example.notebookbot.persist.note.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

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

    @NotNull
    @Column(name = "chat_id")
    private Long chatId;

    @NotNull
    private String name;

    private LocalDateTime date;

    @NotNull
    private String text;

    private String uuid;


    public Note(Long chatId, String name) {
        this.chatId = chatId;
        this.name = name;
    }

    public Note update(String text) {
        this.text = text;
        this.date = LocalDateTime.now(ZoneId.of("Europe/Moscow"));
        this.uuid = this.chatId + "-" + UUID.randomUUID();
        return this;
    }

    public String getDateString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm:ss dd.MM.yyyy");
        return date.format(formatter);
    }
}

package com.example.notebookbot.persist.note.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

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
    private String text;

    public Note(Long chatId, String name) {
        this.chatId = chatId;
        this.name = name;
    }
}

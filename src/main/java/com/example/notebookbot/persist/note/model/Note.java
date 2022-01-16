package com.example.notebookbot.persist.note.model;

import com.example.notebookbot.persist.note.UpdateMod;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

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

    @Enumerated(value = EnumType.STRING)
    @Column(name = "update_mod")
    private UpdateMod updateMod;

    public Note(Long chatId, String name, UpdateMod updateMod) {
        this.chatId = chatId;
        this.name = name;
        this.updateMod = updateMod;
        this.date = LocalDateTime.now(ZoneId.of("Europe/Moscow"));
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note = (Note) o;
        return Objects.equals(chatId, note.chatId) && Objects.equals(name, note.name) && Objects.equals(text, note.text) && updateMod == note.updateMod;
    }

    @Override
    public int hashCode() {
        return Objects.hash(chatId, name, text, updateMod);
    }
}

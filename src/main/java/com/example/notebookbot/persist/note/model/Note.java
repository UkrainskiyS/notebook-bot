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

    public static Builder builder() {
        return new Note().new Builder();
    }

    // Builder for Note
    public class Builder {
        private Builder() {}

        public Builder setChatId(Long chatId) {
            Note.this.chatId = chatId;
            return this;
        }

        public Builder setName(String name) {
            Note.this.name = name;
            return this;
        }

        public Builder setText(String text) {
            Note.this.text = text;
            return this;
        }

        public Note build() {
            return Note.this;
        }
    }
}
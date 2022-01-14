package com.example.notebookbot.persist.chat.model;

import com.example.notebookbot.persist.chat.ChatMode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "chats")
public class Chat {
	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "chat_id")
	private Long chatId;

	@Enumerated(value = EnumType.STRING)
	private ChatMode mode;

	public Chat(Long chatId, ChatMode mode) {
		this.chatId = chatId;
		this.mode = mode;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Chat chat = (Chat) o;
		return chatId.equals(chat.chatId) && mode == chat.mode;
	}

	@Override
	public int hashCode() {
		return Objects.hash(chatId, mode);
	}
}

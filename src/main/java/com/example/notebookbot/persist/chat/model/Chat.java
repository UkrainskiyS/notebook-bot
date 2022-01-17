package com.example.notebookbot.persist.chat.model;

import com.example.notebookbot.persist.chat.ChatMode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

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

	private String uuid;

	public Chat(Long chatId) {
		this.chatId = chatId;
		this.mode = ChatMode.IGNORED;
		this.uuid = UUID.randomUUID().toString();
	}

	public Chat update() {
		this.uuid = UUID.randomUUID().toString();
		return this;
	}
}

package com.example.notebookbot.persist.chat.model;

import com.example.notebookbot.persist.chat.ChatMode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "chats")
public class Chat {
	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(name = "chat_id")
	private Long chatId;

	@Enumerated(value = EnumType.STRING)
	private ChatMode mode;

	public Chat(Long chatId, ChatMode mode) {
		this.chatId = chatId;
		this.mode = mode;
	}
}

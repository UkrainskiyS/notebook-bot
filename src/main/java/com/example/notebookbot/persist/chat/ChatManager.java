package com.example.notebookbot.persist.chat;

import com.example.notebookbot.persist.chat.model.Chat;
import com.example.notebookbot.persist.chat.repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ChatManager {
	private final ChatRepository repository;

	@Autowired
	public ChatManager(ChatRepository repository) {
		this.repository = repository;
	}

	public void setMode(Long chatId, ChatMode mode) {
		Chat chat = repository.getChatByChatId(chatId);
		chat.setMode(mode);
		repository.save(chat);
	}

	public Chat getChat(Long chatId) {
		return repository.getChatByChatId(chatId);
	}

	public ChatMode getMode(Long chatId) {
		return repository.getChatByChatId(chatId).getMode();
	}

	public void saveChat(Long chatId) {
		repository.save(new Chat(chatId, ChatMode.IGNORED));
	}

	public boolean chatExist(Long chatId) {
		return repository.existsByChatId(chatId);
	}
}

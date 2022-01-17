package com.example.notebookbot.persist.chat;

import com.example.notebookbot.persist.chat.model.Chat;
import com.example.notebookbot.persist.chat.repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

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

	public boolean existByUuid(String uuid) {
		return repository.existsByUuid(uuid);
	}

	public void updateUuid(Chat chat) {
		chat.setUuid(UUID.randomUUID().toString());
		repository.save(chat);
	}

	public Optional<Chat> getByUuid(String uuid) {
		return Optional.ofNullable(repository.getByUuid(uuid));
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

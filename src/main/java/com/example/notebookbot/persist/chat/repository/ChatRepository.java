package com.example.notebookbot.persist.chat.repository;

import com.example.notebookbot.persist.chat.model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Integer> {
	boolean existsByChatId(Long chatId);
	boolean existsByUuid(String uuid);
	Chat getByUuid(String uuid);
	Chat getChatByChatId(Long chatId);
}

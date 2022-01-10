package com.example.notebookbot.persist.chat.repository;

import com.example.notebookbot.persist.chat.ChatMode;
import com.example.notebookbot.persist.chat.model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Integer> {
	ChatMode getModeByChatId(Long chatId);
	boolean existsByChatId(Long chatId);
	Chat getChatByChatId(Long chatId);
}

package com.example.notebookbot.service;

import com.example.notebookbot.persist.chat.ChatMode;
import com.example.notebookbot.persist.chat.model.Chat;
import com.example.notebookbot.persist.chat.repository.ChatRepository;
import com.example.notebookbot.utilits.DefaultMessage;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.opentest4j.AssertionFailedError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
public class TestBotService {
	@Autowired
	private Utils utils;
	@Autowired
	private final ChatRepository chatRepository = Mockito.mock(ChatRepository.class);

	private final String NAME = "Telegram";
	private final long CHAT_ID_1 = 999L;
	private final long CHAT_ID_2 = 111L;
	private final Chat CHAT_1 = new Chat(CHAT_ID_1, ChatMode.IGNORED);
	private final Chat CHAT_2 = new Chat(CHAT_ID_2, ChatMode.IGNORED);

	@Test
	@Order(1)
	public void testCorrectInitBot() {
		assertEquals(utils.sendMessage(CHAT_ID_1, "/help"), DefaultMessage.notBotInitMessage(CHAT_ID_1));
		assertNull(utils.sendMessage(CHAT_ID_1, "/helpp"));
		assertNull(utils.sendMessage(CHAT_ID_1, "help"));
		assertNull(utils.sendCallBack(CHAT_ID_1, "java", "1"));

		SendMessage message = (SendMessage) utils.sendMessage(CHAT_ID_1, "/start").get(0);
		assertEquals(message.getText(), "Привет, чат!");
		assertEquals(message.getChatId(), String.valueOf(CHAT_ID_1));
		assertEquals(chatRepository.getChatByChatId(CHAT_ID_1), CHAT_1);

		assertEquals(((SendMessage) utils.sendMessage(CHAT_ID_1, "/start").get(0)).getText(), "Я ужу в чате!");
		log.info("Test command [/start] completed successfully!");
	}

	@Test
	@Order(2)
	public void testNewNotes() {
		var notes = utils.createNoteList(10, CHAT_ID_1, NAME);
		assertEquals(notes, utils.getNoteRepository().getAllByChatId(CHAT_ID_1));
		assertThrows(AssertionFailedError.class, () -> utils.createNote(notes.get(0).getChatId(), notes.get(2).getName()));
		log.info("Test command [/newnote] completed successfully!");
	}

	@Test
	@Order(3)
	public void testGetNote() {

	}
}

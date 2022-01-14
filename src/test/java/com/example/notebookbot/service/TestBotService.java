package com.example.notebookbot.service;

import com.example.notebookbot.persist.chat.ChatMode;
import com.example.notebookbot.persist.chat.model.Chat;
import com.example.notebookbot.persist.chat.repository.ChatRepository;
import com.example.notebookbot.persist.note.UpdateMod;
import com.example.notebookbot.persist.note.model.Note;
import com.example.notebookbot.utilits.DefaultMessage;
import com.example.notebookbot.utilits.TmeButtons;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.opentest4j.AssertionFailedError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
public class TestBotService {
	@Autowired
	private Utils utils;
	@Autowired
	private final ChatRepository chatRepository = Mockito.mock(ChatRepository.class);

	private final String NAME = "Telegram";
	private final String TEXT = "private final ChatRepository chatRepository = Mockito.mock(ChatRepository.class);";
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
	public void testCommands() {
		// /newnote
		var notes = utils.createNoteList(10, CHAT_ID_1, NAME);
		assertEquals(notes, utils.getNoteRepository().getAllByChatId(CHAT_ID_1));
		assertThrows(AssertionFailedError.class, () -> utils.createNote(notes.get(0).getChatId(), notes.get(2).getName()));
		log.info("Test command [/newnote] completed successfully!");

		// /getnote
		var actualButtons = SendMessage.builder().text("Какую заметку показать?")
				.replyMarkup(new InlineKeyboardMarkup(TmeButtons.convertToListButtons(utils.getAllFromNoteRepo(CHAT_ID_1))))
				.chatId(CHAT_ID_1 + "").build();
		assertEquals(utils.sendMessage(notes.get(0).getChatId(), "/getnote").get(0), actualButtons);
		assertEquals(chatRepository.getChatByChatId(CHAT_ID_1).getMode(), ChatMode.GET_NOTE);
		assertEquals(((SendMessage) utils.sendCallBack(CHAT_ID_1, notes.get(0).getName(), String.valueOf(utils.getNote(CHAT_ID_1, notes.get(0).getName()).getId())).get(0)),
				SendMessage.builder().text(String.format("`%s`:\n\n%s", notes.get(0).getName(), notes.get(0).getText()))
						.chatId(String.valueOf(CHAT_ID_1)).parseMode(ParseMode.MARKDOWN).build());
		assertEquals(chatRepository.getChatByChatId(CHAT_ID_1).getMode(), ChatMode.IGNORED);
		log.info("Test command [/getnote] completed successfully!");



//		testEditNote(notes.get(0), UpdateMod.OVERWRITE);

	}


	void testEditNote(Note note, UpdateMod mod) {
		Note noteWithId = utils.getNote(note.getChatId(), note.getName());

		if (mod.equals(UpdateMod.OVERWRITE)) {
			assertEquals(((SendMessage) utils.sendCallBack(CHAT_ID_1, note.getName(), noteWithId.getId() + ":" + mod).get(0)),
					SendMessage.builder().chatId(String.valueOf(note.getChatId())).text("Новое содержание заметки?").build());
			assertEquals(((SendMessage) utils.sendMessage(CHAT_ID_1, TEXT).get(0)), SendMessage.builder()
					.chatId(String.valueOf(note.getChatId())).text("Заметка успешно изменена!").build());
			note.setText(TEXT);
		} else {
			assertEquals(((SendMessage) utils.sendCallBack(CHAT_ID_1, note.getName(), noteWithId.getId() + ":" + mod).get(0)),
					SendMessage.builder().chatId(String.valueOf(note.getChatId())).text("Что добавить в заметку?").build());
			assertEquals(((SendMessage) utils.sendMessage(CHAT_ID_1, TEXT).get(0)), SendMessage.builder()
					.chatId(String.valueOf(note.getChatId())).text("Заметка успешно изменена!").build());
			note.setText(note.getText() + "\n" + TEXT);
		}

		assertEquals(note, utils.getNote(CHAT_ID_1, note.getName()));
	}
}

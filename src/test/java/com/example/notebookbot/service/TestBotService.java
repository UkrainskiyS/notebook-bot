package com.example.notebookbot.service;

import com.example.notebookbot.persist.chat.ChatMode;
import com.example.notebookbot.persist.chat.model.Chat;
import com.example.notebookbot.persist.chat.repository.ChatRepository;
import com.example.notebookbot.persist.note.UpdateMod;
import com.example.notebookbot.persist.note.model.Note;
import com.example.notebookbot.utilits.DefaultMessage;
import com.example.notebookbot.utilits.NotePrinter;
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

	private final long CHAT_ID_1 = 999L;
	private final Chat CHAT_1 = new Chat(CHAT_ID_1, ChatMode.IGNORED);

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
		String NAME = "Telegram";

		// test /newnote
		var notes = utils.createNoteList(30, CHAT_ID_1, NAME);
		assertEquals(notes, utils.getNoteRepository().getAllByChatId(CHAT_ID_1));
		assertThrows(AssertionFailedError.class, () -> utils.createNote(notes.get(0).getChatId(), notes.get(2).getName()));
		log.info("Test command [/newnote] completed successfully!");

		// test /getnote
		notes.forEach(this::testGetNote);
		log.info("Test command [/getnote] completed successfully!");

		// test /editnote
		notes.forEach(note -> testEditNote(note, UpdateMod.ADD));
		notes.forEach(note -> testEditNote(note, UpdateMod.OVERWRITE));
		log.info("Test command [/editnote] completed successfully!");

		assertEquals(utils.getAllFromNoteRepo(CHAT_ID_1).size(), 30);

		// /deletenote
		notes.forEach(this::testDeleteNote);
		assertEquals(utils.getAllFromNoteRepo(CHAT_ID_1).size(), 0);
		log.info("Test command [/deletenote] completed successfully!");

		// finish
		assertEquals(utils.sendMessage(CHAT_ID_1, "/getnote"), DefaultMessage.noteListEmpty(CHAT_ID_1));
		assertEquals(utils.sendMessage(CHAT_ID_1, "/editnote"), DefaultMessage.noteListEmpty(CHAT_ID_1));
		assertEquals(utils.sendMessage(CHAT_ID_1, "/showall"), DefaultMessage.noteListEmpty(CHAT_ID_1));
		assertEquals(utils.sendMessage(CHAT_ID_1, "/getfile"), DefaultMessage.noteListEmpty(CHAT_ID_1));

		chatRepository.delete(chatRepository.getChatByChatId(CHAT_ID_1));
		assertEquals(chatRepository.count(), 0);
		log.info("All test completed successfully!");
	}

	void testDeleteNote(Note note) {
		var actualButtons = SendMessage.builder().text("Какую заметку удалить?")
				.replyMarkup(new InlineKeyboardMarkup(TmeButtons.convertToListButtons(utils.getAllFromNoteRepo(CHAT_ID_1))))
				.chatId(CHAT_ID_1 + "").build();
		assertEquals(utils.sendMessage(note.getChatId(), "/deletenote").get(0), actualButtons);
		assertEquals(chatRepository.getChatByChatId(CHAT_ID_1).getMode(), ChatMode.DEL_NOTE);
		assertEquals(utils.sendCallBack(CHAT_ID_1, note.getName(), String.valueOf(utils.getNote(CHAT_ID_1, note.getName()).getId())),
				DefaultMessage.noteDeleted(CHAT_ID_1, note.getName()));
		assertEquals(chatRepository.getChatByChatId(CHAT_ID_1).getMode(), ChatMode.IGNORED);
		assertNull(utils.getNote(CHAT_ID_1, note.getName()));
	}

	void testGetNote(Note note) {
		var actualButtons = SendMessage.builder().text("Какую заметку показать?")
				.replyMarkup(new InlineKeyboardMarkup(TmeButtons.convertToListButtons(utils.getAllFromNoteRepo(CHAT_ID_1))))
				.chatId(CHAT_ID_1 + "").build();
		assertEquals(utils.sendMessage(note.getChatId(), "/getnote").get(0), actualButtons);
		assertEquals(chatRepository.getChatByChatId(CHAT_ID_1).getMode(), ChatMode.GET_NOTE);
		assertEquals(utils.sendCallBack(CHAT_ID_1, note.getName(), String.valueOf(utils.getNote(CHAT_ID_1, note.getName()).getId())).get(0),
				SendMessage.builder().text(String.format("`%s`:\n\n%s", note.getName(), note.getText()))
						.chatId(String.valueOf(CHAT_ID_1)).parseMode(ParseMode.MARKDOWN).build());
		assertEquals(chatRepository.getChatByChatId(CHAT_ID_1).getMode(), ChatMode.IGNORED);
	}

	void testEditNote(Note note, UpdateMod mod) {
		assertEquals(utils.sendMessage(CHAT_ID_1, "/editnote").get(0), SendMessage.builder()
				.text("Какую заметку обновить?").chatId(String.valueOf(CHAT_ID_1))
				.replyMarkup(new InlineKeyboardMarkup(TmeButtons.convertToListButtons(utils.getAllFromNoteRepo(CHAT_ID_1))))
				.build());
		assertEquals(chatRepository.getChatByChatId(CHAT_ID_1).getMode(), ChatMode.EDIT_MODE);

		Note noteWithId = utils.getNote(note.getChatId(), note.getName());
		var next1 = utils.sendCallBack(CHAT_ID_1, note.getName(), String.valueOf(noteWithId.getId()));
		assertEquals(next1.get(0), SendMessage.builder().chatId(String.valueOf(CHAT_ID_1))
				.replyMarkup(new InlineKeyboardMarkup(TmeButtons.getNoteUpdateModButtons(noteWithId.getId() + ":")))
				.text("Какой формат редактирования?").build());

		var next2 = utils.sendCallBack(CHAT_ID_1, note.getName(), noteWithId.getId() + ":" + mod.name());
		assertEquals(utils.getNote(CHAT_ID_1, note.getName()).getUpdateMod(), mod);
		var setNoteUpdate = new java.util.ArrayList<>(NotePrinter.getMessageOneNote(CHAT_ID_1, noteWithId));
		setNoteUpdate.add(SendMessage.builder().chatId(String.valueOf(CHAT_ID_1))
				.text(mod.equals(UpdateMod.ADD) ? "Что добавить в заметку?" : "Новое содержание заметки?").build());
		assertEquals(next2, setNoteUpdate);

		String TEXT = "private final ChatRepository chatRepository = Mockito.mock(ChatRepository.class);";

		assertEquals(utils.sendMessage(CHAT_ID_1, TEXT).get(0), SendMessage.builder().chatId(String.valueOf(CHAT_ID_1))
				.text("Заметка успешно изменена!").build());
		assertEquals(utils.getNote(note.getChatId(), note.getName()).getUpdateMod(), UpdateMod.NOT);
		assertEquals(chatRepository.getChatByChatId(CHAT_ID_1).getMode(), ChatMode.IGNORED);

		noteWithId.setText(mod.equals(UpdateMod.OVERWRITE) ? TEXT : noteWithId.getText() + "\n\n" + TEXT);
		assertEquals(noteWithId, utils.getNote(noteWithId.getChatId(), noteWithId.getName()));
	}
}

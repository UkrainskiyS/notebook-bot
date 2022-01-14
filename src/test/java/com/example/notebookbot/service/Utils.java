package com.example.notebookbot.service;

import com.example.notebookbot.persist.note.UpdateMod;
import com.example.notebookbot.persist.note.model.Note;
import com.example.notebookbot.persist.note.repository.NoteRepository;
import com.example.notebookbot.utilits.DefaultMessage;
import lombok.Getter;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Getter
@Component
public class Utils {
	@Autowired
	private final BotService testingService = Mockito.mock(BotService.class);
	@Autowired
	private final NoteRepository noteRepository = Mockito.mock(NoteRepository.class);

	public List<PartialBotApiMethod<Message>> sendMessage(Long chatId, String text) {
		return testingService.messageHandler(getMessage(chatId, text));
	}

	public List<PartialBotApiMethod<Message>> sendCallBack(Long chatId, String name, String callBackQuery) {
		return testingService.callBackQueryHandler(getCallBack(chatId, name, callBackQuery));
	}

	public CallbackQuery getCallBack(Long chatId, String name, String callBackQuery) {
		CallbackQuery callback = new CallbackQuery();
		callback.setMessage(getMessage(chatId, name));
		callback.setData(callBackQuery);
		return callback;
	}

	public Message getMessage(Long chatId, String text) {
		Message message = new Message();
		message.setText(text);
		message.setChat(new Chat(chatId, "test"));
		return message;
	}

	public Note createNote(Long chatId, String name) {
		Note note = new Note(chatId, name, UpdateMod.NOT);
		assertEquals(sendMessage(chatId, "/newnote"), DefaultMessage.setNameForNewNote(chatId));
		assertEquals(sendMessage(chatId, note.getName()), DefaultMessage.setTextForNewNote(chatId, note.getName()));
		note.setText("Text");
		assertEquals(sendMessage(chatId, "Text"), DefaultMessage.newNoteCreated(chatId, note.getName()));
		assertEquals(noteRepository.findByNameAndChatId(name, chatId), note);
		return note;
	}

	public List<Note> createNoteList(int size, Long chatId, String name) {
		return IntStream.range(0, size)
				.mapToObj(i -> createNote(chatId, name + i))
				.collect(Collectors.toCollection(ArrayList::new));
	}

	public List<Note> getAllFromNoteRepo(Long chatId) {
		return noteRepository.getAllByChatId(chatId);
	}

	public Note getNote(long chatId, String name) {
		return noteRepository.findByNameAndChatId(name, chatId);
	}

	public void deleteNote(Note note) {
		noteRepository.delete(note);
	}
}

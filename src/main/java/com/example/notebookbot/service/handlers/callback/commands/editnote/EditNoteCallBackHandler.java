package com.example.notebookbot.service.handlers.callback.commands.editnote;

import com.example.notebookbot.persist.chat.ChatManager;
import com.example.notebookbot.persist.note.UpdateMod;
import com.example.notebookbot.persist.note.model.Note;
import com.example.notebookbot.persist.note.repository.NoteRepository;
import com.example.notebookbot.utilits.TmeButtons;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.List;

public class EditNoteCallBackHandler extends AbstractEditor {
	private final String data;

	public EditNoteCallBackHandler(Message message, ChatManager chatManager, NoteRepository noteRepository, String data) {
		super(message, chatManager, noteRepository);
		this.data = data;
	}

	@Override
	public List<PartialBotApiMethod<Message>> execute() {
		try {
			Note note = noteRepository.findById(Integer.parseInt(data));
			return List.of(
					SendMessage.builder().chatId(String.valueOf(message.getChatId()))
							.text("Какой формат редактирования?")
							.replyMarkup(new InlineKeyboardMarkup(TmeButtons.getNoteUpdateModButtons(note.getId() + ":")))
							.build()
			);
		} catch (NumberFormatException e) {
			String[] mod = data.split(":");
			Note note = noteRepository.findById(Integer.parseInt(mod[0]));

			return edit(note, mod);
		}
	}

	private List<PartialBotApiMethod<Message>> edit(Note note, String[] mod) {

		switch (UpdateMod.valueOf(mod[1])) {
			case ADD: return addMod(note);
			case OVERWRITE: return overwriteMod(note);
			default: return null;
		}
	}
}

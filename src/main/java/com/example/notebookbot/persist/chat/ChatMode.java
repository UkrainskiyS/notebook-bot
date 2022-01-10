package com.example.notebookbot.persist.chat;

public enum ChatMode {
	// режим, в котором игнорируются все сообщения, кроме команд
	IGNORED,

	// режимы, необходимые для добавления новой заметки
	NEW_SET_NAME, NEW_SET_TEXT,

}

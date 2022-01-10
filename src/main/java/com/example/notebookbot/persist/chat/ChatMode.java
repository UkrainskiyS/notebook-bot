package com.example.notebookbot.persist.chat;

public enum ChatMode {
	// режим, в котором игнорируются все сообщения, кроме команд
	IGNORED,

	// режимы, необходимые для добавления новой заметки
	NEW_SET_NAME, NEW_SET_TEXT,

	// режим для показа заметки
	GET_NOTE,

	// для скачивания файла с заметкой
	GET_FILE,

	// для удаления заметки
	DEL_NOTE,



}

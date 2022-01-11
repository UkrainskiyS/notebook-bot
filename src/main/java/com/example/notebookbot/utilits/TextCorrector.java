package com.example.notebookbot.utilits;

public class TextCorrector {
	public static String correct(String text) {
		String result = text;

		if (text.contains("{{") && text.contains("}}")) {
			result = text.replace("{{", "```").replace("}}", "```");
		}

		if (text.contains("<<") && text.contains(">>")) {
			result = result.replace("<<", "`").replace(">>", "`");
		}

		return result;
	}
}

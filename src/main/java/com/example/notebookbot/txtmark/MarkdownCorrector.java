package com.example.notebookbot.txtmark;

import com.github.rjeschke.txtmark.Processor;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MarkdownCorrector {
    private final MessageDecorator decorator = new MessageDecorator();
    private final String text;
    private final TypeText typeText;

    public String correct() {
        if (typeText.equals(TypeText.NAME)) {
            return text.replace("/_", "").replace("/*", "").replace("`", "");
        } else {
            return Processor.process(text.replace("/_", "_").replace("/*", "**"), decorator);
        }
    }
}

package com.example.notebookbot.txtmark;

import com.github.rjeschke.txtmark.Decorator;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class MessageDecorator implements Decorator {

    @Override
    public void openStrong(StringBuilder out) {
        out.append("<b>");
    }

    @Override
    public void closeStrong(StringBuilder out) {
        out.append("</b>");
    }

    @Override
    public void openCodeSpan(StringBuilder out) {
        out.append("<code>");
    }

    @Override
    public void closeCodeSpan(StringBuilder out) {
        out.append("</code>");
    }

    @Override
    public void openEmphasis(StringBuilder out) {
        out.append("<i>");
    }

    @Override
    public void closeEmphasis(StringBuilder out) {
        out.append("</i>");
    }

    @Override
    public void openParagraph(StringBuilder out) {}

    @Override
    public void closeParagraph(StringBuilder out) {}

    @Override
    public void openBlockquote(StringBuilder out) {}

    @Override
    public void closeBlockquote(StringBuilder out) {}

    @Override
    public void openCodeBlock(StringBuilder out) {}

    @Override
    public void closeCodeBlock(StringBuilder out) {}

    @Override
    public void openHeadline(StringBuilder out, int level) {}

    @Override
    public void closeHeadline(StringBuilder out, int level) {}

    @Override
    public void openSuper(StringBuilder out) {}

    @Override
    public void closeSuper(StringBuilder out) {}

    @Override
    public void openOrderedList(StringBuilder out) {}

    @Override
    public void closeOrderedList(StringBuilder out) {}

    @Override
    public void openUnorderedList(StringBuilder out) {}

    @Override
    public void closeUnorderedList(StringBuilder out) {}

    @Override
    public void openListItem(StringBuilder out) {}

    @Override
    public void closeListItem(StringBuilder out) {}

    @Override
    public void horizontalRuler(StringBuilder out) {}

    @Override
    public void openLink(StringBuilder out) {}

    @Override
    public void closeLink(StringBuilder out) {}

    @Override
    public void openImage(StringBuilder out) {}

    @Override
    public void closeImage(StringBuilder out) {}
}

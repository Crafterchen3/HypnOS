package com.deckerpw.hypnos.ui.element;

import com.deckerpw.hypnos.Colors;
import com.deckerpw.hypnos.Registry;
import com.deckerpw.hypnos.render.Font;
import com.deckerpw.hypnos.render.IGraphics;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class EditBox extends Element implements Clickable, KeyListener {

    private final String hint;
    private final char[] allowedChars = "0123456789".toCharArray();
    private final Font font;
    private String text = "";
    public EditBox(int x, int y, String hint, Font font) {
        super(x, y, 114, 15);
        this.hint = hint;
        this.font = font;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    private boolean containsChar(char character) {
        for (char allowedChar : allowedChars) {
            if (allowedChar == character) return true;
        }
        return false;
    }

    @Override
    public boolean mousePressed(int mouseX, int mouseY) {
        return true;
    }

    @Override
    public boolean mouseReleased(int mouseX, int mouseY) {
        return false;
    }

    @Override
    public boolean mouseDragged(int mouseX, int mouseY) {
        return false;
    }

    @Override
    public boolean mouseMoved(int mouseX, int mouseY) {
        return false;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getKeyChar() == '\b' && !text.isEmpty())
            text = text.substring(0, text.length() - 1);
        else if (containsChar(e.getKeyChar())) {
            text += e.getKeyChar();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void paint(IGraphics graphics) {
        graphics.drawImage(Registry.EDITBOX, x, y, width, height);
        if (text.isEmpty())
            font.drawString(hint, x + 2, y + 5, Colors.TEXT_COLOR, graphics);
        else
            font.drawString(text, x + 2, y + 5, Color.WHITE, graphics);
    }
}

package com.deckerpw.hypnos.ui.widget;

import com.deckerpw.hypnos.Colors;
import com.deckerpw.hypnos.Registry;
import com.deckerpw.hypnos.render.Font;
import com.deckerpw.hypnos.render.IGraphics;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

public class EditBox extends Widget implements Clickable, KeyListener {

    protected final String hint;
    private final char[] allowedChars;
    protected final Font font;
    protected final BufferedImage[] images;
    protected String text = "";
    private Runnable onReturn;

    public static final char[] NUMBERS = "0123456789".toCharArray();
    public static final char[] EVERYTHING = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789.,;:?!-_~#\"`&()[]|'\\/@°+=*<> ".toCharArray();

    public EditBox(Widget parent, int x, int y, String hint, Font font, char[] allowedChars) {
        super(parent, x, y, 114, 15);
        this.hint = hint;
        this.font = font;
        this.images = Registry.EDITBOX;
        this.allowedChars = allowedChars;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
        update();
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
    public boolean mouseWheelMoved(int mouseX, int mouseY, int scrollAmount) {
        return false;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getKeyChar() == '\b' && !text.isEmpty())
            text = text.substring(0, text.length() - 1);
        else if (e.getKeyChar() == KeyEvent.VK_ENTER && onReturn != null){
            onReturn.run();
        }
        else if (containsChar(e.getKeyChar())) {
            text += e.getKeyChar();
        }
        update();
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);
        update();
    }

    @Override
    public void paint(IGraphics graphics) {
        graphics.drawImage(images[selected ? 1 : 0], 0, 0, width, height);
        if (text.isEmpty())
            font.drawString(hint, 2, 5, Colors.TEXT_COLOR, graphics);
        else
            font.drawString(text, 2, 5, Color.WHITE, graphics);
    }

    public void setOnReturn(Runnable onReturn) {
        this.onReturn = onReturn;
    }
}

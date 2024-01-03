package com.deckerpw.hypnos.ui.element;

import com.deckerpw.hypnos.HypnOS;
import com.deckerpw.hypnos.render.Font;

import java.awt.*;

public class ScrollTextBox extends TextBox implements Clickable{


    public ScrollTextBox(int x, int y, int width, int height, String string, Font font, Color color) {
        super(x, y, width, height, string, font, color);
    }

    public ScrollTextBox(int x, int y, int width, String string, Font font, Color color) {
        super(x, y, width, string, font, color);
    }

    @Override
    public boolean mousePressed(int mouseX, int mouseY) {
        return false;
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
        start = Math.max(0,start+scrollAmount);
        return true;
    }
}

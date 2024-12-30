package com.deckerpw.hypnos.ui.widget;

import com.deckerpw.hypnos.render.Font;
import com.deckerpw.hypnos.render.IGraphics;

import java.awt.*;

public class ScrollTextBox extends TextBox implements Clickable {


    public ScrollTextBox(Widget parent, int x, int y, int width, int height, String string, Font font, Color color) {
        super(parent, x, y, width, height, string, font, color);
    }

    public ScrollTextBox(Widget parent, int x, int y, int width, String string, Font font, Color color) {
        super(parent, x, y, width, string, font, color);
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
        start = Math.max(0, start + scrollAmount);
        update();
        return true;
    }

    @Override
    public void paint(IGraphics graphics) {
        for (int i = start; i < lines.size(); i++) {
            String line = lines.get(i);
            font.drawString(line, 0, (i - start) * 9, color, graphics);
        }
    }
}

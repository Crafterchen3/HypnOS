package com.deckerpw.hypnos.ui.widget;

import com.deckerpw.hypnos.render.Font;
import com.deckerpw.hypnos.render.IGraphics;

import java.awt.*;

public class Label extends Widget {

    private final Font font;
    private final String text;
    private final Color color;

    public Label(int x, int y, String string, Font font, Color color) {
        super(x, y, font.getLength(string), 8);
        this.text = string;
        this.font = font;
        this.color = color;
    }

    @Override
    public void paint(IGraphics graphics) {
        font.drawString(text, x, y, color, graphics);
    }
}

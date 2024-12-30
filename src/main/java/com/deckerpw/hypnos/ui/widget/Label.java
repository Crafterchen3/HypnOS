package com.deckerpw.hypnos.ui.widget;

import com.deckerpw.hypnos.render.Font;
import com.deckerpw.hypnos.render.IGraphics;

import java.awt.*;

public class Label extends Widget {

    private final Font font;
    private final String text;
    private final Color color;

    public Label(Widget parent, int x, int y, String string, Font font, Color color) {
        super(parent, x, y, font.getLength(string), 8);
        this.text = string;
        this.font = font;
        this.color = color;
    }

    @Override
    public void paint(IGraphics graphics) {
        font.drawString(text, 0, 0, color, graphics);
    }
}

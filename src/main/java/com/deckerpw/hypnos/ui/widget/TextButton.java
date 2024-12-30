package com.deckerpw.hypnos.ui.widget;

import com.deckerpw.hypnos.render.Font;
import com.deckerpw.hypnos.render.IGraphics;

import java.awt.*;
import java.awt.image.BufferedImage;

public class TextButton extends Button {

    private final Font font;
    public String text;

    public TextButton(Widget parent, int x, int y, int width, int height, String text, BufferedImage[] images, Runnable runnable, Font font) {
        super(parent, x, y, width, height, images, runnable);
        this.text = text;
        this.font = font;
    }

    @Override
    public void paint(IGraphics graphics) {
        super.paint(graphics);
        font.drawCenteredString(text, width / 2, height / 2 - 3, state == 0 ? Color.WHITE : new Color(0xFF8990F5), graphics);
    }
}

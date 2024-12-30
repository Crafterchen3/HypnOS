package com.deckerpw.hypnos.ui.widget;

import com.deckerpw.hypnos.Colors;
import com.deckerpw.hypnos.render.IGraphics;

import java.awt.*;

public class PasswordBox extends EditBox {
    public PasswordBox(Widget parent, int x, int y, String hint, com.deckerpw.hypnos.render.Font font, char[] allowedChars) {
        super(parent, x, y, hint, font, allowedChars);
    }

    @Override
    public void paint(IGraphics graphics) {
        graphics.drawImage(images[selected ? 1 : 0], 0, 0, width, height);
        if (text.isEmpty())
            font.drawString(hint, 2, 5, Colors.TEXT_COLOR, graphics);
        else {
            String pass = "*".repeat(text.length());
            font.drawString(pass, 2, 5, Color.WHITE, graphics);
        }
    }
}

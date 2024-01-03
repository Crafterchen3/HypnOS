package com.deckerpw.hypnos.ui.element;

import com.deckerpw.hypnos.render.Font;
import com.deckerpw.hypnos.render.IGraphics;

import java.awt.*;
import java.util.ArrayList;

public class TextBox extends Element {

    private final Font font;
    private final Color color;
    public ArrayList<String> lines = new ArrayList<>();

    public TextBox(int x, int y, int width, String string, Font font, Color color) {
        super(x, y, width, 8);
        this.font = font;
        this.color = color;
        genLines(string);
        height = lines.size() * 9;

    }

    public void genLines(String string) {
        String line = "";
        for (char c : string.toCharArray()) {
            if (font.getLength(line + c) > width)
                break;
            line += c;
        }
        lines.add(line);
        String rest = string.substring(line.length());
        if (rest != "") {
            genLines(rest);
        }
    }

    @Override
    public void paint(IGraphics graphics) {
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            font.drawString(line, x, y + (i * 9), color, graphics);
        }
    }
}

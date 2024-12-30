package com.deckerpw.hypnos.ui.widget;

import com.deckerpw.hypnos.render.IGraphics;

import java.awt.*;

public class Box extends Widget {
    private final Color color;

    public Box(Widget parent, int x, int y, int width, int height, Color color) {
        super(parent, x, y, width, height);
        this.color = color;
    }

    @Override
    protected void paint(IGraphics graphics) {
        graphics.fillRect(0, 0, width, height, color);
    }
}

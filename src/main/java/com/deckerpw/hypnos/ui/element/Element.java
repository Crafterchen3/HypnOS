package com.deckerpw.hypnos.ui.element;

import com.deckerpw.hypnos.render.IGraphics;

public abstract class Element {

    public int x;
    public int y;
    public int width;
    public int height;

    public Element(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public boolean isInside(int mouseX, int mouseY) {
        return mouseX >= x && mouseX < x + width && mouseY >= y && mouseY < y + height;
    }

    public void move(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public abstract void paint(IGraphics graphics);

}

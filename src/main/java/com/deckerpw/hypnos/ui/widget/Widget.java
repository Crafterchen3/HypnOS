package com.deckerpw.hypnos.ui.widget;

import com.deckerpw.hypnos.render.IGraphics;

public abstract class Widget {

    protected int x;
    protected int y;
    protected int width;
    protected int height;

    public Widget(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public boolean isInside(int x, int y) {
        return x >= this.getX() && x < this.getX() + width && y >= this.getY() && y < this.getY() + height;
    }

    public void move(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public abstract void paint(IGraphics graphics);

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}

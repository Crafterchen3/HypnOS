package com.deckerpw.hypnos.ui.widget;

import com.deckerpw.hypnos.render.IGraphics;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Widget {

    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected boolean selected;
    protected Widget parent;
    protected BufferedImage buffer;
    private boolean init = false;

    public Widget(Widget parent,int x, int y, int width, int height) {
        this.parent = parent;
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
        update();
    }

    public void update(){
        buffer = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = buffer.createGraphics();
        IGraphics graphics = new IGraphics() {
            @Override
            public Graphics2D create(int x, int y, int width, int height) {
                return (Graphics2D) g2d.create(x,y,width,height);
            }

            @Override
            public void drawImage(BufferedImage image, int x, int y, int width, int height) {
                g2d.drawImage(image,x,y,width,height,null);
            }

            @Override
            public void fillRect(int x, int y, int width, int height, Color color) {
                g2d.setColor(color);
                g2d.fillRect(x,y,width,height);
            }
        };
        paint(graphics);
        if (parent != null && init)
            parent.update();
    }

    protected abstract void paint(IGraphics graphics);

    public final void render(IGraphics graphics){
        if (!init){
            update();
            init = true;
        }
        graphics.drawImage(buffer,x,y,width,height);
    }

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

    public int getHeight() {
        return height;
    }

    public void resize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}

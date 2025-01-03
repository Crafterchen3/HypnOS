package com.deckerpw.hypnos.ui.widget;

import com.deckerpw.hypnos.render.IGraphics;

import java.awt.image.BufferedImage;

public class Button extends Widget implements Clickable {

    private final Runnable runnable;
    private final BufferedImage[] images;
    protected int state;

    public Button(Widget parent, int x, int y, int width, int height, BufferedImage[] images, Runnable runnable) {
        super(parent, x, y, width, height);
        this.runnable = runnable;
        this.images = images;
    }

    public boolean mousePressed(int mouseX, int mouseY) {
        state = 1;
        update();
        return true;
    }

    public boolean mouseReleased(int mouseX, int mouseY) {
        if (state == 1) {
            state = 0;
            update();
            runnable.run();
        }
        return true;
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
        return false;
    }

    @Override
    public void paint(IGraphics graphics) {
        graphics.drawImage(images[state], 0, 0, width, height);
    }
}

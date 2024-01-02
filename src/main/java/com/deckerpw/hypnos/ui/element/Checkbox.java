package com.deckerpw.hypnos.ui.element;

import com.deckerpw.hypnos.Colors;
import com.deckerpw.hypnos.render.Font;
import com.deckerpw.hypnos.render.IGraphics;

import java.awt.image.BufferedImage;

public class Checkbox extends Element implements Clickable {

    private final Font font;
    private final String title;
    private final BufferedImage[] images;
    private boolean state;

    public Checkbox(int x, int y, Font font, String title, BufferedImage[] images, boolean state) {
        super(x, y, 16, 16);
        this.font = font;
        this.title = title;
        this.images = images;
        this.state = state;
    }

    public boolean getState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    @Override
    public boolean mousePressed(int mouseX, int mouseY) {
        state = !state;
        return true;
    }

    @Override
    public boolean mouseReleased(int mouseX, int mouseY) {
        return false;
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
    public void paint(IGraphics graphics) {
        int s = font.getLength(title);
        font.drawString(title, x - 4 - s, y + 6, Colors.TEXT_COLOR, graphics);
        graphics.drawImage(images[state ? 1 : 0], x, y, 16, 16);
    }
}

package com.deckerpw.hypnos.ui.widget;

import com.deckerpw.hypnos.Colors;
import com.deckerpw.hypnos.render.Font;
import com.deckerpw.hypnos.render.IGraphics;

import java.awt.image.BufferedImage;

public class Checkbox extends Widget implements Clickable {

    private final Font font;
    private final String title;
    private final BufferedImage[] images;
    private boolean state;

    public Checkbox(Widget parent, int x, int y, Font font, String title, BufferedImage[] images, boolean state) {
        super(parent, x, y, 16, 16);
        this.font = font;
        this.title = title;
        this.images = images;
        this.state = state;
        int s = font.getLength(title);
        this.width = 20 + s;
        this.x -= s+4;
    }

    @Override
    public boolean isInside(int x, int y) {
        return x >= this.getX()+(width-16) && x < this.getX() + width && y >= this.getY() && y < this.getY() + height;
    }

    public boolean getState() {
        return state;
    }

    @Override
    public boolean mousePressed(int mouseX, int mouseY) {
        state = !state;
        update();
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
    public boolean mouseWheelMoved(int mouseX, int mouseY, int scrollAmount) {
        return false;
    }

    @Override
    public void paint(IGraphics graphics) {
        font.drawString(title, 0, 6, Colors.TEXT_COLOR, graphics);
        graphics.drawImage(images[state ? 1 : 0], width-16, 0, 16, 16);
    }
}

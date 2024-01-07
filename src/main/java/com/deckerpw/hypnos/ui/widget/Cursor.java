package com.deckerpw.hypnos.ui.widget;

import com.deckerpw.hypnos.render.IGraphics;

import java.awt.image.BufferedImage;

public class Cursor {

    private static Cursor instance;
    public final BufferedImage[] icons;
    public int state = 0;

    public Cursor(BufferedImage[] icons) {
        this(icons, 0);
    }

    public Cursor(BufferedImage[] icons, int state) {
        Cursor.instance = this;
        this.icons = icons;
        this.state = state;
    }

    public static Cursor getInstance() {
        return instance;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void paint(IGraphics g, int mouseX, int mouseY) {
        switch (state) {
            case 1 -> g.drawImage(icons[1], mouseX - 4, mouseY, 14, 17);
            case 2 -> g.drawImage(icons[2], mouseX - 4, mouseY + 1, 14, 17);
            case 4 -> g.drawImage(icons[4], mouseX - 7, mouseY - 7, 15, 15);
            case 6 -> g.drawImage(icons[6], mouseX - 4, mouseY - 1, 13, 12);
            default -> g.drawImage(icons[0], mouseX, mouseY, 10, 15);

        }

    }

}

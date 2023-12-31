package com.deckerpw.hypnos.ui.element;

import com.deckerpw.hypnos.render.IGraphics;

import java.awt.image.BufferedImage;

public class CursorElement {


    public final BufferedImage[] icons;
    public int state = 0;

    public CursorElement(BufferedImage[] icons) {
        this(icons, 0);
    }

    public CursorElement(BufferedImage[] icons, int state) {
        this.icons = icons;
        this.state = state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void paint(IGraphics g, int mouseX, int mouseY) {
        switch (state) {
            case 1 -> g.drawImage(icons[1], mouseX - 4, mouseY, 14, 17);
            case 2 -> g.drawImage(icons[2], mouseX - 4, mouseY - 1, 14, 17);
            case 4 -> g.drawImage(icons[4], mouseX - 7, mouseY - 7, 15, 15);
            case 6 -> g.drawImage(icons[6], mouseX - 4, mouseY - 1, 13, 12);
            default -> g.drawImage(icons[0], mouseX, mouseY, 10, 15);

        }

    }

}

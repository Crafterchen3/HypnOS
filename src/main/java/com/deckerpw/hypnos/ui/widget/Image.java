package com.deckerpw.hypnos.ui.widget;

import com.deckerpw.hypnos.render.IGraphics;

import java.awt.image.BufferedImage;

public class Image extends Widget {

    public BufferedImage image;

    public Image(Widget parent, int x, int y, int width, int height, BufferedImage image) {
        super(parent, x, y, width, height);
        this.image = image;
    }

    public Image(Widget parent, int x, int y, BufferedImage image) {
        super(parent, x, y, image.getWidth(), image.getHeight());
        this.image = image;
    }

    @Override
    public void paint(IGraphics graphics) {
        graphics.drawImage(image, 0, 0, width, height);
    }
}

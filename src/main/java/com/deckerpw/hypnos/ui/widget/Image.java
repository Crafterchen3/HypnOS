package com.deckerpw.hypnos.ui.widget;

import com.deckerpw.hypnos.render.IGraphics;

import java.awt.image.BufferedImage;

public class Image extends Widget {

    public BufferedImage image;

    public Image(int x, int y, int width, int height, BufferedImage image) {
        super(x, y, width, height);
        this.image = image;
    }

    public Image(int x, int y, BufferedImage image) {
        super(x, y, image.getWidth(), image.getHeight());
        this.image = image;
    }

    @Override
    public void paint(IGraphics graphics) {
        graphics.drawImage(image, x, y, width, height);
    }
}

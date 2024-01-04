package com.deckerpw.hypnos.render;

import com.deckerpw.hypnos.HypnOS;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Graphics implements IGraphics {

    public Graphics2D graphics2D;

    public Graphics(Graphics2D graphics2D) {
        this.graphics2D = graphics2D;
    }

    @Override
    public Graphics2D create(int x, int y, int width, int height) {
        float size = HypnOS.size;
        return (Graphics2D) graphics2D.create((int) (x * size), (int) (y * size), (int) (width * size), (int) (height * size));
    }

    public void drawImage(BufferedImage image, int x, int y, int width, int height) {
        float size = HypnOS.size;
        graphics2D.drawImage(image, (int) (x * size), (int) (y * size), (int) (width * size), (int) (height * size), null);
    }
}

package com.deckerpw.hypnos.render;

import com.deckerpw.hypnos.HypnOS;

import java.awt.*;

public class Graphics implements IGraphics {

    public Graphics2D graphics2D;

    public Graphics(Graphics2D graphics2D) {
        this.graphics2D = graphics2D;
    }

    public void drawImage(Image image, int x, int y, int width, int height) {
        float size = HypnOS.size;
        graphics2D.drawImage(image, (int) (x * size), (int) (y * size), (int) (width * size), (int) (height * size), null);
    }

    @Override
    public void drawRect(Color color, int x, int y, int width, int height) {
        float size = HypnOS.size;
        graphics2D.setColor(color);
        graphics2D.setStroke(new BasicStroke());
        graphics2D.drawRect((int) (x * size), (int) (y * size), (int) (width * size), (int) (height * size));
    }
}

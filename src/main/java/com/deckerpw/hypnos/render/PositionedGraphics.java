package com.deckerpw.hypnos.render;

import com.deckerpw.hypnos.HypnOS;
import com.deckerpw.hypnos.ui.element.Element;

import java.awt.*;
import java.awt.image.BufferedImage;

public class PositionedGraphics implements IGraphics {

    public IGraphics graphics;
    public Element element;

    public PositionedGraphics(IGraphics graphics, Element element) {
        this.graphics = graphics;
        this.element = element;
    }

    @Override
    public Graphics2D create(int x, int y, int width, int height) {
        return graphics.create(element.x + x, element.y + y, width, height);
    }

    @Override
    public void drawImage(BufferedImage image, int x, int y, int width, int height) {
        float size = HypnOS.size;
        create(0, 0, element.width, element.height).drawImage(image, (int) (x * size), (int) (y * size), (int) (width * size), (int) (height * size), null);
    }
}

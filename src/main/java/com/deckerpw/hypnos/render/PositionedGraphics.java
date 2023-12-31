package com.deckerpw.hypnos.render;

import com.deckerpw.hypnos.ui.element.Element;

import java.awt.*;

public class PositionedGraphics implements IGraphics {

    public IGraphics graphics;
    public Element element;

    public PositionedGraphics(IGraphics graphics, Element element) {
        this.graphics = graphics;
        this.element = element;
    }

    @Override
    public void drawImage(Image image, int x, int y, int width, int height) {
        graphics.drawImage(image, element.x + x, element.y + y, width, height);
    }

    @Override
    public void drawRect(Color color, int x, int y, int width, int height) {
        graphics.drawRect(color, element.x + x, element.y + y, width, height);
    }
}

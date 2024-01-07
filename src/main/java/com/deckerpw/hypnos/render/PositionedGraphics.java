package com.deckerpw.hypnos.render;

import com.deckerpw.hypnos.HypnOS;
import com.deckerpw.hypnos.ui.widget.Widget;

import java.awt.*;
import java.awt.image.BufferedImage;

public class PositionedGraphics implements IGraphics {

    public IGraphics graphics;
    public Widget widget;

    public PositionedGraphics(IGraphics graphics, Widget widget) {
        this.graphics = graphics;
        this.widget = widget;
    }

    @Override
    public Graphics2D create(int x, int y, int width, int height) {
        return graphics.create(widget.getX() + x, widget.getY() + y, width, height);
    }

    @Override
    public void drawImage(BufferedImage image, int x, int y, int width, int height) {
        float size = HypnOS.size;
        create(0, 0, widget.getWidth(), widget.getHeight()).drawImage(image, (int) (x * size), (int) (y * size), (int) (width * size), (int) (height * size), null);
    }
}

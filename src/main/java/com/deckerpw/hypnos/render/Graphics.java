package com.deckerpw.hypnos.render;

import com.deckerpw.hypnos.swing.SwingWindow;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Graphics implements IGraphics {

    private final BufferedImage cache = new BufferedImage(480,270,BufferedImage.TYPE_INT_ARGB);
    private final SwingWindow bridge;

    public Graphics(SwingWindow bridge) {
        this.bridge = bridge;
    }

    @Override
    public Graphics2D create(int x, int y, int width, int height) {
        Graphics2D graphics2D = cache.createGraphics();
        return (Graphics2D) graphics2D.create(x, y, width, height);
    }

    public void drawImage(BufferedImage image, int x, int y, int width, int height) {
        Graphics2D graphics2D = cache.createGraphics();
        graphics2D.drawImage(image, x,y,width,height, null);
    }

    public void fillRect(int x, int y, int width, int height, Color color) {
        Graphics2D graphics2D = cache.createGraphics();
        graphics2D.setColor(color);
        graphics2D.fillRect(x,y,width,height);
    }

    public void paint(Graphics2D graphics2D){
        float scale = bridge.getScale();
        graphics2D.drawImage(cache,0,0,(int)(scale*480),(int)(scale*270),null);
    }

    public BufferedImage getCache() {
        return cache;
    }
}

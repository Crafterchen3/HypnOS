package com.deckerpw.hypnos.util;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SizeableImage {

    private final BufferedImage edgeTL;
    private final BufferedImage edgeTR;
    private final BufferedImage edgeBL;
    private final BufferedImage edgeBR;
    private final BufferedImage sideT;
    private final BufferedImage sideB;
    private final BufferedImage sideL;
    private final BufferedImage sideR;
    private final Color background;

    public SizeableImage(BufferedImage edgeTL, BufferedImage edgeTR, BufferedImage edgeBL, BufferedImage edgeBR, BufferedImage sideT, BufferedImage sideB, BufferedImage sideL, BufferedImage sideR, Color background) {
        this.edgeTL = edgeTL;
        this.edgeTR = edgeTR;
        this.edgeBL = edgeBL;
        this.edgeBR = edgeBR;
        this.sideT = sideT;
        this.sideB = sideB;
        this.sideL = sideL;
        this.sideR = sideR;
        this.background = background;
    }

    private int min(int a, int b, int c) {
        return Math.min(a, Math.min(b, c));
    }

    private int max(int a, int b, int c) {
        return Math.max(a, Math.min(b, c));
    }

    public BufferedImage genImage(int width, int height) {
        BufferedImage res = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics g = res.createGraphics();
        int x = min(edgeTL.getWidth(), sideL.getWidth(), edgeBL.getWidth());
        int y = min(edgeTL.getHeight(), sideT.getHeight(), edgeTR.getHeight());
        int w = max(width - x - edgeTR.getWidth(), width - x - sideR.getWidth(), width - x - edgeBR.getWidth());
        int h = max(height - y - edgeBL.getHeight(), height - y - sideB.getHeight(), height - y - edgeBR.getHeight());
        g.setColor(background);
        g.fillRect(x, y, w, h);
        g.drawImage(edgeTL, 0, 0, null);
        g.drawImage(edgeTR, width - edgeTR.getWidth(), 0, null);
        g.drawImage(edgeBL, 0, height - edgeBL.getHeight(), null);
        g.drawImage(edgeBR, width - edgeBR.getWidth(), height - edgeBR.getHeight(), null);
        g.drawImage(sideT, edgeTL.getWidth(), 0, width - edgeTL.getWidth() - edgeTR.getWidth(), sideT.getHeight(), null);
        g.drawImage(sideB, edgeBL.getWidth(), height - sideB.getHeight(), width - edgeBL.getWidth() - edgeBR.getWidth(), sideB.getHeight(), null);
        g.drawImage(sideL, 0, edgeTL.getHeight(), sideL.getWidth(), height - edgeTL.getHeight() - edgeBL.getHeight(), null);
        g.drawImage(sideR, width - sideR.getWidth(), edgeTR.getHeight(), sideR.getWidth(), height - edgeTR.getHeight() - edgeBR.getHeight(), null);
        return res;
    }
}

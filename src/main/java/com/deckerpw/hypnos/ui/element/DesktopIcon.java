package com.deckerpw.hypnos.ui.element;

import com.deckerpw.hypnos.HypnOS;
import com.deckerpw.hypnos.Registry;
import com.deckerpw.hypnos.render.IGraphics;

import java.awt.image.BufferedImage;

public class DesktopIcon extends Element {

    public final BufferedImage[] icons;
    public int state = 0;
    public boolean dragging = false;
    public boolean active = false;
    public Runnable action;

    public DesktopIcon(int x, int y, int width, int height, BufferedImage[] icons, Runnable action) {
        this(x, y, width, height, icons, action, 0);
    }

    public DesktopIcon(int x, int y, int width, int height, BufferedImage[] icons) {
        this(x, y, width, height, icons, null, 0);
    }

    public DesktopIcon(int x, int y, int width, int height, BufferedImage[] icons, Runnable action, int state) {
        super(x, y, width, height);
        this.icons = icons;
        this.state = state;
        this.action = action;
    }

    public void setState(int state) {
        this.state = state;
        if (action != null) {
            if (state == 1 && !dragging) {
                active = true;
                new Thread(() -> {
                    try {
                        Thread.sleep(200);
                        active = false;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }).start();
                return;
            }
            if (state == 2 && active) {
                action.run();
            }
        }
    }

    @Override
    public void paint(IGraphics g) {
        float size = HypnOS.size;
        if (dragging)
            g.drawImage(Registry.MOVE_BACKGROUND, x, y, width + 1, height + 1);
        g.drawImage(icons[state], x, y, width, height);
    }
}

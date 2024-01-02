package com.deckerpw.hypnos.ui.element;

import com.deckerpw.hypnos.HypnOS;
import com.deckerpw.hypnos.Registry;
import com.deckerpw.hypnos.render.IGraphics;
import org.json.JSONObject;

import java.awt.image.BufferedImage;

public class DesktopIcon extends Element {

    public final BufferedImage[] icons;
    public final String id;
    public int state = 0;
    public boolean dragging = false;
    public boolean active = false;
    public Runnable action;

    public DesktopIcon(int x, int y, int width, int height, BufferedImage[] icons, Runnable action, String id) {
        this(x, y, width, height, icons, action, 0, id);
    }

    public DesktopIcon(int x, int y, int width, int height, BufferedImage[] icons, String id) {
        this(x, y, width, height, icons, null, 0, id);
    }

    public DesktopIcon(int x, int y, int width, int height, BufferedImage[] icons, Runnable action, int state, String id) {
        super(x, y, width, height);
        this.icons = icons;
        this.state = state;
        this.action = action;
        this.id = id;
    }

    public void init() {
        JSONObject desktop = HypnOS.settings.jsonObject.getJSONObject("desktop");
        if (desktop.has(id)) {
            JSONObject icon = desktop.getJSONObject(id);
            this.x = icon.getInt("x");
            this.y = icon.getInt("y");
        }
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

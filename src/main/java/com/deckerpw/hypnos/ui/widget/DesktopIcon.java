package com.deckerpw.hypnos.ui.widget;

import com.deckerpw.hypnos.Registry;
import com.deckerpw.hypnos.config.UserConfig;
import com.deckerpw.hypnos.machine.Machine;
import com.deckerpw.hypnos.render.IGraphics;
import com.deckerpw.hypnos.util.MachineEventListener;
import org.json.JSONObject;

import java.awt.image.BufferedImage;

public class DesktopIcon extends Widget {

    public final BufferedImage[] icons;
    public final String id;
    public int state = 0;
    public boolean dragging = false;
    public boolean active = false;
    public MachineEventListener action;
    private Machine machine;

    public DesktopIcon(int x, int y, int width, int height, BufferedImage[] icons, MachineEventListener action, String id) {
        this(null, x, y, width, height, icons, action, 0, id);
    }

    public DesktopIcon(int x, int y, int width, int height, BufferedImage[] icons, String id) {
        this(null, x, y, width, height, icons, null, 0, id);
    }

    public DesktopIcon(Widget parent,int x, int y, int width, int height, BufferedImage[] icons, MachineEventListener action, int state, String id) {
        super(parent, x, y, width+1, height+1);
        this.icons = icons;
        this.state = state;
        this.action = action;
        this.id = id;
    }

    public DesktopIcon createCopy() {
        return new DesktopIcon(x, y, width-1, height-1, icons, action, id);
    }

    public void init(UserConfig config, Machine machine) {
        this.machine = machine;
        JSONObject desktop = config.getJSONObject("desktop");
        if (desktop.has(id)) {
            JSONObject icon = desktop.getJSONObject(id);
            this.x = icon.getInt("x");
            this.y = icon.getInt("y");
        }
    }

    public void setState(int state) {
        this.state = state;
        update();
        if (action != null) {
            if (state == 1 && active) {
                action.actionPerformed(machine);
                active = false;
                return;
            }
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
            }
        }
    }

    @Override
    public void paint(IGraphics graphics) {
        if (dragging)
            graphics.drawImage(Registry.MOVE_BACKGROUND, 0, 0, width, height);
        graphics.drawImage(icons[state], 0, 0, width-1, height-1);
    }
}

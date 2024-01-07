package com.deckerpw.hypnos.ui;

import com.deckerpw.hypnos.HypnOS;
import com.deckerpw.hypnos.Registry;
import com.deckerpw.hypnos.render.IGraphics;
import com.deckerpw.hypnos.render.PositionedGraphics;
import com.deckerpw.hypnos.swing.SwingWindow;
import com.deckerpw.hypnos.ui.widget.Cursor;
import com.deckerpw.hypnos.ui.widget.Gif;
import com.deckerpw.hypnos.ui.widget.Widget;
import com.deckerpw.hypnos.ui.window.LogWindow;
import com.deckerpw.hypnos.ui.window.Window;
import com.deckerpw.hypnos.util.Sound;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;

public class Screen {

    public static Screen instance;
    private final com.deckerpw.hypnos.render.Font font = Registry.HYPNOFONT_0N;
    private final ArrayList<Window> windows = new ArrayList<>();
    private final SwingWindow.Bridge bridge;
    public Desktop desktop;
    public Cursor cursor; //TODO add Setting (that's why it's not final)
    private int mouseX = 0;
    private int mouseY = 0;
    private boolean draggingDesktop;
    private Window draggingWindow;
    private Window selectedWindow;
    private Gif splashScreen;

    public Screen(SwingWindow.Bridge bridge) {
        this.bridge = bridge;
        HypnOS.logger.println("Starting Screen...");
        instance = this;
        if (HypnOS.settings.jsonObject.getBoolean("intro")) {
            splashScreen = new Gif(0, 0, 480, 270, Registry.INTRO, 10);
            splashScreen.start();
        } else {
            init();
        }
    }

    public static Screen getInstance() {
        return instance;
    }

    public void update() {
        bridge.updateUI();
    }

    private void init() {
        bridge.init();
        HypnOS.logger.println("Initiating Screen...");
        Registry.STARTUP.playSound();
        cursor = Registry.CURSOR_DARK;
        desktop = new Desktop(cursor);

        if (HypnOS.settings.jsonObject.getBoolean("autostart_log")) addWindow(new LogWindow(this, cursor));
    }

    public void mousePressed(MouseEvent e) {
        if (HypnOS.settings.jsonObject.getBoolean("mouse_sfx")) Registry.CLICK0.playSound();
        for (Window window : windows) {
            if (window.isInside(mouseX, mouseY)) {
                selectedWindow = window;
                if (window.mousePressed(mouseX, mouseY))
                    update();
                return;
            }
        }
        if (desktop.mousePressed(mouseX, mouseY)) {
            selectedWindow = null;
            update();
        }
    }

    public void mouseReleased(MouseEvent e) {
        if (HypnOS.settings.jsonObject.getBoolean("mouse_sfx")) Registry.CLICK1.playSound();
        if (draggingDesktop) {
            desktop.mouseReleased(mouseX, mouseY);
            draggingDesktop = false;
            update();
            return;
        }
        if (draggingWindow != null) {
            windows.remove(draggingWindow);
            windows.add(0, draggingWindow);
            draggingWindow.mouseReleased(mouseX, mouseY);
            draggingWindow = null;
            update();
            return;
        }
        for (Window window : windows) {
            if (window.isInside(mouseX, mouseY)) {
                if (window.mouseReleased(mouseX, mouseY))
                    update();
                return;
            }
        }
        if (desktop.mouseReleased(mouseX, mouseY))
            update();
    }

    public void mouseDragged(MouseEvent e) {
        float size = HypnOS.size;
        mouseX = (int) Math.floor((double) e.getX() / size);
        mouseY = (int) Math.floor((double) e.getY() / size);
        if (draggingDesktop) {
            desktop.mouseDragged(mouseX, mouseY);
            update();
            return;
        }
        if (draggingWindow != null) {
            draggingWindow.mouseDragged(mouseX, mouseY);
            update();
            return;
        }
        for (Window window : windows) {
            if (window.isInside(mouseX, mouseY) && window.mouseDragged(mouseX, mouseY)) {
                draggingWindow = window;
                update();
                return;
            }
        }
        draggingDesktop = true;
        desktop.mouseDragged(mouseX, mouseY);
        update();
    }

    public void mouseMoved(MouseEvent e) {
        float size = HypnOS.size;
        mouseX = (int) Math.floor(e.getX() / size);
        mouseY = (int) Math.floor(e.getY() / size);
        cursor.setState(0);
        for (Window window : windows) {
            if (window.isInside(mouseX, mouseY)) {
                window.mouseMoved(mouseX, mouseY);
                break;
            }
        }
        update();
    }

    public void mouseWheelMoved(MouseWheelEvent e) {
        float size = HypnOS.size;
        mouseX = (int) Math.floor(e.getX() / size);
        mouseY = (int) Math.floor(e.getY() / size);
        for (Window window : windows) {
            int amount = e.getScrollAmount() / 3 * e.getWheelRotation();
            //HypnOS.logger.println(amount+"");
            if (window.isInside(mouseX, mouseY) && window.mouseWheelMoved(mouseX, mouseY, amount)) {
                update();
                break;
            }
        }
    }


    public void addWindow(Window window, Sound sound) {
        Window tup = null;
        for (Window window1 : this.windows) {
            if (window1.getClass().equals(window.getClass())) {
                tup = window1;
                break;
            }
        }
        if (tup != null) {
            this.windows.remove(tup);
            this.windows.add(0, tup);
        } else {
            sound.playSound();
            this.windows.add(0, window);
        }
    }

    public void addWindow(Window window) {
        addWindow(window, Registry.OPEN_WINDOW);
    }

    public void removeWindow(Window window) {
        this.windows.remove(window);
    }

    public void paint(java.awt.Graphics graphics) {
        Graphics2D g = (Graphics2D) graphics;
        g.setPaintMode();
        g.setColor(Color.black);
        PositionedGraphics graphics1 = new PositionedGraphics(new com.deckerpw.hypnos.render.Graphics(g), new Widget(0, 0, 480, 270) {
            @Override
            public void paint(IGraphics graphics) {

            }
        });
        if (splashScreen != null) {
            splashScreen.paint(graphics1);
            return;
        }
        desktop.paint(graphics1);
        for (int i = windows.size() - 1; i >= 0; i -= 1) {
            windows.get(i).paint(graphics1);
        }
        cursor.paint(graphics1, mouseX, mouseY);

    }

    public void keyTyped(KeyEvent e) {
        if (splashScreen != null) {
            splashScreen = null;
            init();
            return;
        }
        if (selectedWindow != null)
            selectedWindow.keyTyped(e);
        update();
    }

    public void keyPressed(KeyEvent e) {
        if (splashScreen == null && selectedWindow != null)
            selectedWindow.keyPressed(e);
        update();

    }

    public void keyReleased(KeyEvent e) {
        if (splashScreen == null && selectedWindow != null)
            selectedWindow.keyReleased(e);
        update();

    }
}

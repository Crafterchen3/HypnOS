package com.deckerpw.hypnos.swing;

import com.deckerpw.hypnos.HypnOS;
import com.deckerpw.hypnos.Registry;
import com.deckerpw.hypnos.render.IGraphics;
import com.deckerpw.hypnos.render.PositionedGraphics;
import com.deckerpw.hypnos.ui.Desktop;
import com.deckerpw.hypnos.ui.element.Cursor;
import com.deckerpw.hypnos.ui.element.Element;
import com.deckerpw.hypnos.ui.element.Gif;
import com.deckerpw.hypnos.ui.window.LogWindow;
import com.deckerpw.hypnos.ui.window.Window;
import com.deckerpw.hypnos.util.Sound;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Screen extends JPanel implements KeyListener {

    public static Screen instance;
    private final com.deckerpw.hypnos.render.Font font = Registry.HYPNOFONT_0N;
    private final ArrayList<Window> windows = new ArrayList<>();
    public Desktop desktop;
    public Cursor cursor; //TODO add Setting (that's why it's not final)
    private int mouseX = 0;
    private int mouseY = 0;
    private boolean draggingDesktop;
    private Window draggingWindow;
    private Window selectedWindow;
    private Gif splashScreen;

    public Screen() {
        HypnOS.logger.println("Starting Screen...");
        setFocusable(true);
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

    private void init() {
        HypnOS.logger.println("Initiating Screen...");
        Registry.STARTUP.playSound();
        cursor = Registry.CURSOR_DARK;
        desktop = new Desktop(cursor);
        MouseAdapter adapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (HypnOS.settings.jsonObject.getBoolean("mouse_sfx")) Registry.CLICK0.playSound();
                for (Window window : windows) {
                    if (window.isInside(mouseX, mouseY)) {
                        selectedWindow = window;
                        if (window.mousePressed(mouseX, mouseY))
                            updateUI();
                        return;
                    }
                }
                if (desktop.mousePressed(mouseX, mouseY)) {
                    selectedWindow = null;
                    updateUI();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (HypnOS.settings.jsonObject.getBoolean("mouse_sfx")) Registry.CLICK1.playSound();
                if (draggingDesktop) {
                    desktop.mouseReleased(mouseX, mouseY);
                    draggingDesktop = false;
                    updateUI();
                    return;
                }
                if (draggingWindow != null) {
                    windows.remove(draggingWindow);
                    windows.add(0, draggingWindow);
                    draggingWindow.mouseReleased(mouseX, mouseY);
                    draggingWindow = null;
                    updateUI();
                    return;
                }
                for (Window window : windows) {
                    if (window.isInside(mouseX, mouseY)) {
                        if (window.mouseReleased(mouseX, mouseY))
                            updateUI();
                        return;
                    }
                }
                if (desktop.mouseReleased(mouseX, mouseY))
                    updateUI();
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                float size = HypnOS.size;
                mouseX = (int) Math.floor((double) e.getX() / size);
                mouseY = (int) Math.floor((double) e.getY() / size);
                if (draggingDesktop) {
                    desktop.mouseDragged(mouseX, mouseY);
                    updateUI();
                    return;
                }
                if (draggingWindow != null) {
                    draggingWindow.mouseDragged(mouseX, mouseY);
                    updateUI();
                    return;
                }
                for (Window window : windows) {
                    if (window.isInside(mouseX, mouseY) && window.mouseDragged(mouseX, mouseY)) {
                        draggingWindow = window;
                        updateUI();
                        return;
                    }
                }
                draggingDesktop = true;
                desktop.mouseDragged(mouseX, mouseY);
                updateUI();
            }

            @Override
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
                updateUI();
            }

            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                float size = HypnOS.size;
                mouseX = (int) Math.floor(e.getX() / size);
                mouseY = (int) Math.floor(e.getY() / size);
                for (Window window : windows) {
                    int amount = e.getScrollAmount() / 3 * e.getWheelRotation();
                    //HypnOS.logger.println(amount+"");
                    if (window.isInside(mouseX, mouseY) && window.mouseWheelMoved(mouseX, mouseY, amount)) {
                        updateUI();
                        break;
                    }
                }
            }
        };
        addMouseListener(adapter);
        addMouseMotionListener(adapter);
        addMouseWheelListener(adapter);
        if (HypnOS.settings.jsonObject.getBoolean("autostart_log")) addWindow(new LogWindow(this, cursor));
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension((int) (480 * HypnOS.size), (int) (270 * HypnOS.size));
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

    @Override
    public void paint(java.awt.Graphics graphics) {
        Graphics2D g = (Graphics2D) graphics;
        g.setPaintMode();
        g.setColor(Color.black);
        PositionedGraphics graphics1 = new PositionedGraphics(new com.deckerpw.hypnos.render.Graphics(g), new Element(0, 0, 480, 270) {
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


    @Override
    public void keyTyped(KeyEvent e) {
        if (splashScreen != null) {
            splashScreen = null;
            init();
            return;
        }
        if (selectedWindow != null)
            selectedWindow.keyTyped(e);
        updateUI();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (splashScreen == null && selectedWindow != null)
            selectedWindow.keyPressed(e);
        updateUI();

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (splashScreen == null && selectedWindow != null)
            selectedWindow.keyReleased(e);
        updateUI();

    }
}

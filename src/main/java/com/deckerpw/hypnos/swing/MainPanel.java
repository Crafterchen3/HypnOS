package com.deckerpw.hypnos.swing;

import com.deckerpw.hypnos.HypnOS;
import com.deckerpw.hypnos.Registry;
import com.deckerpw.hypnos.ui.Desktop;
import com.deckerpw.hypnos.ui.element.CursorElement;
import com.deckerpw.hypnos.ui.window.Window;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class MainPanel extends JPanel {

    public static MainPanel instance;
    private final Desktop desktop;
    private final com.deckerpw.hypnos.render.Font font = Registry.HYPNOFONT_0N;
    private final ArrayList<Window> windows = new ArrayList<>();
    public CursorElement cursor; //TODO add Setting (that's why it's not final)
    private int mouseX = 0;
    private int mouseY = 0;
    private boolean draggingDesktop;
    private Window draggingWindow;

    public MainPanel() {
        instance = this;
        cursor = Registry.CURSOR_DARK;
        desktop = new Desktop(cursor);
        MouseAdapter adapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                for (Window window : windows) {
                    if (window.isInside(mouseX, mouseY)) {
                        if (window.mousePressed(mouseX, mouseY))
                            updateUI();
                        return;
                    }
                }
                if (desktop.mousePressed(mouseX, mouseY))
                    updateUI();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
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
                updateUI();
            }
        };
        addMouseListener(adapter);
        addMouseMotionListener(adapter);
    }

    public static MainPanel getInstance() {
        return instance;
    }

    public void addWindow(Window window) {
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
        } else
            this.windows.add(0, window);
    }

    public void removeWindow(Window window) {
        this.windows.remove(window);
    }

    @Override
    public void paint(java.awt.Graphics graphics) {
        Graphics2D g = (Graphics2D) graphics;
        g.setPaintMode();
        g.setColor(Color.black);
        com.deckerpw.hypnos.render.Graphics graphics1 = new com.deckerpw.hypnos.render.Graphics(g);
        desktop.paint(graphics1);
        for (int i = windows.size() - 1; i >= 0; i -= 1) {
            windows.get(i).paint(graphics1);
        }
        cursor.paint(graphics1, mouseX, mouseY);
    }


}

package com.deckerpw.hypnos.ui.widget;

import com.deckerpw.hypnos.machine.Machine;
import com.deckerpw.hypnos.render.IGraphics;
import com.deckerpw.hypnos.ui.window.Window;
import com.deckerpw.hypnos.util.Sound;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class WindowManager extends Widget implements Clickable, KeyListener {

    private final ArrayList<Window> windows = new ArrayList<>();
    private Window selectedWindow;
    private final Machine machine;

    public WindowManager(Widget parent, int x, int y, int width, int height, Machine machine) {
        super(parent, x, y, width, height);
        this.machine = machine;
    }

    public void addWindow(Window window, Sound sound) {
        for (Window w : windows) {
            if (w.getClass().equals(window.getClass())) {
                selectWindow(w);
                moveWindowToFront(w);
                return;
            }
        }
        if (sound != null) sound.playSound();
        windows.add(0, window);
        selectWindow(window);
        update();
    }


    public Machine getMachine() {
        return machine;
    }


    public void removeWindow(Window window) {
        if (selectedWindow == window) selectedWindow = null;
        windows.remove(window);
    }

    public ArrayList<Window> getWindows() {
        return windows;
    }

    public void moveWindowToFront(Window window) {
        windows.remove(window);
        windows.add(0, window);
        update();
    }

    @Override
    protected void paint(IGraphics graphics) {
        ArrayList<Window> toRender = new ArrayList<>();
        for (Window window : windows) {
            toRender.add(window);
            if (window.isFullscreen()) break;
        }
        for (int i = toRender.size() - 1; i >= 0; i--) {
            toRender.get(i).render(graphics);
        }
    }

    @Override
    public boolean mousePressed(int mouseX, int mouseY) {
        boolean change = false;
        for (Window window : windows) {
            if (window.isInside(mouseX, mouseY)) {
                if (selectedWindow != null) selectedWindow.setSelected(false);
                selectedWindow = window;
                change = true;
                break;
            }
        }
        if (change) {
            moveWindowToFront(selectedWindow);
            selectedWindow.mousePressed(mouseX - selectedWindow.getX(), mouseY - selectedWindow.getY());
            update();
            return true;
        }
        return false;
    }

    @Override
    public boolean mouseReleased(int mouseX, int mouseY) {
        if (selectedWindow != null) {
            selectedWindow.mouseReleased(mouseX - selectedWindow.getX(), mouseY - selectedWindow.getY());
            update();
            return true;
        }
        return false;
    }

    @Override
    public boolean mouseDragged(int mouseX, int mouseY) {
        if (selectedWindow != null) {
            selectedWindow.mouseDragged(mouseX - selectedWindow.getX(), mouseY - selectedWindow.getY());
            update();
            return true;
        }
        return false;
    }

    @Override
    public boolean mouseMoved(int mouseX, int mouseY) {
        for (Window window : windows) {
            if (window.isInside(mouseX, mouseY)) {
                window.mouseMoved(mouseX - window.getX(), mouseY - window.getY());
                update();
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean mouseWheelMoved(int mouseX, int mouseY, int scrollAmount) {
        for (Window window : windows) {
            if (window.isInside(mouseX, mouseY)) {
                window.mouseWheelMoved(mouseX - window.getX(), mouseY - window.getY(), scrollAmount);
                update();
                return true;
            }
        }
        return false;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (selectedWindow != null) {
            selectedWindow.keyTyped(e);
            update();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (selectedWindow != null) {
            selectedWindow.keyPressed(e);
            update();
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (selectedWindow != null) {
            selectedWindow.keyReleased(e);
            update();
        }

    }

    public void selectWindow(Window window) {
        if (selectedWindow != null) {
            selectedWindow.setSelected(false);
        }
        selectedWindow = window;
        selectedWindow.setSelected(true);
        update();
    }
}

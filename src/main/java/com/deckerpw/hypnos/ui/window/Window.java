package com.deckerpw.hypnos.ui.window;

import com.deckerpw.hypnos.Colors;
import com.deckerpw.hypnos.Registry;
import com.deckerpw.hypnos.render.Font;
import com.deckerpw.hypnos.render.IGraphics;
import com.deckerpw.hypnos.render.PositionedGraphics;
import com.deckerpw.hypnos.swing.Screen;
import com.deckerpw.hypnos.ui.element.Button;
import com.deckerpw.hypnos.ui.element.Clickable;
import com.deckerpw.hypnos.ui.element.Cursor;
import com.deckerpw.hypnos.ui.element.Element;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Window extends Element implements KeyListener {

    public final Font font = Registry.HYPNOFONT_0N;
    private final BufferedImage windowPane;
    private final Button closeButton;
    private final Cursor cursor;
    private final String title;
    private final Screen screen;
    public ArrayList<Element> elements = new ArrayList<>();
    public boolean dragging = false;
    public Clickable selectedClickable;
    public KeyListener selectedKeyListener;
    private BufferedImage icon;
    private int dragPointX;
    private int dragPointY;

    public Window(Screen screen, BufferedImage pane, int x, int y, int width, int height, Cursor cursor, String title) {
        super(x, y, width, height);
        this.windowPane = pane;
        this.screen = screen;
        this.cursor = cursor;
        this.title = title;
        this.closeButton = new Button(width - 19, 2, 15, 14, Registry.CLOSE_BUTTON, new Runnable() {
            @Override
            public void run() {
                Registry.CLOSE_WINDOW.playSound();
                screen.removeWindow(Window.this);
            }
        });

    }

    public Window(Screen screen, BufferedImage pane, int x, int y, int width, int height, Cursor cursor, String title, BufferedImage icon) {
        super(x, y, width, height);
        this.windowPane = pane;
        this.icon = icon;
        this.screen = screen;
        this.cursor = cursor;
        this.title = title;
        this.closeButton = new Button(width - 19, 2, 15, 14, Registry.CLOSE_BUTTON, () -> {
            Registry.CLOSE_WINDOW.playSound();
            screen.removeWindow(Window.this);
        });

    }

    public void addElement(Element element) {
        this.elements.add(element);
    }

    public boolean mousePressed(int mouseX, int mouseY) {
        if (closeButton.isInside(mouseX - x, mouseY - y)) {
            selectedClickable = closeButton;
            return closeButton.mousePressed(mouseX, mouseY);
        }
        for (Element element : elements) {
            if (element instanceof Clickable clickable && element.isInside(mouseX - x, mouseY - y)) {
                selectedClickable = clickable;
                selectedKeyListener = null;
                if (element instanceof KeyListener listener)
                    selectedKeyListener = listener;
                return clickable.mousePressed(mouseX - x, mouseY - y);
            }
        }
        return false;
    }

    public boolean mouseReleased(int mouseX, int mouseY) {
        dragging = false;
        cursor.setState(0);
        if (selectedClickable != null) {
            boolean rv = selectedClickable.mouseReleased(mouseX - x, mouseY - y);
            selectedClickable = null;
            return rv;
        }
        return false;
    }

    public boolean mouseDragged(int mouseX, int mouseY) {
        if (selectedClickable != null) {
            selectedClickable.mouseDragged(mouseX - x, mouseY - y);
        } else {
            if (mouseY < y + 9 && !dragging) {
                cursor.setState(4);
                dragPointX = mouseX - x;
                dragPointY = mouseY - y;
                dragging = true;
            }
            if (dragging) {
                int newX = mouseX - dragPointX;
                int newY = mouseY - dragPointY;
                move(newX, newY);
            }
        }
        return true;
    }

    public boolean mouseMoved(int mouseX, int mouseY) {
        for (Element element : elements) {
            if (element instanceof Clickable clickable && element.isInside(mouseX - x, mouseY - y)) {
                return clickable.mouseMoved(mouseX - x, mouseY - y);
            }
        }
        return false;
    }

    public boolean mouseWheelMoved(int mouseX, int mouseY, int scrollAmount) {
        for (Element element : elements) {
            if (element instanceof Clickable clickable && element.isInside(mouseX - x,mouseY-y))  {
                return clickable.mouseWheelMoved(mouseX-x,mouseY-y,scrollAmount);
            }
        }
        return false;
    }

    @Override
    public void paint(IGraphics g) {
        g.drawImage(windowPane, x, y, width, height);
        PositionedGraphics graphics = new PositionedGraphics(g, this);
        font.drawString(title, icon == null ? x + 6 : x + 22, y + 2, Colors.WINDOW_TITLE_COLOR, g);
        if (icon != null)
            graphics.drawImage(icon, 4, 3, 16, 16);
        closeButton.paint(graphics);
        for (Element element : elements) {
            element.paint(graphics);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (selectedKeyListener != null)
            selectedKeyListener.keyTyped(e);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (selectedKeyListener != null)
            selectedKeyListener.keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (selectedKeyListener != null)
            selectedKeyListener.keyReleased(e);
    }
}

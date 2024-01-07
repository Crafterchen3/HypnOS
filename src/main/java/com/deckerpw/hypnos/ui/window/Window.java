package com.deckerpw.hypnos.ui.window;

import com.deckerpw.hypnos.Colors;
import com.deckerpw.hypnos.Registry;
import com.deckerpw.hypnos.render.Font;
import com.deckerpw.hypnos.render.IGraphics;
import com.deckerpw.hypnos.render.PositionedGraphics;
import com.deckerpw.hypnos.ui.Screen;
import com.deckerpw.hypnos.ui.widget.Button;
import com.deckerpw.hypnos.ui.widget.Clickable;
import com.deckerpw.hypnos.ui.widget.Cursor;
import com.deckerpw.hypnos.ui.widget.Widget;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Window extends Widget implements KeyListener {

    public final Font font = Registry.HYPNOFONT_0N;
    public final Screen screen;
    private final BufferedImage windowPane;
    private final Button closeButton;
    private final Cursor cursor;
    private final String title;
    public ArrayList<Widget> widgets = new ArrayList<>();
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
        this.closeButton = new Button(width - 19, 2, 15, 14, Registry.CLOSE_BUTTON, this::closeWindow);

    }

    public void addWidget(Widget widget) {
        this.widgets.add(widget);
    }

    public void closeWindow() {
        Registry.CLOSE_WINDOW.playSound();
        screen.removeWindow(this);
    }

    public Widget getWidgetAt(int x, int y) {
        for (Widget widget : widgets) {
            if (widget.isInside(x, y))
                return widget;
        }
        return null;
    }

    public boolean mousePressed(int mouseX, int mouseY) {
        if (closeButton.isInside(mouseX - x, mouseY - y)) {
            selectedClickable = closeButton;
            return closeButton.mousePressed(mouseX, mouseY);
        }
        Widget widget = getWidgetAt(mouseX - x, mouseY - y);
        if (widget instanceof Clickable clickable) {
            selectedClickable = clickable;
            selectedKeyListener = null;
            if (widget instanceof KeyListener listener)
                selectedKeyListener = listener;
            return clickable.mousePressed(mouseX - x, mouseY - y);
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
        Widget widget = getWidgetAt(mouseX - x, mouseY - y);
        if (widget instanceof Clickable clickable) {
            return clickable.mouseMoved(mouseX - x, mouseY - y);
        }
        return false;
    }

    public boolean mouseWheelMoved(int mouseX, int mouseY, int scrollAmount) {
        Widget widget = getWidgetAt(mouseX - x, mouseY - y);
        if (widget instanceof Clickable clickable) {
            return clickable.mouseWheelMoved(mouseX - x, mouseY - y, scrollAmount);
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
        for (Widget widget : widgets) {
            widget.paint(graphics);
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

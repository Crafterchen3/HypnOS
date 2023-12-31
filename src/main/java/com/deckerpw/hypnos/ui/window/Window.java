package com.deckerpw.hypnos.ui.window;

import com.deckerpw.hypnos.Registry;
import com.deckerpw.hypnos.render.Font;
import com.deckerpw.hypnos.render.IGraphics;
import com.deckerpw.hypnos.render.PositionedGraphics;
import com.deckerpw.hypnos.swing.MainPanel;
import com.deckerpw.hypnos.ui.element.Button;
import com.deckerpw.hypnos.ui.element.Clickable;
import com.deckerpw.hypnos.ui.element.CursorElement;
import com.deckerpw.hypnos.ui.element.Element;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Window extends Element {

    public final ArrayList<Element> elements = new ArrayList<>();
    protected final Font font = Registry.HYPNOFONT_0N;
    private final BufferedImage windowPane;
    private final Button closeButton;
    private final CursorElement cursor;
    private final String title;
    private final MainPanel panel;
    public boolean dragging = false;
    public Clickable selected;
    private BufferedImage icon;
    private int dragPointX;
    private int dragPointY;

    public Window(MainPanel panel, BufferedImage pane, int x, int y, int width, int height, CursorElement cursor, String title) {
        super(x, y, width, height);
        this.windowPane = pane;
        this.panel = panel;
        this.cursor = cursor;
        this.title = title;
        this.closeButton = new Button(width - 19, 2, 15, 14, Registry.CLOSE_BUTTON, new Runnable() {
            @Override
            public void run() {
                panel.removeWindow(Window.this);
            }
        });

    }

    public Window(MainPanel panel, BufferedImage pane, int x, int y, int width, int height, CursorElement cursor, String title, BufferedImage icon) {
        super(x, y, width, height);
        this.windowPane = pane;
        this.icon = icon;
        this.panel = panel;
        this.cursor = cursor;
        this.title = title;
        this.closeButton = new Button(width - 19, 2, 15, 14, Registry.CLOSE_BUTTON, new Runnable() {
            @Override
            public void run() {
                panel.removeWindow(Window.this);
            }
        });

    }

    public boolean mousePressed(int mouseX, int mouseY) {
        if (closeButton.isInside(mouseX - x, mouseY - y)) {
            selected = closeButton;
            return closeButton.mousePressed(mouseX, mouseY);
        }
        for (Element element : elements) {
            if (element instanceof Clickable clickable && element.isInside(mouseX - x, mouseY - y)) {
                selected = clickable;
                return clickable.mousePressed(mouseX - x, mouseY - y);
            }
        }
        return false;
    }

    public boolean mouseReleased(int mouseX, int mouseY) {
        dragging = false;
        cursor.setState(0);
        if (selected != null)
            return selected.mouseReleased(mouseX - x, mouseY - y);
        return false;
    }

    public boolean mouseDragged(int mouseX, int mouseY) {
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
            return true;
        }
        return true;
    }

    public boolean mouseMoved(int mouseX, int mouseY) {
        return false;
    }


    @Override
    public void paint(IGraphics g) {
        g.drawImage(windowPane, x, y, width, height);
        PositionedGraphics graphics = new PositionedGraphics(g, this);
        font.drawString(title, icon == null ? x + 6 : x + 22, y + 2, new Color(0xFF302454), g);
        if (icon != null)
            graphics.drawImage(icon, 4, 3, 16, 16);
        closeButton.paint(graphics);
        for (Element element : elements) {
            element.paint(graphics);
        }
    }

}

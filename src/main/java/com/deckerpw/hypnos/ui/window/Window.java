package com.deckerpw.hypnos.ui.window;

import com.deckerpw.hypnos.Colors;
import com.deckerpw.hypnos.Registry;
import com.deckerpw.hypnos.render.Font;
import com.deckerpw.hypnos.render.IGraphics;
import com.deckerpw.hypnos.ui.target.Screen;
import com.deckerpw.hypnos.ui.widget.*;
import com.deckerpw.hypnos.util.PaneBuilder;

import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Window extends Container implements KeyListener {

    public final Font font = Registry.HYPNOFONT_0N;
    public final WindowManager windowManager;
    private final BufferedImage windowPane;
    private final String title;
    private BufferedImage icon;

    public Window(WindowManager windowManager, BufferedImage pane, int x, int y, int width, int height, String title) {
        super(windowManager, x, y, width, height);
        this.windowPane = pane;
        this.windowManager = windowManager;
        this.title = title;
        addWidget(new WindowBar(0, 0, width, 9));
        addWidget(new Button(this, width - 18, 2, 15, 15, Registry.CLOSE_BUTTON, this::closeWindow));
    }

    public Window(WindowManager windowManager, int x, int y, int width, int height, String title) {
        this(windowManager, PaneBuilder.createWindowPane(width, height), x, y, width, height, title);
    }


    public Window(WindowManager windowManager, BufferedImage pane, int x, int y, int width, int height, String title, BufferedImage icon) {
        super(windowManager, x, y, width, height);
        this.windowPane = pane;
        this.icon = icon;
        this.windowManager = windowManager;
        this.title = title;
        addWidget(new WindowBar(0, 0, width, 9));
        addWidget(new Button(this,width - 18, 2, 15, 15, Registry.CLOSE_BUTTON, this::closeWindow));
    }

    public Window(WindowManager windowManager, int x, int y, int width, int height, String title, BufferedImage icon) {
        this(windowManager, PaneBuilder.createWindowPane(width,height), x, y, width, height, title, icon);
    }

    public void closeWindow() {
        Registry.CLOSE_WINDOW.playSound();
        windowManager.removeWindow(this);
    }

    @Override
    public void paint(IGraphics graphics) {
        graphics.drawImage(windowPane, 0, 0, width, height);
        super.paint(graphics);
        font.drawCenteredString(title, width/2, 2, Colors.WINDOW_TITLE_COLOR, graphics);
        if (icon != null)
            graphics.drawImage(icon, 3, 2, 16, 16);
    }

    public boolean isFullscreen() {
        return false;
    }

    private class WindowBar extends Widget implements Clickable {

        int dragX;
        int dragY;

        public WindowBar(int x, int y, int width, int height) {
            super(Window.this,x, y, width, height);
        }

        @Override
        public boolean mousePressed(int mouseX, int mouseY) {
            dragX = mouseX;
            dragY = mouseY;
            return true;
        }

        @Override
        public boolean mouseReleased(int mouseX, int mouseY) {
            Cursor.getInstance().setState(0);
            return true;
        }

        @Override
        public boolean mouseDragged(int mouseX, int mouseY) {
            Window.this.x += mouseX - dragX;
            Window.this.y += mouseY - dragY;
            Cursor.getInstance().setState(4);
            return true;
        }

        @Override
        public boolean mouseMoved(int mouseX, int mouseY) {
            return false;
        }

        @Override
        public boolean mouseWheelMoved(int mouseX, int mouseY, int scrollAmount) {
            return false;
        }

        @Override
        public void paint(IGraphics graphics) {

        }
    }
}

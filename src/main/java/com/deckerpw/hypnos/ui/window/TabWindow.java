package com.deckerpw.hypnos.ui.window;

import com.deckerpw.hypnos.Registry;
import com.deckerpw.hypnos.render.Font;
import com.deckerpw.hypnos.render.IGraphics;
import com.deckerpw.hypnos.ui.target.Screen;
import com.deckerpw.hypnos.ui.widget.*;
import com.deckerpw.hypnos.ui.widget.Container;
import com.deckerpw.hypnos.ui.widget.Cursor;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class TabWindow extends Window {

    public final ArrayList<Widget>[] tabs;
    public Container container;
    public final TabButton[] tabButtons;
    public int selectedTab = 0;

    public TabWindow(WindowManager windowManager, BufferedImage pane, int x, int y, int width, int height, String title, BufferedImage icon, String[] tabs) {
        super(windowManager, pane, x, y, width, height, title, icon);
        this.tabs = new ArrayList[tabs.length];
        this.tabButtons = new TabButton[tabs.length];
        for (int i = 0; i < tabs.length; i++) {
            this.tabButtons[i] = new TabButton((i * 60) + 22, 10, font, tabs[i], false, i);
        }
        for (int i = 0; i < this.tabs.length; i++) {
            this.tabs[i] = new ArrayList<>(List.of(tabButtons));
        }
        this.tabButtons[selectedTab].state = true;
        container = new Container(this,0,0,width,height);
        addWidget(container);
        refreshCurrentTab();
    }


    public void addWidgetSelectedTab(Widget widget, int tab) {
        this.tabs[tab].add(widget);
    }

    public void addWidgetSelectedTab(Widget widget) {
        addWidgetSelectedTab(widget, selectedTab);
    }

    public void refreshCurrentTab() {
        container.replaceWidgets(tabs[selectedTab]);
        container.update();
    }

    class TabButton extends Widget implements Clickable {

        private final Font font;
        private final String title;
        private final BufferedImage[] images;
        private final int index;
        private boolean state;

        public TabButton(int x, int y, Font font, String title, boolean state, int index) {
            super(TabWindow.this, x, y, 60, 9);
            this.font = font;
            this.title = title;
            this.index = index;
            this.images = Registry.TAB_BUTTON;
            this.state = state;
        }

        @Override
        public boolean mousePressed(int mouseX, int mouseY) {
            Registry.TAB_SOUNDS[index].playSound();
            Cursor.getInstance().setState(2);
            tabButtons[selectedTab].state = false;
            tabButtons[selectedTab].update();
            this.state = true;
            update();
            selectedTab = index;
            refreshCurrentTab();
            return true;
        }

        @Override
        public boolean mouseReleased(int mouseX, int mouseY) {
            Cursor.getInstance().setState(1);
            return true;
        }

        @Override
        public boolean mouseDragged(int mouseX, int mouseY) {
            return false;
        }

        @Override
        public boolean mouseMoved(int mouseX, int mouseY) {
            Cursor.getInstance().setState(1);
            return false;
        }

        @Override
        public boolean mouseWheelMoved(int mouseX, int mouseY, int scrollAmount) {
            return false;
        }

        @Override
        public void paint(IGraphics graphics) {
            graphics.drawImage(images[state ? 1 : 0], 0, 0, width, height);
            font.drawCenteredString(title, width / 2, 2, state ? Color.WHITE : new Color(0xFF9AC7F5), graphics);
        }


    }
}

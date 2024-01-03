package com.deckerpw.hypnos.ui.window;

import com.deckerpw.hypnos.Registry;
import com.deckerpw.hypnos.render.Font;
import com.deckerpw.hypnos.render.IGraphics;
import com.deckerpw.hypnos.swing.Screen;
import com.deckerpw.hypnos.ui.element.Clickable;
import com.deckerpw.hypnos.ui.element.Cursor;
import com.deckerpw.hypnos.ui.element.Element;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class TabWindow extends Window {

    public final ArrayList<Element>[] tabs;
    public final TabButton[] tabButtons;
    public int selectedTab = 0;

    public TabWindow(Screen screen, BufferedImage pane, int x, int y, int width, int height, Cursor cursor, String title, BufferedImage icon, String[] tabs) {
        super(screen, pane, x, y, width, height, cursor, title, icon);
        this.tabs = new ArrayList[tabs.length];
        this.tabButtons = new TabButton[tabs.length];
        for (int i = 0; i < tabs.length; i++) {
            this.tabButtons[i] = new TabButton((i * 60) + 22, 10, font, tabs[i], false, i);
        }
        for (int i = 0; i < this.tabs.length; i++) {
            this.tabs[i] = new ArrayList<>(List.of(tabButtons));
        }
        this.tabButtons[selectedTab].state = true;
        refreshCurrentTab();
    }

    public void addElement(Element element, int tab) {
        this.tabs[tab].add(element);
    }

    @Override
    public void addElement(Element element) {
        addElement(element, selectedTab);
    }

    public void refreshCurrentTab() {
        elements = tabs[selectedTab];
    }

    class TabButton extends Element implements Clickable {

        private final Font font;
        private final String title;
        private final BufferedImage[] images;
        private final int index;
        private boolean state;

        public TabButton(int x, int y, Font font, String title, boolean state, int index) {
            super(x, y, 60, 9);
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
            this.state = true;
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
            graphics.drawImage(images[state ? 1 : 0], x, y, width, height);
            font.drawCenteredString(title, x + (width / 2), y + 2, state ? Color.WHITE : new Color(0xFF9AC7F5), graphics);
        }


    }
}

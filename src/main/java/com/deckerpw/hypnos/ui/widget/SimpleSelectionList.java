package com.deckerpw.hypnos.ui.widget;

import com.deckerpw.hypnos.Registry;
import com.deckerpw.hypnos.render.Font;
import com.deckerpw.hypnos.render.IGraphics;

import java.awt.*;

public class SimpleSelectionList  extends AbstractSelectionList{

    public SimpleSelectionList(Widget parent, int x, int y, int width, int height, String[] names) {
        super(parent, x, y, width, height, 15);
        for (int i = 0; i < names.length; i++) {
            this.entries.add(new StringEntry(this,names[i],i % 2 != 0));
        }
    }

    public SimpleSelectionList(Widget parent, int x, int y, int width, int height) {
        super(parent, x, y, width, height, 15);
    }

    public void setEntrys(String[] names) {
        this.entries.clear();
        for (int i = 0; i < names.length; i++) {
            this.entries.add(new StringEntry(this,names[i],i % 2 != 0));
        }
        select(0);
        update();
    }

    class StringEntry extends Entry{

        public String name;
        private final Color[] backgroundColors = new Color[]{
                new Color(0x3C0D26),
                new Color(0x2D092D),
                new Color(0xC42798)
        };
        private final Font font = Registry.HYPNOFONT_0N;
        private final Color defaultColor = new Color(0xffD77BBA);
        private final Color selectedColor = new Color(0xff9AEFFF);
        private boolean odd;

        public StringEntry(Widget parent, String name, boolean odd) {
            super(parent, 355,15);
            this.name = name;
            this.odd = odd;
            update();
        }

        @Override
        protected void paint(IGraphics graphics) {
            graphics.fillRect(0, 0, SimpleSelectionList.this.width, 15, backgroundColors[selected ? 2 : (odd ? 1 : 0)]);
            font.drawString(name, 3, 5, selected ? selectedColor : defaultColor, graphics);
        }
    }
}

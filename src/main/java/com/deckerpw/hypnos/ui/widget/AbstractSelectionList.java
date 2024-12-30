package com.deckerpw.hypnos.ui.widget;

import com.deckerpw.hypnos.render.IGraphics;

import java.util.ArrayList;

public class AbstractSelectionList extends Widget implements Clickable {

    public ArrayList<Entry> entries = new ArrayList<>();
    public int offset = 0;
    protected int itemHeight;
    protected Entry selected;
    private ArrayList<Runnable> changeEvents = new ArrayList<>();

    public AbstractSelectionList(Widget parent, int x, int y, int width, int height, int itemHeight) {
        super(parent, x, y, width, height);
        this.itemHeight = itemHeight;
    }

    @Override
    public boolean mousePressed(int mouseX, int mouseY) {
        int i = (int) Math.floor((mouseY-offset)/itemHeight);
        select(i);
        return true;
    }

    public void addEventListener(Runnable r){
        changeEvents.add(r);
    }

    public void select(int i){
        if (entries.isEmpty())
            return;
        if (selected != null)
            selected.setSelected(false);
        selected = entries.get(i);
        selected.setSelected(true);
        update();
        for (Runnable changeEvent : changeEvents) {
            changeEvent.run();
        }
    }

    public int getSelectedIndex(){
        return entries.indexOf(selected);
    }

    @Override
    public boolean mouseReleased(int mouseX, int mouseY) {
        return false;
    }

    @Override
    public boolean mouseDragged(int mouseX, int mouseY) {
        return false;
    }

    @Override
    public boolean mouseMoved(int mouseX, int mouseY) {
        return false;
    }

    @Override
    public boolean mouseWheelMoved(int mouseX, int mouseY, int scrollAmount) {
        int max = (entries.size() * itemHeight - height) * -1;
        int newOff = offset - scrollAmount * 10;
        offset = Math.min(0, Math.max(max, newOff));
        update();
        return true;
    }

    @Override
    protected void paint(IGraphics graphics) {
        int start = (int) Math.floor((offset * -1) / itemHeight);
        int max = (int) Math.ceil(height/itemHeight)+2;
        for (int i = start; i < Math.min(start+max,entries.size()); i++) {
            entries.get(i).renderEntry(graphics, i * itemHeight + offset);
        }
    }

    public abstract class Entry extends Widget {

        public Entry(Widget parent, int width, int height) {
            super(parent, 0, 0, width, height);
        }

        @Override
        public void setSelected(boolean selected) {
            super.setSelected(selected);
            update();
        }

        public final void renderEntry(IGraphics graphics, int y) {
            graphics.drawImage(buffer, 0, y, width, height);
        }
    }
}

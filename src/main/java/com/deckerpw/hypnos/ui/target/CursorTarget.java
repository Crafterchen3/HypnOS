package com.deckerpw.hypnos.ui.target;

import com.deckerpw.hypnos.Registry;
import com.deckerpw.hypnos.render.IGraphics;
import com.deckerpw.hypnos.swing.SwingWindow;
import com.deckerpw.hypnos.ui.widget.Cursor;

import java.awt.event.MouseEvent;

public abstract class CursorTarget implements Target {

    public Cursor cursor = Registry.CURSOR_DARK; //TODO add Setting
    public int mouseX = 0;
    public int mouseY = 0;
    protected SwingWindow bridge;

    public CursorTarget(SwingWindow bridge) {
        this.bridge = bridge;
    }

    public void mouseDragged(MouseEvent e) {
        float scale = bridge.getScale();
        mouseX = (int) Math.floor((double) e.getX() / scale);
        mouseY = (int) Math.floor((double) e.getY() / scale);
        //update();
    }

    public void mouseMoved(MouseEvent e) {
        float scale = bridge.getScale();
        mouseX = (int) Math.floor(e.getX() / scale);
        mouseY = (int) Math.floor(e.getY() / scale);
        cursor.setState(0);
        //update();
    }

    protected void paintCursor(IGraphics graphics){
        cursor.paint(graphics, mouseX, mouseY);
    }
}

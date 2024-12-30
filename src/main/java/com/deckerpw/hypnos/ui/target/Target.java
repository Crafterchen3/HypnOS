package com.deckerpw.hypnos.ui.target;

import com.deckerpw.hypnos.render.IGraphics;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public interface Target {

    void mousePressed(MouseEvent e);
    void mouseReleased(MouseEvent e);
    void mouseDragged(MouseEvent e);
    void mouseMoved(MouseEvent e);
    void mouseWheelMoved(MouseWheelEvent e);
    void keyTyped(KeyEvent e);
    void keyPressed(KeyEvent e);
    void keyReleased(KeyEvent e);
    void paint(IGraphics g);
    void update();
}

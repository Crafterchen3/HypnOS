package com.deckerpw.hypnos.ui.window;

import com.deckerpw.hypnos.Registry;
import com.deckerpw.hypnos.swing.MainPanel;
import com.deckerpw.hypnos.ui.element.CursorElement;
import com.deckerpw.hypnos.ui.element.Image;
import com.deckerpw.hypnos.ui.element.TextBox;
import com.deckerpw.hypnos.ui.element.TextButton;

import java.awt.*;

public class LogOffWindow extends Window {
    public LogOffWindow(MainPanel panel, int x, int y, CursorElement cursor) {
        super(panel, Registry.POPUP_PANE, x, y, 225, 72, cursor, "LOG OFF?");
        elements.add(new TextButton(55, 45, 47, 21, "YES", Registry.DEFAULT_BUTTON, new Runnable() {
            @Override
            public void run() {
                System.exit(0);
            }
        }, font));
        elements.add(new TextButton(121, 45, 47, 21, "NO", Registry.DEFAULT_BUTTON, new Runnable() {
            @Override
            public void run() {
                panel.removeWindow(LogOffWindow.this);
            }
        }, font));
        elements.add(new Image(11, 16, Registry.WARNING));
        elements.add(new TextBox(42, 15, 160, "Are you sure you want to log off of HypnOS?", font, new Color(0xFF5E4280)));
        Registry.ALERT_WINDOW.playSound();
    }

    public LogOffWindow(MainPanel panel, CursorElement cursor) {
        this(panel, (480 / 2) - (225 / 2), (270 / 2) - (72 / 2), cursor);
    }
}

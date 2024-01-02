package com.deckerpw.hypnos.ui.window;

import com.deckerpw.hypnos.Colors;
import com.deckerpw.hypnos.HypnOS;
import com.deckerpw.hypnos.Registry;
import com.deckerpw.hypnos.swing.Screen;
import com.deckerpw.hypnos.ui.element.Cursor;
import com.deckerpw.hypnos.ui.element.Image;
import com.deckerpw.hypnos.ui.element.TextBox;
import com.deckerpw.hypnos.ui.element.TextButton;

public class LogOffWindow extends Window {
    public LogOffWindow(Screen panel, int x, int y, Cursor cursor) {
        super(panel, Registry.POPUP_PANE, x, y, 225, 72, cursor, "LOG OFF?");
        elements.add(new TextButton(55, 45, 47, 21, "YES", Registry.DEFAULT_BUTTON, new Runnable() {
            @Override
            public void run() {
                HypnOS.exit();
            }
        }, font));
        elements.add(new TextButton(121, 45, 47, 21, "NO", Registry.DEFAULT_BUTTON, new Runnable() {
            @Override
            public void run() {
                Registry.CLOSE_WINDOW.playSound();
                panel.removeWindow(LogOffWindow.this);
            }
        }, font));
        elements.add(new Image(11, 16, Registry.WARNING));
        elements.add(new TextBox(42, 15, 160, "Are you sure you want to log off of HypnOS?", font, Colors.TEXT_COLOR));
    }

    public LogOffWindow(Screen panel, Cursor cursor) {
        this(panel, (480 / 2) - (225 / 2), (270 / 2) - (72 / 2), cursor);
    }
}

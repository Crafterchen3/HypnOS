package com.deckerpw.hypnos.ui.window;

import com.deckerpw.hypnos.Colors;
import com.deckerpw.hypnos.HypnOS;
import com.deckerpw.hypnos.Registry;
import com.deckerpw.hypnos.ui.Screen;
import com.deckerpw.hypnos.ui.widget.Cursor;
import com.deckerpw.hypnos.ui.widget.Image;
import com.deckerpw.hypnos.ui.widget.TextBox;
import com.deckerpw.hypnos.ui.widget.TextButton;

public class LogOffWindow extends Window {
    public LogOffWindow(Screen screen, int x, int y, Cursor cursor) {
        super(screen, Registry.POPUP_PANE, x, y, 225, 72, cursor, "LOG OFF?");
        addWidget(new TextButton(55, 45, 47, 21, "YES", Registry.DEFAULT_BUTTON, HypnOS::exit, font));
        addWidget(new TextButton(121, 45, 47, 21, "NO", Registry.DEFAULT_BUTTON, this::closeWindow, font));
        addWidget(new Image(11, 16, Registry.WARNING));
        addWidget(new TextBox(42, 15, 160, "Are you sure you want to log off of HypnOS?", font, Colors.TEXT_COLOR));
    }

    public LogOffWindow(Screen screen, Cursor cursor) {
        this(screen, (480 / 2) - (225 / 2), (270 / 2) - (72 / 2), cursor);
    }
}

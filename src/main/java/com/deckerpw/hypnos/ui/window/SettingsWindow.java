package com.deckerpw.hypnos.ui.window;

import com.deckerpw.hypnos.Registry;
import com.deckerpw.hypnos.swing.MainPanel;
import com.deckerpw.hypnos.ui.element.CursorElement;

public class SettingsWindow extends Window {

    public SettingsWindow(MainPanel panel, int x, int y, CursorElement cursor) {
        super(panel, Registry.WINDOW_PANE, x, y, 306, 164, cursor, "SETTINGS", Registry.SETTINGS_ICON);
    }

}

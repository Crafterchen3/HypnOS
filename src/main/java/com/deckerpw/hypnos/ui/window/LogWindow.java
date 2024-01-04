package com.deckerpw.hypnos.ui.window;

import com.deckerpw.hypnos.HypnOS;
import com.deckerpw.hypnos.Registry;
import com.deckerpw.hypnos.swing.Screen;
import com.deckerpw.hypnos.ui.element.Cursor;
import com.deckerpw.hypnos.ui.element.ScrollTextBox;
import com.deckerpw.hypnos.ui.element.TextBox;

import java.awt.*;

public class LogWindow extends Window {

    static LogWindow instance;
    TextBox box;
    public LogWindow(Screen screen, Cursor cursor) {
        super(screen, Registry.LOG_PANE, 307, 5, 170, 260, cursor, "LOG");
        instance = this;
        box = new ScrollTextBox(7, 25, width - 14, height - 30, "Loading Log", font, Color.WHITE);
        box.lines.clear();
        for (String line : HypnOS.logger.lines) {
            box.genLines(line);
        }
        addElement(box);
    }

    public static LogWindow getInstance() {
        return instance;
    }

    public void add(String line) {
        box.genLines(line);
    }
}

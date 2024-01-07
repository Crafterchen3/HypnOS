package com.deckerpw.hypnos.ui.window;

import com.deckerpw.hypnos.Colors;
import com.deckerpw.hypnos.Registry;
import com.deckerpw.hypnos.ui.Screen;
import com.deckerpw.hypnos.ui.widget.Cursor;
import com.deckerpw.hypnos.ui.widget.Image;
import com.deckerpw.hypnos.ui.widget.TextBox;
import com.deckerpw.hypnos.ui.widget.TextButton;

public class Popup {

    public static void openPopup(Screen screen, Cursor cursor, String text) {
        screen.addWindow(new InfoPopup(screen, cursor, text), Registry.ALERT_WINDOW);
    }

    public static void openPopup(String text) {
        Screen screen = Screen.getInstance();
        Cursor cursor = Cursor.getInstance();
        openPopup(screen, cursor, text);
    }

    private static class InfoPopup extends Window {

        public InfoPopup(Screen screen, Cursor cursor, String text) {
            super(screen, Registry.POPUP_PANE, (480 / 2) - (225 / 2), (270 / 2) - (72 / 2), 225, 72, cursor, "WARNING");
            addWidget(new TextBox(42, 15, 160, text, font, Colors.TEXT_COLOR));
            addWidget(new Image(11, 16, Registry.WARNING));
            addWidget(new TextButton(width / 2 - (47 / 2), 45, 47, 21, "OKAY", Registry.DEFAULT_BUTTON, this::closeWindow, font));
        }
    }

}

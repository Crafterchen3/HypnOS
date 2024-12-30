package com.deckerpw.hypnos.ui.window;

import com.deckerpw.hypnos.Colors;
import com.deckerpw.hypnos.Registry;
import com.deckerpw.hypnos.ui.target.Screen;
import com.deckerpw.hypnos.ui.widget.Image;
import com.deckerpw.hypnos.ui.widget.TextBox;
import com.deckerpw.hypnos.ui.widget.TextButton;
import com.deckerpw.hypnos.ui.widget.WindowManager;

public class Popup {

    public static void openPopup(WindowManager windowManager, String text) {
        windowManager.addWindow(new InfoPopup(windowManager, text), Registry.ALERT_WINDOW);
    }

    public static void openPopup(String text) {
        Screen screen = Screen.getInstance();
        openPopup(screen.getWindowManager(), text);
    }

    private static class InfoPopup extends Window {

        public InfoPopup(WindowManager windowManager, String text) {
            super(windowManager, Registry.POPUP_PANE, (480 / 2) - (225 / 2), (270 / 2) - (72 / 2), 225, 72, "WARNING");
            addWidget(new TextBox(this,42, 15, 160, text, font, Colors.TEXT_COLOR));
            addWidget(new Image(this,11, 16, Registry.WARNING));
            addWidget(new TextButton(this,width / 2 - (47 / 2), 45, 47, 21, "OKAY", Registry.DEFAULT_BUTTON, this::closeWindow, font));
        }
    }

}

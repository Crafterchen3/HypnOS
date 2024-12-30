package com.deckerpw.hypnos.ui.window;

import com.deckerpw.hypnos.Colors;
import com.deckerpw.hypnos.Registry;
import com.deckerpw.hypnos.ui.widget.*;


public abstract class PasswordPopup extends Window{

    private PasswordBox password;

    public PasswordPopup(WindowManager windowManager, int x, int y, String text) {
        super(windowManager, Registry.POPUP_PANE, x, y, 225, 72, "ADMIN");
        addWidget(new Image(this,11, 16, Registry.WARNING));
        addWidget(new TextBox(this,42, 15, 160 , text, font, Colors.TEXT_COLOR));
        password = new PasswordBox(this, 42, 26, "PASSWORD", Registry.HYPNOFONT_0N, EditBox.EVERYTHING);
        password.setOnReturn(this::finish);
        addWidget(password);
        selectWidget(password);
        addWidget(new TextButton(this, 158, 23, 47, 21, "OKAY",Registry.DEFAULT_BUTTON, this::finish, font));
    }

    public PasswordPopup(WindowManager windowManager, String text) {
        this(windowManager, (480 / 2) - (225 / 2), (270 / 2) - (72 / 2), text);
    }

    private void finish(){
        if (checkPassword(password.getText())) {
            closeWindow();
            onSuccess(password.getText());
        }else {
            Registry.ALERT_WINDOW.playSound();
            password.setText("");
            selectWidget(password);
        }
    }

    protected abstract boolean checkPassword(String password);

    protected abstract void onSuccess(String password);
}

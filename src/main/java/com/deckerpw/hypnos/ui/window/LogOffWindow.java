package com.deckerpw.hypnos.ui.window;

import com.deckerpw.hypnos.Registry;
import com.deckerpw.hypnos.machine.User;
import com.deckerpw.hypnos.ui.target.LoginScreen;
import com.deckerpw.hypnos.ui.target.Screen;
import com.deckerpw.hypnos.ui.widget.Button;

public class LogOffWindow extends Window {
    public LogOffWindow(Screen screen, int x, int y) {
        super(screen.getWindowManager(), Registry.CONTEXT_PANE, x, y, 98, 72, "EXIT");
        User user = screen.getUser();
        addWidget(new Button(this, 15,23,29,29, Registry.CONTEXT_SHUTDOWN,() -> {
            user.logout();
            screen.getMachine().stop();
        }));
        addWidget(new Button(this, 54,23,29,29, Registry.CONTEXT_EXIT,() -> {
            user.logout();
            screen.getBridge().switchTarget(new LoginScreen(screen.getBridge()),true);
        }));
    }

    public LogOffWindow(Screen screen) {
        this(screen, (480 / 2) - (98 / 2), (270 / 2) - (72 / 2));
    }
}

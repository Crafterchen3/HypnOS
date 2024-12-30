package com.deckerpw.hypnos.ui.window;

import com.deckerpw.hypnos.Registry;
import com.deckerpw.hypnos.ui.target.Screen;
import com.deckerpw.hypnos.ui.widget.ScrollTextBox;
import com.deckerpw.hypnos.ui.widget.TextBox;
import com.deckerpw.hypnos.util.Logger;

import java.awt.*;

public class LogWindow extends Window {

    private final Logger logger;
    private final Logger.LoggerEventListener eventListener;
    public TextBox box;

    public LogWindow(Screen screen) {
        super(screen.getWindowManager(), Registry.LOG_PANE, 307, 5, 170, 260, "LOG");
        logger = screen.getMachine().getLogger();
        eventListener = this::add;
        logger.addEventListener(eventListener);
        box = new ScrollTextBox(this,7, 25, width - 14, height - 30, "Loading Log", font, Color.WHITE);
        box.lines.clear();
        for (String line : logger.lines) {
            box.genLines(line);
        }
        addWidget(box);
    }

    public void add(String line) {
        box.genLines(line);
    }

    @Override
    public void closeWindow() {
        logger.removeEventListener(eventListener);
        super.closeWindow();
    }
}

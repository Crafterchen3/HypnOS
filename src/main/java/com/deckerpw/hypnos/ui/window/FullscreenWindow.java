package com.deckerpw.hypnos.ui.window;

import com.deckerpw.hypnos.Registry;
import com.deckerpw.hypnos.ui.target.Screen;
import com.deckerpw.hypnos.ui.widget.WindowManager;

import java.awt.image.BufferedImage;

public class FullscreenWindow extends Window{

    public FullscreenWindow(WindowManager windowManager, BufferedImage fullscreenPane, String title) {
        super(windowManager, fullscreenPane, 0, 0, 481, 271, title, Registry.FILE_MANAGER_ICON);
        removeWidget(0); // remove title bar
    }

    public FullscreenWindow(WindowManager windowManager, String title) {
        this(windowManager, Registry.FULLSCREEN_PANE, title);
    }

    @Override
    public boolean isFullscreen() {
        return true;
    }
}

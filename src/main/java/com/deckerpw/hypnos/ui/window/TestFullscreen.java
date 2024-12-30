package com.deckerpw.hypnos.ui.window;

import com.deckerpw.hypnos.ui.target.Screen;
import com.deckerpw.hypnos.ui.widget.Box;
import com.deckerpw.hypnos.ui.widget.WindowManager;

import java.awt.*;
import java.awt.image.BufferedImage;

public class TestFullscreen extends FullscreenWindow{

    private WindowManager windowManager;

    public TestFullscreen(Screen screen, String title) {
        super(screen.getWindowManager(), title);
        windowManager = new WindowManager(this,20,20,width-40,height-40,screen.getMachine());
        addWidget(new Box(this,20,20,width-40,height-40, Color.BLACK));
        addWidget(windowManager);
        windowManager.addWindow(new SettingsWindow(screen, 20, 20),null);
    }
}

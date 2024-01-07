package com.deckerpw.hypnos.util;

import com.deckerpw.hypnos.ui.Desktop;
import com.deckerpw.hypnos.ui.Screen;

public interface Application {

    void OnDesktopInit(Desktop desktop);

    void OnScreenInit(Screen screen);

}

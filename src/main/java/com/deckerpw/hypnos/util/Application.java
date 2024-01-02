package com.deckerpw.hypnos.util;

import com.deckerpw.hypnos.swing.Screen;
import com.deckerpw.hypnos.ui.Desktop;

public interface Application {

    void OnDesktopInit(Desktop desktop);

    void OnPanelInit(Screen panel);

}

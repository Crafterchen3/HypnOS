package com.deckerpw.hypnos.util;

import com.deckerpw.hypnos.swing.MainPanel;
import com.deckerpw.hypnos.ui.Desktop;

public interface Application {

    void OnDesktopInit(Desktop desktop);

    void OnPanelInit(MainPanel panel);

}

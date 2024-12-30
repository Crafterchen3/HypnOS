package com.deckerpw.hypnos.showcase;

import com.deckerpw.hypnos.Registry;
import com.deckerpw.hypnos.filemanager.FileManagerRegistry;
import com.deckerpw.hypnos.filemanager.FileType;
import com.deckerpw.hypnos.filemanager.FileTypeRegistry;
import com.deckerpw.hypnos.ui.widget.DesktopIcon;
import com.deckerpw.hypnos.ui.window.SettingsWindow;
import com.deckerpw.hypnos.util.Module;

public class Showcase extends Module {

    public Showcase() {
    }

    @Override
    public String getId() {
        return "showcase";
    }

    @Override
    public DesktopIcon[] getDesktopIcons() {
        return new DesktopIcon[] {
            ShowcaseRegistry.DESKTOP_ICON
        };
    }
}

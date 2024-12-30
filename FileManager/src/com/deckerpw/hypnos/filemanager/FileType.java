package com.deckerpw.hypnos.filemanager;

import com.deckerpw.hypnos.machine.Machine;

import java.awt.image.BufferedImage;
import java.io.File;

public class FileType {

    public final BufferedImage icon;

    public final FileTypeAction action;

    public FileType(BufferedImage icon, FileTypeAction action) {
        this.icon = icon;
        this.action = action;
    }

    public BufferedImage getIcon() {
        return icon;
    }

    public void run(File file, Machine machine) {
        action.run(file, machine);
    }

    public interface FileTypeAction {
        void run(File file, Machine machine);
    }
}

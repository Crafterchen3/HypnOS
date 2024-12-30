package com.deckerpw.hypnos;

import com.deckerpw.hypnos.machine.Machine;
import com.deckerpw.hypnos.ui.widget.DesktopIcon;
import com.deckerpw.hypnos.util.Module;
import com.deckerpw.hypnos.util.ModuleManager;

import java.nio.file.Paths;

public class LaunchHelper {

    public static void main(String[] args) {
        String path = "";
        if (args.length > 0)
            path = args[0];

        if (path.isEmpty())
            path = Paths.get(System.getProperty("user.home"), "HypnOS").toString();
        launch(path);
    }

    public static void launch(String path) {
        new ModuleManager().loadDirectory("C:/Users/decke/Development/intelliJ/HypnOS/modules");
        Machine machine = new Machine(path);
        machine.runClient();
    }

    public static void launch(String path, Module[] modules) {
        for (Module module : modules) {
            ModuleManager.addModule(module);
        }
        launch(path);
    }

}

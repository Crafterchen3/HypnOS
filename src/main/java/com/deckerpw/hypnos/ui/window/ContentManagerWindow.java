package com.deckerpw.hypnos.ui.window;

import com.deckerpw.hypnos.Registry;
import com.deckerpw.hypnos.machine.Machine;
import com.deckerpw.hypnos.ui.widget.Box;
import com.deckerpw.hypnos.ui.widget.SimpleSelectionList;
import com.deckerpw.hypnos.ui.widget.TextButton;
import com.deckerpw.hypnos.ui.widget.WindowManager;
import com.deckerpw.hypnos.util.Module;
import com.deckerpw.hypnos.util.ModuleManager;

import java.awt.*;
import java.util.ArrayList;

public class ContentManagerWindow extends TabWindow {

    private TextButton button;
    private boolean disable;

    public ContentManagerWindow(WindowManager windowManager, Machine machine) {
        super(windowManager, Registry.WINDOW_PANE, 20, 20, 306, 164, "CONTENT MANAGER", null, new String[]{
                "MODULES"
        });
        Module[] modules = ModuleManager.getModules().toArray(new Module[0]);
        Module[] enabledModules = machine.getModules();
        String[] moduleNames = new String[modules.length];
        for (int i = 0; i < modules.length; i++) {
            moduleNames[i] = modules[i].getId();
        }

        addWidgetSelectedTab(new Box(container, 11, 25, 114, 132, new Color(0x13061F)), 0);
        disable = false;
        SimpleSelectionList moduleSelectionList = new SimpleSelectionList(container, 12, 26, 112, 130, moduleNames);
        button = new TextButton(container, 131, 136, 47, 21, "ENABLE", Registry.DEFAULT_BUTTON, () -> {
            if (disable) {
                machine.disableModule(modules[moduleSelectionList.getSelectedIndex()]);
                disable = false;
                button.text = "ENABLE";
                button.update();
            }
            else {
                machine.enableModule(modules[moduleSelectionList.getSelectedIndex()]);
                disable = true;
                button.text = "DISABLE";
                button.update();
            }
        }, font);
        moduleSelectionList.addEventListener(() -> {
            Module module = modules[moduleSelectionList.getSelectedIndex()];
            for (Module enabledModule : enabledModules) {
                if (module.equals(enabledModule)) {
                    disable = true;
                    button.text = "DISABLE";
                    button.update();
                    return;
                }
            }
            disable = false;
            button.text = "ENABLE";
            button.update();
        });

        addWidgetSelectedTab(moduleSelectionList,0);

        addWidgetSelectedTab(button, 0);
    }
}

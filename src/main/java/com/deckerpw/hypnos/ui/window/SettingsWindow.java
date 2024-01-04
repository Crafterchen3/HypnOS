package com.deckerpw.hypnos.ui.window;

import com.deckerpw.hypnos.Colors;
import com.deckerpw.hypnos.HypnOS;
import com.deckerpw.hypnos.Registry;
import com.deckerpw.hypnos.Settings;
import com.deckerpw.hypnos.render.IGraphics;
import com.deckerpw.hypnos.swing.Screen;
import com.deckerpw.hypnos.ui.element.Checkbox;
import com.deckerpw.hypnos.ui.element.Cursor;
import com.deckerpw.hypnos.ui.element.*;
import com.deckerpw.hypnos.util.SizeableImage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class SettingsWindow extends TabWindow {

    private final Slider musicVolume;
    private final Slider sfxVolume;
    private final Checkbox mouseSFX;
    private final Checkbox keyboardSFX;
    private final SizeableImage win;
    private final EditBox width;
    private final EditBox height;
    private final Checkbox autoLog;
    private final WallpaperSelector wallpaperSelector;
    private final Checkbox intro;

    public SettingsWindow(Screen screen, int x, int y, Cursor cursor) {
        super(screen, Registry.WINDOW_PANE, x, y, 306, 164, cursor, "SETTINGS", Registry.SETTINGS_ICON, new String[]{
                "AUDIO",
                "DEBUG",
                "DISPLAY"
        });
        //(this.elements.add(new TextBox(7,25,width-14,
        //        "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.",
        //        font, Color.BLACK));
        BufferedImage pop = Registry.POPUP_WINDOW;
        win = new SizeableImage(
                pop.getSubimage(0, 0, 5, 11),
                pop.getSubimage(24, 0, 22, 19),
                pop.getSubimage(0, 21, 5, 4),
                pop.getSubimage(41, 21, 5, 4),
                pop.getSubimage(5, 0, 1, 11),
                pop.getSubimage(5, 21, 1, 4),
                pop.getSubimage(0, 11, 5, 1),
                pop.getSubimage(41, 19, 5, 1),
                new Color(0xFF9BC7F5)
        );
        Settings settings = HypnOS.settings;
        musicVolume = new Slider(8, 25, "MUSIC VOLUME", font, settings.jsonObject.getInt("music_volume"));
        addElement(musicVolume);
        sfxVolume = new Slider(8, 60, "SOUND VOLUME", font, settings.jsonObject.getInt("sfx_volume"));
        addElement(sfxVolume);
        addElement(new TextButton(198, 136, 47, 21, "APPLY", Registry.DEFAULT_BUTTON, this::ApplySettings, font));
        addElement(new TextButton(251, 136, 47, 21, "CANCEL", Registry.DEFAULT_BUTTON, this::closeWindow, font));
        keyboardSFX = new Checkbox(282, 28, font, "KEY SFX", Registry.CHECKBOX, settings.jsonObject.getBoolean("keyboard_sfx"));
        addElement(keyboardSFX);
        mouseSFX = new Checkbox(282, 48, font, "MOUSE SFX", Registry.CHECKBOX, settings.jsonObject.getBoolean("mouse_sfx"));
        addElement(mouseSFX);
        refreshCurrentTab();
        width = new EditBox(11, 25, "WIDTH", font);
        addElement(width, 1);
        height = new EditBox(11, 50, "HEIGHT", font);
        addElement(height, 1);
        addElement(new TextButton(11, 136, 47, 21, "OPEN", Registry.DEFAULT_BUTTON, () -> {
            BufferedImage pane = win.genImage(Integer.parseInt(width.getText()), Integer.parseInt(height.getText()));
            Window window = new Window(screen, pane, 20, 20, pane.getWidth(), pane.getHeight(), cursor, "TEST") {
                @Override
                public void paint(IGraphics g) {
                    super.paint(g);
                    font.drawString("X: " + x + ", Y:" + y, x + 20, y + 20, Colors.TEXT_COLOR, g);
                }
            };
            screen.addWindow(window);
        }, font), 1);
        addElement(new TextButton(11 + 53, 136, 47, 21, "SAVE", Registry.DEFAULT_BUTTON, () -> {
            BufferedImage pane = win.genImage(Integer.parseInt(width.getText()), Integer.parseInt(height.getText()));
            File outputfile = new File(Paths.get(HypnOS.Path, "output.png").toString());
            try {
                ImageIO.write(pane, "png", outputfile);
                HypnOS.logger.println("Written image to: " + outputfile.getPath());
            } catch (IOException e) {
                HypnOS.logger.println("Failed to write image to: " + outputfile.getPath());
            }
        }, font), 1);
        autoLog = new Checkbox(282, 28, font, "AUTOSTART LOG", Registry.CHECKBOX, settings.jsonObject.getBoolean("autostart_log"));
        addElement(autoLog, 1);
        intro = new Checkbox(282, 48, font, "SHOW INTRO", Registry.CHECKBOX, settings.jsonObject.getBoolean("intro"));
        addElement(intro, 1);
        addElement(new TextButton(198, 136, 47, 21, "APPLY", Registry.DEFAULT_BUTTON, this::ApplySettings, font), 1);
        addElement(new TextButton(251, 136, 47, 21, "CANCEL", Registry.DEFAULT_BUTTON, this::closeWindow, font), 1);
        wallpaperSelector = new WallpaperSelector(157, 36, Registry.WALLPAPERS);
        addElement(wallpaperSelector, 2);
        addElement(new TextButton(198, 136, 47, 21, "APPLY", Registry.DEFAULT_BUTTON, this::ApplySettings, font), 2);
        addElement(new TextButton(251, 136, 47, 21, "CANCEL", Registry.DEFAULT_BUTTON, this::closeWindow, font), 2);

    }

    private void ApplySettings() {
        Settings settings = HypnOS.settings;
        settings.jsonObject.put("music_volume", musicVolume.getValue());
        settings.jsonObject.put("sfx_volume", sfxVolume.getValue());
        settings.updateVolume();
        settings.jsonObject.put("keyboard_sfx", keyboardSFX.getState());
        settings.jsonObject.put("mouse_sfx", mouseSFX.getState());
        settings.jsonObject.put("autostart_log", autoLog.getState());
        settings.jsonObject.put("wallpaper", wallpaperSelector.index);
        screen.desktop.wallpaper = Registry.WALLPAPERS[settings.jsonObject.getInt("wallpaper")].wall;
        settings.jsonObject.put("intro", intro.getState());
    }

}

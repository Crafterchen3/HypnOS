package com.deckerpw.hypnos.ui.window;

import com.deckerpw.hypnos.Colors;
import com.deckerpw.hypnos.Registry;
import com.deckerpw.hypnos.config.UserConfig;
import com.deckerpw.hypnos.machine.Machine;
import com.deckerpw.hypnos.machine.User;
import com.deckerpw.hypnos.render.IGraphics;
import com.deckerpw.hypnos.ui.target.Screen;
import com.deckerpw.hypnos.ui.widget.Checkbox;
import com.deckerpw.hypnos.ui.widget.*;
import com.deckerpw.hypnos.ui.widget.Label;
import com.deckerpw.hypnos.util.Logger;
import com.deckerpw.hypnos.util.SizeableImage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class SettingsWindow extends TabWindow {

    private final Screen screen;
    private final Slider musicVolume;
    private final Slider sfxVolume;
    private final Checkbox mouseSFX;
    private final Checkbox keyboardSFX;
    private final SizeableImage win;
    private final SizeableImage win2;
    private final EditBox widthBox;
    private final EditBox heightBox;
    private final EditBox usernameBox;
    private final EditBox passwordBox;
    private final Checkbox autoLog;
    private final WallpaperSelector wallpaperSelector;
    private final Checkbox intro;
    private final User user;
    private final Logger logger;

    public SettingsWindow(Screen screen, int x, int y) {
        super(screen.getWindowManager(), Registry.WINDOW_PANE, x, y, 306, 164, "SETTINGS", Registry.SETTINGS_ICON, new String[]{
                "AUDIO",
                "DEBUG",
                "DISPLAY",
                "USER"
        });
        this.screen = screen;
        this.user = screen.getUser();
        this.logger = screen.getMachine().getLogger();
        //(this.widgets.add(new TextBox(7,25,width-14,
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
        BufferedImage wind = Registry.WINDOW;
        win2 = new SizeableImage(
                wind.getSubimage(0, 0, 21, 21),
                wind.getSubimage(24, 0, 22, 21),
                wind.getSubimage(0, 23, 5, 4),
                wind.getSubimage(41, 23, 5, 4),
                wind.getSubimage(21, 0, 1, 19),
                wind.getSubimage(5, 23, 1, 4),
                wind.getSubimage(0, 21, 5, 1),
                wind.getSubimage(41, 21, 5, 1),
                new Color(0xFF9BC7F5)
        );
        UserConfig userConfig = screen.getUser().getConfig();
        musicVolume = new Slider(container, 8, 25, "MUSIC VOLUME", font, userConfig.getInt("music_volume"));
        addWidgetSelectedTab(musicVolume);
        sfxVolume = new Slider(container, 8, 60, "SOUND VOLUME", font, userConfig.getInt("sfx_volume"));
        addWidgetSelectedTab(sfxVolume);
        addWidgetSelectedTab(new TextButton(container, 198, 136, 47, 21, "APPLY", Registry.DEFAULT_BUTTON, this::ApplySettings, font));
        addWidgetSelectedTab(new TextButton(container, 251, 136, 47, 21, "CANCEL", Registry.DEFAULT_BUTTON, this::closeWindow, font));
        keyboardSFX = new Checkbox(container, 282, 28, font, "KEY SFX", Registry.CHECKBOX, userConfig.getBoolean("keyboard_sfx"));
        addWidgetSelectedTab(keyboardSFX);
        mouseSFX = new Checkbox(container, 282, 48, font, "MOUSE SFX", Registry.CHECKBOX, userConfig.getBoolean("mouse_sfx"));
        addWidgetSelectedTab(mouseSFX);
        refreshCurrentTab();
        widthBox = new EditBox(container, 11, 25, "WIDTH", font, EditBox.NUMBERS);
        addWidgetSelectedTab(widthBox, 1);
        heightBox = new EditBox(container, 11, 50, "HEIGHT", font, EditBox.NUMBERS);
        addWidgetSelectedTab(heightBox, 1);
        addWidgetSelectedTab(new TextButton(container, 11, 136, 47, 21, "OPEN", Registry.DEFAULT_BUTTON, () -> {
            BufferedImage pane = win.genImage(Integer.parseInt(widthBox.getText()), Integer.parseInt(heightBox.getText()));
            Window window = new Window(windowManager, pane, 20, 20, pane.getWidth(), pane.getHeight(), "TEST") {
                @Override
                public void paint(IGraphics graphics) {
                    super.paint(graphics);
                    font.drawString("X: " + x + ", Y:" + y, x + 20, y + 20, Colors.TEXT_COLOR, graphics);
                }
            };
            screen.addWindow(window);
        }, font), 1);
        addWidgetSelectedTab(new TextButton(container, 11 + 53, 136, 47, 21, "SAVE", Registry.DEFAULT_BUTTON, () -> {
            BufferedImage pane = win.genImage(Integer.parseInt(widthBox.getText()), Integer.parseInt(heightBox.getText()));
            File outputfile = new File(Paths.get(screen.getMachine().getPath(), "output.png").toString());
            try {
                ImageIO.write(pane, "png", outputfile);
                logger.println("Written image to: " + outputfile.getPath());
            } catch (IOException e) {
                logger.println("Failed to write image to: " + outputfile.getPath());
            }
        }, font), 1);
        autoLog = new Checkbox(container, 282, 28, font, "AUTOSTART LOG", Registry.CHECKBOX, userConfig.getBoolean("autostart_log"));
        addWidgetSelectedTab(autoLog, 1);
        intro = new Checkbox(container, 282, 48, font, "SHOW INTRO", Registry.CHECKBOX, userConfig.getBoolean("intro"));
        addWidgetSelectedTab(intro, 1);
        addWidgetSelectedTab(new TextButton(container, 198, 136, 47, 21, "APPLY", Registry.DEFAULT_BUTTON, this::ApplySettings, font), 1);
        addWidgetSelectedTab(new TextButton(container, 251, 136, 47, 21, "CANCEL", Registry.DEFAULT_BUTTON, this::closeWindow, font), 1);
        wallpaperSelector = new WallpaperSelector(container, screen.getMachine(), 306 / 2 - (133 / 2), 48, Registry.WALLPAPERS);
        addWidgetSelectedTab(wallpaperSelector, 2);
        addWidgetSelectedTab(new TextButton(container, 198, 136, 47, 21, "APPLY", Registry.DEFAULT_BUTTON, this::ApplySettings, font), 2);
        addWidgetSelectedTab(new TextButton(container, 251, 136, 47, 21, "CANCEL", Registry.DEFAULT_BUTTON, this::closeWindow, font), 2);

        addWidgetSelectedTab(new Label(container, 11, 25, "*Be careful with these settings*", font, Colors.TEXT_COLOR), 3);
        usernameBox = new EditBox(container, 11, 37, "USERNAME", font, EditBox.EVERYTHING);
        usernameBox.setText(screen.getUser().getName());
        addWidgetSelectedTab(usernameBox, 3);
        addWidgetSelectedTab(new TextButton(container, 128, 35, 47, 21, "SET", Registry.DEFAULT_BUTTON, () -> {
            Machine machine = screen.getMachine();
            machine.setUserName(usernameBox.getText());
        }, font), 3);
        passwordBox = new PasswordBox(container, 11, 63, "PASSWORD", font, EditBox.EVERYTHING);
        addWidgetSelectedTab(passwordBox, 3);
        addWidgetSelectedTab(new TextButton(container, 128, 60, 47, 21, "SET", Registry.DEFAULT_BUTTON, () -> {
            Machine machine = screen.getMachine();
            machine.setPassword(passwordBox.getText());
            passwordBox.setText("");
        }, font), 3);
    }

    private void ApplySettings() {
        UserConfig userConfig = screen.getUser().getConfig();
        userConfig.put("music_volume", musicVolume.getValue());
        userConfig.put("sfx_volume", sfxVolume.getValue());
        userConfig.updateVolume();
        userConfig.put("keyboard_sfx", keyboardSFX.getState());
        userConfig.put("mouse_sfx", mouseSFX.getState());
        userConfig.put("autostart_log", autoLog.getState());
        userConfig.put("wallpaper", wallpaperSelector.index);
        screen.setWallpaper(Registry.WALLPAPERS[userConfig.getInt("wallpaper")].wall);
        userConfig.put("intro", intro.getState());
        userConfig.save();
    }

}

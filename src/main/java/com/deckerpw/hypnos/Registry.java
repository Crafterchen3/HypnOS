package com.deckerpw.hypnos;

import com.deckerpw.hypnos.render.Font;
import com.deckerpw.hypnos.swing.Screen;
import com.deckerpw.hypnos.ui.element.Cursor;
import com.deckerpw.hypnos.ui.element.DesktopIcon;
import com.deckerpw.hypnos.ui.window.SettingsWindow;
import com.deckerpw.hypnos.util.Sound;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static com.deckerpw.hypnos.util.Sound.SoundGroup.SFX;

public class Registry {

    //Buttons
    public static final BufferedImage[] CLOSE_BUTTON = new BufferedImage[]{
            getImage("/assets/textures/buttons/close0.png"),
            getImage("/assets/textures/buttons/close1.png")
    };
    public static final BufferedImage[] DEFAULT_BUTTON = new BufferedImage[]{
            getImage("/assets/textures/buttons/button0.png"),
            getImage("/assets/textures/buttons/button1.png")
    };
    public static final BufferedImage[] CHECKBOX = new BufferedImage[]{
            getImage("/assets/textures/buttons/checkbox0.png"),
            getImage("/assets/textures/buttons/checkbox1.png")
    };
    public static final BufferedImage[] TAB_BUTTON = new BufferedImage[]{
            getImage("/assets/textures/buttons/windowtab0.png"),
            getImage("/assets/textures/buttons/windowtab1.png")
    };
    //OS
    public static final BufferedImage WINDOW_PANE = getImage("/assets/textures/os/windowpane_306x164.png");
    public static final BufferedImage POPUP_PANE = getImage("/assets/textures/os/popuppane_225x72.png");
    public static final Sound STARTUP = getSound("/assets/sounds/os/startup.wav", SFX);
    public static final BufferedImage POPUP_WINDOW = getImage("/assets/textures/os/popupwindow.png");
    public static final Sound ALERT_WINDOW = getSound("/assets/sounds/os/alert_window.wav", SFX);
    public static final Sound OPEN_WINDOW = getSound("/assets/sounds/os/openwindow.wav", SFX);
    public static final Sound CLOSE_WINDOW = getSound("/assets/sounds/os/close.wav", SFX);
    public static final Sound[] TAB_SOUNDS = new Sound[]{
            getSound("/assets/sounds/os/tab1.wav", SFX),
            getSound("/assets/sounds/os/tab2.wav", SFX),
            getSound("/assets/sounds/os/tab3.wav", SFX),
            getSound("/assets/sounds/os/tab4.wav", SFX),
    };

    public static final Sound CLICK0 = getSound("/assets/sounds/os/click0.wav", SFX);
    public static final Sound CLICK1 = getSound("/assets/sounds/os/click1.wav", SFX);
    public static final BufferedImage MOVE_BACKGROUND = getImage("/assets/textures/apps/move_background.png");
    //UI
    public static final BufferedImage SLIDER = getImage("/assets/textures/ui/slider.png");
    public static final BufferedImage SLIDER_BG = getImage("/assets/textures/ui/sliderbg1.png");
    public static final BufferedImage EDITBOX = getImage("/assets/textures/ui/editbox.png");
    //Fonts
    public static final Font HYPNOFONT_0N = new Font(getImage("/assets/textures/fonts/hypnofont0n.png"));
    //Wallpapers
    public static final BufferedImage CITY_NIGHT = getImage("/assets/textures/wallpapers/city_02.png");
    public static final BufferedImage GRID = getImage("/assets/textures/wallpapers/grid.png");
    //Cursors
    public static final Cursor CURSOR_DARK = new Cursor(new BufferedImage[]{
            getImage("/assets/textures/cursors/dark/mouse01.png"),
            getImage("/assets/textures/cursors/dark/mouse02.png"),
            getImage("/assets/textures/cursors/dark/mouse03.png"),
            getImage("/assets/textures/cursors/dark/mouse04.png"),
            getImage("/assets/textures/cursors/dark/mouse05.png"),
            getImage("/assets/textures/cursors/dark/mouse06.png"),
            getImage("/assets/textures/cursors/dark/mouse07.png"),});
    //Apps
    public static final DesktopIcon EXPLORER = new DesktopIcon(0, 0, 32, 32, new BufferedImage[]{
            getImage("/assets/textures/apps/explorer1.png"),
            getImage("/assets/textures/apps/explorer2.png"),
            getImage("/assets/textures/apps/explorer3.png")}, "EXPLORER");
    public static final DesktopIcon DOWNLOAD_MANAGER = new DesktopIcon(40, 0, 32, 32, new BufferedImage[]{
            getImage("/assets/textures/apps/downloads1.png"),
            getImage("/assets/textures/apps/downloads2.png"),
            getImage("/assets/textures/apps/downloads3.png")}, "DOWNLOAD_MANAGER");
    public static final DesktopIcon HSPD = new DesktopIcon(80, 0, 32, 32, new BufferedImage[]{
            getImage("/assets/textures/apps/hspd1.png"),
            getImage("/assets/textures/apps/hspd2.png"),
            getImage("/assets/textures/apps/hspd3.png")}, "HSPD");
    public static final DesktopIcon PETS = new DesktopIcon(0, 40, 32, 32, new BufferedImage[]{
            getImage("/assets/textures/apps/pets1.png"),
            getImage("/assets/textures/apps/pets2.png"),
            getImage("/assets/textures/apps/pets3.png")}, "PETS");
    public static final DesktopIcon RECYCLE_BIN = new DesktopIcon(40, 40, 32, 32, new BufferedImage[]{
            getImage("/assets/textures/apps/recyclebin1.png"),
            getImage("/assets/textures/apps/recyclebin2.png"),
            getImage("/assets/textures/apps/recyclebin3.png")}, "RECYCLE_BIN");
    public static final BufferedImage SETTINGS_ICON = getImage("/assets/textures/images/icons/settings.png");
    public static final DesktopIcon SETTINGS = new DesktopIcon(80, 40, 32, 32, new BufferedImage[]{
            getImage("/assets/textures/apps/settings1.png"),
            getImage("/assets/textures/apps/settings2.png"),
            getImage("/assets/textures/apps/settings3.png")}, () -> {
        Screen panel = Screen.getInstance();
        panel.addWindow(new SettingsWindow(panel, 50, 50, panel.cursor));
    }, "SETTINGS");
    public static final DesktopIcon TUNEBOX = new DesktopIcon(0, 80, 32, 32, new BufferedImage[]{
            getImage("/assets/textures/apps/tunebox1.png"),
            getImage("/assets/textures/apps/tunebox2.png"),
            getImage("/assets/textures/apps/tunebox3.png")}, "TUNEBOX");
    //Images
    public static final BufferedImage WARNING = getImage("/assets/textures/images/warning.png");

    private static BufferedImage getImage(String path) {
        try {
            return ImageIO.read(Registry.class.getResource(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Sound getSound(String path, Sound.SoundGroup group) {
        return new Sound(Registry.class.getResource(path), group);
    }

}

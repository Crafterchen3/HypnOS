package com.deckerpw.hypnos;

import com.deckerpw.hypnos.render.Font;
import com.deckerpw.hypnos.ui.Screen;
import com.deckerpw.hypnos.ui.widget.Cursor;
import com.deckerpw.hypnos.ui.widget.DesktopIcon;
import com.deckerpw.hypnos.ui.window.FileManagerWindow;
import com.deckerpw.hypnos.ui.window.LogWindow;
import com.deckerpw.hypnos.ui.window.SettingsWindow;
import com.deckerpw.hypnos.util.Sound;
import com.deckerpw.hypnos.util.Wallpaper;
import org.json.JSONException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

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
    public static final BufferedImage[] SCROLL_UP = new BufferedImage[]{
            getImage("/assets/textures/ui/scrollup1.png"),
            getImage("/assets/textures/ui/scrollup2.png")
    };
    public static final BufferedImage[] SCROLL_DOWN = new BufferedImage[]{
            getImage("/assets/textures/ui/scrolldown1.png"),
            getImage("/assets/textures/ui/scrolldown2.png")
    };
    //OS
    public static final BufferedImage WINDOW_PANE = getImage("/assets/textures/os/windowpane_306x164.png");
    public static final BufferedImage POPUP_PANE = getImage("/assets/textures/os/popuppane_225x72.png");
    public static final BufferedImage LOG_PANE = getImage("/assets/textures/os/logpane.png");
    public static final BufferedImage FILES_PANE = getImage("/assets/textures/os/filespane.png");
    public static final Sound STARTUP = getSound("/assets/sounds/os/startup.wav", SFX);
    public static final BufferedImage POPUP_WINDOW = getImage("/assets/textures/os/popupwindow.png");
    public static final BufferedImage WINDOW = getImage("/assets/textures/os/windowtrans.png");
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
    public static final BufferedImage BACKGROUND_SELECTOR = getImage("/assets/textures/ui/bg_selector.png");
    public static final BufferedImage SCROLL_BAR = getImage("/assets/textures/ui/scrollbar.png");
    //Fonts
    public static final Font HYPNOFONT_0N = new Font(getImage("/assets/textures/fonts/hypnofont0n.png"));
    //Wallpapers
    public static final Wallpaper[] WALLPAPERS = getWallpapers(getImages("/assets/textures/wallpapers/wall", 84));
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
    public static final BufferedImage FILE_MANAGER_ICON = getImage("/assets/textures/images/icons/files.png");
    public static final BufferedImage FILE_ENTRY = getImage("/assets/textures/images/file_explorer/entry.png");
    public static final BufferedImage NEW_FOLDER = getImage("/assets/textures/buttons/newfolder.png");
    public static final DesktopIcon FILE_MANAGER = new DesktopIcon(40, 0, 32, 32, new BufferedImage[]{
            getImage("/assets/textures/apps/files1.png"),
            getImage("/assets/textures/apps/files2.png"),
            getImage("/assets/textures/apps/files3.png")}, () -> {
        Screen screen = Screen.getInstance();
        screen.addWindow(new FileManagerWindow(screen, screen.cursor));

    }, "FILE_MANAGER");
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
        Screen screen = Screen.getInstance();
        try {
            screen.addWindow(new SettingsWindow(screen, 50, 50, screen.cursor));
        } catch (JSONException e) {
            HypnOS.logger.println("JSONException thrown, resetting Settings...");
            HypnOS.settings.loadDefaults();
            screen.addWindow(new SettingsWindow(screen, 50, 50, screen.cursor));
        }
    }, "SETTINGS");
    public static final DesktopIcon TUNEBOX = new DesktopIcon(0, 80, 32, 32, new BufferedImage[]{
            getImage("/assets/textures/apps/tunebox1.png"),
            getImage("/assets/textures/apps/tunebox2.png"),
            getImage("/assets/textures/apps/tunebox3.png")}, "TUNEBOX");
    public static final DesktopIcon LOG = new DesktopIcon(0, 120, 32, 32, new BufferedImage[]{
            getImage("/assets/textures/apps/log1.png"),
            getImage("/assets/textures/apps/log2.png"),
            getImage("/assets/textures/apps/log3.png")}, () -> {
        Screen screen = Screen.getInstance();
        screen.addWindow(new LogWindow(screen, screen.cursor));
    }, "LOG");
    //Images
    public static final BufferedImage WARNING = getImage("/assets/textures/images/warning.png");
    //Gifs
    public static final BufferedImage[] INTRO = getImages("/assets/gifs/intro_logo/image", 22);

    private static BufferedImage getImage(String path) {
        try {
            return ImageIO.read(Registry.class.getResource(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static BufferedImage[] getImages(String folder, int max) {
        BufferedImage[] images = new BufferedImage[max];
        for (int i = 1; i <= max; i++) {
            images[i - 1] = getImage(folder + i + ".png");
        }
        return images;
    }

    private static boolean hasResource(String path) {
        URL url = Registry.class.getResource(path);
        return url != null;
    }

    private static Wallpaper[] getWallpapers(BufferedImage[] images) {
        int newWidth = 120;
        int newHeight = 68;
        Wallpaper[] wallpapers = new Wallpaper[images.length];
        for (int i = 0; i < images.length; i++) {
            BufferedImage inputImage = images[i];
            if (hasResource("/assets/textures/wallpapers/wall" + (i + 1) + "-icon.png")) {
                wallpapers[i] = new Wallpaper(getImage("/assets/textures/wallpapers/wall" + (i + 1) + "-icon.png"), inputImage);
            } else {
                BufferedImage outputImage = new BufferedImage(newWidth, newHeight, inputImage.getType());
                Graphics2D graphics2D = outputImage.createGraphics();

                // Resize using nearest neighbor interpolation
                graphics2D.drawImage(inputImage.getScaledInstance(newWidth, newHeight, Image.SCALE_DEFAULT), 0, 0, newWidth, newHeight, null);
                graphics2D.dispose();

                wallpapers[i] = new Wallpaper(outputImage, inputImage);
            }
        }
        return wallpapers;
    }

    private static Sound getSound(String path, Sound.SoundGroup group) {
        return new Sound(Registry.class.getResource(path), group);
    }

}

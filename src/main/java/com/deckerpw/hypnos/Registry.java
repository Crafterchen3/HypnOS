package com.deckerpw.hypnos;

import com.deckerpw.hypnos.render.Font;
import com.deckerpw.hypnos.ui.target.Screen;
import com.deckerpw.hypnos.ui.widget.Cursor;
import com.deckerpw.hypnos.ui.widget.DesktopIcon;
import com.deckerpw.hypnos.ui.window.ContentManagerWindow;
import com.deckerpw.hypnos.ui.window.LogWindow;
import com.deckerpw.hypnos.ui.window.SettingsWindow;
import com.deckerpw.hypnos.ui.window.TestFullscreen;
import com.deckerpw.hypnos.util.AbstractRegistry;
import com.deckerpw.hypnos.util.Sound;
import com.deckerpw.hypnos.util.Wallpaper;

import java.awt.*;
import java.awt.image.BufferedImage;

import static com.deckerpw.hypnos.util.Sound.SoundGroup.SFX;

public class Registry extends AbstractRegistry {

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
    public static final BufferedImage[] CONTEXT_SHUTDOWN = new BufferedImage[]{
            getImage("/assets/textures/buttons/context_shutdown0.png"),
            getImage("/assets/textures/buttons/context_shutdown1.png")
    };
    public static final BufferedImage[] CONTEXT_EXIT = new BufferedImage[]{
            getImage("/assets/textures/buttons/context_exit0.png"),
            getImage("/assets/textures/buttons/context_exit1.png")
    };
    public static final BufferedImage SHUTDOWN = getImage("/assets/textures/buttons/shutdown.png");
    //OS
    public static final BufferedImage WINDOW_PANE = getImage("/assets/textures/os/windowpane_306x164.png");
    public static final BufferedImage POPUP_PANE = getImage("/assets/textures/os/popuppane_225x72.png");
    public static final BufferedImage LOG_PANE = getImage("/assets/textures/os/logpane.png");
    public static final BufferedImage FILES_PANE = getImage("/assets/textures/os/filespane.png");
    public static final BufferedImage LOGIN_PANE = getImage("/assets/textures/os/loginpane.png");
    public static final BufferedImage FULLSCREEN_PANE = getImage("/assets/textures/os/fullscreenpane.png");
    public static final Sound STARTUP = getSound("/assets/sounds/os/startup.wav", SFX);
    public static final BufferedImage POPUP_WINDOW = getImage("/assets/textures/os/popupwindow.png");
    public static final BufferedImage CONTEXT_PANE = getImage("/assets/textures/os/contextpane.png");
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
    public static final BufferedImage[] EDITBOX = new BufferedImage[]{
            getImage("/assets/textures/ui/editbox1.png"),
            getImage("/assets/textures/ui/editbox2.png")
    };
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
            getImage("/assets/textures/apps/explorer3.png")}, machine -> {
//        for (Application app : HypnOS.apps) {
//            Screen screen = Screen.getInstance();
//            app.unload(screen.desktop, screen);
//        }
//        HypnOS.apps.clear();
//        System.gc();
    }, "EXPLORER");
    public static final BufferedImage FILE_MANAGER_ICON = getImage("/assets/textures/images/icons/files.png");
    public static final BufferedImage FILE_ENTRY = getImage("/assets/textures/images/file_explorer/entry.png");
    public static final BufferedImage NEW_FOLDER = getImage("/assets/textures/buttons/newfolder.png");
    public static final DesktopIcon HSPD = new DesktopIcon(80, 0, 32, 32, new BufferedImage[]{
            getImage("/assets/textures/apps/hspd1.png"),
            getImage("/assets/textures/apps/hspd2.png"),
            getImage("/assets/textures/apps/hspd3.png")}, machine -> {
    }, "HSPD");
    public static final DesktopIcon PETS = new DesktopIcon(0, 40, 32, 32, new BufferedImage[]{
            getImage("/assets/textures/apps/pets1.png"),
            getImage("/assets/textures/apps/pets2.png"),
            getImage("/assets/textures/apps/pets3.png")}, "PETS");
    public static final DesktopIcon RECYCLE_BIN = new DesktopIcon(40, 40, 32, 32, new BufferedImage[]{
            getImage("/assets/textures/apps/recyclebin1.png"),
            getImage("/assets/textures/apps/recyclebin2.png"),
            getImage("/assets/textures/apps/recyclebin3.png")}, "RECYCLE_BIN");
    public static final DesktopIcon ADD_SOFTWARE = new DesktopIcon(120, 0, 32, 32, new BufferedImage[]{
            getImage("/assets/textures/apps/add1.png"),
            getImage("/assets/textures/apps/add2.png"),
            getImage("/assets/textures/apps/add3.png")}, machine -> {
        Screen screen = machine.getCurrentUser().getScreen();
        screen.addWindow(new ContentManagerWindow(screen.getWindowManager(), machine));
    }, "ADD_SOFTWARE");
    public static final BufferedImage SETTINGS_ICON = getImage("/assets/textures/images/icons/settings.png");
    public static final DesktopIcon SETTINGS = new DesktopIcon(80, 40, 32, 32, new BufferedImage[]{
            getImage("/assets/textures/apps/settings1.png"),
            getImage("/assets/textures/apps/settings2.png"),
            getImage("/assets/textures/apps/settings3.png")}, machine -> {
        Screen screen = machine.getCurrentUser().getScreen();
        screen.addWindow(new SettingsWindow(screen, 50, 50));
    }, "SETTINGS");
    public static final DesktopIcon TUNEBOX = new DesktopIcon(0, 80, 32, 32, new BufferedImage[]{
            getImage("/assets/textures/apps/tunebox1.png"),
            getImage("/assets/textures/apps/tunebox2.png"),
            getImage("/assets/textures/apps/tunebox3.png")}, machine -> {
        Screen screen = Screen.getInstance();
        screen.addWindow(new TestFullscreen(screen, "Window"));
    }, "TUNEBOX");
    public static final DesktopIcon LOG = new DesktopIcon(0, 120, 32, 32, new BufferedImage[]{
            getImage("/assets/textures/apps/log1.png"),
            getImage("/assets/textures/apps/log2.png"),
            getImage("/assets/textures/apps/log3.png")}, machine -> {
        Screen screen = Screen.getInstance();
        screen.addWindow(new LogWindow(screen));
    }, "LOG");
    public static final BufferedImage USER_MANAGER_ICON = getImage("/assets/textures/images/icons/users.png");
    public static final BufferedImage USER_MANAGER_APP = getImage("/assets/textures/apps/users.png");
    //Images
    public static final BufferedImage WARNING = getImage("/assets/textures/images/warning.png");
    //Gifs
    public static final BufferedImage[] INTRO = getImages("/assets/gifs/intro_logo/image", 22);

    protected static Wallpaper[] getWallpapers(BufferedImage[] images) {
        int newWidth = 120;
        int newHeight = 68;
        Wallpaper[] wallpapers = new Wallpaper[images.length];
        for (int i = 0; i < images.length; i++) {
            BufferedImage inputImage = images[i];
            BufferedImage outputImage = new BufferedImage(newWidth, newHeight, inputImage.getType());
            Graphics2D graphics2D = outputImage.createGraphics();

            // Resize using nearest neighbor interpolation
            graphics2D.drawImage(inputImage.getScaledInstance(newWidth, newHeight, Image.SCALE_DEFAULT), 0, 0, newWidth, newHeight, null);
            graphics2D.dispose();

            wallpapers[i] = new Wallpaper(outputImage, inputImage);
        }
        return wallpapers;
    }

}

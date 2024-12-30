package com.deckerpw.hypnos.ui.target;

import com.deckerpw.hypnos.machine.Machine;
import com.deckerpw.hypnos.machine.User;
import com.deckerpw.hypnos.render.IGraphics;
import com.deckerpw.hypnos.swing.SwingWindow;
import com.deckerpw.hypnos.Registry;
import com.deckerpw.hypnos.ui.Desktop;
import com.deckerpw.hypnos.ui.widget.Container;
import com.deckerpw.hypnos.ui.widget.Widget;
import com.deckerpw.hypnos.ui.widget.WindowManager;
import com.deckerpw.hypnos.ui.window.LogWindow;
import com.deckerpw.hypnos.ui.window.Window;
import com.deckerpw.hypnos.util.Logger;
import com.deckerpw.hypnos.util.Sound;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;

public class Screen extends CursorTarget {

    private static Screen instance;
    private final SwingWindow bridge;
    private final Desktop desktop;
    private final Machine machine;
    private final Container container;
    private final User user;
    private final Logger logger;
    private final WindowManager windowManager;

    public Screen(SwingWindow bridge) {
        super(bridge);
        this.bridge = bridge;
        this.machine = bridge.getMachine();
        this.user = machine.getCurrentUser();
        this.logger = machine.getLogger();
        this.container = new Container(new Widget(null,1,1,1,1) {
            @Override
            protected void paint(IGraphics graphics) {
                Screen.this.update();
            }
        }, 0, 0, 480, 270);
        this.windowManager = new WindowManager(container, 0, 0, 480, 270,machine);
        this.desktop = new Desktop(machine,cursor,container);
        this.container.addWidget(desktop);
        this.container.addWidget(windowManager);
        logger.println("Starting Screen...");
        instance = this;
        Registry.STARTUP.playSound();
        if (user.getConfig().getBoolean("autostart_log")) addWindow(new LogWindow(this));
//        if (user.getConfig().jsonObject.getBoolean("intro")) {
//            splashScreen = new Gif(null,0, 0, 480, 270, Registry.INTRO, 10);
//            splashScreen.start();
//        } else {
//            init();
//        }
    }

    public Machine getMachine() {
        return machine;
    }

    public User getUser() {
        return user;
    }

    @Deprecated(forRemoval = true)
    public static Screen getInstance() {
        return instance;
    }

    public void update() {
        bridge.repaint();
    }

    public void mousePressed(MouseEvent e) {
        if (user.getConfig().getBoolean("mouse_sfx")) Registry.CLICK0.playSound();
        container.mousePressed(mouseX, mouseY);
        update();
    }

    public void mouseReleased(MouseEvent e) {
        if (user.getConfig().getBoolean("mouse_sfx")) Registry.CLICK1.playSound();
        container.mouseReleased(mouseX, mouseY);
        update();
    }

    public void mouseDragged(MouseEvent e) {
        super.mouseDragged(e);
        container.mouseDragged(mouseX, mouseY);
        update();
    }

    public void mouseMoved(MouseEvent e) {
        super.mouseMoved(e);
        container.mouseMoved(mouseX, mouseY);
        update();
    }

    public void mouseWheelMoved(MouseWheelEvent e) {
        container.mouseWheelMoved(mouseX, mouseY, e.getScrollAmount()*e.getWheelRotation()/3);
        update();
    }


    public void addWindow(Window window, Sound sound) {
//        Window tup = null;
//        for (Window window1 : this.windows) {
//            if (window1.getClass().equals(window.getClass())) {
//                tup = window1;
//                break;
//            }
//        }
//        if (tup != null) {
//            this.windows.remove(tup);
//            this.windows.add(0, tup);
//        } else {
//
//            sound.playSound();
//            this.windows.add(0, window);
//        }
        windowManager.addWindow(window,sound);
    }

    public void addWindow(Window window) {
        addWindow(window, Registry.OPEN_WINDOW);
    }

    public void removeWindow(Window window) {
        windowManager.removeWindow(window);
    }

    public void paint(IGraphics graphics) {
        container.render(graphics);
        paintCursor(graphics);
    }

    public void keyTyped(KeyEvent e) {
        container.keyTyped(e);
        update();
    }

    public void keyPressed(KeyEvent e) {
        container.keyPressed(e);
        update();

    }

    public void keyReleased(KeyEvent e) {
        container.keyReleased(e);
        update();

    }

    public Desktop getDesktop() {
        return desktop;
    }

    public WindowManager getWindowManager() {
        return windowManager;
    }

    public SwingWindow getBridge() {
        return bridge;
    }

    public void setWallpaper(BufferedImage wallpaper){
        desktop.setWallpaper(wallpaper);
        desktop.update();
    }
}

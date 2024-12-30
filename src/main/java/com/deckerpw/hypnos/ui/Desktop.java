package com.deckerpw.hypnos.ui;

import com.deckerpw.hypnos.Registry;
import com.deckerpw.hypnos.config.UserConfig;
import com.deckerpw.hypnos.machine.Machine;
import com.deckerpw.hypnos.render.Font;
import com.deckerpw.hypnos.render.IGraphics;
import com.deckerpw.hypnos.ui.widget.Clickable;
import com.deckerpw.hypnos.ui.widget.Cursor;
import com.deckerpw.hypnos.ui.widget.DesktopIcon;
import com.deckerpw.hypnos.ui.widget.Widget;
import com.deckerpw.hypnos.util.Module;
import org.json.JSONObject;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Objects;

public class Desktop extends Widget implements Clickable {

    private final ArrayList<DesktopIcon> desktopIcons = new ArrayList<>();
    private final BufferedImage grid = Registry.GRID;
    private final Cursor cursor;
    private final Font font = Registry.HYPNOFONT_0N;
    private BufferedImage wallpaper;
    private DesktopIcon pressedIcon;
    private DesktopIcon selectedIcon;
    private int dragPointX;
    private int dragPointY;
    private DesktopIcon draggingIcon;
    private Machine machine;

    public Desktop(Machine machine, Cursor cursor, Widget parent ,int x, int y, int width, int height) {
        super(parent, x, y, width, height);
        this.machine = machine;
        UserConfig config = machine.getCurrentUser().getConfig();
        wallpaper = Registry.WALLPAPERS[config.getInt("wallpaper")].wall;
        this.cursor = cursor;
        addDesktopIcon(Registry.EXPLORER);
        addDesktopIcon(Registry.HSPD);
        addDesktopIcon(Registry.RECYCLE_BIN);
        //addDesktopIcon(Registry.PETS);
        addDesktopIcon(Registry.SETTINGS);
        addDesktopIcon(Registry.TUNEBOX);
        addDesktopIcon(Registry.LOG);
        addDesktopIcon(Registry.ADD_SOFTWARE);
//        //addDesktopIcon(Registry.FILES);
//        for (Application app : HypnOS.apps) {
//            app.OnDesktopInit(this);
//        }
        //TODO: add icons from config
        for (Module module : machine.getModules()) {
            for (DesktopIcon desktopIcon : module.getDesktopIcons()) {
                addDesktopIcon(desktopIcon);
            }
        }
        for (DesktopIcon desktopIcon : desktopIcons) {
            desktopIcon.init(config, machine);
        }
    }

    public Desktop(Machine machine, Cursor cursor, Widget parent) {
        this(machine, cursor,parent, 0, 0, 480, 270);
    }

    public DesktopIcon addDesktopIcon(DesktopIcon icon) {
        for (DesktopIcon desktopIcon : desktopIcons) {
            if (Objects.equals(desktopIcon.id, icon.id))
                return null;
        }
        DesktopIcon copy = icon.createCopy();
        desktopIcons.add(copy);
        return copy;
    }

    public void removeDesktopIcon(DesktopIcon icon) {
        desktopIcons.remove(icon);
    }

    public boolean mousePressed(int mouseX, int mouseY) {
        for (DesktopIcon icon : desktopIcons) {
            if (icon.isInside(mouseX, mouseY)) {

                pressedIcon = icon;
                pressedIcon.setState(2);
                update();
                return true;
            }
        }
        return false;
    }

    public boolean mouseReleased(int mouseX, int mouseY) {
        if (pressedIcon != null) {
            if (selectedIcon != null)
                selectedIcon.setState(0);
            desktopIcons.remove(pressedIcon);
            desktopIcons.add(0, pressedIcon);
            pressedIcon.dragging = false;
            draggingIcon = null;
            cursor.setState(0);
            selectedIcon = pressedIcon;
            selectedIcon.setState(1);
            pressedIcon = null;
            update();
            return true;
        }
        return false;
    }

    public boolean mouseDragged(int mouseX, int mouseY) {
        if (draggingIcon == null && pressedIcon != null) {
            draggingIcon = pressedIcon;
            draggingIcon.dragging = true;
            cursor.setState(6);
            dragPointX = mouseX - draggingIcon.getX();
            dragPointY = mouseY - draggingIcon.getY();
        }
        if (draggingIcon != null) {
            int newX = (int) Math.floor((mouseX - dragPointX) / 4) * 4;
            int newY = (int) Math.floor((mouseY - dragPointY) / 4) * 4;
            draggingIcon.move(newX, newY);
            update();
            return true;
        }
        return false;
    }

    @Override
    public boolean mouseMoved(int mouseX, int mouseY) {
        return false;
    }

    @Override
    public boolean mouseWheelMoved(int mouseX, int mouseY, int scrollAmount) {
        return false;
    }

    public void paint(IGraphics g) {
        g.drawImage(wallpaper, 0, 0, 480, 270);
        if (draggingIcon != null)
            g.drawImage(grid, 0, 0, 480, 270);
        //font.drawString("HypnOS " + HypnOS.VERSION + " (a Java Desktop inspiered of HypnOS from Hypnospace Outlaw)", 2, 260, Color.WHITE, g);
        for (int i = desktopIcons.size() - 1; i >= 0; i--) {
            desktopIcons.get(i).render(g);
        }
    }

    public JSONObject getIconConfig() {
        JSONObject desktop = new JSONObject();
        for (DesktopIcon desktopIcon : desktopIcons) {
            JSONObject icon = new JSONObject();
            icon.put("x", desktopIcon.getX());
            icon.put("y", desktopIcon.getY());
            desktop.put(desktopIcon.id, icon);
        }
        return desktop;
    }

    public void setWallpaper(BufferedImage wallpaper) {
        this.wallpaper = wallpaper;
    }

    public void updateIcons() {
        for (Module module : machine.getModules()) {
            for (DesktopIcon desktopIcon : module.getDesktopIcons()) {
                DesktopIcon icon = addDesktopIcon(desktopIcon);
                if (icon != null)
                    icon.init(machine.getCurrentUser().getConfig(), machine);
            }
        }
        update();
    }
}

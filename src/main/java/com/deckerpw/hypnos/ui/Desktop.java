package com.deckerpw.hypnos.ui;

import com.deckerpw.hypnos.HypnOS;
import com.deckerpw.hypnos.Registry;
import com.deckerpw.hypnos.render.Font;
import com.deckerpw.hypnos.render.IGraphics;
import com.deckerpw.hypnos.ui.element.CursorElement;
import com.deckerpw.hypnos.ui.element.DesktopIcon;
import com.deckerpw.hypnos.util.Application;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Desktop {

    private final ArrayList<DesktopIcon> desktopIcons = new ArrayList<>();
    private final BufferedImage wallpaper = Registry.CITY_NIGHT;
    private final BufferedImage grid = Registry.GRID;
    private final CursorElement cursor;
    private final Font font = Registry.HYPNOFONT_0N;
    private DesktopIcon pressedIcon;
    private DesktopIcon selectedIcon;
    private int dragPointX;
    private int dragPointY;
    private DesktopIcon draggingIcon;

    public Desktop(CursorElement cursor) {
        this.cursor = cursor;
        addDesktopIcon(Registry.EXPLORER);
        addDesktopIcon(Registry.DOWNLOAD_MANAGER);
        addDesktopIcon(Registry.HSPD);
        addDesktopIcon(Registry.RECYCLE_BIN);
        addDesktopIcon(Registry.PETS);
        addDesktopIcon(Registry.SETTINGS);
        addDesktopIcon(Registry.TUNEBOX);
        for (Application app : HypnOS.mods) {
            app.OnDesktopInit(this);
        }

    }

    public void addDesktopIcon(DesktopIcon icon) {
        desktopIcons.add(icon);
    }

    public boolean mousePressed(int mouseX, int mouseY) {
        for (DesktopIcon icon : desktopIcons) {
            if (icon.isInside(mouseX, mouseY)) {

                pressedIcon = icon;
                pressedIcon.setState(2);
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
            return true;
        }
        return false;
    }

    public boolean mouseDragged(int mouseX, int mouseY) {
        if (draggingIcon == null && pressedIcon != null) {
            draggingIcon = pressedIcon;
            draggingIcon.dragging = true;
            cursor.setState(6);
            dragPointX = mouseX - draggingIcon.x;
            dragPointY = mouseY - draggingIcon.y;
        }
        if (draggingIcon != null) {
            int newX = (int) Math.floor((mouseX - dragPointX) / 4) * 4;
            int newY = (int) Math.floor((mouseY - dragPointY) / 4) * 4;
            draggingIcon.move(newX, newY);
            return true;
        }
        return false;
    }

    public void paint(IGraphics g) {
        g.drawImage(wallpaper, 0, 0, 480, 270);
        if (draggingIcon != null)
            g.drawImage(grid, 0, 0, 480, 270);
        font.drawString("HypnOS " + HypnOS.VERSION + " (a Java Desktop inspiered of HypnOS from Hypnospace Outlaw)", 2, 260, Color.black, g);
        for (int i = desktopIcons.size() - 1; i >= 0; i--) {
            desktopIcons.get(i).paint(g);
        }
    }

}

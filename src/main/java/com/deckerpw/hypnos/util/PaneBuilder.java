package com.deckerpw.hypnos.util;

import com.deckerpw.hypnos.Registry;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

public class PaneBuilder {

    private static final HashMap<String,BufferedImage> panes = new HashMap<>();
    private static final HashMap<String,BufferedImage> popupPanes = new HashMap<>();

    private static final BufferedImage popupImage = Registry.POPUP_WINDOW;
    private static final SizeableImage popupBuild = new SizeableImage(
            popupImage.getSubimage(0, 0, 5, 11),
            popupImage.getSubimage(24, 0, 22, 19),
            popupImage.getSubimage(0, 21, 5, 4),
            popupImage.getSubimage(41, 21, 5, 4),
            popupImage.getSubimage(5, 0, 1, 11),
            popupImage.getSubimage(5, 21, 1, 4),
            popupImage.getSubimage(0, 11, 5, 1),
            popupImage.getSubimage(41, 19, 5, 1),
            new Color(0xFF9BC7F5)
        );
    private static final BufferedImage windowImage = Registry.WINDOW;
    private static final SizeableImage windowBuild = new SizeableImage(
            windowImage.getSubimage(0, 0, 20, 19),
            windowImage.getSubimage(27, 0, 19, 18),
            windowImage.getSubimage(0, 23, 5, 4),
            windowImage.getSubimage(41, 23, 5, 4),
            windowImage.getSubimage(20, 0, 1, 18),
            windowImage.getSubimage(5, 23, 1, 4),
            windowImage.getSubimage(0, 19, 5, 1),
            windowImage.getSubimage(41, 18, 5, 1),
            new Color(0xFF9BC7F5)
        );

    public static BufferedImage createWindowPane(int width, int height) {
        if (panes.containsKey(width + "x" + height)) {
            return panes.get(width + "x" + height);
        }
        panes.put(width + "x" + height ,windowBuild.genImage(width,height));
        return panes.get(width + "x" + height);
    }

    public static BufferedImage createPopupPane(int width, int height) {
        if (popupPanes.containsKey(width + "x" + height)) {
            return popupPanes.get(width + "x" + height);
        }
        popupPanes.put(width + "x" + height ,popupBuild.genImage(width,height));
        return popupPanes.get(width + "x" + height);
    }

}

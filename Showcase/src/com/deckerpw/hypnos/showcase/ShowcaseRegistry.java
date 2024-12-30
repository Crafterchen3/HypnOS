package com.deckerpw.hypnos.showcase;

import com.deckerpw.hypnos.ui.target.Screen;
import com.deckerpw.hypnos.ui.widget.DesktopIcon;
import com.deckerpw.hypnos.util.AbstractRegistry;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ShowcaseRegistry extends AbstractRegistry {

    public static DesktopIcon DESKTOP_ICON = new DesktopIcon(20,20,32,32, new BufferedImage[]{
            getImage("/assets/textures/showcase/icon.png"),
            getImage("/assets/textures/showcase/icon.png"),
            getImage("/assets/textures/showcase/icon.png")},machine -> {
        Screen screen = machine.getCurrentUser().getScreen();
        screen.addWindow(new ShowcaseWindow(screen.getWindowManager()));
    },"SHOWCASE");

    protected static BufferedImage getImage(String path) {
        try {
            return ImageIO.read(ShowcaseRegistry.class.getResource(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

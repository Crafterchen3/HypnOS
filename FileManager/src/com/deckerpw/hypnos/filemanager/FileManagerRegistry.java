package com.deckerpw.hypnos.filemanager;

import com.deckerpw.hypnos.filemanager.window.FileManagerWindow;
import com.deckerpw.hypnos.ui.target.Screen;
import com.deckerpw.hypnos.ui.widget.DesktopIcon;
import com.deckerpw.hypnos.util.AbstractRegistry;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class FileManagerRegistry extends AbstractRegistry {

    public static DesktopIcon DESKTOP_ICON = new DesktopIcon(20,20,32,32, new BufferedImage[]{
            getImage("/assets/textures/filemanager/icon/files1.png"),
            getImage("/assets/textures/filemanager/icon/files2.png"),
            getImage("/assets/textures/filemanager/icon/files3.png")},machine -> {
        Screen screen = machine.getCurrentUser().getScreen();
        screen.addWindow(new FileManagerWindow(screen.getWindowManager()));
    },"FILE_MANAGER");

    public static BufferedImage WINDOW_PANE = getImage("/assets/textures/filemanager/filemanagerpane.png");

    public static BufferedImage ICON_UNKNOWN = getImage("/assets/textures/filemanager/icon/unknown.png");
    public static BufferedImage ICON_FOLDER = getImage("/assets/textures/filemanager/icon/folder.png");
    public static BufferedImage ICON_TEXT = getImage("/assets/textures/filemanager/icon/textfile.png");
    public static BufferedImage ICON_IMAGE = getImage("/assets/textures/filemanager/icon/image.png");
    public static BufferedImage ICON_AUDIO = getImage("/assets/textures/filemanager/icon/audio.png");
    public static BufferedImage ICON_VIDEO = getImage("/assets/textures/filemanager/icon/video.png");

    protected static BufferedImage getImage(String path) {
        try {
            return ImageIO.read(FileManagerRegistry.class.getResource(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

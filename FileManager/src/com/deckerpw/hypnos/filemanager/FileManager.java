package com.deckerpw.hypnos.filemanager;

import com.deckerpw.hypnos.LaunchHelper;
import com.deckerpw.hypnos.filemanager.window.PictureViewer;
import com.deckerpw.hypnos.filemanager.window.TextViewer;
import com.deckerpw.hypnos.ui.widget.DesktopIcon;
import com.deckerpw.hypnos.util.Module;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class FileManager extends Module {

    public FileManager() {
        FileTypeRegistry.registerFileType(new String[]{"txt", "json", "log"},new FileType(FileManagerRegistry.ICON_TEXT,(file,machine) -> {
                machine.getCurrentUser().getScreen().addWindow(TextViewer.createFromFile(machine.getCurrentUser().getScreen().getWindowManager(), file));
        }));
        FileTypeRegistry.registerFileType(new String[]{"png", "jpeg"},new FileType(FileManagerRegistry.ICON_IMAGE,(file,machine) -> {
            try {
                BufferedImage image = ImageIO.read(file);
                machine.getCurrentUser().getScreen().addWindow(new PictureViewer(machine.getCurrentUser().getScreen().getWindowManager(), image));
            } catch (IOException e) {

            }
        }));
        FileTypeRegistry.registerFileType(new String[]{"mp4", "mkv"},new FileType(FileManagerRegistry.ICON_VIDEO,(file,machine) -> {}));
        FileTypeRegistry.registerFileType(new String[]{"mp3", "wav"},new FileType(FileManagerRegistry.ICON_AUDIO,(file,machine) -> {}));
    }

    public static void main(String[] args) {
        LaunchHelper.launch(args[0], new Module[]{new FileManager()});
    }


    @Override
    public String getId() {
        return "filemanager";
    }

    @Override
    public DesktopIcon[] getDesktopIcons() {
        return new DesktopIcon[]{FileManagerRegistry.DESKTOP_ICON};
    }

}

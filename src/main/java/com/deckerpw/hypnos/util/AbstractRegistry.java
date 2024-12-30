package com.deckerpw.hypnos.util;

import com.deckerpw.hypnos.machine.filesystem.JavaDrive;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public abstract class AbstractRegistry {

    protected static JavaDrive javaDrive = JavaDrive.getJavaDrive();

    protected static BufferedImage getImage(String path) {
        try {
            return ImageIO.read(javaDrive.getURL(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected static BufferedImage[] getImages(String folder, int max) {
        BufferedImage[] images = new BufferedImage[max];
        for (int i = 1; i <= max; i++) {
            images[i - 1] = getImage(folder + i + ".png");
        }
        return images;
    }

    protected static boolean hasResource(String path) {
        File file = javaDrive.getFile(path);
        return file != null;
    }

    protected static Sound getSound(String path, Sound.SoundGroup group) {
        return new Sound(AbstractRegistry.class.getResource(path), group);
    }

}

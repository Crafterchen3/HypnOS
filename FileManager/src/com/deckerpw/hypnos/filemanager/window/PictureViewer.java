package com.deckerpw.hypnos.filemanager.window;

import com.deckerpw.hypnos.render.IGraphics;
import com.deckerpw.hypnos.ui.widget.WindowManager;
import com.deckerpw.hypnos.ui.window.Window;

import java.awt.*;
import java.awt.image.BufferedImage;

public class PictureViewer extends Window {

    private final BufferedImage image;

    public PictureViewer(WindowManager windowManager, int x, int y, BufferedImage simage) {
        super(windowManager, x, y, Math.min(306,Math.max(96,simage.getWidth() + 10)), Math.min(164,Math.max(64,simage.getHeight() + 23)), "Image Viewer");
        int newWidth = Math.min(getWidth() - 10, simage.getWidth());
        int newHeight = Math.min(getHeight() - 23, simage.getHeight());
        this.image = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2D = image.createGraphics();

        // Resize using nearest neighbor interpolation
        graphics2D.drawImage(simage.getScaledInstance(newWidth, newHeight, Image.SCALE_DEFAULT), 0, 0, newWidth, newHeight, null);
        graphics2D.dispose();
    }

    public PictureViewer(WindowManager windowManager, BufferedImage image) {
        this(windowManager, windowManager.getWidth() / 2 - Math.min(306,Math.max(96,image.getWidth() + 10)) / 2, windowManager.getHeight() / 2 - Math.min(164,Math.max(64,image.getHeight() + 23)) / 2, image);
    }

    @Override
    public void paint(IGraphics graphics) {
        super.paint(graphics);
        graphics.drawImage(image, 5, 19, image.getWidth(), image.getHeight());
    }
}

package com.deckerpw.hypnos.ui.element;

import com.deckerpw.hypnos.render.IGraphics;
import com.deckerpw.hypnos.swing.Screen;

import java.awt.image.BufferedImage;

public class Gif extends Element {

    final BufferedImage[] images;
    final int millis;
    Thread thread;
    int index;

    public Gif(int x, int y, int width, int height, BufferedImage[] images, int fps) {
        super(x, y, width, height);
        this.images = images;
        this.index = 0;
        this.millis = (int) (1f / fps * 1000);
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
        }
    }

    public void start() {
        thread = new Thread(() -> {
            while (true) {
                sleep(millis);
                if (index + 1 >= images.length)
                    index = 0;
                else
                    index++;
                Screen.getInstance().updateUI();
            }
        });
        thread.start();
    }

    public void stop() {
        if (thread != null)
            thread.stop();
    }

    @Override
    public void paint(IGraphics graphics) {
        graphics.drawImage(images[index], x, y, width, height);
    }
}

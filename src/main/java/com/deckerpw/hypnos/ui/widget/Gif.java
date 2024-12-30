package com.deckerpw.hypnos.ui.widget;

import com.deckerpw.hypnos.render.IGraphics;
import com.deckerpw.hypnos.ui.target.Screen;

import java.awt.image.BufferedImage;


//TODO redo with Timer instead of Thread
public class Gif extends Widget {

    final BufferedImage[] images;
    final int millis;
    Thread thread;
    int index;

    public Gif(Widget parent, int x, int y, int width, int height, BufferedImage[] images, int fps) {
        super(parent, x, y, width, height);
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
                Screen.getInstance().update();
            }
        });
        thread.start();
    }

    public void stop() {
        if (thread != null)
            thread.interrupt();
    }

    @Override
    public void paint(IGraphics graphics) {
        graphics.drawImage(images[index], 0, 0, width, height);
    }
}

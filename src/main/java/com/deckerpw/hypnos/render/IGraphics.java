package com.deckerpw.hypnos.render;

import java.awt.*;
import java.awt.image.BufferedImage;

public interface IGraphics {

    Graphics2D create(int x, int y, int width, int height);

    void drawImage(BufferedImage image, int x, int y, int width, int height);

}

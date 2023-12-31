package com.deckerpw.hypnos.render;

import java.awt.*;

public interface IGraphics {

    void drawImage(Image image, int x, int y, int width, int height);

    void drawRect(Color color, int x, int y, int width, int height);

}

package com.deckerpw.hypnos.ui.widget;

import com.deckerpw.hypnos.Colors;
import com.deckerpw.hypnos.Registry;
import com.deckerpw.hypnos.render.Font;
import com.deckerpw.hypnos.render.IGraphics;

import java.awt.*;

public class Slider extends Widget implements Clickable {

    final String title;
    final Font font;
    int sliderX = 0;

    public Slider(Widget parent,int x, int y, String title, Font font) {
        super(parent, x, y, 3 + 200 + 4 + 5, 23 + 9);
        this.title = title;
        this.font = font;
    }

    public Slider(Widget parent,int x, int y, String title, Font font, int value) {
        this(parent, x, y, title, font);
        setValue(value);
    }

    @Override
    public void paint(IGraphics g) {
        font.drawString(title, 2, 0, Colors.TEXT_COLOR, g);
        font.drawString(getValue() + "", Math.max(80, sliderX), 0, new Color(0xFFC82828), g);
        g.drawImage(Registry.SLIDER_BG, 3, 9, 200, 23);
        g.drawImage(Registry.SLIDER, sliderX, 9, 8, 18);
    }

    public int getValue() {
        return (int) (sliderX / 199f * 100f);
    }

    public void setValue(int value) {
        sliderX = (int) Math.ceil((value / 100d * 199d));
    }

    @Override
    public boolean mousePressed(int mouseX, int mouseY) {
        sliderX = Math.min(199, Math.max(0, mouseX - 3));
        update();
        return true;
    }

    @Override
    public boolean mouseReleased(int mouseX, int mouseY) {
        return false;
    }

    @Override
    public boolean mouseDragged(int mouseX, int mouseY) {
        sliderX = Math.min(199, Math.max(0, mouseX - 3));
        update();
        return true;
    }

    @Override
    public boolean mouseMoved(int mouseX, int mouseY) {
        return false;
    }

    @Override
    public boolean mouseWheelMoved(int mouseX, int mouseY, int scrollAmount) {
        return false;
    }
}

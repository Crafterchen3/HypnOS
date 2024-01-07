package com.deckerpw.hypnos.ui.widget;

import com.deckerpw.hypnos.Colors;
import com.deckerpw.hypnos.HypnOS;
import com.deckerpw.hypnos.Registry;
import com.deckerpw.hypnos.render.IGraphics;
import com.deckerpw.hypnos.render.PositionedGraphics;
import com.deckerpw.hypnos.util.Wallpaper;

public class WallpaperSelector extends Widget implements Clickable {

    public final Wallpaper[] wallpapers;
    private final Button scrollUp;
    public int index;
    public Button scrollDown;
    public ScrollBar scrollBar;
    private Clickable selected;

    public WallpaperSelector(int x, int y, Wallpaper[] wallpapers) {
        super(x, y, 133, 72);
        this.wallpapers = wallpapers;
        this.scrollUp = new Button(124, 0, 9, 10, Registry.SCROLL_UP, () -> {
            setIndex(index - 1);
        });
        this.scrollDown = new Button(124, 62, 9, 10, Registry.SCROLL_DOWN, () -> {
            setIndex(index + 1);
        });
        this.scrollBar = new ScrollBar(124, 10);
        setIndex(HypnOS.settings.jsonObject.getInt("wallpaper"));
    }

    void setIndex(int i) {
        index = Math.min(wallpapers.length - 1, Math.max(0, i));
        float y = 10 + (25 * ((float) index / (wallpapers.length - 1)));
        scrollBar.y = (int) y;
    }

    @Override
    public void paint(IGraphics graphics) {
        Registry.HYPNOFONT_0N.drawCenteredString("WALLPAPER:", x + (width / 2), y - 10, Colors.TEXT_COLOR, graphics);
        PositionedGraphics graphics1 = new PositionedGraphics(graphics, this);
        graphics1.drawImage(Registry.BACKGROUND_SELECTOR, 0, 0, width, height);
        graphics1.drawImage(wallpapers[index].icon, 2, 2, 120, 68);
        scrollUp.paint(graphics1);
        scrollDown.paint(graphics1);
        scrollBar.paint(graphics1);
    }

    @Override
    public boolean mousePressed(int mouseX, int mouseY) {
        if (scrollUp.isInside(mouseX - x, mouseY - y))
            selected = scrollUp;
        else if (scrollDown.isInside(mouseX - x, mouseY - y))
            selected = scrollDown;
        else if (scrollBar.isInside(mouseX - x, mouseY - y))
            selected = scrollBar;
        if (selected != null)
            return selected.mousePressed(mouseX - x, mouseY - y);
        return false;
    }

    @Override
    public boolean mouseReleased(int mouseX, int mouseY) {
        boolean rv = false;
        if (selected != null)
            rv = selected.mouseReleased(mouseX - x, mouseY - y);
        selected = null;
        return rv;
    }

    @Override
    public boolean mouseDragged(int mouseX, int mouseY) {
        if (selected != null)
            return selected.mouseDragged(mouseX - x, mouseY - y);
        return false;
    }

    @Override
    public boolean mouseMoved(int mouseX, int mouseY) {
        return false;
    }

    @Override
    public boolean mouseWheelMoved(int mouseX, int mouseY, int scrollAmount) {
        setIndex(index + scrollAmount);
        return true;
    }

    private class ScrollBar extends Widget implements Clickable {

        int dragY;

        public ScrollBar(int x, int y) {
            super(x, y, 9, 27);
        }

        @Override
        public boolean mousePressed(int mouseX, int mouseY) {
            dragY = mouseY - y;
            return false;
        }

        @Override
        public boolean mouseReleased(int mouseX, int mouseY) {
            return false;
        }

        @Override
        public boolean mouseDragged(int mouseX, int mouseY) {
            int newY = Math.min(35, Math.max(10, mouseY - dragY));
            setIndex((int) ((wallpapers.length - 1f) * ((newY - 10f) / 25f)));
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

        @Override
        public void paint(IGraphics graphics) {
            graphics.drawImage(Registry.SCROLL_BAR, x, y, width, height);
        }
    }
}

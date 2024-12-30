package com.deckerpw.hypnos.ui.widget;

import com.deckerpw.hypnos.Colors;
import com.deckerpw.hypnos.Registry;
import com.deckerpw.hypnos.machine.Machine;
import com.deckerpw.hypnos.render.IGraphics;
import com.deckerpw.hypnos.util.Wallpaper;

public class WallpaperSelector extends Widget implements Clickable {

    public final Wallpaper[] wallpapers;
    private final Button scrollUp;
    public int index;
    public Button scrollDown;
    public ScrollBar scrollBar;
    private Clickable selected;
    private final Container scrollContainer;

    public WallpaperSelector(Widget parent, Machine machine, int x, int y, Wallpaper[] wallpapers) {
        super(parent, x, y-10, 133, 82);
        this.wallpapers = wallpapers;
        scrollContainer = new Container(this, 124, 10, 9, 70);
        this.scrollUp = new Button(scrollContainer, 0, 0, 9, 10, Registry.SCROLL_UP, () -> {
            setIndex(index - 1);
        });
        this.scrollDown = new Button(scrollContainer, 0, 63, 9, 9, Registry.SCROLL_DOWN, () -> {
            setIndex(index + 1);
        });
        this.scrollBar = new ScrollBar(scrollContainer, 0, 10);
        scrollContainer.addWidget(scrollUp);
        scrollContainer.addWidget(scrollDown);
        scrollContainer.addWidget(scrollBar);
        setIndex(machine.getCurrentUser().getConfig().getInt("wallpaper"));
    }

    void setIndex(int i) {
        index = Math.min(wallpapers.length - 1, Math.max(0, i));
        float y = 10 + (25 * ((float) index / (wallpapers.length - 1)));
        scrollBar.y = (int) y;
        update();
        scrollContainer.update();
    }

    @Override
    public void paint(IGraphics graphics) {
        Registry.HYPNOFONT_0N.drawCenteredString("WALLPAPER:", width / 2, 0, Colors.TEXT_COLOR, graphics);
        graphics.drawImage(Registry.BACKGROUND_SELECTOR, 0, 10, width, 72);
        graphics.drawImage(wallpapers[index].icon, 2, 12, 120, 68);
        scrollContainer.render(graphics);
    }

    @Override
    public boolean mousePressed(int mouseX, int mouseY) {
        return scrollContainer.mousePressed(mouseX - scrollContainer.x, mouseY - scrollContainer.y);
    }

    @Override
    public boolean mouseReleased(int mouseX, int mouseY) {
        return scrollContainer.mouseReleased(mouseX - scrollContainer.x, mouseY - scrollContainer.y);
    }

    @Override
    public boolean mouseDragged(int mouseX, int mouseY) {
        return scrollContainer.mouseDragged(mouseX - scrollContainer.x, mouseY - scrollContainer.y);
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

    @Override
    public boolean isInside(int x, int y) {
        return x >= this.getX() && x < this.getX() + width && y >= this.getY()+10 && y < this.getY() + height;
    }

    private class ScrollBar extends Container implements Clickable {

        int dragY;

        public ScrollBar(Widget parent, int x, int y) {
            super(parent, x, y, 9, 27);
        }

        @Override
        public boolean mousePressed(int mouseX, int mouseY) {
            dragY = mouseY;
            return true;
        }

        @Override
        public boolean mouseReleased(int mouseX, int mouseY) {
            return false;
        }

        @Override
        public boolean mouseDragged(int mouseX, int mouseY) {
            int newY = Math.min(35, Math.max(10, y + (mouseY - dragY)));
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
            graphics.drawImage(Registry.SCROLL_BAR, 0, 0, width, height);
        }
    }
}

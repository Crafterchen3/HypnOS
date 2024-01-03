package com.deckerpw.hypnos.ui.element;

public interface Clickable {
    boolean mousePressed(int mouseX, int mouseY);

    boolean mouseReleased(int mouseX, int mouseY);

    boolean mouseDragged(int mouseX, int mouseY);

    boolean mouseMoved(int mouseX, int mouseY);

    boolean mouseWheelMoved(int mouseX, int mouseY, int scrollAmount);
}

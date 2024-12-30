package com.deckerpw.hypnos.ui.widget;

import com.deckerpw.hypnos.render.IGraphics;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class Container extends Widget implements Clickable, KeyListener {

    protected Widget selectedWidget;
    protected ArrayList<Widget> widgets = new ArrayList<>();

    public Container(Widget parent, int x, int y, int width, int height) {
        super(parent, x, y, width, height);
    }

    public void addWidget(Widget widget) {
        widgets.add(widget);
    }
    public void addWidget(int i,Widget widget) {
        widgets.add(i,widget);
    }

    public void removeWidget(Widget widget) {
        widgets.remove(widget);
    }

    public void removeWidget(int index) {
        widgets.remove(index);
    }

    public ArrayList<Widget> getWidgets() {
        return widgets;
    }

    @Override
    public boolean mousePressed(int mouseX, int mouseY) {
        if (selectedWidget != null) {
            selectedWidget.setSelected(false);
            selectedWidget = null;
        }
        for (int i = widgets.size()-1; i >= 0; i--) {
            Widget widget = widgets.get(i);
            if (widget instanceof Clickable clickable && widget.isInside(mouseX, mouseY)) {
                if (clickable.mousePressed(mouseX - widget.x, mouseY - widget.y)){
                    selectedWidget = widget;
                    selectedWidget.setSelected(true);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean mouseReleased(int mouseX, int mouseY) {
        if (selectedWidget != null && selectedWidget instanceof Clickable clickable) {
            return clickable.mouseReleased(mouseX - selectedWidget.x, mouseY - selectedWidget.y);
        }
        return false;
    }

    @Override
    public boolean mouseDragged(int mouseX, int mouseY) {
        if (selectedWidget != null && selectedWidget instanceof Clickable clickable) {
            return clickable.mouseDragged(mouseX - selectedWidget.x, mouseY - selectedWidget.y);
        }
        return false;
    }

    @Override
    public boolean mouseMoved(int mouseX, int mouseY) {
        for (int i = widgets.size()-1; i >= 0; i--) {
            Widget widget = widgets.get(i);
            if (widget instanceof Clickable clickable && widget.isInside(mouseX, mouseY))
                return clickable.mouseMoved(mouseX - widget.x, mouseY -widget.y);
        }
        return false;
    }

    @Override
    public boolean mouseWheelMoved(int mouseX, int mouseY, int scrollAmount) {
        for (int i = widgets.size()-1; i >= 0; i--) {
            Widget widget = widgets.get(i);
            if (widget instanceof Clickable clickable && widget.isInside(mouseX, mouseY))
                return clickable.mouseWheelMoved(mouseX - widget.x, mouseY - widget.y, scrollAmount);
        }
        return false;
    }

    @Override
    public void paint(IGraphics graphics) {
        for (Widget widget : widgets) {
            widget.render(graphics);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (selectedWidget != null && selectedWidget instanceof KeyListener listener)
            listener.keyTyped(e);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (selectedWidget != null && selectedWidget instanceof KeyListener listener)
            listener.keyPressed(e);

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (selectedWidget != null && selectedWidget instanceof KeyListener listener)
            listener.keyReleased(e);

    }

    public void replaceWidgets(ArrayList<Widget> widgets) {
        this.widgets = widgets;
    }

    public void selectWidget(Widget widget){
        if (selectedWidget != null)
            selectedWidget.setSelected(false);
        selectedWidget = widget;
        widget.setSelected(true);
    }
}

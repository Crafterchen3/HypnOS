package com.deckerpw.hypnos.swing;

import com.deckerpw.hypnos.HypnOS;
import com.deckerpw.hypnos.Registry;
import com.deckerpw.hypnos.ui.window.LogOffWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class SwingWindow extends JFrame {
    private boolean fullscreen = false;
    private Screen panel;

    public SwingWindow() {
        this.setLayout(new BorderLayout());
        this.setResizable(false);
        rootPane.registerKeyboardAction((e -> {
            try {
                setFullscreen();
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        }), KeyStroke.getKeyStroke("F11"), JComponent.WHEN_FOCUSED);
        rootPane.registerKeyboardAction((e -> {
            panel.addWindow(new LogOffWindow(panel, panel.cursor), Registry.ALERT_WINDOW);
            panel.updateUI();
        }), KeyStroke.getKeyStroke("ESCAPE"), JComponent.WHEN_FOCUSED);
        //set Cursor to Blank
        this.setCursor(this.getToolkit().createCustomCursor(
                new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB),
                new Point(),
                null));
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


        panel = new Screen();
        this.add(panel);
        panel.requestFocusInWindow();
        rootPane.addKeyListener(panel);
        try {
            setFullscreen();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void setFullscreen() throws InterruptedException {
        fullscreen = !fullscreen;
        dispose();
        this.setUndecorated(fullscreen);
        if (fullscreen) {
            setExtendedState(JFrame.MAXIMIZED_BOTH);
            setVisible(true);
            Dimension dimension = this.getSize();
            HypnOS.size = Math.min(dimension.width / 480f, dimension.height / 270f);
        } else {
            setExtendedState(JFrame.NORMAL);
            HypnOS.size = 1f;
            setVisible(true);
            pack();
        }
        panel.updateUI();
    }
}

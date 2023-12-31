package com.deckerpw.hypnos.swing;

import com.deckerpw.hypnos.HypnOS;
import com.deckerpw.hypnos.ui.window.LogOffWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class MainFrame extends JFrame {
    private float size;
    private boolean fullscreen = true;
    private MainPanel panel;

    public MainFrame() {
        this.setLayout(null);
        this.setBounds(new Rectangle(480, 270));
        this.size = HypnOS.size;
        this.setUndecorated(true);
        setExtendedState(Frame.MAXIMIZED_BOTH);

        rootPane.registerKeyboardAction((e -> {
            if (fullscreen) {
                dispose();
                this.setUndecorated(false);
                this.setBounds(new Rectangle(480, 270));
                setExtendedState(Frame.NORMAL);
                this.size = 1f;
            } else {
                dispose();
                this.setUndecorated(true);
                setExtendedState(Frame.MAXIMIZED_BOTH);
            }

            HypnOS.size = this.size;
            fullscreen = !fullscreen;
            this.setVisible(true);
            panel.updateUI();
            setPanelSize();
        }), KeyStroke.getKeyStroke("F11"), JComponent.WHEN_FOCUSED);

        rootPane.registerKeyboardAction((e -> {
            panel.addWindow(new LogOffWindow(panel, panel.cursor));
            panel.updateUI();
        }), KeyStroke.getKeyStroke("ESCAPE"), JComponent.WHEN_FOCUSED);
        //set Cursor to Blank
        this.setCursor(this.getToolkit().createCustomCursor(
                new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB),
                new Point(),
                null));
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        panel = new MainPanel();
        this.add(panel, BorderLayout.CENTER);
        panel.setBounds(0, 0, (int) (480f * size), (int) (270f * size));
        this.setVisible(true);
        setPanelSize();
    }

    private void setPanelSize() {
        try {
            Thread.sleep(10);
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
        Dimension dim = getSize();
        this.size = Math.min(dim.width / 480F, dim.height / 270F);
        HypnOS.size = this.size;
        Dimension dimension = new Dimension((int) (480 * size), (int) (270 * size));
        Dimension screenDim = this.getSize();
        panel.setBounds((screenDim.width - dimension.width) / 2, (screenDim.height - dimension.height) / 2, dimension.width, dimension.height);
    }

    @Override
    public void validate() {
        super.validate();
        setPanelSize();
    }
}

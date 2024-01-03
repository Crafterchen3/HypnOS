package com.deckerpw.hypnos.swing;

import com.deckerpw.hypnos.HypnOS;
import com.deckerpw.hypnos.Registry;
import com.deckerpw.hypnos.ui.window.LogOffWindow;
import com.deckerpw.hypnos.util.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.image.BufferedImage;
import java.awt.datatransfer.*;
import java.io.*;

public class SwingWindow extends JFrame implements ClipboardOwner {
    private boolean fullscreen = false;
    private Screen screen;

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
            BufferedImage bufferedImage = new BufferedImage(480,270,BufferedImage.TYPE_INT_ARGB);
            float size = HypnOS.size;
            HypnOS.size = 1;
            screen.paint(bufferedImage.createGraphics());
            HypnOS.size = size;
            TransferableImage image = new TransferableImage(bufferedImage);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(image, SwingWindow.this);
            HypnOS.logger.println("Copied Screenshot to Clipboard");
        }), KeyStroke.getKeyStroke("ctrl R"), JComponent.WHEN_FOCUSED);
        rootPane.registerKeyboardAction((e -> {
            screen.addWindow(new LogOffWindow(screen, screen.cursor), Registry.ALERT_WINDOW);
            screen.updateUI();
        }), KeyStroke.getKeyStroke("ESCAPE"), JComponent.WHEN_FOCUSED);
        //set Cursor to Blank
        this.setCursor(this.getToolkit().createCustomCursor(
                new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB),
                new Point(),
                null));
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


        screen = new Screen();
        this.add(screen);
        screen.requestFocusInWindow();
        rootPane.addKeyListener(screen);
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
        screen.updateUI();
    }

    @Override
    public void lostOwnership(Clipboard clipboard, Transferable contents) {

    }

    //Taken from https://www.coderanch.com/t/333565/java/BufferedImage-System-Clipboard (:
    //PS That website looks like it is from hypnospace tbh
    private class TransferableImage implements Transferable {

        Image i;

        public TransferableImage( Image i ) {
            this.i = i;
        }

        public Object getTransferData( DataFlavor flavor )
                throws UnsupportedFlavorException, IOException {
            if ( flavor.equals( DataFlavor.imageFlavor ) && i != null ) {
                return i;
            }
            else {
                throw new UnsupportedFlavorException( flavor );
            }
        }

        public DataFlavor[] getTransferDataFlavors() {
            DataFlavor[] flavors = new DataFlavor[ 1 ];
            flavors[ 0 ] = DataFlavor.imageFlavor;
            return flavors;
        }

        public boolean isDataFlavorSupported( DataFlavor flavor ) {
            DataFlavor[] flavors = getTransferDataFlavors();
            for ( int i = 0; i < flavors.length; i++ ) {
                if ( flavor.equals( flavors[ i ] ) ) {
                    return true;
                }
            }

            return false;
        }
    }
}

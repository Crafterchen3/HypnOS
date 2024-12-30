package com.deckerpw.hypnos.swing;

import com.deckerpw.hypnos.Registry;
import com.deckerpw.hypnos.machine.Machine;
import com.deckerpw.hypnos.ui.target.CursorTarget;
import com.deckerpw.hypnos.ui.target.LoginScreen;
import com.deckerpw.hypnos.ui.target.Screen;
import com.deckerpw.hypnos.ui.target.Target;
import com.deckerpw.hypnos.ui.window.LogOffWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class SwingWindow extends JFrame implements ClipboardOwner, KeyListener{
    private boolean fullscreen = false;
    private Target target;
    private Timer timer;
    private BufferedImage fadeScreenshot;
    private float alpha = 0f;
    private Machine machine;
    private float scale = 1f;
    private final boolean keepScale;

    public SwingWindow(Machine machine, boolean keepScale) {
        this.machine = machine;
        this.keepScale = keepScale;
        this.setLayout(new BorderLayout());
        this.setResizable(false);
        rootPane.setFocusTraversalKeysEnabled(false);
        rootPane.registerKeyboardAction((e -> {
            try {
                setFullscreen();
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        }), KeyStroke.getKeyStroke("F11"), JComponent.WHEN_FOCUSED);
        rootPane.registerKeyboardAction((e -> {
            com.deckerpw.hypnos.render.Graphics graphics = new com.deckerpw.hypnos.render.Graphics(this);
            target.paint(graphics);
            BufferedImage bufferedImage = graphics.getCache();
            TransferableImage image = new TransferableImage(bufferedImage);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(image, SwingWindow.this);
            machine.getLogger().println("Copied Screenshot to Clipboard");
        }), KeyStroke.getKeyStroke("ctrl R"), JComponent.WHEN_FOCUSED);
        rootPane.registerKeyboardAction((e -> {
            if (target instanceof Screen screen) {
                screen.addWindow(new LogOffWindow(screen), Registry.ALERT_WINDOW);
                target.update();
            }
        }), KeyStroke.getKeyStroke("ESCAPE"), JComponent.WHEN_FOCUSED);
        //set Cursor to Blank
        this.setCursor(this.getToolkit().createCustomCursor(
                new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB),
                new Point(),
                null));
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        target = new LoginScreen(this);
        initMouse();
        rootPane.addKeyListener (this);
        try {
            setFullscreen();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public SwingWindow(Machine machine) {
        this(machine, false);
    }

    public float getScale() {
        return scale;
    }

    public void switchTarget(Target target, boolean fade){
        if (fade){
            if (timer == null){
                if (target instanceof CursorTarget newCursor && this.target instanceof CursorTarget oldCursor){
                    newCursor.mouseX = oldCursor.mouseX;
                    newCursor.mouseY = oldCursor.mouseY;
                }
                com.deckerpw.hypnos.render.Graphics graphics = new com.deckerpw.hypnos.render.Graphics(this);
                target.paint(graphics);
                BufferedImage screenshot = graphics.getCache();
                timer = new Timer(20, e -> {
                    alpha += 0.05f;
                    if (alpha >= 1f) {
                        this.target = target;
                        fadeScreenshot = screenshot;
                        repaint();
                        fadeScreenshot = null;
                        alpha = 0f;
                        destroyTimer();
                        return;
                    } else {
                        fadeScreenshot = new BufferedImage(480, 270, BufferedImage.TYPE_INT_ARGB);
                        Graphics2D g2d = fadeScreenshot.createGraphics();
                        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
                        g2d.drawImage(screenshot, 0, 0, 480, 270, null);
                    }
                    repaint();
                });
                timer.start();
            }
        }else {
            if (target instanceof CursorTarget newCursor && this.target instanceof CursorTarget oldCursor){
                newCursor.mouseX = oldCursor.mouseX;
                newCursor.mouseY = oldCursor.mouseY;
            }
            this.target = target;
            repaint();
        }
    }

    private void destroyTimer(){
        timer.stop();
        timer = null;
    }

    private void setFullscreen() throws InterruptedException {
        fullscreen = !fullscreen;
        dispose();
        this.setUndecorated(fullscreen);
        if (fullscreen) {
            for ( Window w : Window.getWindows() ) {
                GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow( w );
            }
            setVisible(true);
            if (!keepScale) {
                Dimension dimension = this.getSize();
                scale = Math.min(dimension.width / 480f, dimension.height / 270f);
            }
        } else {
            setExtendedState(JFrame.NORMAL);
            scale = 1f;
            setVisible(true);
            pack();
        }
        target.update();
    }

    @Override
    public void paint(Graphics g) {
        com.deckerpw.hypnos.render.Graphics graphics = new com.deckerpw.hypnos.render.Graphics(this);
        target.paint(graphics);
        if (fadeScreenshot != null)
            graphics.drawImage(fadeScreenshot,0,0,480,270);
        graphics.paint((Graphics2D) g);
    }

    @Override
    public void lostOwnership(Clipboard clipboard, Transferable contents) {

    }

    private void initMouse() {
        MouseAdapter adapter = new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                target.mousePressed(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                target.mouseReleased(e);
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                target.mouseDragged(e);
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                target.mouseMoved(e);
            }

            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                target.mouseWheelMoved(e);
            }

        };
        addMouseListener(adapter);
        addMouseMotionListener(adapter);
        addMouseWheelListener(adapter);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        target.keyTyped(e);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        target.keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        target.keyReleased(e);
    }

    public Machine getMachine() {
        return machine;
    }

    //Taken from https://www.coderanch.com/t/333565/java/BufferedImage-System-Clipboard (:
    //PS That website looks like it is from hypnospace tbh
    private class TransferableImage implements Transferable {

        Image i;

        public TransferableImage(Image i) {
            this.i = i;
        }

        public Object getTransferData(DataFlavor flavor)
                throws UnsupportedFlavorException, IOException {
            if (flavor.equals(DataFlavor.imageFlavor) && i != null) {
                return i;
            } else {
                throw new UnsupportedFlavorException(flavor);
            }
        }

        public DataFlavor[] getTransferDataFlavors() {
            DataFlavor[] flavors = new DataFlavor[1];
            flavors[0] = DataFlavor.imageFlavor;
            return flavors;
        }

        public boolean isDataFlavorSupported(DataFlavor flavor) {
            DataFlavor[] flavors = getTransferDataFlavors();
            for (int i = 0; i < flavors.length; i++) {
                if (flavor.equals(flavors[i])) {
                    return true;
                }
            }

            return false;
        }
    }

}

package com.deckerpw.hypnos.showcase;

import com.deckerpw.hypnos.render.IGraphics;
import com.deckerpw.hypnos.ui.widget.Widget;
import com.deckerpw.hypnos.ui.widget.WindowManager;
import com.deckerpw.hypnos.ui.window.FullscreenWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;

import static java.lang.Math.*;

public class ShowcaseWindow extends FullscreenWindow {
    private RotatingCube cube = new RotatingCube();

    public ShowcaseWindow(WindowManager windowManager) {
        super(windowManager, "Showcase");
    }

    @Override
    public void closeWindow() {
        cube.destroy();
        super.closeWindow();
    }

    @Override
    public void paint(IGraphics graphics) {
        super.paint(graphics);
        BufferedImage image = new BufferedImage(getWidth()-30, getHeight()-30, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, image.getWidth(), image.getHeight());
        g.setColor(new Color(0xFF00FF));
        cube.drawCube(g);
        graphics.drawImage(image, 15, 20, image.getWidth(), image.getHeight());
    }

    class RotatingCube {
        private final Timer timer;
        double[][] nodes = {{-1, -1, -1}, {-1, -1, 1}, {-1, 1, -1}, {-1, 1, 1},
                {1, -1, -1}, {1, -1, 1}, {1, 1, -1}, {1, 1, 1}};

        int[][] edges = {{0, 1}, {1, 3}, {3, 2}, {2, 0}, {4, 5}, {5, 7}, {7, 6},
                {6, 4}, {0, 4}, {1, 5}, {2, 6}, {3, 7}};

        public RotatingCube() {

            scale(60);
            rotateCube(PI / 4, atan(sqrt(2)));

            timer = new Timer(17, (ActionEvent e) -> {
                rotateCube(PI / 180, 0);
                ShowcaseWindow.this.update();
            });
            timer.start();
        }

        final void destroy() {
            timer.stop();
        }

        final void scale(double s) {
            for (double[] node : nodes) {
                node[0] *= s;
                node[1] *= s;
                node[2] *= s;
            }
        }

        final void rotateCube(double angleX, double angleY) {
            double sinX = sin(angleX);
            double cosX = cos(angleX);

            double sinY = sin(angleY);
            double cosY = cos(angleY);

            for (double[] node : nodes) {
                double x = node[0];
                double y = node[1];
                double z = node[2];

                node[0] = x * cosX - z * sinX;
                node[2] = z * cosX + x * sinX;

                z = node[2];

                node[1] = y * cosY - z * sinY;
                node[2] = z * cosY + y * sinY;
            }
        }

        void drawCube(Graphics2D g) {
            g.translate(getWidth() / 2, getHeight() / 2);

            for (int[] edge : edges) {
                double[] xy1 = nodes[edge[0]];
                double[] xy2 = nodes[edge[1]];
                g.drawLine((int) round(xy1[0]), (int) round(xy1[1]),
                        (int) round(xy2[0]), (int) round(xy2[1]));
            }

            for (double[] node : nodes)
                g.fillOval((int) round(node[0]) - 4, (int) round(node[1]) - 4, 8, 8);
        }
    }
}

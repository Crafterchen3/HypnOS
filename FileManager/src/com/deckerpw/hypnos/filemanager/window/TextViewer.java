package com.deckerpw.hypnos.filemanager.window;

import com.deckerpw.hypnos.Colors;
import com.deckerpw.hypnos.Registry;
import com.deckerpw.hypnos.render.IGraphics;
import com.deckerpw.hypnos.ui.widget.Box;
import com.deckerpw.hypnos.ui.widget.ScrollTextBox;
import com.deckerpw.hypnos.ui.widget.TextBox;
import com.deckerpw.hypnos.ui.widget.WindowManager;
import com.deckerpw.hypnos.ui.window.Window;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class TextViewer extends Window {

    public TextViewer(WindowManager windowManager, int x, int y, String text) {
        super(windowManager, x, y, 160, 200, "Text Viewer");
        ScrollTextBox textBox = new ScrollTextBox(this, 5, 19, 150, 177, text, Registry.HYPNOFONT_0N, Color.BLACK);
        textBox.genLines(text);
        addWidget(new Box(this, 5, 19, 150, 177, Color.WHITE));
        addWidget(textBox);
    }

    public TextViewer(WindowManager windowManager, String text) {
        this(windowManager, windowManager.getWidth() / 2 - 160 / 2, windowManager.getHeight() / 2 - 200 / 2, text);
    }

    public static TextViewer createFromFile(WindowManager windowManager, File file) {
        //read File
        StringBuilder text = new StringBuilder();
        try {
            java.util.Scanner scanner = new java.util.Scanner(file);
            while (scanner.hasNextLine()) {
                text.append(scanner.nextLine()).append("\n");
            }
        } catch (java.io.FileNotFoundException e) {
            text = new StringBuilder("File not found");
        }
        return new TextViewer(windowManager, text.toString());
    }
}

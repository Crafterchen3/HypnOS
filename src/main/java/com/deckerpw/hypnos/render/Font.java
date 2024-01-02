package com.deckerpw.hypnos.render;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.TreeMap;

public class Font {


    private static String characters;
    static final Map<Character, Integer> charmap = genMap();
    final BufferedImage fontImage;

    public Font(BufferedImage fontImage) {
        this.fontImage = fontImage;
    }

    public static Map<Character, Integer> genMap() {
        characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789.,;:?!-_~#\"`&()[]|'\\/@°+=*<> ";
        String digits = "55555555455565555554566564654555552452655554545564555455555555223353666763734343366747774433";

        Map<Character, Integer> charToDigitMap = new TreeMap<Character, Integer>();

        // Check that both input strings have the same length
        if (characters.length() <= digits.length()) {
            for (int i = 0; i < characters.length(); i++) {
                char charKey = characters.charAt(i);
                int digitValue = Character.getNumericValue(digits.charAt(i));
                charToDigitMap.put(charKey, digitValue);
            }
        }
        return charToDigitMap;
    }

    public void drawChar(char ch, int x, int y, Color color, IGraphics g) {
        drawChar(ch, x, y, charmap.get(ch), color, g);
    }

    public void drawChar(char ch, int x, int y, int w, Color color, IGraphics g) {
        int pos = characters.indexOf(ch);
        int row = (int) Math.floor(pos / 8F);
        int px = (pos - (row * 8)) * 7;
        int py = row * 7;
        BufferedImage charImg = fontImage.getSubimage(px, py, w, 7);
        charImg = setImageColor(charImg, color);
        g.drawImage(charImg, x, y, w, 7);
    }

    public void drawString(String string, int x, int y, Color color, IGraphics g) {
        int start = 0;
        for (int i = 0; i < string.length(); i++) {
            char ch = string.charAt(i);
            int w = charmap.get(ch);
            drawChar(ch, x + start, y, w, color, g);
            start += w;
        }
    }

    public BufferedImage setImageColor(BufferedImage image, Color color) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage res = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        // Iterate through each pixel
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                // Get the RGB value of the pixel at (x, y)
                int pixel = image.getRGB(x, y);

                // Check if the pixel is black
                if (pixel == 0xFF000000) { // 0xFF000000 represents black color (fully opaque black)
                    // Set the pixel to white
                    res.setRGB(x, y, color.getRGB()); // 0xFFFFFFFF represents white color (fully opaque white)
                }
            }
        }
        return res;
    }

    public void drawCenteredString(String string, int x, int y, Color color, IGraphics g) {
        int length = getLength(string);
        int b = x - (length / 2);
        drawString(string, b, y, color, g);
    }

    public int getLength(String string) {
        int width = 0;
        for (int i = 0; i < string.length(); i++) {
            char ch = string.charAt(i);
            int w = charmap.get(ch);
            width += w;
        }
        return width;
    }
}

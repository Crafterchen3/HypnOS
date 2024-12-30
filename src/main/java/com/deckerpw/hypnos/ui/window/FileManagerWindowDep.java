package com.deckerpw.hypnos.ui.window;

import com.deckerpw.hypnos.Registry;
import com.deckerpw.hypnos.render.Font;
import com.deckerpw.hypnos.render.IGraphics;
import com.deckerpw.hypnos.ui.widget.Button;
import com.deckerpw.hypnos.ui.widget.Clickable;
import com.deckerpw.hypnos.ui.widget.Widget;
import com.deckerpw.hypnos.ui.widget.WindowManager;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class FileManagerWindowDep extends Window {
    public FileManagerWindowDep(WindowManager windowManager) {
        super(windowManager, Registry.FILES_PANE, 480 / 2 - (278 / 2), 270 / 2 - (160 / 2), 278, 160, "FILE MANAGER", Registry.FILE_MANAGER_ICON);
        addWidget(new FileList());
        addWidget(new Button(this,11, 143, 9, 9, new BufferedImage[]{
                Registry.NEW_FOLDER,
                Registry.NEW_FOLDER
        }, () -> {

        }));
    }

    private class FileList extends Widget implements Clickable {

        private final ArrayList<File> files = new ArrayList<>();
        private int scroll = 0;

        public FileList() {
            super(FileManagerWindowDep.this,9, 32, 252, 108);
            addFile("test1", "2024.07.01");
            addFile("test2", "2024.07.01");
            addFile("test3", "2024.07.01");
            addFile("test4", "2024.07.01");
            addFile("test5", "2024.07.01");
            addFile("test6", "2024.07.01");
            addFile("test7", "2024.07.01");
            addFile("test8", "2024.07.01");
            addFile("test9", "2024.07.01");
            addFile("test10", "2024.07.01");
        }

        public void addFile(String name, String date) {
            int selected = (files.size() % 2) == 0 ? 0 : 1;
            files.add(new File(0, (files.size() * 15), selected, name, date));
        }

        @Override
        public boolean mousePressed(int mouseX, int mouseY) {
            return false;
        }

        @Override
        public boolean mouseReleased(int mouseX, int mouseY) {
            return false;
        }

        @Override
        public boolean mouseDragged(int mouseX, int mouseY) {
            return false;
        }

        @Override
        public boolean mouseMoved(int mouseX, int mouseY) {
            return false;
        }

        @Override
        public boolean mouseWheelMoved(int mouseX, int mouseY, int scrollAmount) {
            scroll = Math.min(Math.max(scroll + scrollAmount, 0), files.size() - 7);
            return true;
        }

        @Override
        public void paint(IGraphics graphics) {
            for (int i = scroll; i < Math.min(8 + scroll, files.size()); i++) {
                files.get(i).render(graphics);
            }
        }

        private class File extends Widget {

            public String name;
            public String date;
            private final BufferedImage entry = Registry.FILE_ENTRY;
            private final BufferedImage[] entrys = new BufferedImage[]{
                    entry.getSubimage(0, 0, 252, 15),
                    entry.getSubimage(0, 15, 252, 15),
                    entry.getSubimage(0, 30, 252, 15)
            };
            private int selected = 0;
            private final Font font = Registry.HYPNOFONT_0N;
            private final Color defaultColor = new Color(0xffD77BBA);
            private final Color selectedColor = new Color(0xff9AEFFF);

            public File(int x, int y, int selected, String name, String date) {
                super(null,x, y, 252, 15);
                this.selected = selected;
                this.name = name;
                this.date = date;
            }

            @Override
            public int getY() {
                return y - (scroll * 15);
            }

            @Override
            public void paint(IGraphics graphics) {
                graphics.drawImage(entrys[selected], 0, getY(), 252, 15);
                font.drawString(name, 28, getY() + 4, selected == 2 ? selectedColor : defaultColor, graphics);
                font.drawString(date, 156, getY() + 5, selected == 2 ? selectedColor : defaultColor, graphics);
            }
        }
    }
}

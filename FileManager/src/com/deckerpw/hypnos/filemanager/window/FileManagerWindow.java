package com.deckerpw.hypnos.filemanager.window;

import com.deckerpw.hypnos.Registry;
import com.deckerpw.hypnos.filemanager.FileManagerRegistry;
import com.deckerpw.hypnos.filemanager.FileType;
import com.deckerpw.hypnos.filemanager.FileTypeRegistry;
import com.deckerpw.hypnos.machine.Machine;
import com.deckerpw.hypnos.machine.filesystem.Filesystem;
import com.deckerpw.hypnos.render.Font;
import com.deckerpw.hypnos.render.IGraphics;
import com.deckerpw.hypnos.ui.widget.AbstractSelectionList;
import com.deckerpw.hypnos.ui.widget.SimpleSelectionList;
import com.deckerpw.hypnos.ui.widget.Widget;
import com.deckerpw.hypnos.ui.widget.WindowManager;
import com.deckerpw.hypnos.ui.window.FullscreenWindow;

import java.awt.*;
import java.awt.image.BufferedImage;

public class FileManagerWindow extends FullscreenWindow {

    private final Machine machine;
    private final Filesystem filesystem;
    private final FileSelectionList fileSelectionList;
    private final SimpleSelectionList driveSelectionList;
    private String currentPath;

    public FileManagerWindow(WindowManager windowManager) {
        super(windowManager, FileManagerRegistry.WINDOW_PANE, "File Manager");
        machine = windowManager.getMachine();
        currentPath = "0:/users/"+machine.getCurrentUser().getPath()+"/";
        filesystem = machine.getFilesystem();
        driveSelectionList = new SimpleSelectionList(this, 9, 23, 95, 243);
        String[] drives = filesystem.listDrives();
        driveSelectionList.setEntrys(drives);
        driveSelectionList.addEventListener(() -> {
            currentPath = driveSelectionList.getSelectedIndex()+":/";
            updateFileList();
            update();
        });
        addWidget(driveSelectionList);
        fileSelectionList = new FileSelectionList(this, 119, 32, 345, 218);
        addWidget(fileSelectionList);
        updateFileList();
    }

    private void updateFileList(){
        String[] files = filesystem.list(currentPath);
        fileSelectionList.clear();
        for (String file : files) {
            fileSelectionList.addEntry(file, "NEVER",filesystem.isDirectory(currentPath+file));
        }
    }

    @Override
    public void paint(IGraphics graphics) {
        super.paint(graphics);
        font.drawString(currentPath, 21, 11, Color.WHITE, graphics);
    }

    private void selectFile(String name) {
        FileType type = FileTypeRegistry.getFileType(name.substring(name.lastIndexOf(".")+1));
        if (type != null)
            type.run(filesystem.getFile(currentPath+name),machine);
    }

    private void selectFolder(String name) {
        currentPath = currentPath + name + "/";
        updateFileList();
        update();
    }

    class FileSelectionList extends AbstractSelectionList{

        public FileSelectionList(Widget parent, int x, int y, int width, int height) {
            super(parent, x, y, width, height, 15);
        }

        private void clear(){
            selected = null;
            entries.clear();
            offset = 0;
            update();
        }

        private void addEntry(FileEntry fileEntry){
            fileEntry.odd = entries.size() % 2 == 1;
            entries.add(fileEntry);
            fileEntry.update();
            update();
        }

        private void addEntry(String name,String date, boolean folder){
            addEntry(new FileEntry(name,date,folder));
        }

        class FileEntry extends Entry{

            public String name;
            public String date;
            private final BufferedImage entry = Registry.FILE_ENTRY;
            private final BufferedImage[] entrys = new BufferedImage[]{
                    entry.getSubimage(0, 0, 345, 15),
                    entry.getSubimage(0, 15, 345, 15),
                    entry.getSubimage(0, 30, 345, 15)
            };
            private final Font font = Registry.HYPNOFONT_0N;
            private final Color defaultColor = new Color(0xffD77BBA);
            private final Color selectedColor = new Color(0xff9AEFFF);
            private boolean odd;
            private boolean ready = false;
            private boolean folder;

            public FileEntry(String name, String date,boolean folder) {
                super(FileManagerWindow.this, 355,15);
                this.name = name;
                this.date = date;
                this.folder = folder;
                update();
            }

            @Override
            public void setSelected(boolean selected) {
                super.setSelected(selected);
                update();
                if (selected && ready){
                    if (folder)
                        FileManagerWindow.this.selectFolder(name);
                    else
                        FileManagerWindow.this.selectFile(name);
                    ready = false;
                }
                else if (selected){
                    ready = true;
                    Thread thread = new Thread(() -> {
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        ready = false;
                    });
                    thread.start();
                }
            }

            @Override
            protected void paint(IGraphics graphics) {
                graphics.drawImage(entrys[selected ? 2 : (odd ? 1 : 0)], 0, 0, 345, 15);
                BufferedImage icon = folder ? FileManagerRegistry.ICON_FOLDER : FileManagerRegistry.ICON_UNKNOWN;
                if (!folder){
                    String ext = name.substring(name.lastIndexOf(".")+1);
                    FileType fileType = FileTypeRegistry.getFileType(ext);
                    if (fileType != null)
                        icon = fileType.getIcon();
                }
                graphics.drawImage(icon, 7, 3, 9, 9);
                font.drawString(name, 28, 5, selected ? selectedColor : defaultColor, graphics);
                font.drawString(date, 249, 5, selected ? selectedColor : defaultColor, graphics);

            }
        }
    }
}

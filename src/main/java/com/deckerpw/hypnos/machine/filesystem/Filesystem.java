package com.deckerpw.hypnos.machine.filesystem;

import com.deckerpw.hypnos.machine.Machine;

import java.io.File;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Filesystem {

    public ArrayList<Drive> drives = new ArrayList<>();

    public Filesystem(Machine machine) {
        drives.add(new PathDrive(Paths.get(machine.getPath()).toString(), "Root"));
        drives.add(JavaDrive.getJavaDrive());
        drives.add(new PathDrive(Paths.get("C:").toString(), "Windows"));
    }

    public String[] listDrives(){
        String[] names = new String[drives.size()];
        for (int i = 0; i < drives.size(); i++) {
            names[i] = drives.get(i).getLabel();
        }
        return names;
    }

    public File getFile(String path) {
        String[] parts = path.split(":",2);
        int drive = Integer.parseInt(parts[0]);
        if (drive < 0 || drive > drives.size())
            return null;
        return drives.get(drive).getFile(parts[1]);
    }

    public URL getURL(String path) {
        String[] parts = path.split(":",2);
        int drive = Integer.parseInt(parts[0]);
        if (drive < 0 || drive > drives.size())
            return null;
        return drives.get(drive).getURL(parts[1]);
    }


    public String[] list(String folderPath){
        String[] parts = folderPath.split(":",2);
        int drive = Integer.parseInt(parts[0]);
        if (drive < 0 || drive > drives.size())
            return null;
        return drives.get(drive).list(parts[1]);
    }

    public boolean isDirectory(String path) {
        String[] parts = path.split(":",2);
        int drive = Integer.parseInt(parts[0]);
        if (drive < 0 || drive > drives.size())
            return false;
        return drives.get(drive).isDirectory(parts[1]);
    }
}

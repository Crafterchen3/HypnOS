package com.deckerpw.hypnos.machine.filesystem;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class PathDrive implements Drive {

    private String path;
    private String label;

    public PathDrive(String path, String label) {
        this.path = path;
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public File getFile(String path) {
        return new File(this.path, path);
    }

    public URL getURL(String path) {
        try {
            return getFile(path).toURI().toURL();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String[] list(String path) {
        return getFile(path).list();
    }

    @Override
    public boolean isDirectory(String path) {
        return getFile(path).isDirectory();
    }
}

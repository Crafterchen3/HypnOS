package com.deckerpw.hypnos.machine.filesystem;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.*;
import java.util.*;

public class JavaDrive implements Drive {

    private static JavaDrive instance;
    private FileSystem fileSystem;


    private JavaDrive() throws URISyntaxException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        URI uri = classLoader.getResource("assets").toURI();
        if (uri.getScheme().equals("jar")) {
            try {
                fileSystem = FileSystems.newFileSystem(uri, Collections.emptyMap());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static JavaDrive getJavaDrive() {
        if (instance == null) {
            try {
                JavaDrive.instance = new JavaDrive();
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }
        return instance;
    }

    @Override
    public File getFile(String path) {
        URL url = getURL(path);
        if (url == null)
            return null;
        File file = new File(url.getFile());
        return file;
    }

    @Override
    public String[] list(String path) {
        if (fileSystem != null) {
            Path path1 = fileSystem.getPath(path);
            try {
                List<Path> list = new ArrayList<>(Files.walk(path1, 1).toList());
                list.remove(0);
                String[] files = new String[list.size()];
                for (int i = 0; i < list.size(); i++) {
                    files[i] = list.get(i).getFileName().toString();
                }
                return files;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        File folder = getFile(path);
        return folder.list();
    }

    @Override
    public boolean isDirectory(String path) {
        if (fileSystem != null) {
            Path path1 = fileSystem.getPath(path);
            return Files.isDirectory(path1);
        }
        File file = getFile(path);
        return file.isDirectory();
    }

    @Override
    public URL getURL(String path) {
        return JavaDrive.class.getResource(path);
    }

    @Override
    public String getLabel() {
        return "JAVA";
    }
}

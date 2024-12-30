package com.deckerpw.hypnos.machine.filesystem;

import java.io.File;
import java.net.URL;

public interface Drive {

    String getLabel();
    String[] list(String path);
    File getFile(String path);
    URL getURL(String path);
    boolean isDirectory(String path);

}

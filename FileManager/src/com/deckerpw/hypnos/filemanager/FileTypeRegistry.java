package com.deckerpw.hypnos.filemanager;

import java.util.ArrayList;
import java.util.HashMap;

public class FileTypeRegistry {

    private static HashMap<String,FileType> fileTypes = new HashMap<>();

    public static void registerFileType(String extension, FileType fileType) {
        fileTypes.put(extension, fileType);
    }

    public static void registerFileType(String[] extensions, FileType fileType) {
        for (String extension : extensions) {
            fileTypes.put(extension, fileType);
        }
    }

    public static FileType getFileType(String extension) {
        return fileTypes.get(extension);
    }

    public static boolean hasFileType(String extension) {
        return fileTypes.containsKey(extension);
    }

}

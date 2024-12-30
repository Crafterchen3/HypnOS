package com.deckerpw.hypnos.util;

import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.util.zip.*;

public class ZipHelper {

    public static void unzip(URL zipfile, Path outputDir) throws IOException {
        try (InputStream inputStream = zipfile.openStream()) {
            unzip(inputStream, outputDir);
        }
    }

    public static void unzip(InputStream inputStream, Path outputDir) throws IOException {
        try (ZipInputStream zipInputStream = new ZipInputStream(inputStream)) {
            ZipEntry entry;
            while ((entry = zipInputStream.getNextEntry()) != null) {
                if (!entry.isDirectory()) {
                    Path entryPath = outputDir.resolve(entry.getName());
                    Path parentDir = entryPath.getParent();
                    if (parentDir != null) {
                        Files.createDirectories(parentDir);
                    }
                    try (OutputStream outputStream = Files.newOutputStream(entryPath)) {
                        byte[] buffer = new byte[1024];
                        int bytesRead;
                        while ((bytesRead = zipInputStream.read(buffer)) != -1) {
                            outputStream.write(buffer, 0, bytesRead);
                        }
                    }
                }
                zipInputStream.closeEntry();
            }
        }
    }

    public static void main(String[] args) {
        try {
            URL url = new URL("https://example.com/example.zip");
            Path outputDir = Paths.get("output");
            unzip(url, outputDir);
            System.out.println("Extraction complete.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
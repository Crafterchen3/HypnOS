package com.deckerpw.hypnos.util;

import com.deckerpw.hypnos.HypnOS;
import com.deckerpw.hypnos.ui.window.LogWindow;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Logger {

    public final String ANSI_GREEN = "\u001B[32m";
    public final String ANSI_RESET = "\u001B[0m";
    public final ArrayList<String> lines = new ArrayList<>();

    public Logger() {
    }

    public void println(String string) {
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String formattedTime = currentTime.format(formatter);

        String coloredlogMessage = "[" + ANSI_GREEN + formattedTime + ANSI_RESET + "] " + string;
        String logMessage = "[" + formattedTime + "] " + string;
        lines.add(logMessage);
        System.out.println(coloredlogMessage);
        if (LogWindow.getInstance() != null) LogWindow.getInstance().add(logMessage);
    }

    public void save(String filePath) {
        try {
            FileWriter fileWriter = new FileWriter(filePath);
            for (String line : lines) {
                fileWriter.write(line + "\n");
            }
            fileWriter.flush();
            fileWriter.close();
            System.out.println("Log saved to file: " + filePath);
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    public void save() {
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedTime = currentTime.format(formatter);
        save(Paths.get(HypnOS.Path, "logs", formattedTime + ".log").toString());
    }

}

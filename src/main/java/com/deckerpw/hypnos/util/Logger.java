package com.deckerpw.hypnos.util;

import com.deckerpw.hypnos.machine.Machine;
import com.deckerpw.hypnos.ui.window.LogWindow;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.EventListener;

public class Logger {

    public final String ANSI_GREEN = "\u001B[32m";
    public final String ANSI_RESET = "\u001B[0m";
    public final ArrayList<String> lines = new ArrayList<>();
    public final String directory;
    public final ArrayList<LoggerEventListener> eventListeners = new ArrayList<>();

    public Logger(Machine machine) {
        directory = Paths.get(machine.getPath(), "logs").toString();
    }

    public Logger(String directory) {
        this.directory = directory;
    }

    public void addEventListener(LoggerEventListener listener) {
        eventListeners.add(listener);
    }

    public void removeEventListener(LoggerEventListener listener) {
        eventListeners.remove(listener);
    }

    public void println(Object obj) {
        String string = obj.toString();
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String formattedTime = currentTime.format(formatter);

        String coloredlogMessage = "[" + ANSI_GREEN + formattedTime + ANSI_RESET + "] " + string;
        String logMessage = "[" + formattedTime + "] " + string;
        lines.add(logMessage);
        System.out.println(coloredlogMessage);
        for (LoggerEventListener eventListener : eventListeners) {
            eventListener.actionPerformed(logMessage);
        }
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
        save(Paths.get(directory, formattedTime + ".log").toString());
    }

    public interface LoggerEventListener extends EventListener {
        void actionPerformed(String newLine);
    }

}

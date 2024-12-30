package com.deckerpw.hypnos.config;

import com.deckerpw.hypnos.machine.Machine;
import com.deckerpw.hypnos.util.Logger;
import org.json.JSONObject;

import java.io.*;


public abstract class FileConfig extends BasicConfig {

    private final String filePath;
    protected final Logger logger;

    public FileConfig(Machine machine, String filePath) {
        this.logger = machine.getLogger();
        this.filePath = filePath;
        this.jsonObject = new JSONObject();

    }

    public void load() {
        try {
            File file = new File(filePath);
            if (file.exists()) {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();
                jsonObject = new JSONObject(stringBuilder.toString());
                logger.println("Loaded JSON from file: " + filePath);
            } else {
                loadDefaults();
                save();
            }
        } catch (IOException e) {
            loadDefaults();
            save();
        }
    }

    public void save() {
        try {
            FileWriter fileWriter = new FileWriter(filePath);
            fileWriter.write(jsonObject.toString(4));
            fileWriter.flush();
            fileWriter.close();
            logger.println("JSON saved to file: " + filePath);
        } catch (IOException e) {
            logger.println("Error writing to file: " + e.getMessage());
        }
    }

}

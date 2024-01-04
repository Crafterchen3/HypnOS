package com.deckerpw.hypnos;

import com.deckerpw.hypnos.util.Sound;
import org.json.JSONObject;

import java.io.*;
import java.nio.file.Paths;

public class Settings {

    private final String filePath = Paths.get(HypnOS.Path, "config.json").toString();
    public JSONObject jsonObject;

    public Settings() {
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
                HypnOS.logger.println("Loaded JSON from file: " + filePath);
            } else {
                loadDefaults();
            }
        } catch (IOException e) {
            loadDefaults();
        }
        updateVolume();
    }

    public void save() {
        try {
            FileWriter fileWriter = new FileWriter(filePath);
            fileWriter.write(jsonObject.toString(1));
            fileWriter.flush();
            fileWriter.close();
            HypnOS.logger.println("JSON saved to file: " + filePath);
        } catch (IOException e) {
            HypnOS.logger.println("Error writing to file: " + e.getMessage());
        }
    }

    public void loadDefaults() {
        HypnOS.logger.println("Loading default settings...");
        jsonObject = new JSONObject();
        jsonObject.put("music_volume", 100);
        jsonObject.put("sfx_volume", 100);
        jsonObject.put("mouse_sfx", true);
        jsonObject.put("keyboard_sfx", true);
        jsonObject.put("desktop", new JSONObject());
        jsonObject.put("autostart_log", false);
        jsonObject.put("intro", true);
        jsonObject.put("wallpaper", 0);
    }

    public void updateVolume() {
        HypnOS.logger.println("Updating Volume...");
        Sound.SoundGroup.MUSIC.setVolume(jsonObject.getInt("music_volume"));
        Sound.SoundGroup.SFX.setVolume(jsonObject.getInt("sfx_volume"));
    }
}

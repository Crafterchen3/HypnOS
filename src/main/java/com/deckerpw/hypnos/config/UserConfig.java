package com.deckerpw.hypnos.config;

import com.deckerpw.hypnos.machine.Machine;
import com.deckerpw.hypnos.machine.User;
import com.deckerpw.hypnos.util.Sound;
import org.json.JSONObject;

public class UserConfig extends FileConfig {

//Paths.get(HypnOS.Path, "config.json").toString()
    public UserConfig(Machine machine, User user) {
        super(machine, machine.getFilesystem().getFile(user.getConfigPath()).getPath());
    }

    @Override
    public void loadDefaults() {
        logger.println("Loading default settings...");
        jsonObject = new JSONObject();
        put("music_volume", 80);
        put("sfx_volume", 80);
        put("mouse_sfx", true);
        put("keyboard_sfx", true);
        put("desktop", new JSONObject());
        put("autostart_log", false);
        put("intro", true);
        put("wallpaper", 0);
    }

    public void updateVolume() {
        logger.println("Updating Volume...");
        Sound.SoundGroup.MUSIC.setVolume(getInt("music_volume"));
        Sound.SoundGroup.SFX.setVolume(getInt("sfx_volume"));
    }
}

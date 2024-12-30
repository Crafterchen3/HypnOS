package com.deckerpw.hypnos.machine;

import com.deckerpw.hypnos.config.UserConfig;
import com.deckerpw.hypnos.swing.SwingWindow;
import com.deckerpw.hypnos.ui.target.Screen;

import java.nio.file.Paths;

public class User {

    private String path;
    private String name;
    private int passwordHash;
    private UserConfig config;
    private Screen screen;

    public User(Machine machine, String path, String name, int passwordHash) {
        this.path = path;
        this.name = name;
        this.passwordHash = passwordHash;
        this.config = new UserConfig(machine, this);
        config.load();
    }

    public boolean checkCredentials(String username, int passwordHash) {
        return this.name.equals(username) && this.passwordHash == passwordHash;
    }

    public String getConfigPath() {
        return "0:/users/"+path+"/config.json";
    }

    public UserConfig getConfig() {
        return config;
    }

    public String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public Screen getScreen() {
        return screen;
    }

    public Screen createScreen(SwingWindow bridge) {
        if (screen != null) {
            return screen;
        }
        screen = new Screen(bridge);
        return screen;
    }

    public void logout() {
        config.put("desktop", getScreen().getDesktop().getIconConfig());
        config.save();
        screen = null;
    }

    int getPasswordHash() {
        return passwordHash;
    }

    void setPasswordHash(int hash) {
        this.passwordHash = hash;
    }

    void setPath(String path) {
        this.path = path;
    }

}

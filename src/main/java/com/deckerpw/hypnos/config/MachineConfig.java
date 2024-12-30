package com.deckerpw.hypnos.config;

import com.deckerpw.hypnos.machine.Machine;
import com.deckerpw.hypnos.machine.User;
import com.deckerpw.hypnos.util.Module;
import com.deckerpw.hypnos.util.ModuleManager;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MachineConfig extends FileConfig {

    private Machine machine;

    public MachineConfig(Machine machine) {
        super(machine, machine.getConfigPath());
        this.machine = machine;
    }

    @Override
    public void loadDefaults() {
        logger.println("Loading default settings...");
        jsonObject = new JSONObject();
        put("enabled_modules", new JSONArray());
        put("users", new JSONArray());
        save();
    }

    public ArrayList<User> getUsers() {
        JSONArray array = new JSONArray(getJSONArray("users"));
        ArrayList<User> users = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            JSONObject user = array.getJSONObject(i);
            users.add(new User(machine,user.getString("path"), user.getString("name"), user.getInt("password_hash")));
        }
        return users;
    }

    public ArrayList<Module> getEnabledModules() {
        JSONArray array = new JSONArray(getJSONArray("enabled_modules"));
        ArrayList<Module> modules = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            Module module = ModuleManager.getModule(array.getString(i));
            if (module != null)
                modules.add(module);
        }
        return modules;
    }

    public int getAdminPasswordHash() {
        if (!has("admin_password_hash")) {
            return 0;
        }
        return getInt("admin_password_hash");
    }
}

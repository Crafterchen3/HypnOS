package com.deckerpw.hypnos.config;

import org.json.JSONArray;
import org.json.JSONObject;

public interface IConfig {

    void load();

    void save();

    void loadDefaults();

    void put(String key, Object value);

    Object get(String key);

    String getString(String key);

    int getInt(String key);

    double getDouble(String key);

    float getFloat(String key);

    boolean getBoolean(String key);

    JSONObject getJSONObject(String key);

    JSONArray getJSONArray(String key);

    boolean has(String key);

}

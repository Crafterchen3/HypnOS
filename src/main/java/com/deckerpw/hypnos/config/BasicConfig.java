package com.deckerpw.hypnos.config;

import org.json.JSONArray;
import org.json.JSONObject;

public abstract class BasicConfig implements IConfig{

    public JSONObject jsonObject = new JSONObject();

    public void put(String key, Object value) {
        jsonObject.put(key, value);
    }

    public Object get(String key) {
        return jsonObject.get(key);
    }

    public String getString(String key) {
        return jsonObject.getString(key);
    }

    public int getInt(String key) {
        return jsonObject.getInt(key);
    }

    public double getDouble(String key) {
        return jsonObject.getDouble(key);
    }

    public float getFloat(String key) {
        return jsonObject.getFloat(key);
    }

    public boolean getBoolean(String key) {
        return jsonObject.getBoolean(key);
    }

    public JSONObject getJSONObject(String key) {
        return jsonObject.getJSONObject(key);
    }

    public JSONArray getJSONArray(String key) {
        return jsonObject.getJSONArray(key);
    }

    public boolean has(String key) {
        return jsonObject.has(key);
    }

}

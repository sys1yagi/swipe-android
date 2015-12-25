package com.sys1yagi.swipe.core.tool;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

public class JsonConverter {

    final static JsonConverter INSTANCE = new JsonConverter();

    Gson gson;

    private JsonConverter() {
        gson = new GsonBuilder()
                .create();
    }

    public static JsonConverter getInstance() {
        return INSTANCE;
    }

    public <T> T fromJson(String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }

    public <T> T fromJson(JsonObject json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }
}

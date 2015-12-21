package com.sys1yagi.swipe.core.tool;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sys1yagi.swipe.core.entity.index.Index;
import com.sys1yagi.swipe.core.entity.swipe.SwipeDocument;

public class SwipeEntityDecoder {

    private final static String KEY_TYPE = "type";

    private final static String TYPE_INDEX = "net.swipe.list";

    public Index decodeToIndex(Gson gson, String jsonString) {
        JsonObject json = new JsonParser().parse(jsonString).getAsJsonObject();
        if (!json.has(KEY_TYPE)) {
            throw new IllegalArgumentException("Invalid index swipe file. data:" + jsonString);
        }
        if (!TYPE_INDEX.equals(json.get(KEY_TYPE).getAsString())) {
            throw new IllegalArgumentException("Invalid index swipe file. data:" + jsonString);
        }
        return gson.fromJson(json, Index.class);
    }

    public SwipeDocument decodeToSwipe(Gson gson, String jsonString) {
        JsonObject json = new JsonParser().parse(jsonString).getAsJsonObject();
        if (json.has(KEY_TYPE) && TYPE_INDEX.equals(json.get(KEY_TYPE).getAsString())) {
            throw new IllegalArgumentException("Invalid contents swipe file. data:" + jsonString);
        }
        return gson.fromJson(json, SwipeDocument.class);
    }
}

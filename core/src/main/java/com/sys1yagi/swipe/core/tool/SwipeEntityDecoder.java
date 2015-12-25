package com.sys1yagi.swipe.core.tool;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sys1yagi.swipe.core.entity.index.Index;
import com.sys1yagi.swipe.core.entity.swipe.SwipeDocument;

import org.hjson.JsonValue;

public class SwipeEntityDecoder {

    private final static String KEY_TYPE = "type";

    private final static String TYPE_INDEX = "net.swipe.list";

    //TODO remove it
    private String hjson(String jsonString) {
        return JsonValue.readHjson(jsonString).asObject().toString();
    }

    public Index decodeToIndex(JsonConverter jsonConverter, String jsonString) {

        JsonObject json = new JsonParser().parse(hjson(jsonString)).getAsJsonObject();
        if (!json.has(KEY_TYPE)) {
            throw new IllegalArgumentException("Invalid index swipe file. data:" + jsonString);
        }
        if (!TYPE_INDEX.equals(json.get(KEY_TYPE).getAsString())) {
            throw new IllegalArgumentException("Invalid index swipe file. data:" + jsonString);
        }
        return jsonConverter.fromJson(json, Index.class);
    }

    public SwipeDocument decodeToSwipe(JsonConverter jsonConverter, String jsonString) {
        JsonObject json = new JsonParser().parse(hjson(jsonString)).getAsJsonObject();
        if (json.has(KEY_TYPE) && TYPE_INDEX.equals(json.get(KEY_TYPE).getAsString())) {
            throw new IllegalArgumentException("Invalid contents swipe file. data:" + jsonString);
        }
        return jsonConverter.fromJson(json, SwipeDocument.class);
    }
}

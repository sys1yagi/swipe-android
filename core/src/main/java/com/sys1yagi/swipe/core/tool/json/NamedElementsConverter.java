package com.sys1yagi.swipe.core.tool.json;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.sys1yagi.swipe.core.entity.swipe.NamedElements;
import com.sys1yagi.swipe.core.entity.swipe.SwipeElement;

import java.lang.reflect.Type;
import java.util.Map;

public class NamedElementsConverter implements JsonDeserializer<NamedElements> {
    @Override
    public NamedElements deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (json == null) {
            return null;
        }
        NamedElements namedElements = new NamedElements();

        for (Map.Entry<String, JsonElement> entry : json.getAsJsonObject().entrySet()) {
            SwipeElement swipeElement = JsonConverter.getInstance().fromJson(entry.getValue().getAsJsonObject(), SwipeElement.class);
            namedElements.put(entry.getKey(), swipeElement);
        }

        return namedElements;
    }
}

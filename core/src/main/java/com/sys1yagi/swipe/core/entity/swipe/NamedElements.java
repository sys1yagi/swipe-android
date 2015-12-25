package com.sys1yagi.swipe.core.entity.swipe;

import java.util.HashMap;
import java.util.Map;

public class NamedElements {
    Map<String, SwipeElement> elements = new HashMap<>();

    public SwipeElement get(String key) {
        return elements.get(key);
    }

    public void put(String key, SwipeElement value) {
        elements.put(key, value);
    }
}

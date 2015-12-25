package com.sys1yagi.swipe.core.entity.swipe;

import com.google.gson.annotations.SerializedName;

public class Font {

    @SerializedName("size")
    int size;

    @SerializedName("name")
    String name;

    public int getSize() {
        return size;
    }

    public String getName() {
        return name;
    }
}

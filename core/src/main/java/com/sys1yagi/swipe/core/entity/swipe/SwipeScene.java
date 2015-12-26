package com.sys1yagi.swipe.core.entity.swipe;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SwipeScene {

    @SerializedName("bc")
    String bc;

    @SerializedName("elements")
    List<SwipeElement> elements;

    public String getBc() {
        return bc;
    }

    public List<SwipeElement> getElements() {
        return elements;
    }
}


package com.sys1yagi.swipe.core.entity.swipe;

import com.google.gson.annotations.SerializedName;

public class Style {

    @SerializedName("color")
    String color;

    @SerializedName("alignment")
    String alignment;

    @SerializedName("font")
    Font font;

    public String getColor() {
        return color;
    }

    public String getAlignment() {
        return alignment;
    }

    public Font getFont() {
        return font;
    }
}

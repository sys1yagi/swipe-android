package com.sys1yagi.swipe.core.entity;

import com.google.gson.annotations.SerializedName;

public class Item {

    @SerializedName("url")
    String url;

    @SerializedName("title")
    String title;

    @SerializedName("icon")
    String icon;

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    public String getIcon() {
        return icon;
    }
}

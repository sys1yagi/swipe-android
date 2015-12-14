package com.sys1yagi.swipe.sample.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Index {

    @SerializedName("type")
    String type;

    @SerializedName("rowHeight")
    String rowHeight;

    @SerializedName("items")
    List<Item> items;

    public String getType() {
        return type;
    }

    public String getRowHeight() {
        return rowHeight;
    }

    public List<Item> getItems() {
        return items;
    }
}

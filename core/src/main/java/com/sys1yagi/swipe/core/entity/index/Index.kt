package com.sys1yagi.swipe.core.entity.index

import com.google.gson.annotations.SerializedName

class Index {

    @SerializedName("type")
    var type: String? = null

    @SerializedName("rowHeight")
    var rowHeight: String? = null

    @SerializedName("items")
    var items: List<Item>? = null
}

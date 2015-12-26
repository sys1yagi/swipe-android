package com.sys1yagi.swipe.core.entity.index

import com.google.gson.annotations.SerializedName

class Item {

    @SerializedName("url")
    var url: String? = null

    @SerializedName("title")
    var title: String? = null

    @SerializedName("icon")
    var icon: String? = null

}

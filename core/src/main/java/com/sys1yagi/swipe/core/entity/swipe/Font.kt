package com.sys1yagi.swipe.core.entity.swipe

import com.google.gson.annotations.SerializedName

class Font() {

    constructor(name: String?, size: Int) : this() {
        this.name = name
        this.size = size
    }

    @SerializedName("size")
    var size: Int = 0

    @SerializedName("name")
    var name: String? = null
}

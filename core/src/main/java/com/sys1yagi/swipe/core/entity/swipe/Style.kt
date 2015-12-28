package com.sys1yagi.swipe.core.entity.swipe

import com.google.gson.annotations.SerializedName

class Style() {

    constructor(color: String?, alignment: String?, font: Font?) : this() {
        this.color = color
        this.alignment = alignment
        this.font = font
    }

    @SerializedName("color")
    var color: String? = null

    @SerializedName("alignment")
    var alignment: String? = null

    @SerializedName("font")
    var font: Font? = null
}

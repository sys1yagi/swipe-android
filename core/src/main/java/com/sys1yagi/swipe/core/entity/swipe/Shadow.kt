package com.sys1yagi.swipe.core.entity.swipe

import com.google.gson.annotations.SerializedName

class Shadow : Cloneable {

    @SerializedName("offset")
    var offset: IntArray? = null

    @SerializedName("opacity")
    var opacity: Float = 0.toFloat()

    @SerializedName("radius")
    var radius: Int = 0

    override public fun clone(): Shadow {
        val clone = Shadow()

        clone.offset = offset?.copyOf()
        clone.opacity = opacity
        clone.radius = radius

        return clone
    }
}

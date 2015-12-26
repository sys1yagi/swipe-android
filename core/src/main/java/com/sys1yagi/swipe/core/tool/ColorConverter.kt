package com.sys1yagi.swipe.core.tool

import android.support.annotation.Size

class ColorConverter {
    companion object {
        fun toColorInt(@Size(min = 3) color: String): Int {
            var colorString = color
            if (colorString.startsWith("#")) {
                colorString = colorString.substring(1)
            }
            if (colorString.length == 3) {
                colorString = StringBuilder(6)
                        .append(colorString[0])
                        .append(colorString[0])
                        .append(colorString[1])
                        .append(colorString[1])
                        .append(colorString[2])
                        .append(colorString[2]).toString()
            }
            var convertColor = java.lang.Long.parseLong(colorString, 16)

            if (colorString.length < 8) {
                convertColor = convertColor or -16777216
            }

            return convertColor.toInt()
        }
    }
}

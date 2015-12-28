package com.sys1yagi.swipe.core.entity.swipe

import java.util.*

class SwipeMarkdown {
    companion object {
        var prefixes = mapOf(
                "-" to "\u2022", // bullet (U+2022), http://graphemica.com/%E2%80%A2
                "```" to " "
        )
    }

    // TODO
    val defaultStyles = mapOf(
            "#" to Style()
    )

    var styles: Map<String, Style> = HashMap()
        internal set
}

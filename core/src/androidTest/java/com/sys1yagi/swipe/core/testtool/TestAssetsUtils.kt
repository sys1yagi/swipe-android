package com.sys1yagi.swipe.core.testtool

import android.content.Context

object TestAssetsUtils {

    fun loadFromAssets(context: Context, name: String): String? {
        return context.assets.open(name)
                .bufferedReader("utf-8")
                .readText()
    }
}

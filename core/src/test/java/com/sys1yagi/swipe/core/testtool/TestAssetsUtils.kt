package com.sys1yagi.swipe.core.testtool

import java.io.File
import java.io.FileInputStream

object TestAssetsUtils {

    private val ASSETS_PATH = "src/test/assets"

    fun loadFromAssets(name: String): String? {
        val file = File(ASSETS_PATH, name)
        return FileInputStream(file)
                .bufferedReader("utf-8")
                .readText()
    }
}

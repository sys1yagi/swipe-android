package com.sys1yagi.swipe.core.util

import android.content.Context
import android.content.res.AssetManager
import android.text.TextUtils
import java.util.*

class AssetsUtils {

    fun loadFromAssets(context: Context, fileName: String): String? {
        return context.assets.open(fileName).bufferedReader().readText()
    }

    fun scan(assetManager: AssetManager, pathList: MutableList<String>) {
        scan(assetManager, pathList, "")
    }

    internal fun scan(assetManager: AssetManager, pathList: MutableList<String>, path: String) {
        if (IGNORE_NAME.contains(path)) {
            return
        }

        for (file in assetManager.list(path)) {
            if (file.contains(".")) {
                pathList.add(path + (if (TextUtils.isEmpty(path)) "" else "/") + file)
            } else {
                scan(assetManager, pathList,
                        path + (if (TextUtils.isEmpty(path)) "" else "/") + file)
            }
        }
    }

    companion object {

        private val IGNORE_NAME = object : ArrayList<String>() {
            init {
                add("webkit")
                add("sounds")
                add("images")
            }
        }
    }
}

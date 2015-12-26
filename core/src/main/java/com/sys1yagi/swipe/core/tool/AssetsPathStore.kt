package com.sys1yagi.swipe.core.tool


import com.sys1yagi.swipe.core.util.AssetsUtils

import android.content.res.AssetManager
import android.text.TextUtils

import java.util.ArrayList

class AssetsPathStore(assetManager: AssetManager) {

    internal var assetsUtils = AssetsUtils()

    private val pathList = ArrayList<String>()

    init {
        assetsUtils.scan(assetManager, pathList)
    }

    operator fun contains(name: String): Boolean {
        return !TextUtils.isEmpty(toPath(name))
    }

    fun toPath(name: String): String? {
        for (path in pathList) {
            if (path.lastIndexOf(name) != -1) {
                return path
            }
        }
        return null
    }

    fun getAssetPath(name: String): String? {
        if (!contains(name)) {
            return null
        }
        return "file:///android_asset/" + toPath(name)!!
    }

}

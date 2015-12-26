package com.sys1yagi.swipe.core.util

object ListUtils {

    fun <T> isEmpty(list: List<T>?): Boolean {
        return list == null || list.isEmpty()
    }
}

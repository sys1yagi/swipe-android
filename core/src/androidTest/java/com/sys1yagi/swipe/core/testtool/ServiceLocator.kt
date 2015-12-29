package com.sys1yagi.swipe.core.testtool

import android.support.test.InstrumentationRegistry

object ServiceLocator {

    fun <T> getSystemService(name: String, clazz: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return InstrumentationRegistry.getContext().getSystemService(name) as T
    }
}

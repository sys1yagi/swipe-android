package com.sys1yagi.swipe.core.tool

import com.squareup.picasso.Picasso

import android.content.Context

class PabloPicasso(context: Context) {

    var picasso: Picasso

    init {
        picasso = Picasso.with(context)
    }


}

package com.sys1yagi.swipe.core.tool;

import com.squareup.picasso.Picasso;

import android.content.Context;

public class PabloPicasso {

    public Picasso picasso;

    public PabloPicasso(Context context) {
        picasso = Picasso.with(context);
    }


}

package com.sys1yagi.swipe.sample.tools;

import com.squareup.picasso.Picasso;

import android.content.Context;

public class PabloPicasso {

    public Picasso picasso;

    public PabloPicasso(Context context) {
        picasso = Picasso.with(context);
    }


}

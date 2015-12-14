package com.sys1yagi.swipe.sample.activity;

import com.sys1yagi.swipe.browser.SwipeBrowser;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new SwipeBrowser().initialize(this);
    }
}

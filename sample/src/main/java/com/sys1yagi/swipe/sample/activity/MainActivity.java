package com.sys1yagi.swipe.sample.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.sys1yagi.swipe.browser.SwipeBrowser;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new SwipeBrowser().initialize(this);
    }
}

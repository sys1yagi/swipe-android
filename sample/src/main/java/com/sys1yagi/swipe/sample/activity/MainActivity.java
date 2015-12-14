package com.sys1yagi.swipe.sample.activity;

import com.google.gson.Gson;

import com.sys1yagi.swipe.browser.view.IndexAdapter;
import com.sys1yagi.swipe.core.entity.Index;
import com.sys1yagi.swipe.core.util.AssetsUtils;
import com.sys1yagi.swipe.sample.R;
import com.sys1yagi.swipe.sample.databinding.ActivityMainBinding;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    AssetsUtils assetsUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        assetsUtils = new AssetsUtils();
        setupUi();
    }

    private void setupUi() {
        IndexAdapter adapter = new IndexAdapter(this);
        binding.listView.setAdapter(adapter);
        binding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        setupIndex(adapter);
    }

    private void setupIndex(IndexAdapter adapter) {
        setTitle("index.swipe");
        String json = assetsUtils.loadFromAssets(this, "index.swipe");
        Gson gson = new Gson();
        Index index = gson.fromJson(json, Index.class);
        adapter.addAll(index.getItems());
    }
}

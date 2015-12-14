package com.sys1yagi.swipe.browser;

import com.google.gson.Gson;

import com.sys1yagi.browser.R;
import com.sys1yagi.browser.databinding.ActivitySwipeIndexBinding;
import com.sys1yagi.swipe.browser.view.IndexAdapter;
import com.sys1yagi.swipe.core.entity.Index;
import com.sys1yagi.swipe.core.util.AssetsUtils;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.view.View;
import android.widget.AdapterView;

public class SwipeBrowser {

    ActivitySwipeIndexBinding binding;

    AssetsUtils assetsUtils;

    Activity target;

    String indexFileName = "index.swipe";

    public SwipeBrowser() {
    }

    public SwipeBrowser(String indexFileName) {
        this.indexFileName = indexFileName;
    }

    public void initialize(Activity activity) {
        this.target = activity;
        this.assetsUtils = new AssetsUtils();
        this.binding = DataBindingUtil.setContentView(activity, R.layout.activity_swipe_index);
        setupUi();
    }

    private void setupUi() {
        IndexAdapter adapter = new IndexAdapter(target);
        binding.listView.setAdapter(adapter);
        binding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        setupIndex(adapter);
    }

    private void setupIndex(IndexAdapter adapter) {
        target.setTitle(indexFileName);
        String json = assetsUtils.loadFromAssets(target, indexFileName);
        Gson gson = new Gson();
        Index index = gson.fromJson(json, Index.class);
        adapter.addAll(index.getItems());
    }
}

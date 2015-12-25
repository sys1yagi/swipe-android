package com.sys1yagi.swipe.browser;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.view.View;
import android.widget.AdapterView;

import com.sys1yagi.browser.R;
import com.sys1yagi.browser.databinding.ActivitySwipeIndexBinding;
import com.sys1yagi.swipe.browser.activity.SwipeContentsBrowseActivity;
import com.sys1yagi.swipe.browser.view.IndexAdapter;
import com.sys1yagi.swipe.core.entity.index.Index;
import com.sys1yagi.swipe.core.entity.index.Item;
import com.sys1yagi.swipe.core.tool.JsonConverter;
import com.sys1yagi.swipe.core.tool.SwipeEntityDecoder;
import com.sys1yagi.swipe.core.util.AssetsUtils;

public class SwipeBrowser {

    private final static String DEFAULT_INDEX_FILE_NAME = "index.swipe";

    ActivitySwipeIndexBinding binding;

    AssetsUtils assetsUtils;

    Activity target;

    SwipeEntityDecoder swipeEntityDecoder;

    String indexFileName;

    public SwipeBrowser() {
        this(DEFAULT_INDEX_FILE_NAME);
    }

    public SwipeBrowser(String indexFileName) {
        this.indexFileName = indexFileName;
        this.swipeEntityDecoder = new SwipeEntityDecoder();
    }

    public void initialize(Activity activity) {
        this.target = activity;
        this.assetsUtils = new AssetsUtils();
        this.binding = DataBindingUtil.setContentView(activity, R.layout.activity_swipe_index);
        setupUi();
    }

    private void setupUi() {
        final IndexAdapter adapter = new IndexAdapter(target);
        binding.listView.setAdapter(adapter);
        binding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Item item = adapter.getItem(position);
                Intent intent = SwipeContentsBrowseActivity.createIntent(adapter.getContext(), item.getUrl());
                adapter.getContext().startActivity(intent);
            }
        });
        setupIndex(adapter);
    }

    private void setupIndex(IndexAdapter adapter) {
        target.setTitle(indexFileName);
        String jsonString = assetsUtils.loadFromAssets(target, indexFileName);
        Index index = swipeEntityDecoder.decodeToIndex(JsonConverter.getInstance(), jsonString);
        adapter.addAll(index.getItems());
    }
}

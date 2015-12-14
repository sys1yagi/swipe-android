package com.sys1yagi.swipe.browser;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.view.View;
import android.widget.AdapterView;

import com.google.gson.Gson;
import com.sys1yagi.browser.R;
import com.sys1yagi.browser.databinding.ActivitySwipeIndexBinding;
import com.sys1yagi.swipe.browser.view.IndexAdapter;
import com.sys1yagi.swipe.core.entity.Index;
import com.sys1yagi.swipe.core.entity.Item;
import com.sys1yagi.swipe.core.tool.SwipeEntityDecoder;
import com.sys1yagi.swipe.core.util.AssetsUtils;

public class SwipeBrowser {

    private final static String DEFAULT_INDEX_FILE_NAME = "index.swipe";

    ActivitySwipeIndexBinding binding;

    AssetsUtils assetsUtils;

    Activity target;

    Gson gson = new Gson();

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

            }
        });
        setupIndex(adapter);
    }

    private void setupIndex(IndexAdapter adapter) {
        target.setTitle(indexFileName);
        String jsonString = assetsUtils.loadFromAssets(target, indexFileName);
        Index index = swipeEntityDecoder.decodeToIndex(gson, jsonString);
        adapter.addAll(index.getItems());
    }
}

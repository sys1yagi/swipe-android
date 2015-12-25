package com.sys1yagi.swipe.browser.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.sys1yagi.browser.R;
import com.sys1yagi.browser.databinding.ActivitySwipeContentsBrowseBinding;
import com.sys1yagi.swipe.browser.view.SwipeViewPagerCountAdapter;
import com.sys1yagi.swipe.core.entity.swipe.SwipeDocument;
import com.sys1yagi.swipe.core.tool.AssetsPathStore;
import com.sys1yagi.swipe.core.tool.SwipeEntityDecoder;
import com.sys1yagi.swipe.core.tool.json.JsonConverter;
import com.sys1yagi.swipe.core.util.AssetsUtils;

public class SwipeContentsBrowseActivity extends AppCompatActivity {

    private final static String EXTRA_SWIPE_FILE_NAME = "swipe_file_name";

    public static Intent createIntent(Context context, String swipeFileName) {
        Intent intent = new Intent(context, SwipeContentsBrowseActivity.class);
        intent.putExtra(EXTRA_SWIPE_FILE_NAME, swipeFileName);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String swipeFileName = getIntent().getStringExtra(EXTRA_SWIPE_FILE_NAME);
        SwipeDocument swipeDocument = loadSwipeDocument(swipeFileName);

        applyOrientation(swipeDocument);

        ActivitySwipeContentsBrowseBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_swipe_contents_browse);

        binding.pager.setAdapter(new SwipeViewPagerCountAdapter(swipeDocument.getPages().size()));
        binding.pager.setSwipeDocument(swipeDocument);
    }

    void applyOrientation(SwipeDocument swipeDocument) {
        switch (swipeDocument.getOrientation()) {
            case PORTRAIT:
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                break;
            case LANDSCAPE:
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                break;
            case UNKNOWN:
                throw new IllegalStateException("invalid orientation setting : " + swipeDocument.getOrientationString());
        }
    }

    private SwipeDocument loadSwipeDocument(String swipeFileName) {
        AssetsUtils assetsUtils = new AssetsUtils();
        AssetsPathStore assetsPathStore = new AssetsPathStore(this.getAssets());

        setTitle(swipeFileName);
        if (!assetsPathStore.contains(swipeFileName)) {
            Toast.makeText(this, swipeFileName + " is not found", Toast.LENGTH_SHORT).show();
            return null;
        }
        String jsonString = assetsUtils.loadFromAssets(this, assetsPathStore.toPath(swipeFileName));
        SwipeEntityDecoder swipeEntityDecoder = new SwipeEntityDecoder();
        return swipeEntityDecoder.decodeToSwipe(JsonConverter.getInstance(), jsonString);
    }


}

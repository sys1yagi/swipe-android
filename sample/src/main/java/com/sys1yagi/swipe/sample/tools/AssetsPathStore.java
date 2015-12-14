package com.sys1yagi.swipe.sample.tools;

import com.sys1yagi.swipe.sample.util.AssetsUtils;

import android.content.res.AssetManager;

import java.util.ArrayList;
import java.util.List;

public class AssetsPathStore {

    AssetsUtils assetsUtils = new AssetsUtils();

    private List<String> pathList = new ArrayList<>();

    public AssetsPathStore(AssetManager assetManager) {
        assetsUtils.scan(assetManager, pathList);
    }

    public boolean contains(String name) {
        for (String path : pathList) {
            if (path.lastIndexOf(name) != -1) {
                return true;
            }
        }
        return false;
    }

    public String toPath(String name) {
        for (String path : pathList) {
            if (path.lastIndexOf(name) != -1) {
                return path;
            }
        }
        return null;
    }

    public String getAssetPath(String name) {
        if (!contains(name)) {
            return null;
        }
        return "file:///android_asset/" + toPath(name);
    }

}

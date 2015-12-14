package com.sys1yagi.swipe.core.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class AssetsUtils {

    private final static List<String> IGNORE_NAME = new ArrayList<String>() {{
        add("webkit");
        add("sounds");
        add("images");
    }};

    public String loadFromAssets(Context context, String fileName) {
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(context.getAssets().open(fileName)))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (IOException e) {
            return null;
        }
    }

    public void scan(AssetManager assetManager, List<String> pathList) {
        scan(assetManager, pathList, "");
    }

    void scan(AssetManager assetManager, List<String> pathList, String path) {
        if (IGNORE_NAME.contains(path)) {
            return;
        }
        try {
            for (String file : assetManager.list(path)) {
                if (file.contains(".")) {
                    pathList.add(path + (TextUtils.isEmpty(path) ? "" : "/") + file);
                } else {
                    scan(assetManager, pathList,
                            path + (TextUtils.isEmpty(path) ? "" : "/") + file);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

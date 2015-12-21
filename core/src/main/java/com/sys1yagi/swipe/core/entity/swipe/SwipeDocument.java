package com.sys1yagi.swipe.core.entity.swipe;

import android.text.TextUtils;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * https://github.com/snakajima/swipe/blob/master/SPECIFICATION.md
 * <p/>
 * title (String): Title of the document, optional
 * bc (Color): Background color, defalut is darkGray
 * dimension ([Int, Int]): Dimension of the document, default is [320, 568]
 * paging (String): Paging direction, vertical (default), or leftToRight
 * orientation (String): Document orientation, portrait (default) or landscape
 * scenes ({Name:Scene}): Named Scenes dictionary
 * elements ({Name:Element}): Named Elements dictionary
 * paths ({Name:Path}): Named Paths dictionary
 * markdown ({Name:Style}): Named Markdown style
 * voices ({Name:VoiceInfo}): Named Voice style
 * pages ([Page,...]): Collection of Pages
 * resources ([String,...]):Resource keys for on-demand resources
 * viewstate (Bool):Indicate if we need to save&restore view state. The default is true.
 */
public class SwipeDocument {

    @SerializedName("title")
    String title;

    @SerializedName("bc")
    int bc;

    @SerializedName("dimension")
    int[] dimension;

    // TODO enum vertical|leftToRight
    @SerializedName("paging")
    String paging;

    // TODO enum portrait|landscape
    @SerializedName("orientation")
    String orientation;

    @SerializedName("scenes")
    JsonObject scenes;

    @SerializedName("elements")
    JsonObject elements;

    @SerializedName("paths")
    JsonObject paths;

    @SerializedName("markdown")
    SwipeMarkdown markdown;

    @SerializedName("voices")
    SwipeVoice voice;

    @SerializedName("pages")
    List<SwipePage> pages;

    @SerializedName("resources")
    List<String> resources;

    @SerializedName("viewstate")
    boolean viewState;

    public String getTitle() {
        return title;
    }

    public int getBc() {
        return bc;
    }

    public int[] getDimension() {
        return dimension;
    }

    public String getPaging() {
        if (TextUtils.isEmpty(paging)) {
            return "vertical";
        }
        return paging;
    }

    public String getOrientation() {
        if (TextUtils.isEmpty(orientation)) {
            return "portrait";
        }
        return orientation;
    }

    public JsonObject getScenes() {
        return scenes;
    }

    public JsonObject getElements() {
        return elements;
    }

    public JsonObject getPaths() {
        return paths;
    }

    public SwipeMarkdown getMarkdown() {
        return markdown;
    }

    public SwipeVoice getVoice() {
        return voice;
    }

    public List<SwipePage> getPages() {
        return pages;
    }

    public List<String> getResources() {
        return resources;
    }

    public boolean isViewState() {
        return viewState;
    }
}

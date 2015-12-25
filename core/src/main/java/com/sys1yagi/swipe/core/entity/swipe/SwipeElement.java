package com.sys1yagi.swipe.core.entity.swipe;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Element properties
 * id (String): the element identifier to identify an element in the associated Scene
 * element (String): the name of the named Element to inherit properties from
 * x (Float or Percent): x-position of the left-top corner of element, default is 0
 * y (Float or Percent): y-position of the left-top corner of the element, default is 0
 * pos ([Float/Percent, Float/Percent]): alternative way to specificy the position by the location of the anchor point
 * anchor ([Float/Percent, Float/Percent]): anchor point, default is ["50%", "50%"]
 * w (Float, Percent or "fill"): width of the element, default is "100%".
 * h (Float, Percent or "fill"): height of the element, default is "100%"
 * bc (Color): background color, default is clear, animatable
 * clip (Boolean): Specifies clipping behavior, default is false
 * borderWidth (Float): Width of the border, default is 0, animatable
 * borderColor (Color): Color of the border, animatable
 * cornerRadius (Float): Size of the corner radius, animatable
 * opacity (Float): Opacity of the element, between 0 to 1, animatable
 * rotate (Float or Float[3]): Rotation in degree around the anchor point, clockwise, defalut is 0, animatable.
 * scale (Float or [Float, Float]): Scaling factor around the anchor point, default is [1, 1], animatable
 * translate ([Float, Float]): Translation, default is [0, 0], animatable
 * text (String): Text to display
 * textAlign (String): Text alignment, center (default), left or right
 * fontSize (Float or Percent): Font size
 * textColor (Color): Color of the text, animatable
 * img (URL): Image to display, animatable
 * mask (URL): Image mask (PNG with the alpha channel)
 * sprite (URL): Sprite to display
 * slice ([Int, Int]): Dimension of the sprite
 * slot ([Int, Int]): Slot to diplay, animatable
 * path (Path): Path to display (SVG style), animatable
 * lineWidth (Float): Width of stroke, default is 0
 * strokeColor (Color): Color of the stroke, default is black, animatable
 * fillColor (Color): Fill color, default is clear, animatable
 * strokeStart (Float): Starting offset, default is 0, animatable
 * strokeEnd (Float): Ending offset, default is 1, animatable
 * video or radio (URL): Video/Radio to play
 * videoStart (Float): Starting point of the video in seconds, default is 0
 * videoDuration (Float): Ending point of the video in seconds
 * stream (Bool): Specifies if the resource specified with the video tag is stream or not, default is false
 * to (Transition Animation): Specifies the Transitional Animation
 * loop (Loop Animation): Specifies the Loop Animation
 * tiling (Bool): Specifies the tiling (to be used with shift loop animation)
 * action (String): Specifies the Action
 * repeat (Bool): Repeat rule for the element. The default is false.
 */
public class SwipeElement {

    @SerializedName("id")
    String id;

    @SerializedName("element")
    String element;

    @SerializedName("x")
    String x;

    @SerializedName("y")
    String y;

    @SerializedName("pos")
    String pos;

    @SerializedName("anchor")
    String anchor;

    @SerializedName("w")
    String w;

    @SerializedName("h")
    String h;

    @SerializedName("bc")
    String bc;

    @SerializedName("clip")
    boolean clip;

    @SerializedName("borderWidth")
    float borderWidth;

    @SerializedName("borderColor")
    String borderColor;

    @SerializedName("cornerRadius ")
    float cornerRadius;

    @SerializedName("opacity")
    float opacity;

    @SerializedName("rotate")
    String rotate;

    @SerializedName("scale")
    String scale;

    @SerializedName("translate")
    float[] translate;

    @SerializedName("text")
    String text;

    @SerializedName("markdown")
    List<String> markdown;

    @SerializedName("textAlign")
    String textAlign;

    @SerializedName("fontSize")
    String fontSize;

    @SerializedName("textColor")
    String textColor;

    @SerializedName("img")
    String img;

    @SerializedName("mask")
    String mask;

    @SerializedName("sprite")
    String sprite;

    @SerializedName("slice")
    int[] slice;

    @SerializedName("slot")
    int[] slot;

    // TODO
    @SerializedName("path")
    String path;

    @SerializedName("video")
    String video;

    @SerializedName("radio")
    String radio;

    @SerializedName("stream")
    boolean stream;

    // TODO
    @SerializedName("to")
    JsonObject to;

    @SerializedName("loop")
    JsonObject loop;

    @SerializedName("tiling")
    boolean tiling;

    @SerializedName("action")
    String action;

    @SerializedName("repeat")
    boolean repeat;

    public String getId() {
        return id;
    }

    public String getElement() {
        return element;
    }

    public String getX() {
        return x;
    }

    public String getY() {
        return y;
    }

    public String getPos() {
        return pos;
    }

    public String getAnchor() {
        return anchor;
    }

    public String getW() {
        return w;
    }

    public String getH() {
        return h;
    }

    public String getBc() {
        return bc;
    }

    public boolean isClip() {
        return clip;
    }

    public float getBorderWidth() {
        return borderWidth;
    }

    public String getBorderColor() {
        return borderColor;
    }

    public float getCornerRadius() {
        return cornerRadius;
    }

    public float getOpacity() {
        return opacity;
    }

    public String getRotate() {
        return rotate;
    }

    public String getScale() {
        return scale;
    }

    public float[] getTranslate() {
        return translate;
    }

    public String getText() {
        return text;
    }

    public List<String> getMarkdown() {
        return markdown;
    }

    public String getTextAlign() {
        return textAlign;
    }

    public String getFontSize() {
        return fontSize;
    }

    public String getTextColor() {
        return textColor;
    }

    public String getImg() {
        return img;
    }

    public String getMask() {
        return mask;
    }

    public String getSprite() {
        return sprite;
    }

    public int[] getSlice() {
        return slice;
    }

    public int[] getSlot() {
        return slot;
    }

    public String getPath() {
        return path;
    }

    public String getVideo() {
        return video;
    }

    public String getRadio() {
        return radio;
    }

    public boolean isStream() {
        return stream;
    }

    public JsonObject getTo() {
        return to;
    }

    public JsonObject getLoop() {
        return loop;
    }

    public boolean isTiling() {
        return tiling;
    }

    public String getAction() {
        return action;
    }

    public boolean isRepeat() {
        return repeat;
    }
}

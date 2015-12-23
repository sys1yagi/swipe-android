package com.sys1yagi.swipe.core.entity.swipe;

import com.google.gson.JsonArray;
import com.google.gson.annotations.SerializedName;

/**
 * Page Properties
 * <p/>
 * bc (Color): Background color, the default is white
 * fpt (Int): Frame per second, the default is 60
 * transition (String): Inter-page transition style, scroll (default), fadeIn or replace
 * play (String): Play(animation) control, auto (default), pause, always or scroll
 * duration (Float): Duration of the animation in seconds, the default is 0.2 seconds
 * repeat (Boolean): Repeat rule of the animation, default is false
 * rewind (Boolean): Rewind rule of the animation when the user leaves the page, defaul is false
 * scene (String): Name of the scene, default is *
 * audio (URL): Specifies the sound effect to be played in sync with the animation
 * speech (SpeechInfo): Specifies the text-to-speech to be played in sync with the animation
 * vibrate (Bool): Specifies the vibration in sync with the animation, the default is false
 * elements ([Element+]): Collection of Elements
 * eyePosition (Float): Eye position (z-height) for the perspective relative to width, default is 1.0
 */
public class SwipePage {

    @SerializedName("bc")
    String bc;

    @SerializedName("fpt")
    int fpt;

    // scroll|fadeIn|replace
    @SerializedName("transition")
    String transition;

    // auto|pause|always|scroll
    @SerializedName("play")
    String play;

    @SerializedName("duration")
    float duration;

    // default is false
    @SerializedName("repeat")
    boolean repeat;

    // defaul is false
    @SerializedName("rewind")
    boolean rewind;

    @SerializedName("scene")
    String scene;

    @SerializedName("audio")
    String audio;

    @SerializedName("speech")
    String speech;

    @SerializedName("vibrate")
    boolean vibrate;

    @SerializedName("elements")
    JsonArray elements;

    @SerializedName("eyePosition")
    float eyePosition;

    public String getBc() {
        return bc;
    }

    public int getFpt() {
        return fpt;
    }

    public String getTransition() {
        return transition;
    }

    public String getPlay() {
        return play;
    }

    public float getDuration() {
        return duration;
    }

    public boolean isRepeat() {
        return repeat;
    }

    public boolean isRewind() {
        return rewind;
    }

    public String getScene() {
        return scene;
    }

    public String getAudio() {
        return audio;
    }

    public String getSpeech() {
        return speech;
    }

    public boolean isVibrate() {
        return vibrate;
    }

    public JsonArray getElements() {
        return elements;
    }

    public float getEyePosition() {
        return eyePosition;
    }
}

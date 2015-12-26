package com.sys1yagi.swipe.core.tool;

import android.support.annotation.Size;

public class ColorConverter {
    public static int toColorInt(@Size(min = 3) String color) {
        String colorString = color;
        if (colorString.startsWith("#")) {
            colorString = colorString.substring(1);
        }
        if (colorString.length() == 3) {
            colorString = new StringBuilder(6)
                    .append(colorString.charAt(0))
                    .append(colorString.charAt(0))
                    .append(colorString.charAt(1))
                    .append(colorString.charAt(1))
                    .append(colorString.charAt(2))
                    .append(colorString.charAt(2))
                    .toString();
        }
        long convertColor = Long.parseLong(colorString, 16);

        if (colorString.length() < 8) {
            convertColor |= 0x00000000ff000000;
        }

        return (int) convertColor;
    }
}

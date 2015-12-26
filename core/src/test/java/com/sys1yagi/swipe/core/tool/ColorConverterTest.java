package com.sys1yagi.swipe.core.tool;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ColorConverterTest {

    @Test
    public void testToColorInt() throws Exception {
        {
            int color = ColorConverter.Companion.toColorInt("#000000");
            assertThat(color).isEqualTo(-16777216);
        }
        {
            int color = ColorConverter.Companion.toColorInt("#FFFFFF");
            assertThat(color).isEqualTo(-1);
        }
        {
            int color = ColorConverter.Companion.toColorInt("#ffffff");
            assertThat(color).isEqualTo(-1);
        }
        {
            int color = ColorConverter.Companion.toColorInt("ffffff");
            assertThat(color).isEqualTo(-1);
        }
        {
            int color = ColorConverter.Companion.toColorInt("#fff");
            assertThat(color).isEqualTo(-1);
        }
        {
            int color = ColorConverter.Companion.toColorInt("fff");
            assertThat(color).isEqualTo(-1);
        }
        {
            int color = ColorConverter.Companion.toColorInt("abc");
            assertThat(color).isEqualTo(-5588020);
        }
    }
}

package com.sys1yagi.swipe.core.tool

import org.junit.Test

import org.assertj.core.api.Assertions.assertThat

class ColorConverterTest {

    @Test
    @Throws(Exception::class)
    fun testToColorInt() {
        run {
            val color = ColorConverter.toColorInt("#000000")
            assertThat(color).isEqualTo(-16777216)
        }
        run {
            val color = ColorConverter.toColorInt("#FFFFFF")
            assertThat(color).isEqualTo(-1)
        }
        run {
            val color = ColorConverter.toColorInt("#ffffff")
            assertThat(color).isEqualTo(-1)
        }
        run {
            val color = ColorConverter.toColorInt("ffffff")
            assertThat(color).isEqualTo(-1)
        }
        run {
            val color = ColorConverter.toColorInt("#fff")
            assertThat(color).isEqualTo(-1)
        }
        run {
            val color = ColorConverter.toColorInt("fff")
            assertThat(color).isEqualTo(-1)
        }
        run {
            val color = ColorConverter.toColorInt("abc")
            assertThat(color).isEqualTo(-5588020)
        }
    }
}

package com.sys1yagi.swipe.core.view


import android.graphics.Paint
import android.graphics.Rect
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.sys1yagi.swipe.core.entity.swipe.SwipeDocument
import com.sys1yagi.swipe.core.testtool.TestAssetsUtils
import com.sys1yagi.swipe.core.tool.SwipeEntityDecoder
import com.sys1yagi.swipe.core.tool.json.JsonConverter
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SwipeRendererTest {

    lateinit var swipeDocument: SwipeDocument

    lateinit var renderer: SwipeRenderer

    @Before
    fun setUp() {
        val swipeEntityDecoder = SwipeEntityDecoder()
        swipeDocument = swipeEntityDecoder.decodeToSwipe(JsonConverter.getInstance(),
                TestAssetsUtils.loadFromAssets(
                        InstrumentationRegistry.getContext(),
                        "swipe.swipe")!!)
        renderer = SwipeRenderer(swipeDocument)
    }

    @Test
    fun measureMarkdown() {
        // TODO dimensionを考慮する
        renderer.displaySize = Rect(0, 0, 1794, 1080)

        val paint = Paint()

        run {
            paint.textSize = 92f

            val height = renderer.measureMarkdownHeight(swipeDocument, swipeDocument.pages[0].elements[0], swipeDocument.pages[0].elements[0].markdown)
            assertThat(height).isEqualTo(paint.fontSpacing)
        }

        run {
            var expected = 0.0f
            paint.textSize = 60f
            expected += paint.fontSpacing
            paint.textSize = 42f
            expected += paint.fontSpacing * 4
            paint.textSize = 48f
            expected += paint.fontSpacing

            val height = renderer.measureMarkdownHeight(swipeDocument, swipeDocument.pages[1].elements[0], swipeDocument.pages[1].elements[0].markdown)
            assertThat(height).isEqualTo(expected)
        }
    }
}

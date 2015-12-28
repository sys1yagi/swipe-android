package com.sys1yagi.swipe.core.view


import com.sys1yagi.swipe.core.entity.swipe.SwipeDocument
import com.sys1yagi.swipe.core.testtool.TestAssetsUtils
import com.sys1yagi.swipe.core.tool.SwipeEntityDecoder
import com.sys1yagi.swipe.core.tool.json.JsonConverter
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class SwipeRendererTest {

    lateinit var swipeDocument: SwipeDocument

    lateinit var renderer: SwipeRenderer

    @Before
    fun setUp() {
        val swipeEntityDecoder = SwipeEntityDecoder()
        swipeDocument = swipeEntityDecoder.decodeToSwipe(JsonConverter.getInstance(), TestAssetsUtils.loadFromAssets("swipe.swipe")!!)
        renderer = SwipeRenderer(swipeDocument)
    }

    @Test
    fun measureMarkdown() {

        // display size
        // expected height

//        val height = renderer.measureMarkdownHeight(swipeDocument, swipeDocument.pages[0].elements[0], swipeDocument.pages[0].elements[0].markdown)
//        assertThat(height).isEqualTo(0)
    }
}

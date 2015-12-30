package com.sys1yagi.swipe.core.entity.swipe

import com.sys1yagi.swipe.core.testtool.TestAssetsUtils
import com.sys1yagi.swipe.core.tool.SwipeEntityDecoder
import com.sys1yagi.swipe.core.tool.json.JsonConverter
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.data.Index
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class SwipeDocumentTest {

    val swipeEntityDecoder = SwipeEntityDecoder()

    @Test
    @Throws(Exception::class)
    fun decodeFromJson() {
        val swipe = swipeEntityDecoder.decodeToSwipe(JsonConverter.getInstance(), TestAssetsUtils.loadFromAssets("swipe.swipe")!!)
        assertThat(swipe).isNotNull()
        assertThat(swipe.getBc()).isEqualTo(0)
        assertThat(swipe.getTitle()).isEqualTo("Swipe")
        assertThat(swipe.getDimension()).hasSize(2).contains(1280, Index.atIndex(0)).contains(0, Index.atIndex(1))

        assertThat(swipe.getPaging()).isEqualTo("vertical")
        assertThat(swipe.getOrientation()).isEqualTo(SwipeDocument.Orientation.LANDSCAPE)

        assertThat(swipe.getScenes().get("*")!!.getBc()).isEqualTo("#ddf")
        assertThat(swipe.getScenes().get("demo")!!.getBc()).isEqualTo("#fff")

        //TODO Named Element Assertion
        val elements = swipe.getElements()
        assertThat(elements).isNotNull()

        assertThat(elements.get("body")).isNotNull()
        assertThat(elements.get("body").x).isEqualTo("center")
        assertThat(elements.get("body").w).isEqualTo("66.7%")
        assertThat(elements.get("code").x).isEqualTo("4%")
        assertThat(elements.get("code").y).isEqualTo("4%")
        assertThat(elements.get("code").w).isEqualTo("44%")
        assertThat(elements.get("code").h).isEqualTo("92%")

        assertThat(swipe.getPaths()).isNull()


        assertThat(swipe.getMarkdown()).isNotNull()
        assertThat(swipe.getMarkdown().styles.size).isEqualTo(7)
        assertThat(swipe.getMarkdown().styles.get("#")!!.color).isEqualTo("#616161")
        assertThat(swipe.getMarkdown().styles.get("#")!!.alignment).isEqualTo("center")
        assertThat(swipe.getMarkdown().styles.get("#")!!.font!!.size).isEqualTo(92)
        assertThat(swipe.getMarkdown().styles.get("#")!!.font!!.name).isEqualTo("Helvetica-Bold")

        assertThat(swipe.getVoice()).isNull()

        //TODO pages
        assertThat<SwipePage>(swipe.getPages()).hasSize(10)

        assertThat<String>(swipe.getResources()).isNull()
        assertThat(swipe.isViewState()).isFalse()
    }
}

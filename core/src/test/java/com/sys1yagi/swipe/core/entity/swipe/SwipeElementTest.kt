package com.sys1yagi.swipe.core.entity.swipe


import com.sys1yagi.swipe.core.testtool.TestAssetsUtils
import com.sys1yagi.swipe.core.tool.SwipeEntityDecoder
import com.sys1yagi.swipe.core.tool.json.JsonConverter
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

class SwipeElementTest {

    lateinit var swipeDocument: SwipeDocument

    @Before
    fun setUp() {
        val swipeEntityDecoder = SwipeEntityDecoder()
        swipeDocument = swipeEntityDecoder.decodeToSwipe(
                JsonConverter.getInstance(),
                TestAssetsUtils.loadFromAssets("swipe.swipe")!!)
    }


    @Test
    fun inheritElement() {
        val element = swipeDocument.pages[0].elements[1]
        val namedElement = swipeDocument.elements["logo"]

        val inherited = element.inheritElement(namedElement)

        assertThat(inherited)
                .isNotNull()
        assertThat(inherited.x).isEqualTo("300")
        assertThat(inherited.w).isEqualTo("192")

        val back = inherited.elements!![0]
        assertThat(back.id).isEqualTo("back")
        assertThat(back.w).isEqualTo("128")
        assertThat(back.h).isEqualTo("176")
        assertThat(back.cornerRadius).isEqualTo(12f)
        assertThat(back.shadow?.opacity).isEqualTo(0.2f)
        assertThat(back.shadow?.offset!![0]).isEqualTo(0)
        assertThat(back.shadow?.offset!![1]).isEqualTo(8)
        assertThat(back.shadow?.radius).isEqualTo(8)
    }
}
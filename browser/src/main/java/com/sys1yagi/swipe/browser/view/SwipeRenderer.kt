package com.sys1yagi.swipe.browser.view

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.text.TextUtils

import com.sys1yagi.swipe.core.entity.swipe.SwipeDocument
import com.sys1yagi.swipe.core.entity.swipe.SwipeElement
import com.sys1yagi.swipe.core.entity.swipe.SwipePage
import com.sys1yagi.swipe.core.entity.swipe.SwipeScene
import com.sys1yagi.swipe.core.tool.ColorConverter
import com.sys1yagi.swipe.core.util.ListUtils

class SwipeRenderer(internal var swipeDocument: SwipeDocument) {

    internal var paint: Paint

    lateinit internal var displaySize: Rect

    init {
        this.paint = Paint()

        //TODO for debug text
        paint.textSize = 60f
        paint.color = Color.BLACK
        paint.alpha = 100
        paint.isAntiAlias = true
    }

    fun setDisplaySize(displaySize: Rect) {
        this.displaySize = displaySize
    }

    fun draw(canvas: Canvas, scrollX: Int, scrollY: Int) {
        val currentPage = scrollY / displaySize.height()
        val drawNextPage = scrollY % displaySize.height() != 0
        val pageScrollY = scrollY - currentPage * displaySize.height()
        renderPage(canvas, swipeDocument, swipeDocument.pages[currentPage], scrollX, -pageScrollY)
        if (drawNextPage) {
            val pageScrollY1 = displaySize.height() - pageScrollY
            renderPage(canvas, swipeDocument, swipeDocument.pages[currentPage + 1], scrollX, pageScrollY1)
            canvas.drawText("pageOffset1=" + pageScrollY1, 20f, paint.fontSpacing * 4, paint)
        }
        //debug string
        canvas.drawText("currentPage=" + currentPage, 20f, paint.fontSpacing, paint)
        canvas.drawText("drawNextPage=" + drawNextPage, 20f, paint.fontSpacing * 2, paint)
        canvas.drawText("pageOffset0=" + -pageScrollY, 20f, paint.fontSpacing * 3, paint)
    }

    private fun renderPage(canvas: Canvas, document: SwipeDocument, page: SwipePage, offsetX: Int, offsetY: Int) {
        canvas.save()
        canvas.translate(0f, offsetY.toFloat())

        renderBackground(canvas, page)
        renderElements(canvas, document, page)

        canvas.restore()
    }

    private fun renderScene(canvas: Canvas, document: SwipeDocument, page: SwipePage) {
        var scene: SwipeScene? = document.scenes[page.scene]
        if (scene == null) {
            scene = document.scenes["*"]
        }
        if (scene == null) {
            return
        }
        val color = ColorConverter.toColorInt(scene.bc)
        val oldColor = paint.color
        paint.color = color
        canvas.drawRect(0f, 0f, displaySize.width().toFloat(), displaySize.height().toFloat(), paint)
        paint.color = oldColor
    }

    private fun renderElement(canvas: Canvas, document: SwipeDocument, element: SwipeElement) {
        val w = element.w
        if (!ListUtils.isEmpty(element.markdown)) {
            // TODO
        }
    }

    private fun renderElements(canvas: Canvas, document: SwipeDocument, page: SwipePage) {
        renderScene(canvas, document, page)
        for (element in page.elements) {
            renderElement(canvas, document, element)
        }
    }

    private fun renderBackground(canvas: Canvas, page: SwipePage) {
        if (TextUtils.isEmpty(page.bc)) {
            return
        }
        paint.color = ColorConverter.toColorInt(page.bc)
        canvas.drawRect(displaySize, paint)
    }
}

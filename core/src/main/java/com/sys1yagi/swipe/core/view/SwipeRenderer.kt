package com.sys1yagi.swipe.core.view

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.text.TextUtils
import com.sys1yagi.swipe.core.entity.swipe.*
import com.sys1yagi.swipe.core.tool.ColorConverter
import com.sys1yagi.swipe.core.util.ListUtils

class SwipeRenderer(internal var swipeDocument: SwipeDocument) {

    val isDebugPrint = false

    internal var paint: Paint

    internal var oldPaint: Paint

    lateinit internal var displaySize: Rect

    init {
        this.paint = Paint()
        this.oldPaint = Paint()

        paint.textSize = 60f
        paint.color = Color.BLACK
        paint.alpha = 100
        paint.isAntiAlias = true
    }

    fun debugPrint(canvas: Canvas, text: String, line: Int) {
        if (!isDebugPrint) {
            return
        }
        canvas.drawText(text, 20f, paint.fontSpacing * line, paint)
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
            debugPrint(canvas, "pageOffset1=" + pageScrollY1, 4)
        }
        //debug string
        debugPrint(canvas, "currentPage=" + currentPage, 1)
        debugPrint(canvas, "drawNextPage=" + drawNextPage, 2)
        debugPrint(canvas, "pageOffset0=" + -pageScrollY, 3)
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

    private fun savePaint() {
        oldPaint.set(paint)
    }

    private fun restorePaint() {
        paint.set(oldPaint)
    }

    fun measureMarkdownHeight(document: SwipeDocument, element: SwipeElement, markdown: List<String>): Int {
        val dimension = document.dimension
        val displayWidth = if (dimension[0] == 0) displaySize.width() else dimension[0]
        val displayHeight = if (dimension[1] == 0) displaySize.height() else dimension[1]

        var markdownHeight = 0

        markdown.forEach {
            oldPaint.set(paint)



            paint.set(oldPaint)
        }

        return markdownHeight
    }

    private fun renderMarkdown(canvas: Canvas, document: SwipeDocument, element: SwipeElement, markdown: List<String>) {
        val dimension = document.dimension
        val displayWidth = if (dimension[0] == 0) displaySize.width() else dimension[0]
        val displayHeight = if (dimension[1] == 0) displaySize.height() else dimension[1]


        val styles = document.markdown
        //element.element
        var startX = 0.0f
        var startY = 0.0f
        var alignment = "left"

        val markdownHeight = measureMarkdownHeight(document, element, markdown)

        markdown.forEach {
            savePaint()
            paint.color = Color.BLACK
            var line = it
            var style: Style? = null
            when {
                it.startsWith("####") -> {
                    line = it.substring(4)
                    style = styles.styles.get("####")
                }
                it.startsWith("###") -> {
                    line = it.substring(3)
                    style = styles.styles.get("###")
                }
                it.startsWith("##") -> {
                    line = it.substring(2)
                    style = styles.styles.get("##")
                }
                it.startsWith("#") -> {
                    line = it.substring(1)
                    style = styles.styles.get("#")
                }
                it.startsWith("*") -> {
                    line = SwipeMarkdown.prefixes.get("-") + it.substring(1)
                }
                it.startsWith("-") -> {
                    line = SwipeMarkdown.prefixes.get("-") + it.substring(1)
                }
                it.startsWith("```") -> {

                }
                it.startsWith("```+") -> {

                }
                else -> {

                }
            }

            style?.let {
                it.font?.let {
                    paint.textSize = it.size.toFloat()
                }
                paint.color = ColorConverter.toColorInt(it.color)

                //alignment
                if (it.alignment != null ) {
                    alignment = it.alignment!!
                }
            }

            val width = paint.measureText(line)
            if ("center".equals(alignment)) {
                startX = displaySize.width() / 2 - width / 2
            }

            canvas.drawText(line, startX, startY + paint.fontSpacing, paint)
            startY += paint.fontSpacing
            restorePaint()
        }
    }

    private fun renderElement(canvas: Canvas, document: SwipeDocument, element: SwipeElement) {
        val w = element.w
        if (!ListUtils.isEmpty(element.markdown)) {
            renderMarkdown(canvas, document, element, element.markdown)
        }
        if (!TextUtils.isEmpty(element.text)) {

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

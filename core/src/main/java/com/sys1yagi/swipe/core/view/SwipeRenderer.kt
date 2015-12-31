package com.sys1yagi.swipe.core.view

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.text.TextUtils
import android.util.Log
import com.sys1yagi.swipe.core.entity.swipe.*
import com.sys1yagi.swipe.core.tool.ColorConverter
import com.sys1yagi.swipe.core.util.ListUtils

class SwipeRenderer(internal var swipeDocument: SwipeDocument) {

    val isDebugPrint = false

    internal var paint: Paint

    internal var oldPaint: Paint
    
    lateinit var displaySize: Rect

    init {
        this.paint = Paint()
        this.oldPaint = Paint()

        paint.textSize = 40f
        paint.color = Color.BLACK
        paint.alpha = 100
        paint.isAntiAlias = true
    }

    fun debugPrint(canvas: Canvas, text: String, line: Int) {
        if (!isDebugPrint) {
            return
        }
        savePaint()
        paint.textSize = 40f
        paint.color = Color.BLACK
        paint.alpha = 50
        canvas.drawText(text, 20f, paint.fontSpacing * line, paint)
        restorePaint()
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

    fun scale(displaySize: Rect, dimension: IntArray): Float {
        val scaleX = displaySize.width() /
                if (dimension[0] == 0) displaySize.width().toFloat() else dimension[0].toFloat()
        val scaleY = displaySize.height() /
                if (dimension[1] == 0) displaySize.height().toFloat() else dimension[1].toFloat()
        return Math.max(scaleX, scaleY)
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

    //markdown

    fun extractMarkdownKey(line: String): String {
        when {
            line.startsWith("####") -> {
                return "####"
            }
            line.startsWith("###") -> {
                return "###"
            }
            line.startsWith("##") -> {
                return "##"
            }
            line.startsWith("#2") -> {
                return "#2"
            }
            line.startsWith("#") -> {
                return "#"
            }
            line.startsWith("*") -> {
                return "*"
            }
            line.startsWith("-") -> {
                return "-"
            }
            line.startsWith("```") -> {
                return "```"
            }
            line.startsWith("```+") -> {
                return "```+"
            }
        }
        return ""
    }

    fun measureMarkdownHeight(document: SwipeDocument, element: SwipeElement, markdown: List<String>): Float {
        val styles = document.markdown
        val scale = scale(displaySize, document.dimension)
        val elementWidth = elementWidth(document, element)

        var markdownHeight = 0.0f

        Log.d("moge", "calc------------------------------ $elementWidth")

        markdown.forEach {
            savePaint()

            styles.styles[extractMarkdownKey(it)]?.let {
                it.font?.let {
                    paint.textSize = it.size.toFloat() * scale
                }
            }
            val textWidth = paint.measureText(it)
            val lines = (textWidth / elementWidth).toInt() + if (textWidth % elementWidth == 0f) 0 else 1
            Log.d("moge", "lines $lines : $it")
            markdownHeight += (paint.fontSpacing * lines) + (paint.letterSpacing * lines)

            restorePaint()
        }

        return markdownHeight
    }

    fun elementWidth(document: SwipeDocument, element: SwipeElement): Float {
        var w = element.w
        if ( "0".equals(w)) {
            w = element.element
        }
        var width: Float

        when {
            TextUtils.isEmpty(w) -> {
                width = 0f
            }
            w.endsWith("%") -> {
                width = document.dimension[0] * (w.replace("%", "").toFloat() /
                        100f) * scale(displaySize, document.dimension)
            }
            !w.matches("[0-9]+".toRegex()) -> {
                val namedElement = document.elements[w]
                width = elementWidth(document, namedElement)
            }
            else -> {
                width = w.toFloat()
            }
        }
        return width
    }

    private fun renderMarkdown(canvas: Canvas, document: SwipeDocument, element: SwipeElement, markdown: List<String>) {
        val dimension = document.dimension
        val scale = scale(displaySize, document.dimension)
        val elementWidth = elementWidth(document, element)
        val displayWidth = displaySize.width()
        val displayHeight = displaySize.height()

        val styles = document.markdown
        //element.element
        var startX = 0.0f
        var startY = 0.0f
        var alignment = "left"

        val markdownHeight = measureMarkdownHeight(document, element, markdown)
        startY = displayHeight / 2 - markdownHeight / 2

        markdown.forEach {
            savePaint()
            paint.color = Color.BLACK
            var line = it
            val markdownKey = extractMarkdownKey(it)
            var style: Style? = styles.styles[markdownKey]
            if (it.startsWith(markdownKey)) {
                line = it.substring(markdownKey.length + 1)
            }
            when (markdownKey) {
                "*", "-" -> {
                    line = SwipeMarkdown.prefixes["-"] + line
                }
            }

            style?.let {
                it.font?.let {
                    paint.textSize = it.size.toFloat() * scale
                }
                paint.color = ColorConverter.toColorInt(it.color)

                //alignment
                if (it.alignment != null ) {
                    alignment = it.alignment!!
                }
            }

            val width = paint.measureText(line)
            val lines = (width / elementWidth).toInt() + if (width % elementWidth == 0f) 0 else 1

            var pair: Pair<String, String> = Pair("", line)
            for (i in 1..lines) {
                if ("center".equals(alignment)) {
                    startX = displaySize.width() / 2 - width / 2
                } else {
                    startX = displayWidth / 2 - elementWidth / 2
                }

                pair = split(pair.second, elementWidth, paint)
                canvas.drawText(pair.first, startX, startY + paint.fontSpacing, paint)
                startY += paint.fontSpacing
            }

            restorePaint()
        }
    }

    fun split(line: String, width: Float, paint: Paint): Pair<String, String> {
        var totalWidth = 0f
        for (i in 0..line.length - 1) {
            totalWidth += paint.measureText(line.get(i).toString())
            if (totalWidth > width) {
                return Pair(line.substring(0, i), line.substring(i))
            }
        }
        return Pair(line, "")
    }

    //element

    private fun renderElement(canvas: Canvas, document: SwipeDocument, element: SwipeElement, parent: SwipeElement? = null) {

        val namedElement = swipeDocument.elements[element.element]

        //TODO inherit

        if (!ListUtils.isEmpty(element.markdown)) {
            renderMarkdown(canvas, document, element, element.markdown)
        }
        if (!TextUtils.isEmpty(element.text)) {

        }
        element.elements?.let {
            it.forEach {
                renderElement(canvas, document, it, element)
            }
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

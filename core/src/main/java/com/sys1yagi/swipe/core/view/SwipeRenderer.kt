package com.sys1yagi.swipe.core.view

import android.graphics.*
import android.text.TextUtils
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

    fun dimensionWidth(displaySize: Rect, dimension: IntArray): Float {
        return if (dimension[0] == 0) displaySize.width().toFloat() else dimension[0].toFloat()
    }

    fun dimensionHeight(displaySize: Rect, dimension: IntArray): Float {
        return if (dimension[1] == 0) displaySize.height().toFloat() else dimension[1].toFloat()
    }

    fun scale(displaySize: Rect, dimension: IntArray): Float {
        val scaleX = displaySize.width() / dimensionWidth(displaySize, dimension)
        val scaleY = displaySize.height() / dimensionHeight(displaySize, dimension)
        return Math.max(scaleX, scaleY)
    }

    private fun renderPage(canvas: Canvas, document: SwipeDocument, page: SwipePage, offsetX: Int, offsetY: Int) {
        canvas.save()
        canvas.translate(0f, offsetY.toFloat())

        renderBackground(canvas, page)
        renderElements(canvas, document, page, displaySize)

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

    fun extractMarkdownKey(document: SwipeDocument, line: String): String {

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
        val elementWidth = measureElement(document, element, dimensionWidth(displaySize, document.dimension), { it.w })

        var markdownHeight = 0.0f

        markdown.forEach {
            savePaint()
            var line = it
            val markdownKey = extractMarkdownKey(document, line)
            styles.styles[markdownKey]?.let {
                it.font?.let {
                    paint.textSize = it.size.toFloat() * scale
                }
            }
            if (line.startsWith(markdownKey)) {
                line = line.substring(markdownKey.length + 1)
            }

            val textWidth = paint.measureText(line)
            val lines = Math.floor(textWidth.toDouble() / elementWidth.toDouble()).toInt() + if (textWidth % elementWidth == 0f) 0 else 1
            markdownHeight += (paint.fontSpacing * lines)

            restorePaint()
        }

        return markdownHeight
    }

    fun calculateElementPos(document: SwipeDocument, element: SwipeElement, valueExtractor: (SwipeElement) -> String, elementLength: Float, regionLength: Float): Float {
        var value = valueExtractor(element)
        if ( "0".equals(value)) {
            value = element.element
        }
        var floatValue: Float
        val scale = scale(displaySize, document.dimension)

        when {
            TextUtils.isEmpty(value) -> {
                floatValue = 0f
            }
            "center".equals(value) -> {
                floatValue = (regionLength / 2 - elementLength / 2)
            }
            "left".equals(value) -> {
                floatValue = 0f
            }
            "right".equals(value) -> {
                floatValue = (regionLength - elementLength)
            }
            value.endsWith("%") -> {
                floatValue = document.dimension[0] * (value.replace("%", "").toFloat() / 100f) *
                        scale
            }
            !value.matches("[0-9]+".toRegex()) -> {
                val namedElement = document.elements[value]
                floatValue = calculateElementPos(document, namedElement, valueExtractor, elementLength, regionLength)
            }
            else -> {
                floatValue = value.toFloat() * scale
            }
        }
        return floatValue
    }

    fun measureElement(document: SwipeDocument, element: SwipeElement, dimensionValue: Float, valueExtractor: (SwipeElement) -> String): Float {
        var value = valueExtractor(element)
        if ( "0".equals(value)) {
            value = element.element
        }
        var floatValue: Float
        val scale = scale(displaySize, document.dimension)

        when {
            TextUtils.isEmpty(value) -> {
                floatValue = dimensionValue.toFloat()
            }
            value.endsWith("%") -> {
                floatValue = dimensionValue * (value.replace("%", "").toFloat() / 100f) *
                        scale
            }
            !value.matches("[0-9]+".toRegex()) -> {
                val namedElement = document.elements[value]
                floatValue = measureElement(document, namedElement, dimensionValue, valueExtractor)
            }
            else -> {
                floatValue = value.toFloat() * scale
            }
        }
        return floatValue
    }

    private fun renderMarkdown(canvas: Canvas, document: SwipeDocument, element: SwipeElement, markdown: List<String>) {
        val dimension = document.dimension
        val scale = scale(displaySize, dimension)
        val elementWidth = measureElement(document, element, dimensionWidth(displaySize, dimension), { it.w })
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
            val markdownKey = extractMarkdownKey(document, it)
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

                val fontMetrics = paint.fontMetrics
                pair = split(pair.second, elementWidth, paint)
                canvas.drawText(pair.first, startX, startY + paint.fontSpacing - fontMetrics.bottom, paint)
                startY += (paint.fontSpacing)
            }

            restorePaint()
        }
    }

    fun split(line: String, width: Float, paint: Paint): Pair<String, String> {
        var totalWidth = 0f
        for (i in 0..line.length - 1) {
            totalWidth += paint.measureText(line[i].toString()) + paint.fontSpacing
            if (totalWidth > width) {
                return Pair(line.substring(0, i - 1), line.substring(i - 1))
            }
        }
        return Pair(line, "")
    }

    //element
    fun inheritElementIfNeeded(document: SwipeDocument, swipeElement: SwipeElement): SwipeElement {
        var element = swipeElement
        document.elements[swipeElement.element]?.let {
            element = element.inheritElement(it)
        }
        return element
    }

    private fun renderElement(canvas: Canvas, document: SwipeDocument, swipeElement: SwipeElement, region: Rect) {

        var renderCanvas = canvas;

        var clipBitmap: Bitmap? = null

        //TODO Change the timing of the inheritance.
        var element = inheritElementIfNeeded(document, swipeElement)

        var rect = calculateElementRect(document, swipeElement, region)
        var renderRect = rect
        if (element.isClip) {
            clipBitmap = Bitmap.createBitmap(rect.width(), rect.height(), Bitmap.Config.ARGB_8888)
            renderCanvas = Canvas(clipBitmap)
            renderRect = Rect(0, 0, clipBitmap.width, clipBitmap.height)
        }

        renderElementBackground(renderCanvas, document, element, renderRect)
        renderElementFillColor(renderCanvas, document, element, renderRect)

        if (!ListUtils.isEmpty(element.markdown)) {
            renderMarkdown(renderCanvas, document, element, element.markdown)
        }
        if (!TextUtils.isEmpty(element.text)) {

        }

        element.elements?.let {
            it.forEach {
                renderElement(renderCanvas, document, it, renderRect)
            }
        }
        if (clipBitmap != null) {
            canvas.drawBitmap(clipBitmap, renderRect, rect, paint)
        }
    }

    private fun renderElements(canvas: Canvas, document: SwipeDocument, page: SwipePage, region: Rect) {
        renderScene(canvas, document, page)
        for (element in page.elements) {
            renderElement(canvas, document, element, region)
        }
    }

    fun calculateElementRect(document: SwipeDocument, element: SwipeElement, region: Rect): Rect {
        val rect = Rect()

        val w = measureElement(document, element, dimensionWidth(displaySize, document.dimension), { it.w })
        val h = measureElement(document, element, dimensionHeight(displaySize, document.dimension), { it.h })
        val x = calculateElementPos(document, element, { it.x }, w, region.width().toFloat()).toInt()
        val y = calculateElementPos(document, element, { it.y }, h, region.height().toFloat()).toInt()

        rect.set(region.left + x, region.top + y, (region.left + x + w).toInt(), (region.top + y + h).toInt())

        return rect
    }

    private fun renderElementFillColor(canvas: Canvas, document: SwipeDocument, element: SwipeElement, region: Rect) {
        if (TextUtils.isEmpty(element.fillColor)) {
            return
        }
        savePaint()
        paint.color = ColorConverter.toColorInt(element.fillColor)

        val area = calculateElementRect(document, element, region)

        canvas.drawRect(area, paint)
        restorePaint()
    }

    private fun renderElementBackground(canvas: Canvas, document: SwipeDocument, element: SwipeElement, region: Rect) {
        if (TextUtils.isEmpty(element.bc)) {
            return
        }
        savePaint()
        paint.color = ColorConverter.toColorInt(element.bc)

        val area = calculateElementRect(document, element, region)

        canvas.drawRect(area, paint)
        restorePaint()
    }

    private fun renderBackground(canvas: Canvas, page: SwipePage) {
        if (TextUtils.isEmpty(page.bc)) {
            return
        }
        paint.color = ColorConverter.toColorInt(page.bc)
        canvas.drawRect(displaySize, paint)
    }
}

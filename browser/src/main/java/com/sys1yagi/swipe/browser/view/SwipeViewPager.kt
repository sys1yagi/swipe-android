package com.sys1yagi.swipe.browser.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

import com.sys1yagi.swipe.core.entity.swipe.SwipeDocument

class SwipeViewPager(context: Context, attrs: AttributeSet) : ViewPager(context, attrs) {

    internal class Offset(var container: ViewPager) {
        var x: Int = 0
        var y: Int = 0
    }

    internal var scrollOffset = Offset(this)

    lateinit internal var swipeDocument: SwipeDocument
    lateinit internal var swipeRenderer: SwipeRenderer

    init {
        init()
    }

    private fun init() {
        setPageTransformer(true, VerticalPageTransformer())
        overScrollMode = View.OVER_SCROLL_NEVER
        addOnPageChangeListener(PageChangeListener(scrollOffset))
    }

    fun setSwipeDocument(swipeDocument: SwipeDocument) {
        this.swipeDocument = swipeDocument
        swipeRenderer = SwipeRenderer(swipeDocument)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        swipeRenderer.setDisplaySize(Rect(0, 0, w, h))
    }

    private fun swapXY(ev: MotionEvent): MotionEvent {
        val width = width.toFloat()
        val height = height.toFloat()

        val newX = ev.y / height * width
        val newY = ev.x / width * height

        ev.setLocation(newX, newY)

        return ev
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        val intercepted = super.onInterceptTouchEvent(swapXY(ev))
        swapXY(ev)
        return intercepted
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        return super.onTouchEvent(swapXY(ev))
    }

    override fun onDraw(canvas: Canvas) {
        val clipBounds = canvas.clipBounds

        canvas.translate(clipBounds.left.toFloat(), clipBounds.top.toFloat())

        swipeRenderer.draw(canvas, scrollOffset.x, scrollOffset.y)
    }

    internal class PageChangeListener(var scrollOffset:

                                      Offset) : ViewPager.OnPageChangeListener {

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            val ratio = scrollOffset.container.height.toFloat() / scrollOffset.container.width.toFloat()
            val offset = position * scrollOffset.container.height + (positionOffsetPixels * ratio).toInt()
            scrollOffset.x = 0
            scrollOffset.y = offset
        }

        override fun onPageSelected(position: Int) {

        }

        override fun onPageScrollStateChanged(state: Int) {

        }
    }

    internal class VerticalPageTransformer : ViewPager.PageTransformer {

        override fun transformPage(view: View, position: Float) {

            if (position < -1) {
                view.alpha = 0f

            } else if (position <= 0) {
                view.alpha = 1f
                view.translationX = view.width * -position

                val yPosition = position * view.height
                view.translationY = yPosition
                view.scaleX = 1f
                view.scaleY = 1f

            } else if (position <= 1) {
                view.alpha = 1f

                view.translationX = view.width * -position

                val scaleFactor = MIN_SCALE + (1 - MIN_SCALE) * (1 - Math.abs(position))
                view.scaleX = scaleFactor
                view.scaleY = scaleFactor

            } else {
                view.alpha = 0f
            }
        }

        companion object {
            private val MIN_SCALE = 0.75f
        }
    }
}

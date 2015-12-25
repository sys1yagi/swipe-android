package com.sys1yagi.swipe.browser.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.sys1yagi.swipe.core.entity.swipe.SwipeDocument;

public class SwipeViewPager extends ViewPager {

    static class Offset {
        ViewPager container;
        int x;
        int y;

        public Offset(ViewPager container) {
            this.container = container;
        }
    }

    Offset scrollOffset = new Offset(this);

    SwipeDocument swipeDocument;
    SwipeRenderer swipeRenderer;

    public SwipeViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setPageTransformer(true, new VerticalPageTransformer());
        setOverScrollMode(OVER_SCROLL_NEVER);
        addOnPageChangeListener(new PageChangeListener(scrollOffset));
    }

    public void setSwipeDocument(SwipeDocument swipeDocument) {
        this.swipeDocument = swipeDocument;
        swipeRenderer = new SwipeRenderer(swipeDocument);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        swipeRenderer.setDisplaySize(new Rect(0, 0, w, h));
    }

    private MotionEvent swapXY(MotionEvent ev) {
        float width = getWidth();
        float height = getHeight();

        float newX = (ev.getY() / height) * width;
        float newY = (ev.getX() / width) * height;

        ev.setLocation(newX, newY);

        return ev;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercepted = super.onInterceptTouchEvent(swapXY(ev));
        swapXY(ev);
        return intercepted;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return super.onTouchEvent(swapXY(ev));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Rect clipBounds = canvas.getClipBounds();

        canvas.translate(clipBounds.left, clipBounds.top);

        swipeRenderer.draw(canvas, scrollOffset.x, scrollOffset.y);
    }

    static class PageChangeListener implements OnPageChangeListener {

        Offset scrollOffset;

        public PageChangeListener(Offset scrollOffset) {
            this.scrollOffset = scrollOffset;
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            float ratio = ((float) scrollOffset.container.getHeight()) / (float) scrollOffset.container.getWidth();
            int offset = (position) * scrollOffset.container.getHeight() + (int) (positionOffsetPixels * ratio);
            scrollOffset.x = 0;
            scrollOffset.y = offset;
        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    static class VerticalPageTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.75f;

        @Override
        public void transformPage(View view, float position) {

            if (position < -1) {
                view.setAlpha(0);

            } else if (position <= 0) {
                view.setAlpha(1);
                view.setTranslationX(view.getWidth() * -position);

                float yPosition = position * view.getHeight();
                view.setTranslationY(yPosition);
                view.setScaleX(1);
                view.setScaleY(1);

            } else if (position <= 1) {
                view.setAlpha(1);

                view.setTranslationX(view.getWidth() * -position);

                float scaleFactor = MIN_SCALE + (1 - MIN_SCALE) * (1 - Math.abs(position));
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

            } else {
                view.setAlpha(0);
            }
        }
    }
}

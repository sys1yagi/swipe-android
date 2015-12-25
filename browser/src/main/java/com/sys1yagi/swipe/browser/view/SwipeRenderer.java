package com.sys1yagi.swipe.browser.view;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextUtils;

import com.sys1yagi.swipe.core.entity.swipe.SwipeDocument;
import com.sys1yagi.swipe.core.entity.swipe.SwipeElement;
import com.sys1yagi.swipe.core.entity.swipe.SwipePage;

public class SwipeRenderer {

    Paint paint;

    SwipeDocument swipeDocument;

    Rect displaySize;

    public SwipeRenderer(SwipeDocument swipeDocument) {
        this.swipeDocument = swipeDocument;
        this.paint = new Paint();

        //TODO for debug text
        paint.setTextSize(60);
        paint.setColor(Color.BLACK);
        paint.setAlpha(100);
        paint.setAntiAlias(true);
    }

    public void setDisplaySize(Rect displaySize) {
        this.displaySize = displaySize;
    }

    public void draw(Canvas canvas, int scrollX, int scrollY) {
        int currentPage = scrollY / displaySize.height();
        boolean drawNextPage = scrollY % displaySize.height() != 0;
        canvas.drawText("currentPage=" + currentPage, 20, paint.getFontSpacing(), paint);
        canvas.drawText("drawNextPage=" + drawNextPage, 20, paint.getFontSpacing() * 2, paint);

        int pageScrollY = (scrollY - (currentPage * displaySize.height()));
        canvas.drawText("pageOffset0=" + (-pageScrollY), 20, paint.getFontSpacing() * 3, paint);

        renderPage(canvas, swipeDocument, swipeDocument.getPages().get(currentPage), scrollX, -pageScrollY);
        if (drawNextPage) {
            int pageScrollY1 = (displaySize.height() - pageScrollY);
            canvas.drawText("pageOffset1=" + pageScrollY1, 20, paint.getFontSpacing() * 4, paint);
            renderPage(canvas, swipeDocument, swipeDocument.getPages().get(currentPage + 1), scrollX, pageScrollY1);
        }
    }

    private void renderPage(Canvas canvas, SwipeDocument document, SwipePage page, int offsetX, int offsetY) {
        canvas.save();
        canvas.translate(0, offsetY);

        renderBackground(canvas, page);
        renderElements(canvas, document, page);

        canvas.restore();
    }


    private void renderScene() {

    }

    private void renderElement(Canvas canvas, SwipeDocument document, SwipeElement element) {
        String w = element.getW();
        

        if (!element.getMarkdown().isEmpty()) {

        }
    }

    private void renderElements(Canvas canvas, SwipeDocument document, SwipePage page) {
        for (SwipeElement element : page.getElements()) {
            renderElement(canvas, document, element);
        }
    }

    private void renderBackground(Canvas canvas, SwipePage page) {
        if (TextUtils.isEmpty(page.getBc())) {
            return;
        }
        paint.setColor(Color.parseColor(page.getBc()));
        canvas.drawRect(displaySize, paint);
    }
}

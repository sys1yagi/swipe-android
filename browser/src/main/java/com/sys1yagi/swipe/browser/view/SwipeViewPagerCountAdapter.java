package com.sys1yagi.swipe.browser.view;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

public class SwipeViewPagerCountAdapter extends PagerAdapter {

    int count;

    public SwipeViewPagerCountAdapter(int count) {
        this.count = count;
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        // no op
        View view = new View(container.getContext());
        container.addView(view);
        return null;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // no op
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}

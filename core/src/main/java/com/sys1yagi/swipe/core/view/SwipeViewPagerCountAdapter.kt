package com.sys1yagi.swipe.core.view

import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup

class SwipeViewPagerCountAdapter(internal var count: Int) : PagerAdapter() {

    override fun getCount(): Int {
        return count
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any? {
        // no op
        val view = View(container.context)
        container.addView(view)
        return null
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any?) {
        // no op
        `object`?.let {
            container.removeView(it as View)
        }
    }

    override fun isViewFromObject(view: View, `object`: Any?): Boolean {
        return view === `object`
    }
}

package com.sys1yagi.swipe.browser.activity

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.sys1yagi.browser.R
import com.sys1yagi.browser.databinding.ActivitySwipeContentsBrowseBinding
import com.sys1yagi.swipe.browser.view.SwipeViewPagerCountAdapter
import com.sys1yagi.swipe.core.entity.swipe.SwipeDocument
import com.sys1yagi.swipe.core.tool.AssetsPathStore
import com.sys1yagi.swipe.core.tool.SwipeEntityDecoder
import com.sys1yagi.swipe.core.tool.json.JsonConverter
import com.sys1yagi.swipe.core.util.AssetsUtils

class SwipeContentsBrowseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val swipeFileName = intent.getStringExtra(EXTRA_SWIPE_FILE_NAME)
        val swipeDocument = loadSwipeDocument(swipeFileName)

        applyOrientation(swipeDocument)

        val binding = DataBindingUtil.setContentView<ActivitySwipeContentsBrowseBinding>(this, R.layout.activity_swipe_contents_browse)

        binding.pager.adapter = SwipeViewPagerCountAdapter(swipeDocument!!.pages.size)
        binding.pager.setSwipeDocument(swipeDocument)
    }

    internal fun applyOrientation(swipeDocument: SwipeDocument?) {
        swipeDocument?.let {
            if (it.orientation == SwipeDocument.Orientation.PORTRAIT) {
                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            } else if (it.orientation == SwipeDocument.Orientation.LANDSCAPE) {
                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            } else {
                throw IllegalStateException("invalid orientation setting : " + swipeDocument.orientationString)
            }
        }
    }

    private fun loadSwipeDocument(swipeFileName: String): SwipeDocument? {
        val assetsUtils = AssetsUtils()
        val assetsPathStore = AssetsPathStore(this.assets)

        title = swipeFileName
        if (!assetsPathStore.contains(swipeFileName)) {
            Toast.makeText(this, swipeFileName + " is not found", Toast.LENGTH_SHORT).show()
            return null
        }
        val jsonString = assetsUtils.loadFromAssets(this, assetsPathStore.toPath(swipeFileName)!!)
        val swipeEntityDecoder = SwipeEntityDecoder()
        return swipeEntityDecoder.decodeToSwipe(JsonConverter.getInstance(), jsonString!!)
    }

    companion object {

        private val EXTRA_SWIPE_FILE_NAME = "swipe_file_name"

        fun createIntent(context: Context, swipeFileName: String): Intent {
            val intent = Intent(context, SwipeContentsBrowseActivity::class.java)
            intent.putExtra(EXTRA_SWIPE_FILE_NAME, swipeFileName)
            return intent
        }
    }


}

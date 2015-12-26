package com.sys1yagi.swipe.browser

import android.app.Activity
import android.databinding.DataBindingUtil
import android.widget.AdapterView
import com.sys1yagi.browser.R
import com.sys1yagi.browser.databinding.ActivitySwipeIndexBinding
import com.sys1yagi.swipe.browser.activity.SwipeContentsBrowseActivity
import com.sys1yagi.swipe.browser.view.IndexAdapter
import com.sys1yagi.swipe.core.tool.SwipeEntityDecoder
import com.sys1yagi.swipe.core.tool.json.JsonConverter
import com.sys1yagi.swipe.core.util.AssetsUtils

class SwipeBrowser @JvmOverloads constructor(internal var indexFileName: String = SwipeBrowser.DEFAULT_INDEX_FILE_NAME) {

    lateinit internal var binding: ActivitySwipeIndexBinding

    lateinit internal var assetsUtils: AssetsUtils

    lateinit internal var target: Activity

    internal var swipeEntityDecoder: SwipeEntityDecoder

    init {
        this.swipeEntityDecoder = SwipeEntityDecoder()
    }

    fun initialize(activity: Activity) {
        this.target = activity
        this.assetsUtils = AssetsUtils()
        this.binding = DataBindingUtil.setContentView<ActivitySwipeIndexBinding>(activity, R.layout.activity_swipe_index)
        setupUi()
    }

    private fun setupUi() {
        val adapter = IndexAdapter(target)
        binding.listView.adapter = adapter
        binding.listView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val item = adapter.getItem(position)
            val intent = SwipeContentsBrowseActivity.createIntent(adapter.context, item.url!!)
            adapter.context.startActivity(intent)
        }
        setupIndex(adapter)
    }

    private fun setupIndex(adapter: IndexAdapter) {
        target.title = indexFileName
        val jsonString = assetsUtils.loadFromAssets(target, indexFileName)
        val index = swipeEntityDecoder.decodeToIndex(JsonConverter.getInstance(), jsonString!!)
        adapter.addAll(index.items)
    }

    companion object {

        private val DEFAULT_INDEX_FILE_NAME = "index.swipe"
    }
}

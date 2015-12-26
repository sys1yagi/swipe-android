package com.sys1yagi.swipe.browser.view

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

import com.sys1yagi.browser.R
import com.sys1yagi.swipe.core.entity.index.Item
import com.sys1yagi.swipe.core.tool.AssetsPathStore
import com.sys1yagi.swipe.core.tool.PabloPicasso

class IndexAdapter(context: Context) : ArrayAdapter<Item>(context, -1) {

    internal var pablo: PabloPicasso

    internal var store: AssetsPathStore

    init {
        pablo = PabloPicasso(context)
        store = AssetsPathStore(context.assets)
    }

    override fun getView(position: Int, _convertView: View?, parent: ViewGroup): View? {
        var convertView: View
        if (_convertView == null) {
            convertView = View.inflate(context, R.layout.list_item, null)
        } else {
            convertView = _convertView
        }

        val item = getItem(position) ?: return null

        val text = convertView.findViewById(R.id.title) as TextView
        text.text = item.title

        val icon = convertView.findViewById(R.id.icon) as ImageView
        val path = store.getAssetPath(item.icon)
        pablo.picasso.load(path).into(icon)
        return convertView
    }
}

package com.sys1yagi.swipe.browser.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sys1yagi.browser.R;
import com.sys1yagi.swipe.core.entity.index.Item;
import com.sys1yagi.swipe.core.tool.AssetsPathStore;
import com.sys1yagi.swipe.core.tool.PabloPicasso;

public class IndexAdapter extends ArrayAdapter<Item> {

    PabloPicasso pablo;

    AssetsPathStore store;

    public IndexAdapter(Context context) {
        super(context, -1);
        pablo = new PabloPicasso(context);
        store = new AssetsPathStore(context.getAssets());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(getContext(), R.layout.list_item, null);
        }

        Item item = getItem(position);
        if (item == null) {
            return null;
        }

        TextView text = (TextView) convertView.findViewById(R.id.title);
        text.setText(item.getTitle());

        ImageView icon = (ImageView) convertView.findViewById(R.id.icon);
        String path = store.getAssetPath(item.getIcon());
        pablo.getPicasso().load(path).into(icon);
        return convertView;
    }
}

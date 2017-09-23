package cloud.safe.com.kuchmynda.mark.safecloud.Infrastructure.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

import cloud.safe.com.kuchmynda.mark.safecloud.UI.CustomControls.StructureItem;
import cloud.safe.com.kuchmynda.mark.safecloud.Models.StructureItemModel;

/**
 * Created by Markiian Kuchmynda on 13.09.2017.
 */

public class StructureAdapter extends BaseAdapter {
    private int position=0;
    private final Context activity;
    private final ArrayList<StructureItemModel> itemModels;

    public StructureAdapter(ArrayList<StructureItemModel> itemModels, Activity activity) {
        this.itemModels = itemModels;
        this.activity=activity;
    }

    @Override
    public int getCount() {
        return itemModels.size();
    }

    @Override
    public Object getItem(int position) {
        return itemModels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
        {
            StructureItem itemGallery =
                    new StructureItem(activity);
            itemGallery.setData((StructureItemModel) getItem(position));
            return itemGallery;
        }
        return null;
    }
}

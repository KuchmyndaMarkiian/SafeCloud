package cloud.safe.com.kuchmynda.mark.safecloud.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;

import java.util.ArrayList;

import cloud.safe.com.kuchmynda.mark.safecloud.CustomControls.GalleryItem;
import cloud.safe.com.kuchmynda.mark.safecloud.Models.GalleryItemModel;

/**
 * Created by MARKAN on 13.09.2017.
 */

public class GalleryAdapter extends BaseAdapter {
    private int position=0;
    private Context activity;
    private ArrayList<GalleryItemModel> itemModels;

    public GalleryAdapter(ArrayList<GalleryItemModel> itemModels, Activity activity) {
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
            GalleryItem itemGallery =
                    new GalleryItem(activity);
            itemGallery.setData((GalleryItemModel) getItem(position));
            return itemGallery;
        }
        return null;
    }
}

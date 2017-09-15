package cloud.safe.com.kuchmynda.mark.safecloud.CustomControls;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;

import cloud.safe.com.kuchmynda.mark.safecloud.Models.GalleryItemModel;
import cloud.safe.com.kuchmynda.mark.safecloud.R;

/**
 * Created by MARKAN on 13.09.2017.
 */

public class GalleryItem extends LinearLayout {
    View current;
    public GalleryItem(Context context) {
        super(context);
        current=inflate(context, R.layout.customlayout_gallery_item,this);
    }
    public void setData(GalleryItemModel model){
        ((TextView)current.findViewById(R.id.galleryitem_header)).setText(model.getHeader()+"("+model.getCount()+" photos)");
        ((TextView)current.findViewById(R.id.galleryitem_date)).setText(new SimpleDateFormat("dd/MM/yyyy").format(model.getDate()));
    }
}

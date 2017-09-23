package cloud.safe.com.kuchmynda.mark.safecloud.UI.CustomControls;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;

import cloud.safe.com.kuchmynda.mark.safecloud.Models.StructureItemModel;
import cloud.safe.com.kuchmynda.mark.safecloud.R;

/**
 * Created by Markiian Kuchmynda on 13.09.2017.
 */

public class StructureItem extends LinearLayout {
    private final View current;
    public StructureItem(Context context) {
        super(context);
        current=inflate(context, R.layout.customlayout_structure_item,this);
    }
    public void setData(StructureItemModel model){
        ((TextView)current.findViewById(R.id.structure_item_header)).setText(model.getHeader());
        ((TextView)current.findViewById(R.id.structure_item_date)).setText(new SimpleDateFormat("dd/MM/yyyy").format(model.getDate()));
    }
}

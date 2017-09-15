package cloud.safe.com.kuchmynda.mark.safecloud.Views.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;

import cloud.safe.com.kuchmynda.mark.safecloud.Common.CommonData;
import cloud.safe.com.kuchmynda.mark.safecloud.Models.GalleryItemModel;
import cloud.safe.com.kuchmynda.mark.safecloud.R;

public class PhotosFragment extends Fragment {
    public static int ID = R.layout.fragment_photos;

    public PhotosFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(ID, container, false);

        GalleryItemModel model= (GalleryItemModel) getArguments().getSerializable(CommonData.SERIALIZABLE_EXTRA);
        ((TextView)view.findViewById(R.id.photoHeader)).setText(model.getHeader()+"("+model.getCount()+" photos)");
        ((TextView)view.findViewById(R.id.photoDate)).setText(new SimpleDateFormat("dd/MM/yyyy").format(model.getDate()));
        return view;
    }
}

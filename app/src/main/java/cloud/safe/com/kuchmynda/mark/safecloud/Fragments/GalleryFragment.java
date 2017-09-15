package cloud.safe.com.kuchmynda.mark.safecloud.Fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import cloud.safe.com.kuchmynda.mark.safecloud.Activities.NavigationDrawerActivity;
import cloud.safe.com.kuchmynda.mark.safecloud.Adapters.GalleryAdapter;
import cloud.safe.com.kuchmynda.mark.safecloud.Models.GalleryItemModel;
import cloud.safe.com.kuchmynda.mark.safecloud.R;

import static cloud.safe.com.kuchmynda.mark.safecloud.Common.CommonData.FRAGMENT_EXTRA;
import static cloud.safe.com.kuchmynda.mark.safecloud.Common.CommonData.SERIALIZABLE_EXTRA;

public class GalleryFragment extends Fragment {
    public static int ID=R.layout.fragment_gallery;
    public GalleryFragment() {
        // Required empty public constructor
    }
    //todo: test data
    ArrayList<GalleryItemModel> getTest(){
        ArrayList<GalleryItemModel> galleryItemModels=new ArrayList<>();
        Random random=new Random();
        for(int i=0;i<5;i++){
            galleryItemModels.add(new GalleryItemModel("Photos",new Date(),random.nextInt(500)));
        }
        return galleryItemModels;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(ID, container, false);
        ListView listView= (ListView) view.findViewById(R.id.galleryList);
        listView.setAdapter(new GalleryAdapter(getTest(),getActivity()));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            //Todo: animation test
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getActivity(), NavigationDrawerActivity.class);
                intent.putExtra(FRAGMENT_EXTRA, PhotosFragment.ID);
                intent.putExtra(SERIALIZABLE_EXTRA, getTest().get(position));
                ActivityOptionsCompat  optionsCompat= ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),
                        new Pair<>(view.findViewById(R.id.galleryitem_header), getString(R.string.transist1)),
                        new Pair<>(view.findViewById(R.id.galleryitem_date), getString(R.string.transist2)),
                        new Pair<>(view.findViewById(R.id.galleryitem_icon), getString(R.string.transist3)));

                getActivity().startActivity(intent,optionsCompat.toBundle());
            }
        });
        return view;
    }
}

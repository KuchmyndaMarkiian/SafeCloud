package cloud.safe.com.kuchmynda.mark.safecloud.Views.Activities;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import cloud.safe.com.kuchmynda.mark.safecloud.Views.Fragments.GalleryFragment;
import cloud.safe.com.kuchmynda.mark.safecloud.Views.Fragments.PhotosFragment;
import cloud.safe.com.kuchmynda.mark.safecloud.R;

import static cloud.safe.com.kuchmynda.mark.safecloud.Common.CommonData.FRAGMENT_EXTRA;

public class NavigationDrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{
    DrawerLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);
        int id=getIntent().getIntExtra(FRAGMENT_EXTRA,GalleryFragment.ID);
        Fragment fragment;
        if(id==GalleryFragment.ID){
            fragment=new GalleryFragment();
        }
        else if(id== PhotosFragment.ID){
            fragment=new PhotosFragment();
            Bundle bundle= getIntent().getExtras();
            fragment.setArguments(bundle);
        }
        else {
            fragment =new Fragment();
        }
        layout = (DrawerLayout) findViewById(R.id.nav_drawer_layout);
        getFragmentManager().beginTransaction().add(R.id.mainFrame,fragment).commit();
        ((NavigationView) findViewById(R.id.navigation_drawer)).setNavigationItemSelectedListener(this);
        ImageButton imageButton= (ImageButton)findViewById(R.id.gallery_drawer_icon);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((DrawerLayout)findViewById(R.id.nav_drawer_layout)).openDrawer(GravityCompat.START);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (layout.isDrawerOpen(GravityCompat.START)) {
            layout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        //ToDo: Set up intents in nav menu.
        if(id==R.id.navmenu_gallery)
        {
            /*LayoutInflater inflater = (LayoutInflater) this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View contentView = inflater.inflate(R.layout.fragment_registration, null, false);
            layout.removeViewAt(0);
            layout.addView(contentView,0);*/
        }
        layout.closeDrawer(GravityCompat.START);
        return true;
    }
}

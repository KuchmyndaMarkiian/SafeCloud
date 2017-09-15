package cloud.safe.com.kuchmynda.mark.safecloud.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.rd.PageIndicatorView;
import com.rd.animation.type.AnimationType;

import cloud.safe.com.kuchmynda.mark.safecloud.Adapters.TabPagerAdapter;
import cloud.safe.com.kuchmynda.mark.safecloud.Animations.ZoomOutPageTransformer;
import cloud.safe.com.kuchmynda.mark.safecloud.Fragments.LoginFragment;
import cloud.safe.com.kuchmynda.mark.safecloud.Fragments.RegisterFragment;
import cloud.safe.com.kuchmynda.mark.safecloud.R;

import static cloud.safe.com.kuchmynda.mark.safecloud.Common.CommonData.IMAGE_PICK;

public class AuthorizationActivity extends AppCompatActivity {

    private ViewPager viewPager;
    TabPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);
        pagerAdapter = new TabPagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(new LoginFragment(), "Login");
        pagerAdapter.addFragment(new RegisterFragment(), "Registration");
        viewPager = (ViewPager) findViewById(R.id.authorization_container);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setPageTransformer(true,new ZoomOutPageTransformer());
        PageIndicatorView indicatorView= (PageIndicatorView) findViewById(R.id.authorization_pageIndicatorView);
        indicatorView.setViewPager(viewPager);
        initIndicatorView(indicatorView);
    }
    void initIndicatorView(PageIndicatorView indicatorView){
        indicatorView.setRadius(8);
        indicatorView.setSelectedColor(getResources().getColor(R.color.colorPrimary));
        indicatorView.setUnselectedColor(getResources().getColor(R.color.grayColorBase));
        indicatorView.setAnimationType(AnimationType.WORM);
        indicatorView.setInteractiveAnimation(true);
        indicatorView.setDynamicCount(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == IMAGE_PICK) {
            pagerAdapter.getItem(viewPager.getCurrentItem()).onActivityResult(requestCode, resultCode, data);
        }
    }
}

package cloud.safe.com.kuchmynda.mark.safecloud.Views.Activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.rd.PageIndicatorView;
import com.rd.animation.type.AnimationType;

import cloud.safe.com.kuchmynda.mark.safecloud.Common.CommonData;
import cloud.safe.com.kuchmynda.mark.safecloud.Infrastructure.Adapters.TabPagerAdapter;
import cloud.safe.com.kuchmynda.mark.safecloud.Infrastructure.Services.Preferences.AccountPreference;
import cloud.safe.com.kuchmynda.mark.safecloud.Infrastructure.UserAccount;
import cloud.safe.com.kuchmynda.mark.safecloud.R;
import cloud.safe.com.kuchmynda.mark.safecloud.UI.Animations.ZoomOutPageTransformer;
import cloud.safe.com.kuchmynda.mark.safecloud.Views.Fragments.LoginFragment;
import cloud.safe.com.kuchmynda.mark.safecloud.Views.Fragments.RegisterFragment;

import static cloud.safe.com.kuchmynda.mark.safecloud.Common.CommonData.IMAGE_PICK;

public class AuthorizationActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TabPagerAdapter pagerAdapter;

    static {
        StrictMode.ThreadPolicy tp = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(tp);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);
        AccountPreference accountPreference=new AccountPreference(getSharedPreferences(CommonData.UserPreference,MODE_PRIVATE));
        if(accountPreference.isLogged()) {
            UserAccount.read(this);
            startActivity(new Intent(this, NavigationDrawerActivity.class));
            finish();
        }

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
    private void initIndicatorView(PageIndicatorView indicatorView){
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

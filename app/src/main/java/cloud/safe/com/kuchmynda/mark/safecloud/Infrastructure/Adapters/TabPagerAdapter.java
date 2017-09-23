package cloud.safe.com.kuchmynda.mark.safecloud.Infrastructure.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.SparseArray;

import java.util.AbstractMap;
import java.util.Map;

public class TabPagerAdapter extends FragmentPagerAdapter {
    private final SparseArray<Map.Entry<String, Fragment>> tabs;

    public TabPagerAdapter(FragmentManager fm) {
        super(fm);
        tabs = new SparseArray<>();
    }

    public void addFragment(Fragment fragment, String title) {
        int index = tabs.size();
        tabs.put(index, new AbstractMap.SimpleEntry<>(title, fragment));
    }

    @Override
    public Fragment getItem(int position) {
        return tabs.get(position).getValue();
    }

    @Override
    public int getCount() {
        return tabs.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs.get(position).getKey();
    }
}

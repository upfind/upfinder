package cn.upfinder.upfinder.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by upfinder on 2016/7/21 0021.
 * 主页面ViewPager的Adapter
 */
public class HomeVPAdapter extends FragmentPagerAdapter {

    public static final int FRAGMENT_COUNT = 4;//子页面个数
    public static final int FRAGMENT_INDEX_FIRST = 0;
    public static final int FRAGMENT_INDEX_SECOND = 1;
    public static final int FRAGMENT_INDEX_THIRD = 2;
    public static final int FRAGMENT_INDEX_FORE = 3;

    private ArrayList<Fragment> fragmentArrayList;

    public HomeVPAdapter(FragmentManager fm, ArrayList<Fragment> fragmentArrayList) {
        super(fm);
        this.fragmentArrayList = fragmentArrayList;
    }

    public HomeVPAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentArrayList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentArrayList.size();
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);

    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }
}

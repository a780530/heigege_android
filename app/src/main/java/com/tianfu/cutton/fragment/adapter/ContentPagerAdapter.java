package com.tianfu.cutton.fragment.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by xiaohei on 2017/2/22.
 */
public class ContentPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> tabFragments;

    public ContentPagerAdapter(FragmentManager fm, List<Fragment> tabFragments) {
        super(fm);
        this.tabFragments = tabFragments;
    }

    @Override
    public Fragment getItem(int position) {
        return tabFragments.get(position);
    }

    @Override
    public int getCount() {
        return tabFragments.size();
    }

}

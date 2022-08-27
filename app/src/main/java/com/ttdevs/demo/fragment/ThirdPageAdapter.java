package com.ttdevs.demo.fragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

/**
 * @author ttdevs
 */
public class ThirdPageAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragmentList;

    public ThirdPageAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);

        mFragmentList = fragmentList;
    }


    @Override
    public Fragment getItem(int position) {
        System.out.println(">>>>>position: " + position);
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }
}

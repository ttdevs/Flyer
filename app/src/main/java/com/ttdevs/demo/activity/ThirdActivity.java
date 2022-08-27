package com.ttdevs.demo.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.ttdevs.demo.R;
import com.ttdevs.demo.fragment.Third1Fragment;
import com.ttdevs.demo.fragment.Third2Fragment;
import com.ttdevs.demo.fragment.Third3Fragment;
import com.ttdevs.demo.fragment.Third4Fragment;
import com.ttdevs.demo.fragment.Third5Fragment;
import com.ttdevs.demo.fragment.ThirdPageAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ttdevs
 */
public class ThirdActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int TAB_COUNT = 5;

    private List<Fragment> mFragmentList = new ArrayList<>();

    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        initView();
    }

    private void initView() {

        mTabLayout = findViewById(R.id.tabLayout);
        mViewPager = findViewById(R.id.viewPager);
        for (int i = 1; i <= TAB_COUNT; i++) {
            mTabLayout.addTab(mTabLayout.newTab().setText("tab" + i));
        }
        mFragmentList.add(Third1Fragment.newInstance("Content: 1"));
        mFragmentList.add(Third2Fragment.newInstance("Content: 2"));
        mFragmentList.add(Third3Fragment.newInstance("Content: 3"));
        mFragmentList.add(Third4Fragment.newInstance("Content: 4"));
        mFragmentList.add(Third5Fragment.newInstance("Content: 5"));

        mViewPager.setAdapter(new ThirdPageAdapter(getSupportFragmentManager(), mFragmentList));
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}

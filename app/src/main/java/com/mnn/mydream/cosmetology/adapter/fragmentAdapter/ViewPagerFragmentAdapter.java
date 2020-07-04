package com.mnn.mydream.cosmetology.adapter.fragmentAdapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.mnn.mydream.cosmetology.fragment.beauty.khfragments.KHHYFragment;
import com.mnn.mydream.cosmetology.fragment.beauty.khfragments.KHZSFragment;
import com.mnn.mydream.cosmetology.fragment.beauty.khfragments.XJKHFragment;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by YoKeyword on 16/6/5.
 */
public class ViewPagerFragmentAdapter extends FragmentPagerAdapter {
    private String[] mTitles;
    private SupportFragment[] mFragments;

    public ViewPagerFragmentAdapter(FragmentManager fm, String[] titles, SupportFragment[] mFragments) {
        super(fm);
        mTitles = titles;
        this.mFragments = mFragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments[position];

    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}

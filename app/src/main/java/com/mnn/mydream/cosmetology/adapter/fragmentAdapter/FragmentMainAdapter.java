package com.mnn.mydream.cosmetology.adapter.fragmentAdapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 *
 * @author My Dream
 * @date 2017/4/20
 */


public class FragmentMainAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> mFragments;


    public FragmentMainAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        mFragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }


}

package com.mnn.mydream.cosmetology.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * 创建人：张高明
 * 创建时间：2016/11/15 10:49
 * 类描述：
 */

public class ViewPagerAdapter extends PagerAdapter {

    private List<View> mViewList;

    public ViewPagerAdapter(List<View> list) {
        mViewList = list;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mViewList.get(position));
    }

    @Override
    public int getCount() {
        return mViewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mViewList.get(position));//添加页卡
        return mViewList.get(position);
    }
}

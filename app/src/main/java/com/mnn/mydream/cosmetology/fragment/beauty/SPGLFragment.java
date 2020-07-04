package com.mnn.mydream.cosmetology.fragment.beauty;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mnn.mydream.cosmetology.R;
import com.mnn.mydream.cosmetology.adapter.fragmentAdapter.ViewPagerFragmentAdapter;
import com.mnn.mydream.cosmetology.fragment.beauty.commoditiesFragments.CPFragment;
import com.mnn.mydream.cosmetology.fragment.beauty.commoditiesFragments.CXKFragment;
import com.mnn.mydream.cosmetology.fragment.beauty.commoditiesFragments.FuWuFragment;
import com.mnn.mydream.cosmetology.fragment.beauty.commoditiesFragments.XMKFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportFragment;


/**
 * 创建人 :MyDream
 * 创建时间：2020/6/11 18:18
 * 类描述：SPGLFragment 商品管理
 */

public class SPGLFragment extends SupportFragment {

    Unbinder unbinder;
    private String TAG = "SPGLFragment";
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.viewpagers)
    ViewPager viewpagers;

    private View view;

    private String[] stringitles = new String[]{"服务", "产品", "项目卡", "储蓄卡"};

    private SupportFragment[] supportFragments = new SupportFragment[]{
            FuWuFragment.newInstance(),
            CPFragment.newInstance(),
            XMKFragment.newInstance(),
            CXKFragment.newInstance()

    };


    public static SPGLFragment newInstance() {

        Bundle args = new Bundle();
        SPGLFragment fragment = new SPGLFragment();
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.spgl_fragment, container, false);


        unbinder = ButterKnife.bind(this, view);

        initview();
        return view;

    }


    private void initview() {

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);

        viewpagers.setAdapter(new ViewPagerFragmentAdapter(getChildFragmentManager(), stringitles, supportFragments));

        tabs.setupWithViewPager(viewpagers);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
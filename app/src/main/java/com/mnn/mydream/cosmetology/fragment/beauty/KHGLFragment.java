package com.mnn.mydream.cosmetology.fragment.beauty;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mnn.mydream.cosmetology.R;
import com.mnn.mydream.cosmetology.adapter.fragmentAdapter.ViewPagerFragmentAdapter;
import com.mnn.mydream.cosmetology.fragment.beauty.khfragments.KHHYFragment;
import com.mnn.mydream.cosmetology.fragment.beauty.khfragments.KHZSFragment;
import com.mnn.mydream.cosmetology.fragment.beauty.khfragments.XJKHFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportFragment;


/**
 * 创建人 :MyDream
 * 创建时间：2020/6/11 18:18
 * 类描述：KHGLFragment 客户管理
 */

public class KHGLFragment extends SupportFragment {

    private String TAG = "KHGLFragment";
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.viewpagers)
    ViewPager viewpagers;
    Unbinder unbinder;

    Unbinder unbinder1;
    private View view;

    private String[] stringitles = new String[]{"新建顾客", "所有顾客", "会员顾客"};

    private SupportFragment[] supportFragments = new SupportFragment[]{
            XJKHFragment.newInstance(),
            KHZSFragment.newInstance(),
            KHHYFragment.newInstance()
    };

    public static KHGLFragment newInstance() {
        Bundle args = new Bundle();
        KHGLFragment fragment = new KHGLFragment();
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.khgl_fragment, container, false);
            unbinder = ButterKnife.bind(this, view);
            initview();

        }
        // 缓存的viewiew需要判断是否已经被加过parent，
        // 如果有parent需要从parent删除，要不然会发生这个view已经有parent的错误。
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            parent.removeView(view);
        }
        unbinder1 = ButterKnife.bind(this, view);
        return view;

    }


    private void initview() {

//        Log.e(TAG, "initview: " );
//        tabs.addTab(tabs.newTab().setText("新建顾客"));
//        tabs.addTab(tabs.newTab().setText("所有顾客"));
//        tabs.addTab(tabs.newTab().setText("会员顾客"));


    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);

        viewpagers.setAdapter(new ViewPagerFragmentAdapter(getChildFragmentManager(), stringitles, supportFragments));
        tabs.setupWithViewPager(viewpagers);
    }
}
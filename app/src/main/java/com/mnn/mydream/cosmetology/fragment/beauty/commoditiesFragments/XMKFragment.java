package com.mnn.mydream.cosmetology.fragment.beauty.commoditiesFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mnn.mydream.cosmetology.R;

import me.yokeyword.fragmentation.SupportFragment;


/**
 * 创建人 :MyDream
 * 创建时间：2020/6/11 18:18
 * 类描述：SJZXFragment 服务界面
 */

public class XMKFragment extends SupportFragment {

    private View view;

    public static XMKFragment newInstance() {
        Bundle args = new Bundle();
        XMKFragment fragment = new XMKFragment();
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.xmk_fragment, container, false);
        initview();

        return view;

    }


    private void initview() {

    }


}
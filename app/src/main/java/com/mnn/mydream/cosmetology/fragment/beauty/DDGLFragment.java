package com.mnn.mydream.cosmetology.fragment.beauty;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mnn.mydream.cosmetology.R;

import me.yokeyword.fragmentation.SupportFragment;


/**
 * 创建人 :MyDream
 * 创建时间：2020/6/11 18:18
 * 类描述：DDGLFragment 订单管理
 */

public class DDGLFragment extends SupportFragment {

    private View view;

    public static DDGLFragment newInstance() {
        Bundle args = new Bundle();
        DDGLFragment fragment = new DDGLFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.ddgl_fragment, container, false);

        initview();

        return view;

    }


    private void initview() {

    }


}
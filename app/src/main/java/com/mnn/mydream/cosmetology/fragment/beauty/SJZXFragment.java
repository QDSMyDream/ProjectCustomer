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
 * 类描述：SJZXFragment 数据中心
 */

public class SJZXFragment extends SupportFragment {

    private View view;

    public static SJZXFragment newInstance() {
        Bundle args = new Bundle();
        SJZXFragment fragment = new SJZXFragment();
        fragment.setArguments(args);
        return fragment;

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.sjzx_fragment, container, false);
            initview();

        }
        // 缓存的viewiew需要判断是否已经被加过parent，
        // 如果有parent需要从parent删除，要不然会发生这个view已经有parent的错误。
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            parent.removeView(view);
        }

        return view;

    }


    private void initview() {

    }


}
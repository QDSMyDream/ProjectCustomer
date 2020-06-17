package com.mnn.mydream.cosmetology.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mnn.mydream.cosmetology.R;


/**
 * @author My Dream
 * <p>
 * 账户设置
 */
public class StatisticsFragment extends Fragment {


    private View myView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (myView == null) {
            myView = inflater.inflate(R.layout.my_fragment, container, false);
            initview();

        }
// 缓存的viewiew需要判断是否已经被加过parent，
// 如果有parent需要从parent删除，要不然会发生这个view已经有parent的错误。
        ViewGroup parent = (ViewGroup) myView.getParent();
        if (parent != null) {
            parent.removeView(myView);
        }

        return myView;

    }

    private void initview() {
    }
}
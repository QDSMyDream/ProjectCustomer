package com.mnn.mydream.cosmetology.fragment.beauty.commoditiesFragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.smoothcheckbox.SmoothCheckBox;
import com.mnn.mydream.cosmetology.R;
import com.mnn.mydream.cosmetology.activity.CXKDialogActivity;
import com.mnn.mydream.cosmetology.activity.XMKDialogActivity;
import com.mnn.mydream.cosmetology.utils.Constons;
import com.zhy.android.percent.support.PercentLinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportFragment;


/**
 * 创建人 :MyDream
 * 创建时间：2020/6/11 18:18
 * 类描述：SJZXFragment 服务界面
 */

public class CXKFragment extends SupportFragment {

    @BindView(R.id.add_cxk_layout)
    PercentLinearLayout addCxkLayout;
    @BindView(R.id.check_box1)
    SmoothCheckBox checkBox1;
    @BindView(R.id.select_server_all)
    PercentLinearLayout selectServerAll;
    @BindView(R.id.operation)
    TextView operation;
    @BindView(R.id.title_layout)
    PercentLinearLayout titleLayout;
    @BindView(R.id.cxk_listview)
    ListView cxkListview;
    Unbinder unbinder;
    private View view;

    public static CXKFragment newInstance() {
        Bundle args = new Bundle();
        CXKFragment fragment = new CXKFragment();
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.cxk_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        initview();

        return view;

    }


    private void initview() {

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.add_cxk_layout)
    public void onViewClicked() {
        Intent Intent = new Intent(getActivity(), CXKDialogActivity.class);
        startActivityForResult(Intent, Constons.RESULT_CXK_CODE_SCUESS_REQUEST);
    }
}
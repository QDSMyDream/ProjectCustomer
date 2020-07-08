package com.mnn.mydream.cosmetology.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.mnn.mydream.cosmetology.R;
import com.mnn.mydream.cosmetology.adapter.BeautyWithinListViewAdapter;
import com.mnn.mydream.cosmetology.bean.User;
import com.mnn.mydream.cosmetology.fragment.beauty.withinFragment.KKFragment;
import com.mnn.mydream.cosmetology.utils.Constons;
import com.zhy.android.percent.support.PercentLinearLayout;
import com.zhy.android.percent.support.PercentRelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation.SupportFragment;

public class BeautyWithinActivity extends SupportActivity {

    @BindView(R.id.title_content2)
    TextView titleContent2;
    @BindView(R.id.within_fragment)
    FrameLayout withinFragment;
    @BindView(R.id.layout_content)
    PercentLinearLayout layoutContent;
    private String TAG = "BeautyWithinActivity";

    @BindView(R.id.back_img)
    ImageView backImg;

    @BindView(R.id.back_layout)
    PercentRelativeLayout backLayout;

    @BindView(R.id.operation_text)
    TextView operationText;

    @BindView(R.id.operation_name)
    TextView operationName;

    @BindView(R.id.layout_left)
    PercentLinearLayout layoutLeft;


    @BindView(R.id.menu_listview)
    ListView menuListview;

    BeautyWithinListViewAdapter beautyWithinListViewAdapter;

    private String[] strings = new String[]{"充值", "开卡", "续充卡", "消费", "服务", "产品", "项目卡", "消耗", "耗卡", "券核销", "服务核销", "其它", "消费赠送", "激活礼品卡"};


    SupportFragment[] mFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beauty_within);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {


        mFragments = new SupportFragment[]{
                KKFragment.newInstance()
        };

        //这里需要如下判断，否则可能出现这个错误https://xuexuan.blog.csdn.net/article/details/103733622
        if (findFragment(KKFragment.class) == null) {
            loadMultipleRootFragment(R.id.within_fragment, 0,
                    mFragments[0]);
        }

        titleContent2.setText(strings[0]);

        beautyWithinListViewAdapter = new BeautyWithinListViewAdapter(this, strings, onClickListener);
        BeautyWithinListViewAdapter.BEAUTY_SELECT_ITEM = Constons.BEAUTY_WITHIN_PREVIOUS_POSTION;
        menuListview.setAdapter(beautyWithinListViewAdapter);

        User user = BmobUser.getCurrentUser(User.class);
        operationName.setText(user.getUsername());


    }


    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int position = menuListview.getPositionForView(v);
            BeautyWithinListViewAdapter.BEAUTY_SELECT_ITEM = position;

            isAdapter(beautyWithinListViewAdapter);
            Log.e("click positon::", "" + position);

        }
    };

    private void isAdapter(BeautyWithinListViewAdapter adapters) {
        if (adapters != null) {
            adapters.notifyDataSetChanged();
        }
    }

    @OnClick({R.id.back_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_layout:
                finish();
                break;
        }
    }


    ///刷新Handler
    @SuppressLint("HandlerLeak")
    private Handler refreshHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {

                //输入提示
                case 1:

                    break;

                case 2:

                    break;

                default:
                    break;
            }
        }
    };


}

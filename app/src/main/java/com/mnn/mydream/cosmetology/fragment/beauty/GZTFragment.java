package com.mnn.mydream.cosmetology.fragment.beauty;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.mnn.mydream.cosmetology.R;
import com.mnn.mydream.cosmetology.activity.AddProjectsActivity;
import com.mnn.mydream.cosmetology.activity.BeautyActivity;
import com.mnn.mydream.cosmetology.activity.BeautyWithinActivity;
import com.mnn.mydream.cosmetology.bean.BeautyTitleBean;
import com.mnn.mydream.cosmetology.eventBus.EventBusMsg;
import com.mnn.mydream.cosmetology.utils.Constons;
import com.mnn.mydream.cosmetology.utils.Tools;
import com.zhy.android.percent.support.PercentLinearLayout;
import com.zhy.android.percent.support.PercentRelativeLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import me.yokeyword.fragmentation.ISupportFragment;
import me.yokeyword.fragmentation.SupportFragment;


/**
 * 创建人 :MyDream
 * 创建时间：2020/6/11 18:18
 * 类描述：GZTFragment  工作台
 */
public class GZTFragment extends SupportFragment {
    private String TAG = "GZTFragment";
    @BindView(R.id.vf_text)
    ViewFlipper vfText;
    Unbinder unbinder1;

    Unbinder unbinder;
    @BindView(R.id.title_expression_img)
    ImageView titleExpressionImg;
    @BindView(R.id.flipper_layout)
    PercentRelativeLayout flipperLayout;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.title2)
    TextView title2;
    @BindView(R.id.content_title_layout)
    PercentRelativeLayout contentTitleLayout;
    @BindView(R.id.img1)
    ImageView img1;
    @BindView(R.id.text1)
    TextView text1;
    @BindView(R.id.layout1)
    PercentRelativeLayout layout1;
    @BindView(R.id.img2)
    ImageView img2;
    @BindView(R.id.text2)
    TextView text2;
    @BindView(R.id.layout2)
    PercentRelativeLayout layout2;
    @BindView(R.id.img3)
    ImageView img3;
    @BindView(R.id.text3)
    TextView text3;
    @BindView(R.id.layout3)
    PercentRelativeLayout layout3;
    @BindView(R.id.img4)
    ImageView img4;
    @BindView(R.id.text4)
    TextView text4;
    @BindView(R.id.layout4)
    PercentRelativeLayout layout4;
    @BindView(R.id.title3)
    TextView title3;
    @BindView(R.id.content_title_layout2)
    PercentRelativeLayout contentTitleLayout2;
    @BindView(R.id.img5)
    ImageView img5;
    @BindView(R.id.text5)
    TextView text5;
    @BindView(R.id.layout5)
    PercentRelativeLayout layout5;
    @BindView(R.id.img6)
    ImageView img6;
    @BindView(R.id.text6)
    TextView text6;
    @BindView(R.id.layout6)
    PercentRelativeLayout layout6;
    @BindView(R.id.img7)
    ImageView img7;
    @BindView(R.id.text7)
    TextView text7;
    @BindView(R.id.layout7)
    PercentRelativeLayout layout7;
    @BindView(R.id.img8)
    ImageView img8;
    @BindView(R.id.text8)
    TextView text8;
    @BindView(R.id.layout8)
    PercentRelativeLayout layout8;
    @BindView(R.id.title4)
    TextView title4;
    @BindView(R.id.content_title_layout3)
    PercentRelativeLayout contentTitleLayout3;
    @BindView(R.id.img9)
    ImageView img9;
    @BindView(R.id.text9)
    TextView text9;
    @BindView(R.id.layout9)
    PercentRelativeLayout layout9;
    @BindView(R.id.img10)
    ImageView img10;
    @BindView(R.id.text10)
    TextView text10;
    @BindView(R.id.layout10)
    PercentRelativeLayout layout10;
    @BindView(R.id.img11)
    ImageView img11;
    @BindView(R.id.text11)
    TextView text11;
    @BindView(R.id.layout11)
    PercentRelativeLayout layout11;
    @BindView(R.id.menu_img_listview)
    ListView menuImgListview;
    @BindView(R.id.line1)
    ImageView line1;
    @BindView(R.id.agency_order_text)
    TextView agencyOrderText;
    @BindView(R.id.agency_order_layout)
    PercentRelativeLayout agencyOrderLayout;
    @BindView(R.id.line2)
    ImageView line2;
    @BindView(R.id.agency_appointment_text)
    TextView agencyAppointmentText;
    @BindView(R.id.agency_appointment_layout)
    PercentRelativeLayout agencyAppointmentLayout;
    @BindView(R.id.agency_layout)
    PercentLinearLayout agencyLayout;
    @BindView(R.id.menu_left_layout)
    PercentLinearLayout menuLeftLayout;
    @BindView(R.id.img_question)
    ImageView imgQuestion;
    @BindView(R.id.common_problem_text)
    TextView commonProblemText;
    @BindView(R.id.common_problem_layout)
    PercentRelativeLayout commonProblemLayout;
    @BindView(R.id.menu_right_layout)
    PercentLinearLayout menuRightLayout;
    @BindView(R.id.menu_layout)
    PercentLinearLayout menuLayout;

    private View view;


    public static GZTFragment newInstance() {

        Bundle args = new Bundle();
        GZTFragment fragment = new GZTFragment();
        fragment.setArguments(args);
        return fragment;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.gzt_fragment, container, false);
            unbinder = ButterKnife.bind(this, view);
            EventBus.getDefault().register(this);
            initview();
//            selectTitleStrings();

        }
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            parent.removeView(view);
        }

        unbinder1 = ButterKnife.bind(this, view);
        return view;

    }


    private void initview() {


    }

//    //查询某个时间前
//    private void selectTitleStrings() {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date createdAtDate = null;
//        try {
//            createdAtDate = sdf.parse(Tools.getSameDay());
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        BmobDate bmobCreatedAtDate = new BmobDate(createdAtDate);
//        BmobQuery<BeautyTitleBean> categoryBmobQuery = new BmobQuery<>();
//
//        Log.e(TAG, "selectMenuItem: " + createdAtDate);
//        categoryBmobQuery.addWhereLessThan("createdAt", bmobCreatedAtDate);
//        categoryBmobQuery.findObjects(new FindListener<BeautyTitleBean>() {
//            @Override
//            public void done(List<BeautyTitleBean> object, BmobException e) {
//                if (e == null) {
//
//                    for (BeautyTitleBean beautyTitleBean : object) {
//                        titleStrings.add(beautyTitleBean.getTitleString());
//                    }
//
//                    refreshHandler.sendEmptyMessage(1);
//
//                } else {
//                    Log.e(TAG, "done: " + "查询失败" + e.toString());
//
//                }
//            }
//        });
//
//    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick({R.id.layout1, R.id.layout2, R.id.layout3, R.id.layout4, R.id.layout5, R.id.layout6, R.id.layout7, R.id.layout8, R.id.layout9, R.id.layout10, R.id.agency_order_layout, R.id.agency_appointment_layout, R.id.common_problem_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            //订单待处理
            case R.id.agency_order_layout:

                break;

            //预约服务
            case R.id.agency_appointment_layout:

                break;


            //常见问题
            case R.id.common_problem_layout:


                break;


            //开卡
            case R.id.layout1:
                Constons.BEAUTY_WITHIN_PREVIOUS_POSTION = 1;

                startBeautyWithinActivity();
                break;

            //续充卡
            case R.id.layout2:
                Constons.BEAUTY_WITHIN_PREVIOUS_POSTION = 2;

                startBeautyWithinActivity();
                break;

            //单次服务
            case R.id.layout3:
                Constons.BEAUTY_WITHIN_PREVIOUS_POSTION = 4;

                startBeautyWithinActivity();
                break;

            //购买项目卡
            case R.id.layout4:
                Constons.BEAUTY_WITHIN_PREVIOUS_POSTION = 5;

                startBeautyWithinActivity();
                break;

            //好卡
            case R.id.layout5:
                Constons.BEAUTY_WITHIN_PREVIOUS_POSTION = 6;

                startBeautyWithinActivity();
                break;

            //店铺核销
            case R.id.layout6:
                Constons.BEAUTY_WITHIN_PREVIOUS_POSTION = 8;

                startBeautyWithinActivity();
                break;

            //第三方核销
            case R.id.layout7:
                Constons.BEAUTY_WITHIN_PREVIOUS_POSTION = 9;

                startBeautyWithinActivity();
                break;

            //服务核销
            case R.id.layout8:
                Constons.BEAUTY_WITHIN_PREVIOUS_POSTION = 10;

                startBeautyWithinActivity();
                break;

            //消费赠送
            case R.id.layout9:
                Constons.BEAUTY_WITHIN_PREVIOUS_POSTION = 12;

                startBeautyWithinActivity();
                break;

            //激活礼品卡
            case R.id.layout10:
                Constons.BEAUTY_WITHIN_PREVIOUS_POSTION = 13;

                startBeautyWithinActivity();

                break;


        }
    }


    private void setFlipperString() {
        Log.e(TAG, "setFlipperString: ");

        for (int i = 0; i < Constons.titleStrings.size(); i++) {
            Log.e(TAG, "setFlipperString: " + Constons.titleStrings.get(i));
            View view = LayoutInflater.from(getContext()).inflate(R.layout.beauty_viewflipper_item, null);
            TextView textView = view.findViewById(R.id.flipper_text);
            textView.setText(Constons.titleStrings.get(i));
            vfText.addView(view);
        }

        vfText.startFlipping();
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.e(TAG, "onHiddenChanged: " + hidden);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.e(TAG, "setUserVisibleHint: " + isVisibleToUser);
    }

    private void startBeautyWithinActivity() {

        Intent Intent = new Intent(getActivity(), BeautyWithinActivity.class);
        startActivity(Intent);

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMain(EventBusMsg event) {

        int flag = event.getMsgInt();
        switch (flag) {
            case Constons.SELECT_TITLE:
                Log.e(TAG, "onEventMain: " + event.toString());
                setFlipperString();
                break;


        }


    }

}
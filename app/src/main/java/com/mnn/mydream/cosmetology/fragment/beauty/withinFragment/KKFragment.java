package com.mnn.mydream.cosmetology.fragment.beauty.withinFragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.applandeo.materialcalendarview.view.NiceSpinner;
import com.mnn.mydream.cosmetology.R;
import com.mnn.mydream.cosmetology.activity.BeautyWithinActivity;
import com.mnn.mydream.cosmetology.adapter.BeautyWithinCardGridViewAdapter;
import com.mnn.mydream.cosmetology.adapter.BeautyWithinTipsAdapter;
import com.mnn.mydream.cosmetology.bean.BeautyBeanKh;
import com.mnn.mydream.cosmetology.bean.BeautyWithinCardsBean;
import com.mnn.mydream.cosmetology.utils.Constons;
import com.mnn.mydream.cosmetology.utils.ImageLoader;
import com.mnn.mydream.cosmetology.utils.ToastUtils;
import com.mnn.mydream.cosmetology.utils.Tools;
import com.mnn.mydream.cosmetology.view.CircleImageView;
import com.xujiaji.happybubble.BubbleLayout;
import com.zhy.android.percent.support.PercentLinearLayout;
import com.zhy.android.percent.support.PercentRelativeLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import me.yokeyword.fragmentation.SupportFragment;


/**
 * 创建人 :MyDream
 * 创建时间：2020/6/11 18:18
 * 类描述：KKfragment 开卡
 */

public class KKFragment extends SupportFragment {

    private String TAG = "KKFragment";

    @BindView(R.id.layout1)
    LinearLayout layout1;
    @BindView(R.id.layout1_tv1)
    TextView layout1Tv1;
    @BindView(R.id.layout1_tv2)
    TextView layout1Tv2;
    @BindView(R.id.layout1_tv4)
    TextView layout1Tv4;
    @BindView(R.id.layout1_tv3)
    TextView layout1Tv3;
    @BindView(R.id.layout1_text)
    LinearLayout layout1Text;
    @BindView(R.id.layout2)
    LinearLayout layout2;
    @BindView(R.id.layout2_tv1)
    TextView layout2Tv1;
    @BindView(R.id.layout2_tv2)
    TextView layout2Tv2;
    @BindView(R.id.layout2_tv3)
    TextView layout2Tv3;
    @BindView(R.id.layout2_tv4)
    TextView layout2Tv4;
    @BindView(R.id.layout2_text)
    LinearLayout layout2Text;
    @BindView(R.id.tv_lines)
    TextView tvLines;
    @BindView(R.id.layout3_tv1)
    TextView layout3Tv1;
    @BindView(R.id.layout3_tv2)
    TextView layout3Tv2;
    @BindView(R.id.layout3_tv3)
    TextView layout3Tv3;
    @BindView(R.id.layout3_tv4)
    TextView layout3Tv4;
    @BindView(R.id.layout3_tv5)
    TextView layout3Tv5;
    @BindView(R.id.layout3_tv6)
    TextView layout3Tv6;
    @BindView(R.id.layout3)
    LinearLayout layout3;
    @BindView(R.id.bubbleLayout)
    BubbleLayout bubbleLayout;
    @BindView(R.id.text_cards)
    TextView textCards;
    @BindView(R.id.gridview)
    GridView gridview;
    @BindView(R.id.menu_left_layout)
    PercentLinearLayout menuLeftLayout;
    @BindView(R.id.ed_select)
    AutoCompleteTextView edSelect;
    @BindView(R.id.btn_select)
    TextView btnSelect;
    @BindView(R.id.select_kh_layout)
    PercentLinearLayout selectKhLayout;
    @BindView(R.id.kh_tx)
    CircleImageView khTx;
    @BindView(R.id.kh_name)
    TextView khName;
    @BindView(R.id.kh_sex)
    ImageView khSex;
    @BindView(R.id.title_username_layout)
    PercentLinearLayout titleUsernameLayout;
    @BindView(R.id.kh_phone)
    TextView khPhone;
    @BindView(R.id.khxq_left_layout)
    PercentLinearLayout khxqLeftLayout;
    @BindView(R.id.khsr_text)
    TextView khsrText;
    @BindView(R.id.khsr_layout)
    PercentLinearLayout khsrLayout;
    @BindView(R.id.khssmd_text)
    TextView khssmdText;
    @BindView(R.id.khxq_right_layout)
    PercentLinearLayout khxqRightLayout;
    @BindView(R.id.delete_kh)
    ImageView deleteKh;
    @BindView(R.id.user_info_layout)
    PercentRelativeLayout userInfoLayout;
    @BindView(R.id.khxq_layout)
    PercentLinearLayout khxqLayout;
    @BindView(R.id.consumption_time)
    TextView consumptionTime;
    @BindView(R.id.consumption_txt)
    TextView consumptionTxt;
    @BindView(R.id.consumption_img)
    ImageView consumptionImg;
    @BindView(R.id.select_layout1)
    PercentLinearLayout selectLayout1;
    @BindView(R.id.consumption_md)
    TextView consumptionMd;
    @BindView(R.id.spinner_md)
    NiceSpinner spinnerMd;
    @BindView(R.id.select_layout2)
    PercentRelativeLayout selectLayout2;
    @BindView(R.id.select_right_layout)
    PercentLinearLayout selectRightLayout;
    @BindView(R.id.menu_layout)

    PercentLinearLayout menuLayout;
    Unbinder unbinder;

    private View view;

    private BeautyWithinCardGridViewAdapter beautyWithinCardGridViewAdapter;//卡包列表

    private List<BeautyWithinCardsBean> beautyWithinCardsBeans = new ArrayList<>();//默认卡包列表

    private List<BeautyBeanKh> beautyBeanKhs = new ArrayList<>();//查询列表

    private BeautyWithinTipsAdapter beautyWithinTipsAdapter;

    public static KKFragment newInstance() {

        Bundle args = new Bundle();

        KKFragment fragment = new KKFragment();
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.kk_fragment_layout, container, false);
        unbinder = ButterKnife.bind(this, view);


        initview();
        return view;
    }


    private void initview() {

        spinnerMd.attachDataSource(Constons.getOperationMd());

        //气泡布局
        bubbleLayout.setLook(BubbleLayout.Look.RIGHT);
        bubbleLayout.setShadowColor(getResources().getColor(R.color.beauty_add_custmer_bg));
        bubbleLayout.setBubbleRadius(Tools.dpTopx(getContext(), 5));
        bubbleLayout.setShadowRadius(Tools.dpTopx(getContext(), 3));
        bubbleLayout.invalidate();

        beautyWithinCardsBeans.add(new BeautyWithinCardsBean("我是秦大帅1", "大苏打大撒", "储蓄卡", 0));
        beautyWithinCardsBeans.add(new BeautyWithinCardsBean("我是秦大帅2", "大撒哇强大", "储蓄卡", 1));
        beautyWithinCardsBeans.add(new BeautyWithinCardsBean("我是秦大帅3", "大沙发阿斯顿", "储蓄卡", 2));
        beautyWithinCardsBeans.add(new BeautyWithinCardsBean("我是秦大帅4", "温热打赏", "储蓄卡", 3));
        beautyWithinCardsBeans.add(new BeautyWithinCardsBean("我是秦大帅5", "大沙发阿斯顿", "储蓄卡", 4));
        beautyWithinCardsBeans.add(new BeautyWithinCardsBean("我是秦大帅6", "温热打赏", "储蓄卡", 5));

        beautyWithinCardsBeans.add(new BeautyWithinCardsBean("我是秦大帅7", "大沙发阿斯顿", "储蓄卡", 6));
        beautyWithinCardsBeans.add(new BeautyWithinCardsBean("我是秦大帅8", "温热打赏", "储蓄卡", 7));

        beautyWithinCardsBeans.add(new BeautyWithinCardsBean("我是秦大帅9", "大沙发阿斯顿", "储蓄卡", 8));
        beautyWithinCardsBeans.add(new BeautyWithinCardsBean("我是秦大帅0", "温热打赏", "储蓄卡", 9));

        beautyWithinCardGridViewAdapter = new BeautyWithinCardGridViewAdapter(getActivity(), beautyWithinCardsBeans);
        gridview.setAdapter(beautyWithinCardGridViewAdapter);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BeautyWithinCardsBean beautyWithinCardsBean = (BeautyWithinCardsBean) beautyWithinCardGridViewAdapter.getItem(position);
                Log.e(TAG, "onClick: " + beautyWithinCardsBean.toString());

            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btn_select, R.id.delete_kh})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_select:
                selectKhInfo();
                break;
            case R.id.delete_kh:
                deleteKhInfo();
                break;
        }
    }


    //删除客户
    private void deleteKhInfo() {

        khxqLayout.setVisibility(View.GONE);
        selectKhLayout.setVisibility(View.VISIBLE);
        edSelect.setText("");

    }


    //查询客户
    private void selectKhInfo() {

        getSelectCustomers(edSelect.getText().toString());
    }

    //按name,phone“或” 查询
    private void getSelectCustomers(String string) {
        Log.e(TAG, "getSelectCustomers: " + string);

        BmobQuery<BeautyBeanKh> eq1 = new BmobQuery<BeautyBeanKh>();
        eq1.addWhereEqualTo("name", string);
        BmobQuery<BeautyBeanKh> eq2 = new BmobQuery<BeautyBeanKh>();
        eq2.addWhereEqualTo("phone", string);
        List<BmobQuery<BeautyBeanKh>> queries = new ArrayList<BmobQuery<BeautyBeanKh>>();
        queries.add(eq1);
        queries.add(eq2);
        BmobQuery<BeautyBeanKh> mainQuery = new BmobQuery<BeautyBeanKh>();
        mainQuery.or(queries);

        mainQuery.findObjects(new FindListener<BeautyBeanKh>() {
            @Override
            public void done(List<BeautyBeanKh> object, BmobException e) {
                if (e == null) {
                    beautyBeanKhs.clear();
                    beautyBeanKhs = object;
                    ToastUtils.showToast(getContext(), "查询成功" + object.size(), true);
                    refreshHandler.sendEmptyMessage(1);
                } else {
                    ToastUtils.showToast(getContext(), "查询失败" + e.toString(), true);
                }
            }
        });
    }


    ///刷新Handler
    @SuppressLint("HandlerLeak")
    private Handler refreshHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {

                //输入提示
                case 1:
                    Log.e(TAG, "handleMessage: 1");

                    if (beautyBeanKhs.size() == 0) {

                        BeautyBeanKh beautyBeanKh = new BeautyBeanKh();
                        beautyBeanKh.setName("无");
                        beautyBeanKh.setPhone("无");
                        beautyBeanKhs.add(beautyBeanKh);
                    }

                    //输入姓名提示
                    beautyWithinTipsAdapter = new BeautyWithinTipsAdapter(getContext(), beautyBeanKhs);
                    edSelect.setAdapter(beautyWithinTipsAdapter);
                    edSelect.setOnItemClickListener(onTipsItemClickListener);
                    edSelect.showDropDown();
                    break;

                case 2:

                    break;

                default:
                    break;
            }
        }
    };

    private AdapterView.OnItemClickListener onTipsItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Log.e(TAG, "onItemClick: " + beautyWithinTipsAdapter.getItem(position));
            BeautyBeanKh beautyBeanKh = (BeautyBeanKh) beautyWithinTipsAdapter.getItem(position);

            if (beautyBeanKh.getName().equals("无")) {

                edSelect.setText("");
                return;
            }

            khxqLayout.setVisibility(View.VISIBLE);
            selectKhLayout.setVisibility(View.GONE);

            //加载图片
            ImageLoader.displayImageView(getContext(), beautyBeanKh.getTx(), khTx, R.mipmap.def_photo);

            khName.setText(beautyBeanKh.getName());

            if (beautyBeanKh.getSex().equals("男")) {
                khSex.setImageResource(R.mipmap.nan);
            } else {
                khSex.setImageResource(R.mipmap.nv);
            }
            khPhone.setText("手机号：" + beautyBeanKh.getPhone());
            khsrText.setText("生日：" + beautyBeanKh.getBir());
            khssmdText.setText("所属门店：" + beautyBeanKh.getMd());


        }
    };
}
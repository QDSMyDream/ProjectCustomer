package com.mnn.mydream.cosmetology.fragment.beauty.commoditiesFragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.applandeo.materialcalendarview.view.NiceSpinner;
import com.example.smoothcheckbox.SmoothCheckBox;
import com.mnn.mydream.cosmetology.R;
import com.mnn.mydream.cosmetology.activity.CPAddDialogActivity;
import com.mnn.mydream.cosmetology.activity.FuWuServerDialogActivity;
import com.mnn.mydream.cosmetology.adapter.fragmentAdapter.MyViewPagerAdapter;
import com.mnn.mydream.cosmetology.bean.fuwuBean.CPDataBean;
import com.mnn.mydream.cosmetology.bean.fuwuBean.FuWuSaleBean;
import com.mnn.mydream.cosmetology.dialog.LoadingDialog;
import com.mnn.mydream.cosmetology.fragment.beauty.commoditiesFragments.adapter.CPListAdapter;
import com.mnn.mydream.cosmetology.fragment.beauty.commoditiesFragments.adapter.FuWuView1ListAdapter;
import com.mnn.mydream.cosmetology.interfaces.CPListOnClickListener;
import com.mnn.mydream.cosmetology.utils.Constons;
import com.mnn.mydream.cosmetology.utils.ToastUtils;
import com.mnn.mydream.cosmetology.utils.Tools;
import com.mnn.mydream.cosmetology.view.MyViewPager;
import com.zhy.android.percent.support.PercentLinearLayout;
import com.zhy.android.percent.support.PercentRelativeLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
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
 * 类描述：SJZXFragment 服务界面
 */

public class CPFragment extends SupportFragment implements CPListOnClickListener {
    private String TAG = getClass().getSimpleName();
    @BindView(R.id.cp_name_edit)
    AppCompatEditText cpNameEdit;
    @BindView(R.id.cp_type_spinner)
    NiceSpinner cpTypeSpinner;
    @BindView(R.id.cp_select_btn)
    TextView cpSelectBtn;
    @BindView(R.id.remake)
    TextView remake;
    @BindView(R.id.remake_layout)
    PercentRelativeLayout remakeLayout;
    @BindView(R.id.sale_text)
    TextView saleText;
    @BindView(R.id.dismount_text)
    TextView dismountText;
    @BindView(R.id.add_cp_layout)
    PercentLinearLayout addCpLayout;
    @BindView(R.id.viewpagers)
    MyViewPager viewpagers;
    Unbinder unbinder;
    private View view;
    private View saleView, dismountView;

    private LayoutInflater mInflater;

    private ListView cpView1ListView, cpView2ListView;

    //页卡视图集合
    private List<View> mViewList = new ArrayList<>();
    private List<String> titleList = new ArrayList<>();

    private LoadingDialog loadingDialog;

    /**
     * view1 上架项目
     */
    private CPListAdapter cpListAdapter1;
    private List<CPDataBean> cpSaleDataBeans = new ArrayList<>();

    private CPListAdapter cpListAdapter2;
    private List<CPDataBean> cpDismounDatatBeans = new ArrayList<>();


    private SmoothCheckBox smoothCheckBox1, smoothCheckBox2;

    public static CPFragment newInstance() {
        Bundle args = new Bundle();
        CPFragment fragment = new CPFragment();
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.cp_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        initview();
        return view;

    }


    private void initview() {

        cpTypeSpinner.attachDataSource(Constons.SelectServerTypeString);

        mInflater = LayoutInflater.from(getActivity());
        saleView = mInflater.inflate(R.layout.cp_view1_layout, null);
        saleViewFindViewInit(saleView);
        dismountView = mInflater.inflate(R.layout.cp_view2_layout, null);
        dismountViewFindViewInit(dismountView);

        mViewList.add(saleView);
        mViewList.add(dismountView);

        MyViewPagerAdapter myViewPagerAdapter = new MyViewPagerAdapter(mViewList, titleList);
        //给ViewPager设置适配器
        viewpagers.setAdapter(myViewPagerAdapter);
        viewpagers.setCurrentItem(0);

        getSelectCpAll();


    }

    private void getSelectCpAll() {
        LoadingDialog.Builder addSignDialogBuild = new LoadingDialog.Builder(getActivity());
        loadingDialog = addSignDialogBuild.createDialog();
        loadingDialog.setCanceledOnTouchOutside(false);
        // 设置点击屏幕Dialog不消失
        loadingDialog.show();

        BmobQuery<CPDataBean> categoryBmobQuery = new BmobQuery<>();
        categoryBmobQuery.findObjects(new FindListener<CPDataBean>() {
            @Override
            public void done(List<CPDataBean> object, BmobException e) {
                if (e == null) {
                    cpSaleDataBeans.clear();
                    cpDismounDatatBeans.clear();
                    for (int i = 0; i < object.size(); i++) {
                        if (object.get(i).isCpSaleFlag()) {
                            cpSaleDataBeans.add(object.get(i));
                        } else {
                            cpDismounDatatBeans.add(object.get(i));
                        }
                    }
                    refreshHandler.sendEmptyMessage(0);

                } else {

                    loadingDialog.dismiss();

                    String s1 = String.format(getString(R.string.beauty_within_saleing_txt), cpSaleDataBeans.size());
                    saleText.setText(s1);
                    String s2 = String.format(getString(R.string.beauty_within_dismount_txt), cpDismounDatatBeans.size());
                    dismountText.setText(s2);
                    Log.e("BMOB", e.toString());

                    ToastUtils.showToast(getContext(), "查询失败", false);
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
                case 0://查询

                    Log.e(TAG, "handleMessage: " + cpSaleDataBeans.size());
                    //设置数据倒叙
                    setSortList(cpSaleDataBeans);
//                    fuWuView1ListAdapter = new FuWuView1ListAdapter(getActivity(), fuWuSaleBeans);
//                    fuWuView1ListAdapter.setFuWuListOnClickListener(fuWuListOnClickListener1);
//                    fuwuView1List.setAdapter(fuWuView1ListAdapter);
                    cpListAdapter1.notifyDataSetChanged();
                    String s1 = String.format(getString(R.string.beauty_within_saleing_txt), cpSaleDataBeans.size());
                    saleText.setText(s1);

                    //设置数据倒叙
                    setSortList(cpDismounDatatBeans);
                    cpListAdapter2.notifyDataSetChanged();
//                    fuWuView2ListAdapter = new FuWuView1ListAdapter(getActivity(), fuWuDismountBeans);
//                    fuWuView2ListAdapter.setFuWuListOnClickListener(fuWuListOnClickListener2);
//                    fuwuView2List.setAdapter(fuWuView2ListAdapter);
                    String s2 = String.format(getString(R.string.beauty_within_dismount_txt), cpDismounDatatBeans.size());
                    dismountText.setText(s2);

                    loadingDialog.dismiss();
                    break;

                case 1:

                    break;

            }
        }

    };


    private void saleViewFindViewInit(View saleView) {

        smoothCheckBox1 = saleView.findViewById(R.id.check_box1);
        cpView1ListView = saleView.findViewById(R.id.cp_listview);

        cpListAdapter1 = new CPListAdapter(getContext(), cpSaleDataBeans);
        cpListAdapter1.setCpListOnClickListener(this);
        cpView1ListView.setAdapter(cpListAdapter2);
    }


    private void dismountViewFindViewInit(View dismountView) {
        smoothCheckBox2 = saleView.findViewById(R.id.check_box1);
        cpView2ListView = dismountView.findViewById(R.id.cp_listview);

        cpListAdapter2 = new CPListAdapter(getContext(), cpDismounDatatBeans);
        cpListAdapter2.setCpListOnClickListener(this);
        cpView2ListView.setAdapter(cpListAdapter2);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.cp_select_btn, R.id.remake_layout, R.id.sale_text, R.id.dismount_text, R.id.add_cp_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cp_select_btn:


                break;
            case R.id.remake_layout:


                break;

            case R.id.sale_text:
                checkViePager(0);
                break;

            case R.id.dismount_text:
                checkViePager(1);
                break;

            case R.id.add_cp_layout:
                setServerDialog();
                break;
        }
    }


    //倒叙
    public void setSortList(List<CPDataBean> list) {
        Collections.sort(list, new Comparator<CPDataBean>() {
            @Override
            public int compare(CPDataBean o1, CPDataBean o2) {
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    Date dt1 = df.parse(o1.getCreatedAt());
                    Date dt2 = df.parse(o2.getCreatedAt());
                    if (dt1.getTime() > dt2.getTime()) {
                        return -1;
                    } else if (dt1.getTime() < dt2.getTime()) {
                        return 1;
                    } else {
                        return 0;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return 0;
            }
        });
    }


    //倒叙
    public static void ListSorts(List<FuWuSaleBean> list) {
        Collections.sort(list, new Comparator<FuWuSaleBean>() {
            @Override
            public int compare(FuWuSaleBean o1, FuWuSaleBean o2) {
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    Date dt1 = df.parse(o1.getCreatedAt());
                    Date dt2 = df.parse(o2.getCreatedAt());
                    if (dt1.getTime() > dt2.getTime()) {
                        return -1;
                    } else if (dt1.getTime() < dt2.getTime()) {
                        return 1;
                    } else {
                        return 0;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return 0;
            }
        });
    }

    @Override
    public void onClickUpdate(View v, int pos, CPDataBean cpDataBean) {


    }

    @Override
    public void onClickDismount(View v, int pos, CPDataBean cpDataBean) {

    }

    @Override
    public void onClickSale(View v, int pos, CPDataBean cpDataBean) {

    }


    //切换viewpager
    private void checkViePager(int postion) {

        switch (postion) {
            case 0:
                saleText.setBackgroundResource(R.drawable.beauty_within_text_frame_option);
                dismountText.setBackgroundResource(R.drawable.beauty_within_text_frame_no_option);
                addCpLayout.setVisibility(View.VISIBLE);
                break;

            case 1:
                saleText.setBackgroundResource(R.drawable.beauty_within_text_frame_no_option);
                dismountText.setBackgroundResource(R.drawable.beauty_within_text_frame_option);
                addCpLayout.setVisibility(View.GONE);
                break;
        }

        viewpagers.setCurrentItem(postion);

    }

    private void setServerDialog() {

        Intent Intent = new Intent(getActivity(), CPAddDialogActivity.class);
        Intent.putExtra("flagInt", "1");
        startActivityForResult(Intent, Constons.RESULT_FUWU_SERVER_CODE_VIEW_REQUEST);
    }

}
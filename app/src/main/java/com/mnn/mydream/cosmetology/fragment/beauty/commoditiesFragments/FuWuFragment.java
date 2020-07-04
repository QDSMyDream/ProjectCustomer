package com.mnn.mydream.cosmetology.fragment.beauty.commoditiesFragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.applandeo.materialcalendarview.view.NiceSpinner;
import com.example.smoothcheckbox.SmoothCheckBox;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.mnn.mydream.cosmetology.R;
import com.mnn.mydream.cosmetology.activity.AddProjectsActivity;
import com.mnn.mydream.cosmetology.activity.BeautyActivity;
import com.mnn.mydream.cosmetology.activity.EditProjectsActivity;
import com.mnn.mydream.cosmetology.activity.FuWuServerDialogActivity;
import com.mnn.mydream.cosmetology.activity.MainActivity;
import com.mnn.mydream.cosmetology.adapter.CustomerPeojectsListAdapter;
import com.mnn.mydream.cosmetology.adapter.fragmentAdapter.MyViewPagerAdapter;
import com.mnn.mydream.cosmetology.bean.Customer;
import com.mnn.mydream.cosmetology.bean.CustomerAndProject;
import com.mnn.mydream.cosmetology.bean.CustomerProjectBean;
import com.mnn.mydream.cosmetology.bean.fuwuBean.FuWuSaleBean;
import com.mnn.mydream.cosmetology.dialog.AddProjectsDialog;
import com.mnn.mydream.cosmetology.dialog.BeautyWithinServerDialog;
import com.mnn.mydream.cosmetology.dialog.CommonDialog;
import com.mnn.mydream.cosmetology.dialog.LoadingDialog;
import com.mnn.mydream.cosmetology.fragment.beauty.commoditiesFragments.adapter.FuWuView1ListAdapter;
import com.mnn.mydream.cosmetology.interfaces.FuWuListOnClickListener;
import com.mnn.mydream.cosmetology.utils.Constons;
import com.mnn.mydream.cosmetology.utils.ImageLoader;
import com.mnn.mydream.cosmetology.utils.StringUtils;
import com.mnn.mydream.cosmetology.utils.ToastUtils;
import com.mnn.mydream.cosmetology.utils.Tools;
import com.mnn.mydream.cosmetology.view.MyViewPager;
import com.zhy.android.percent.support.PercentLinearLayout;
import com.zhy.android.percent.support.PercentRelativeLayout;

import java.io.File;
import java.text.ParseException;
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
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;
import cn.bmob.v3.util.V;
import me.yokeyword.fragmentation.SupportFragment;


/**
 * 创建人 :MyDream
 * 创建时间：2020/6/11 18:18
 * 类描述：SJZXFragment 服务界面
 */

public class FuWuFragment extends SupportFragment {

    private String TAG = FuWuFragment.class.getSimpleName();

    @BindView(R.id.server_name_edit)
    AppCompatEditText serverNameEdit;
    @BindView(R.id.server_type_spinner)
    NiceSpinner serverTypeSpinner;
    @BindView(R.id.search_server_btn)
    TextView searchServerBtn;
    @BindView(R.id.sale_text)
    TextView saleText;
    @BindView(R.id.dismount_text)
    TextView dismountText;
    @BindView(R.id.add_server_layout)
    PercentLinearLayout addServerLayout;
    @BindView(R.id.viewpagers)
    MyViewPager viewpagers;
    Unbinder unbinder;

    private View view;

    //页卡视图集合
    private List<View> mViewList = new ArrayList<>();
    private List<String> titleList = new ArrayList<>();
    private LayoutInflater mInflater;
    private View saleView, dismountView;

    private ListView fuwuView1List, fuwuView2List;
    private SmoothCheckBox smoothCheckBox1, smoothCheckBox2;

    /**
     * view1 上架项目
     */
    private FuWuView1ListAdapter fuWuView1ListAdapter;
    private List<FuWuSaleBean> fuWuSaleBeans = new ArrayList<>();//售卖中列表

    private FuWuView1ListAdapter fuWuView2ListAdapter;
    private List<FuWuSaleBean> fuWuDismountBeans = new ArrayList<>();//下架列表

    private LoadingDialog loadingDialog;

    private int FLAG_INDEX;//标记

    private int FLAG_POSTION;


    public static FuWuFragment newInstance() {
        Bundle args = new Bundle();
        FuWuFragment fragment = new FuWuFragment();
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fuwu_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        initview();
        return view;
    }


    private void initview() {


        mInflater = LayoutInflater.from(getActivity());
        saleView = mInflater.inflate(R.layout.fuwu_view1_layout, null);
        saleViewFindViewInit(saleView);
        dismountView = mInflater.inflate(R.layout.fuwu_view2_layout, null);
        dismountViewFindViewInit(dismountView);

        mViewList.add(saleView);
        mViewList.add(dismountView);

        MyViewPagerAdapter myViewPagerAdapter = new MyViewPagerAdapter(mViewList, titleList);
        //给ViewPager设置适配器
        viewpagers.setAdapter(myViewPagerAdapter);
        viewpagers.setCurrentItem(0);

        getSelectServerAll();
    }


    private void dismountViewFindViewInit(View dismountView) {

        smoothCheckBox2 = dismountView.findViewById(R.id.check_box2);
        fuwuView2List = (ListView) dismountView.findViewById(R.id.fuwu_view2_list);


        fuWuView2ListAdapter = new FuWuView1ListAdapter(getActivity(), fuWuDismountBeans);
        fuWuView2ListAdapter.setFuWuListOnClickListener(fuWuListOnClickListener2);
        fuwuView2List.setAdapter(fuWuView2ListAdapter);


    }


    //切换viewpager
    private void checkViePager(int postion) {

        switch (postion) {
            case 0:
                saleText.setBackgroundResource(R.drawable.beauty_within_text_frame);
                dismountText.setBackgroundResource(R.drawable.beauty_within_not_select_bg);
                addServerLayout.setVisibility(View.VISIBLE);
                break;

            case 1:
                saleText.setBackgroundResource(R.drawable.beauty_within_not_select_bg);
                dismountText.setBackgroundResource(R.drawable.beauty_within_text_frame);
                addServerLayout.setVisibility(View.GONE);
                break;
        }

        viewpagers.setCurrentItem(postion);

    }

    private void saleViewFindViewInit(View saleView) {

        smoothCheckBox1 = saleView.findViewById(R.id.check_box1);
        fuwuView1List = (ListView) saleView.findViewById(R.id.fuwu_view1_list);

        fuWuView1ListAdapter = new FuWuView1ListAdapter(getActivity(), fuWuSaleBeans);
        fuWuView1ListAdapter.setFuWuListOnClickListener(fuWuListOnClickListener1);
        fuwuView1List.setAdapter(fuWuView1ListAdapter);


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.e(TAG, "onAttach: ");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "onCreate: ");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.e(TAG, "onSaveInstanceState: ");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e(TAG, "onResume: ");

    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e(TAG, "onPause: ");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e(TAG, "onCreate: ");
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy: ");
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        Log.e(TAG, "onSupportVisible: ");


    }

    @Override
    public void onSupportInvisible() {
        super.onSupportInvisible();
        Log.e(TAG, "onSupportInvisible: ");
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

    @OnClick({R.id.search_server_btn, R.id.sale_text, R.id.dismount_text, R.id.add_server_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.search_server_btn:

                break;

            case R.id.sale_text:
                checkViePager(0);
                break;

            case R.id.dismount_text:
                checkViePager(1);
                break;

            case R.id.add_server_layout:
                setServerDialog();
                break;
        }
    }

    private void setServerDialog() {
//
//        BeautyWithinServerDialog.Builder beautyWithinServerDialogBuild = new BeautyWithinServerDialog.Builder(getActivity());
//        beautyWithinServerDialogBuild.setYesOnClick(onClickListenerYes);
//        beautyWithinServerDialogBuild.setServerTypeData(serverTypeStrings);
//        beautyWithinServerDialogBuild.setServerMdData(serverMdStrings);
//
//        beautyWithinServerDialogBuild.setPhotoClickOnClick(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showPickerDialog();
//            }
//        });
//
//        beautyWithinServerDialog = beautyWithinServerDialogBuild.createDialog();
//        beautyWithinServerDialog.setCanceledOnTouchOutside(false);
//        beautyWithinServerDialog.show();
        Intent Intent = new Intent(getActivity(), FuWuServerDialogActivity.class);
        Intent.putExtra("flagInt", "1");
        startActivityForResult(Intent, Constons.RESULT_FUWU_SERVER_CODE_VIEW_REQUEST);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e(TAG, "onActivityResult: " + requestCode);
        Log.e(TAG, "onActivityResult: " + resultCode);
        //界面刷新

        if (resultCode == Constons.RESULT_FUWU_SERVER_CODE_SCUESS_REQUEST) {
            getSelectServerAll();

        }
        if (resultCode == Constons.RESULT_FUWU_SERVER_CODE_UPDATE_REQUEST) {
//            getSelectServerAll();
            setUpdateSaleBean(data);
        }

    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        Log.e(TAG, "onFragmentResult: requestCode" + requestCode + "----resultCode" + resultCode);
    }

    /**
     * 服务总数
     */
    private void getSelectServerAll() {

        LoadingDialog.Builder addSignDialogBuild = new LoadingDialog.Builder(getActivity());
        loadingDialog = addSignDialogBuild.createDialog();
        loadingDialog.setCanceledOnTouchOutside(false);
        // 设置点击屏幕Dialog不消失
        loadingDialog.show();

//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//
//        Date createdAtDate = null;
//        try {
//            createdAtDate = sdf.parse(Tools.getSameDay());
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        BmobDate bmobCreatedAtDate = new BmobDate(createdAtDate);
//
        BmobQuery<FuWuSaleBean> categoryBmobQuery = new BmobQuery<>();
//        categoryBmobQuery.addWhereLessThanOrEqualTo("createdAt", bmobCreatedAtDate);
        categoryBmobQuery.findObjects(new FindListener<FuWuSaleBean>() {
            @Override
            public void done(List<FuWuSaleBean> object, BmobException e) {
                if (e == null) {
                    fuWuSaleBeans.clear();
                    fuWuDismountBeans.clear();
                    for (int i = 0; i < object.size(); i++) {
                        if (object.get(i).isServerSaleFlag()) {
                            fuWuSaleBeans.add(object.get(i));
                        } else {
                            fuWuDismountBeans.add(object.get(i));
                        }
                    }
                    refreshHandler.sendEmptyMessage(0);

                } else {

                    loadingDialog.dismiss();
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

                    Log.e(TAG, "handleMessage: " + fuWuSaleBeans.size());
                    //设置数据倒叙
                    Tools.ListSorts(fuWuSaleBeans);
//                    fuWuView1ListAdapter = new FuWuView1ListAdapter(getActivity(), fuWuSaleBeans);
//                    fuWuView1ListAdapter.setFuWuListOnClickListener(fuWuListOnClickListener1);
//                    fuwuView1List.setAdapter(fuWuView1ListAdapter);
                    fuWuView1ListAdapter.notifyDataSetChanged();
                    String s1 = String.format(getString(R.string.beauty_within_saleing_txt), fuWuSaleBeans.size());
                    saleText.setText(s1);

                    //设置数据倒叙
                    Tools.ListSorts(fuWuDismountBeans);
                    fuWuView2ListAdapter.notifyDataSetChanged();
//                    fuWuView2ListAdapter = new FuWuView1ListAdapter(getActivity(), fuWuDismountBeans);
//                    fuWuView2ListAdapter.setFuWuListOnClickListener(fuWuListOnClickListener2);
//                    fuwuView2List.setAdapter(fuWuView2ListAdapter);
                    String s2 = String.format(getString(R.string.beauty_within_dismount_txt), fuWuDismountBeans.size());
                    dismountText.setText(s2);

                    loadingDialog.dismiss();
                    break;

                case 1:

                    break;

            }
        }

    };


    FuWuListOnClickListener fuWuListOnClickListener1 = new FuWuListOnClickListener() {
        @Override
        public void onClickUpdate(View v, int pos, FuWuSaleBean fuWuSaleBean) {
            Log.e(TAG, "onClickUpdate: " + pos);
            FLAG_INDEX = 0;
            FLAG_POSTION = pos;
            Intent intent = new Intent(getActivity(), FuWuServerDialogActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(Constons.RESULT_FUWU_SERVER_STR_UPDATE_REQUEST, fuWuSaleBean);
            intent.putExtras(bundle);
            startActivityForResult(intent, Constons.RESULT_FUWU_SERVER_CODE_UPDATE_REQUEST);

        }

        @Override
        public void onClickDismount(View v, int pos, FuWuSaleBean fuWuSaleBean) {
            Log.e(TAG, "onClickDismount: " + pos);
            adapter1Dis(fuWuSaleBean, pos);
        }

        @Override
        public void onClickSale(View v, int pos, FuWuSaleBean fuWuSaleBean) {
            Log.e(TAG, "onClickSale: " + pos);
        }
    };
    FuWuListOnClickListener fuWuListOnClickListener2 = new FuWuListOnClickListener() {
        @Override
        public void onClickUpdate(View v, int pos, FuWuSaleBean fuWuSaleBean) {
            Log.e(TAG, "onClickUpdate: " + pos);
            FLAG_POSTION = pos;
            FLAG_INDEX = 1;
            Intent intent = new Intent(getActivity(), FuWuServerDialogActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(Constons.RESULT_FUWU_SERVER_STR_UPDATE_REQUEST, fuWuSaleBean);
            intent.putExtras(bundle);
            startActivityForResult(intent, Constons.RESULT_FUWU_SERVER_CODE_UPDATE_REQUEST);


        }

        @Override
        public void onClickDismount(View v, int pos, FuWuSaleBean fuWuSaleBean) {
            Log.e(TAG, "onClickDismount: " + pos);


        }

        @Override
        public void onClickSale(View v, int pos, FuWuSaleBean fuWuSaleBean) {
            Log.e(TAG, "onClickSale: " + pos);

            adapter2Sale(fuWuSaleBean, pos);
        }
    };


    //下架
    private void adapter1Dis(FuWuSaleBean fuWuSaleBean, int pos) {
        fuWuSaleBean.setServerSaleFlag(false);
        fuWuSaleBean.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {

                    fuWuView1ListAdapter.deleteView(pos, fuwuView1List);
                    fuWuDismountBeans.add(fuWuSaleBean);

                    Tools.ListSorts(fuWuDismountBeans);
                    fuWuView2ListAdapter.notifyDataSetChanged();
//                    fuWuView2ListAdapter = new FuWuView1ListAdapter(getActivity(), fuWuDismountBeans);
//                    fuWuView2ListAdapter.setFuWuListOnClickListener(fuWuListOnClickListener2);
//                    fuwuView2List.setAdapter(fuWuView2ListAdapter);

                    String s2 = String.format(getString(R.string.beauty_within_dismount_txt), fuWuDismountBeans.size());
                    dismountText.setText(s2);
                    String s1 = String.format(getString(R.string.beauty_within_saleing_txt), fuWuSaleBeans.size());
                    saleText.setText(s1);

                    ToastUtils.showToast(getContext(), "下架成功", true);
                } else {

                    ToastUtils.showToast(getContext(), "下架失败" + e.getMessage().toString(), false);
                }
            }
        });

    }


    //上架
    private void adapter2Sale(FuWuSaleBean fuWuSaleBean, int pos) {
        fuWuSaleBean.setServerSaleFlag(true);
        fuWuSaleBean.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    fuWuView2ListAdapter.deleteView(pos, fuwuView2List);
                    fuWuSaleBeans.add(fuWuSaleBean);

                    Tools.ListSorts(fuWuSaleBeans);

                    fuWuView1ListAdapter.notifyDataSetChanged();
//                    fuWuView1ListAdapter = new FuWuView1ListAdapter(getActivity(), fuWuSaleBeans);
//                    fuWuView1ListAdapter.setFuWuListOnClickListener(fuWuListOnClickListener1);
//                    fuwuView1List.setAdapter(fuWuView1ListAdapter);、
                    String s2 = String.format(getString(R.string.beauty_within_dismount_txt), fuWuDismountBeans.size());
                    dismountText.setText(s2);
                    String s1 = String.format(getString(R.string.beauty_within_saleing_txt), fuWuSaleBeans.size());
                    saleText.setText(s1);

                    ToastUtils.showToast(getContext(), "上架成功", true);
                } else {


                    ToastUtils.showToast(getContext(), "上架失败" + e.getMessage().toString(), false);
                }
            }
        });

    }

    private void setUpdateSaleBean(Intent intent) {

        FuWuSaleBean fuWuSaleBean = (FuWuSaleBean) intent.getSerializableExtra(Constons.RESULT_FUWU_SERVER_STR_UPDATE_REQUEST);
        Log.e(TAG, "setUpdateSaleBean: " + FLAG_POSTION);
        Log.e(TAG, "setUpdateSaleBean: " + FLAG_INDEX);
        Log.e(TAG, "setUpdateSaleBean: " + fuWuSaleBean.toString());


        if (FLAG_INDEX == 0) {
//            fuWuView1ListAdapter.updataView(FLAG_POSTION, fuwuView1List, fuWuSaleBean);

            int firstVisiblePosition = fuwuView1List.getFirstVisiblePosition(); //屏幕内当前可以看见的第一条数据
            if (FLAG_POSTION - firstVisiblePosition >= 0) {
                //1.获取当前点击的条目的view
                View itemView = fuwuView1List.getChildAt(FLAG_POSTION - firstVisiblePosition);
                //2.查找出相应的控件

                TextView serverName = itemView.findViewById(R.id.serverName);
                TextView serverMoney = itemView.findViewById(R.id.server_money);
                TextView applyMd = itemView.findViewById(R.id.apply_md);
                TextView typeText = itemView.findViewById(R.id.type_text);
                TextView addDate = itemView.findViewById(R.id.add_date);
                ImageView ivServer = itemView.findViewById(R.id.iv_server);

                //加载图片
                ImageLoader.displayImageView(getContext(), fuWuSaleBean.getServerUrl(), ivServer);
                serverName.setText(fuWuSaleBean.getServerName() + "");
                serverMoney.setText(fuWuSaleBean.getServerMoney() + "");
                applyMd.setText(fuWuSaleBean.getApplyMd() + "");
                typeText.setText(fuWuSaleBean.getServerType() + "");
                addDate.setText(fuWuSaleBean.getCreatedAt() + "");

            }

        } else {
//            fuWuView2ListAdapter.updataView(FLAG_POSTION, fuwuView2List, fuWuSaleBean);
            int firstVisiblePosition = fuwuView2List.getFirstVisiblePosition(); //屏幕内当前可以看见的第一条数据
            if (FLAG_POSTION - firstVisiblePosition >= 0) {
                //1.获取当前点击的条目的view
                View itemView = fuwuView2List.getChildAt(FLAG_POSTION - firstVisiblePosition);
                //2.查找出相应的控件

                TextView serverName = itemView.findViewById(R.id.serverName);
                TextView serverMoney = itemView.findViewById(R.id.server_money);
                TextView applyMd = itemView.findViewById(R.id.apply_md);
                TextView typeText = itemView.findViewById(R.id.type_text);
                TextView addDate = itemView.findViewById(R.id.add_date);
                ImageView ivServer = itemView.findViewById(R.id.iv_server);

                //加载图片
                ImageLoader.displayImageView(getContext(), fuWuSaleBean.getServerUrl(), ivServer);
                serverName.setText(fuWuSaleBean.getServerName() + "");
                serverMoney.setText(fuWuSaleBean.getServerMoney() + "");
                applyMd.setText(fuWuSaleBean.getApplyMd() + "");
                typeText.setText(fuWuSaleBean.getServerType() + "");
                addDate.setText(fuWuSaleBean.getCreatedAt() + "");

            }
        }

    }


}
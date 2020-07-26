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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.applandeo.materialcalendarview.view.NiceSpinner;
import com.example.smoothcheckbox.SmoothCheckBox;
import com.mnn.mydream.cosmetology.R;
import com.mnn.mydream.cosmetology.activity.CPAddDialogActivity;

import com.mnn.mydream.cosmetology.adapter.fragmentAdapter.MyViewPagerAdapter;
import com.mnn.mydream.cosmetology.bean.spglBean.CPDataBean;

import com.mnn.mydream.cosmetology.dialog.BeautyDeleteDialog;
import com.mnn.mydream.cosmetology.dialog.LoadingDialog;
import com.mnn.mydream.cosmetology.eventBus.EventBusMsg;
import com.mnn.mydream.cosmetology.fragment.beauty.commoditiesFragments.adapter.CPListAdapter;

import com.mnn.mydream.cosmetology.interfaces.SPGLListOnClickListener;
import com.mnn.mydream.cosmetology.utils.Constons;
import com.mnn.mydream.cosmetology.utils.ImageLoader;
import com.mnn.mydream.cosmetology.utils.StringUtils;
import com.mnn.mydream.cosmetology.utils.ToastUtils;

import com.mnn.mydream.cosmetology.view.MyViewPager;
import com.zhy.android.percent.support.PercentLinearLayout;
import com.zhy.android.percent.support.PercentRelativeLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
import cn.bmob.v3.listener.UpdateListener;
import me.yokeyword.fragmentation.SupportFragment;


/**
 * 创建人 :MyDream
 * 创建时间：2020/6/11 18:18
 * 类描述：SJZXFragment 服务界面
 */

public class CPFragment extends SupportFragment {

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
    private List<CPDataBean> cpDismounDataBeans = new ArrayList<>();

    private SmoothCheckBox smoothCheckBox1, smoothCheckBox2;

    private boolean saleFlag;//标记

    private int FLAG_POSTION;

    BeautyDeleteDialog beautyDeleteDialog;

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
        EventBus.getDefault().register(this);

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
                    cpDismounDataBeans.clear();
                    for (int i = 0; i < object.size(); i++) {
                        if (object.get(i).isCpSaleFlag()) {
                            cpSaleDataBeans.add(object.get(i));
                        } else {
                            cpDismounDataBeans.add(object.get(i));
                        }
                    }
                    refreshHandler.sendEmptyMessage(0);

                } else {

                    loadingDialog.dismiss();

                    String s1 = String.format(getString(R.string.beauty_within_saleing_txt), cpSaleDataBeans.size());
                    saleText.setText(s1);
                    String s2 = String.format(getString(R.string.beauty_within_dismount_txt), cpDismounDataBeans.size());
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
//                    cpListAdapter1 = new CPListAdapter(getActivity(), cpSaleDataBeans);
//                    cpListAdapter1.setCpListOnClickListener(spglListOnClickListener1);
//                    cpView1ListView.setAdapter(cpListAdapter1);
                    cpListAdapter1.notifyDataSetChanged();
                    String s1 = String.format(getString(R.string.beauty_within_saleing_txt), cpSaleDataBeans.size());
                    saleText.setText(s1);

                    //设置数据倒叙
                    setSortList(cpDismounDataBeans);
                    cpListAdapter2.notifyDataSetChanged();
//                    fuWuView2ListAdapter = new FuWuView1ListAdapter(getActivity(), fuWuDismountBeans);
//                    fuWuView2ListAdapter.setFuWuListOnClickListener(spglListOnClickListener2);
//                    fuwuView2List.setAdapter(fuWuView2ListAdapter);
                    String s2 = String.format(getString(R.string.beauty_within_dismount_txt), cpDismounDataBeans.size());
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
        cpListAdapter1.setCpListOnClickListener(spglListOnClickListener1);
        cpView1ListView.setAdapter(cpListAdapter1);
    }


    private void dismountViewFindViewInit(View dismountView) {
        smoothCheckBox2 = saleView.findViewById(R.id.check_box1);
        cpView2ListView = dismountView.findViewById(R.id.cp_listview);

        cpListAdapter2 = new CPListAdapter(getContext(), cpDismounDataBeans);
        cpListAdapter2.setCpListOnClickListener(spglListOnClickListener2);
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
                getQueryList();
                break;
            case R.id.remake_layout:
                getRemakeLayoutCP();
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e(TAG, "onActivityResult: " + requestCode);
        Log.e(TAG, "onActivityResult: " + resultCode);
        //界面刷新

        if (resultCode == Constons.RESULT_CP_CODE_SCUESS_REQUEST) {
            getSelectCpAll();

        }
        if (resultCode == Constons.RESULT_CP_CODE_UPDATE_REQUEST) {
//            getSelectServerAll();
            setUpdateSaleBean(data);
        }

    }

    //倒叙
    public void ListSorts(List<CPDataBean> list) {
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

    //    重制
    private void getRemakeLayoutCP() {
        cpTypeSpinner.setSelectedIndex(0);
        cpNameEdit.setText("");
        getSelectCpAll();
    }

    SPGLListOnClickListener spglListOnClickListener1 = new SPGLListOnClickListener() {
        @Override
        public void onClickUpdate(View v, int pos, Object object) {
            Log.e(TAG, "onClickUpdate: " + pos);
            FLAG_POSTION = pos;
            saleFlag = true;
            Intent intent = new Intent(getActivity(), CPAddDialogActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(Constons.RESULT_UPDATE_REQUEST, (CPDataBean) object);
            intent.putExtras(bundle);
            startActivityForResult(intent, Constons.RESULT_CP_CODE_UPDATE_REQUEST);

        }

        @Override
        public void onClickDismount(View v, int pos, Object object) {
            adapter1Dis((CPDataBean) object, pos);
        }

        @Override
        public void onClickSale(View v, int pos, Object object) {

        }

        @Override
        public void onClickDelete(View v, int pos, Object object) {
            deleteCPDialog((CPDataBean) object, pos, true);
        }
    };
    SPGLListOnClickListener spglListOnClickListener2 = new SPGLListOnClickListener() {
        @Override
        public void onClickUpdate(View v, int pos, Object object) {
            Log.e(TAG, "onClickUpdate: " + pos);
            FLAG_POSTION = pos;
            saleFlag = false;
            Intent intent = new Intent(getActivity(), CPAddDialogActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(Constons.RESULT_UPDATE_REQUEST, (CPDataBean) object);
            intent.putExtras(bundle);
            startActivityForResult(intent, Constons.RESULT_CP_CODE_UPDATE_REQUEST);

        }

        @Override
        public void onClickDismount(View v, int pos, Object object) {

        }

        @Override
        public void onClickSale(View v, int pos, Object object) {
            adapter2Sale((CPDataBean) object, pos);
        }

        @Override
        public void onClickDelete(View v, int pos, Object object) {
            deleteCPDialog((CPDataBean) object, pos, false);
        }
    };

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
        startActivityForResult(Intent, Constons.RESULT_CP_CODE_VIEW_REQUEST);
    }

    //下架
    private void adapter1Dis(CPDataBean cpDataBean, int pos) {
        cpDataBean.setCpSaleFlag(false);
        cpDataBean.update( new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    cpListAdapter1.deleteView(pos, cpView1ListView);
                    cpDismounDataBeans.add(cpDataBean);
                    ListSorts(cpDismounDataBeans);

                    cpListAdapter2.notifyDataSetChanged();
//                    fuWuView2ListAdapter = new FuWuView1ListAdapter(getActivity(), fuWuDismountBeans);
//                    fuWuView2ListAdapter.setFuWuListOnClickListener(fuWuListOnClickListener2);
//                    fuwuView2List.setAdapter(fuWuView2ListAdapter);
                    String s2 = String.format(getString(R.string.beauty_within_dismount_txt), cpDismounDataBeans.size());
                    dismountText.setText(s2);
                    String s1 = String.format(getString(R.string.beauty_within_saleing_txt), cpSaleDataBeans.size());
                    saleText.setText(s1);

                    ToastUtils.showToast(getContext(), "下架成功", true);
                } else {
                    ToastUtils.showToast(getContext(), "下架失败" + e.getMessage().toString(), false);
                }
            }
        });

    }

    //上架
    private void adapter2Sale(CPDataBean cpDataBean, int pos) {
        cpDataBean.setCpSaleFlag(true);
        cpDataBean.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    cpListAdapter2.deleteView(pos, cpView2ListView);
                    cpSaleDataBeans.add(cpDataBean);

                    ListSorts(cpSaleDataBeans);

                    cpListAdapter1.notifyDataSetChanged();
//                    fuWuView1ListAdapter = new FuWuView1ListAdapter(getActivity(), fuWuSaleBeans);
//                    fuWuView1ListAdapter.setFuWuListOnClickListener(fuWuListOnClickListener1);
//                    fuwuView1List.setAdapter(fuWuView1ListAdapter);、
                    String s2 = String.format(getString(R.string.beauty_within_dismount_txt), cpDismounDataBeans.size());
                    dismountText.setText(s2);
                    String s1 = String.format(getString(R.string.beauty_within_saleing_txt), cpSaleDataBeans.size());
                    saleText.setText(s1);

                    ToastUtils.showToast(getContext(), "上架成功", true);
                } else {
                    ToastUtils.showToast(getContext(), "上架失败" + e.getMessage().toString(), false);
                }
            }
        });

    }

    //修改单条item
    private void setUpdateSaleBean(Intent intent) {

        CPDataBean cpDataBean = (CPDataBean) intent.getSerializableExtra(Constons.RESULT_UPDATE_REQUEST);

        Log.e(TAG, "setUpdateSaleBean: " + FLAG_POSTION);
        Log.e(TAG, "setUpdateSaleBean: " + saleFlag);
        Log.e(TAG, "setUpdateSaleBean: " + cpDataBean.toString());
        int firstVisiblePosition;
        View itemView = null;
        if (saleFlag) {
//            fuWuView1ListAdapter.updataView(FLAG_POSTION, fuwuView1List, fuWuSaleBean);
            firstVisiblePosition = cpView1ListView.getFirstVisiblePosition();
            if (FLAG_POSTION - firstVisiblePosition >= 0) {
                itemView = cpView1ListView.getChildAt(FLAG_POSTION - firstVisiblePosition);
            }

        } else {
//            fuWuView2ListAdapter.updataView(FLAG_POSTION, fuwuView2List, fuWuSaleBean);
            firstVisiblePosition = cpView2ListView.getFirstVisiblePosition();
            if (FLAG_POSTION - firstVisiblePosition >= 0) {
                itemView = cpView2ListView.getChildAt(FLAG_POSTION - firstVisiblePosition);

            }
        }
        if (itemView != null) {

            ImageView ivServer = itemView.findViewById(R.id.iv_server);
            TextView serverName = itemView.findViewById(R.id.serverName);
            TextView serverMoney = itemView.findViewById(R.id.server_money);
            TextView typeText = itemView.findViewById(R.id.type_text);
            TextView applyMd = itemView.findViewById(R.id.apply_md);
            TextView applySpecifications = itemView.findViewById(R.id.apply_specifications);
            TextView addDate = itemView.findViewById(R.id.add_date);

            ImageLoader.displayImageView(getContext(), cpDataBean.getCpUrl(), ivServer, R.mipmap.ic_img_default);
            serverName.setText(cpDataBean.getCpName() + "");
            serverMoney.setText(cpDataBean.getCpMoney() + "");
            applyMd.setText(cpDataBean.getApplyMd() + "");
            typeText.setText(cpDataBean.getCpType() + "");
            addDate.setText(cpDataBean.getCreatedAt() + "");
            applySpecifications.setText(cpDataBean.getIntSpecifications() + "支");

        }

    }

    private void deleteCPDialog(CPDataBean cpDataBean, int pos, boolean flag) {
        BeautyDeleteDialog.Builder beautyAddServerTypeBuilder = new BeautyDeleteDialog.Builder(getActivity())
                .setTitleMsg("确定删除(" + cpDataBean.getCpName() + ")的信息？")
                .setYesOnClick(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (flag) {
                            cpListAdapter1.deleteView(pos, cpView1ListView);
                        } else {
                            cpListAdapter2.deleteView(pos, cpView2ListView);
                        }
//
                        cpDataBean.delete(new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    String s2 = String.format(getString(R.string.beauty_within_dismount_txt), cpSaleDataBeans.size());
                                    dismountText.setText(s2);
                                    String s1 = String.format(getString(R.string.beauty_within_saleing_txt), cpDismounDataBeans.size());
                                    saleText.setText(s1);

                                    ToastUtils.showToast(getContext(), "删除成功", true);
                                } else {

                                    ToastUtils.showToast(getContext(), "删除失败" + e.getMessage().toString(), false);
                                }
                            }
                        });
                        beautyDeleteDialog.dismiss();
                    }
                });
        beautyDeleteDialog = beautyAddServerTypeBuilder.createDialog();
        // 设置点击屏幕Dialog不消失
        beautyDeleteDialog.show();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMain(EventBusMsg event) {
        int flag = event.getMsgInt();
        switch (flag) {
            case Constons.POST_DELETE_SUCCESS:
                Log.e(TAG, "onEventMain: ");
                String s2 = String.format(getString(R.string.beauty_within_dismount_txt), cpDismounDataBeans.size());
                dismountText.setText(s2);
                String s1 = String.format(getString(R.string.beauty_within_saleing_txt), cpSaleDataBeans.size());
                saleText.setText(s1);

                ToastUtils.showToast(getContext(), "删除成功", true);

                break;


            case Constons .POST_UPDATE_SUCCESS:

                ToastUtils.showToast(getContext(), "修改成功", true);


                break;




        }
    }
    private void getQueryList() {
        String serverName = cpNameEdit.getText().toString();

        String typeString = cpTypeSpinner.getSelectedItem().toString();

        LoadingDialog.Builder addSignDialogBuild = new LoadingDialog.Builder(getActivity());
        loadingDialog = addSignDialogBuild.createDialog();
        loadingDialog.setCanceledOnTouchOutside(false);
        // 设置点击屏幕Dialog不消失
        loadingDialog.show();


        BmobQuery<CPDataBean> categoryBmobQuery = new BmobQuery<>();

        if (StringUtils.isEmpty(serverName)) {
            if (!typeString.equals("全部")) {
                BmobQuery<CPDataBean> eq1 = new BmobQuery<CPDataBean>();
                eq1.addWhereEqualTo("serverType", typeString);
                List<BmobQuery<CPDataBean>> queries = new ArrayList<BmobQuery<CPDataBean>>();
                queries.add(eq1);
                categoryBmobQuery.and(queries);
            }

        } else {
            if (typeString.equals("全部")) {
                BmobQuery<CPDataBean> eq2 = new BmobQuery<CPDataBean>();
                eq2.addWhereEqualTo("serverName", serverName);
                List<BmobQuery<CPDataBean>> queries = new ArrayList<BmobQuery<CPDataBean>>();
                queries.add(eq2);
                categoryBmobQuery.and(queries);
            } else {
                BmobQuery<CPDataBean> eq1 = new BmobQuery<CPDataBean>();
                eq1.addWhereEqualTo("serverType", typeString);
                BmobQuery<CPDataBean> eq2 = new BmobQuery<CPDataBean>();
                eq2.addWhereEqualTo("serverName", serverName);
                List<BmobQuery<CPDataBean>> queries = new ArrayList<BmobQuery<CPDataBean>>();
                queries.add(eq1);
                queries.add(eq2);
                categoryBmobQuery.and(queries);
            }
        }
        categoryBmobQuery.findObjects(new FindListener<CPDataBean>() {
            @Override
            public void done(List<CPDataBean> object, BmobException e) {
                if (e == null) {
                    cpSaleDataBeans.clear();
                    cpDismounDataBeans.clear();
                    for (int i = 0; i < object.size(); i++) {
                        if (object.get(i).isCpSaleFlag()) {
                            cpSaleDataBeans.add(object.get(i));
                        } else {
                            cpDismounDataBeans.add(object.get(i));
                        }
                    }
                    refreshHandler.sendEmptyMessage(0);
                } else {

                    loadingDialog.dismiss();
                    String s1 = String.format(getString(R.string.beauty_within_saleing_txt), cpSaleDataBeans.size());
                    saleText.setText(s1);
                    String s2 = String.format(getString(R.string.beauty_within_dismount_txt), cpDismounDataBeans.size());
                    dismountText.setText(s2);
                    Log.e("BMOB", e.toString());
                    ToastUtils.showToast(getContext(), "查询失败", false);
                }
            }
        });

    }
}
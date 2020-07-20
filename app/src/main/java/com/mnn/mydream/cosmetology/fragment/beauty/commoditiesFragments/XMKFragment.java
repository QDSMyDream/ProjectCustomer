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
import com.mnn.mydream.cosmetology.activity.XMKDialogActivity;
import com.mnn.mydream.cosmetology.adapter.fragmentAdapter.MyViewPagerAdapter;
import com.mnn.mydream.cosmetology.bean.fuwuBean.CPDataBean;
import com.mnn.mydream.cosmetology.bean.fuwuBean.FuWuSaleBean;
import com.mnn.mydream.cosmetology.bean.fuwuBean.XMKDataBean;
import com.mnn.mydream.cosmetology.dialog.BeautyDeleteDialog;
import com.mnn.mydream.cosmetology.dialog.LoadingDialog;
import com.mnn.mydream.cosmetology.fragment.beauty.commoditiesFragments.adapter.CPListAdapter;
import com.mnn.mydream.cosmetology.fragment.beauty.commoditiesFragments.adapter.XmkListAdapter;
import com.mnn.mydream.cosmetology.interfaces.SPGLListOnClickListener;
import com.mnn.mydream.cosmetology.utils.Constons;
import com.mnn.mydream.cosmetology.utils.StringUtils;
import com.mnn.mydream.cosmetology.utils.ToastUtils;
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
import cn.bmob.v3.listener.UpdateListener;
import me.yokeyword.fragmentation.SupportFragment;


/**
 * 创建人 :MyDream
 * 创建时间：2020/6/11 18:18
 * 类描述：XMKFragment 项目卡界面
 */

public class XMKFragment extends SupportFragment {
    private String TAG = XMKFragment.class.getSimpleName();
    @BindView(R.id.xmk_name_edit)
    AppCompatEditText xmkNameEdit;
    @BindView(R.id.xmk_type_spinner)
    NiceSpinner xmkTypeSpinner;
    @BindView(R.id.xmk_server_btn)
    TextView xmkServerBtn;
    @BindView(R.id.remake)
    TextView remake;
    @BindView(R.id.remake_layout)
    PercentRelativeLayout remakeLayout;
    @BindView(R.id.sale_text)
    TextView saleText;
    @BindView(R.id.dismount_text)
    TextView dismountText;
    @BindView(R.id.add_xmk_layout)
    PercentLinearLayout addXmkLayout;
    @BindView(R.id.viewpagers)
    MyViewPager viewpagers;
    Unbinder unbinder;
    private View view;

    private LayoutInflater mInflater;
    private LoadingDialog loadingDialog;

    //页卡视图集合
    private List<View> mViewList = new ArrayList<>();

    private View saleView, dismountView;

    private ListView xmkView1ListView, xmkView2ListView;

    private List<String> titleList = new ArrayList<>();
    /**
     * view1 上架项目
     */
    private XmkListAdapter xmkListAdapter1;
    private List<XMKDataBean> xmkSaleDataBeans = new ArrayList<>();

    private XmkListAdapter xmkListAdapter2;
    private List<XMKDataBean> xmkDismounDataBeans = new ArrayList<>();

    private SmoothCheckBox smoothCheckBox1, smoothCheckBox2;

    BeautyDeleteDialog beautyDeleteDialog;

    private boolean saleFlag;//标记

    private int FLAG_POSTION;

    public static XMKFragment newInstance() {
        Bundle args = new Bundle();
        XMKFragment fragment = new XMKFragment();
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.xmk_fragment, container, false);

        unbinder = ButterKnife.bind(this, view);
        initview();
        return view;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.xmk_server_btn, R.id.remake_layout, R.id.sale_text, R.id.dismount_text, R.id.add_xmk_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.xmk_server_btn:
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
            case R.id.add_xmk_layout:
                setXmkDialog();
                break;
        }
    }

    //切换viewpager
    private void checkViePager(int postion) {

        switch (postion) {
            case 0:
                saleText.setBackgroundResource(R.drawable.beauty_within_text_frame_option);
                dismountText.setBackgroundResource(R.drawable.beauty_within_text_frame_no_option);
                addXmkLayout.setVisibility(View.VISIBLE);
                break;
            case 1:
                saleText.setBackgroundResource(R.drawable.beauty_within_text_frame_no_option);
                dismountText.setBackgroundResource(R.drawable.beauty_within_text_frame_option);
                addXmkLayout.setVisibility(View.GONE);
                break;
        }

        viewpagers.setCurrentItem(postion);

    }

    private void setXmkDialog() {

        Intent Intent = new Intent(getActivity(), XMKDialogActivity.class);
        startActivityForResult(Intent, Constons.RESULT_XMK_CODE_VIEW_REQUEST);

    }


    private void initview() {

        xmkTypeSpinner.attachDataSource(Constons.SelectServerTypeString);

        mInflater = LayoutInflater.from(getActivity());
        saleView = mInflater.inflate(R.layout.xmk_view1_layout, null);
        xmkFindViewInit(saleView);
        dismountView = mInflater.inflate(R.layout.xmk_view2_layout, null);
        dismountViewFindViewInit(dismountView);

        mViewList.add(saleView);
        mViewList.add(dismountView);

        MyViewPagerAdapter myViewPagerAdapter = new MyViewPagerAdapter(mViewList, titleList);
        //给ViewPager设置适配器
        viewpagers.setAdapter(myViewPagerAdapter);
        viewpagers.setCurrentItem(0);

        getSelectXmkAll();

    }


    private void dismountViewFindViewInit(View dismountView) {
        smoothCheckBox2 = dismountView.findViewById(R.id.check_box1);
        xmkView2ListView = dismountView.findViewById(R.id.xmk_listview);

        xmkListAdapter2 = new XmkListAdapter(getContext(), xmkDismounDataBeans);
        xmkListAdapter2.setCpListOnClickListener(spglListOnClickListener2);
        xmkView2ListView.setAdapter(xmkListAdapter2);

    }

    private void xmkFindViewInit(View saleView) {
        smoothCheckBox1 = saleView.findViewById(R.id.check_box1);
        xmkView1ListView = saleView.findViewById(R.id.xmk_listview);

        xmkListAdapter1 = new XmkListAdapter(getContext(), xmkSaleDataBeans);
        xmkListAdapter1.setCpListOnClickListener(spglListOnClickListener1);
        xmkView1ListView.setAdapter(xmkListAdapter1);

    }

    SPGLListOnClickListener spglListOnClickListener1 = new SPGLListOnClickListener() {
        @Override
        public void onClickUpdate(View v, int pos, Object object) {
            Log.e(TAG, "onClickUpdate: " + pos);
            FLAG_POSTION = pos;
            saleFlag = true;
            Intent intent = new Intent(getActivity(), XMKDialogActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(Constons.RESULT_UPDATE_REQUEST, (XMKDataBean) object);
            intent.putExtras(bundle);
            startActivityForResult(intent, Constons.RESULT_XMK_CODE_UPDATE_REQUEST);

        }

        @Override
        public void onClickDismount(View v, int pos, Object object) {
            adapter1Dis((XMKDataBean) object, pos);
        }

        @Override
        public void onClickSale(View v, int pos, Object object) {

        }

        @Override
        public void onClickDelete(View v, int pos, Object object) {
            deleteCPDialog((XMKDataBean) object, pos, true);
        }
    };

    private void deleteCPDialog(XMKDataBean xmkDataBean, int pos, boolean flag) {
        BeautyDeleteDialog.Builder beautyAddServerTypeBuilder = new BeautyDeleteDialog.Builder(getActivity())
                .setTitleMsg("确定删除(" + xmkDataBean.getXmkName() + ")的信息？")
                .setYesOnClick(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (flag) {
                            xmkListAdapter1.deleteView(pos, xmkView1ListView);
                        } else {
                            xmkListAdapter2.deleteView(pos, xmkView2ListView);
                        }

                        xmkDataBean.delete(new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    String s2 = String.format(getString(R.string.beauty_within_dismount_txt), xmkDismounDataBeans.size());
                                    dismountText.setText(s2);
                                    String s1 = String.format(getString(R.string.beauty_within_saleing_txt), xmkSaleDataBeans.size());
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

    private void adapter1Dis(XMKDataBean xmkDataBean, int pos) {
        xmkDataBean.setXmlSaleFlag(false);
        xmkDataBean.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    xmkListAdapter1.deleteView(pos, xmkView1ListView);
                    xmkDismounDataBeans.add(xmkDataBean);
                    setSortList(xmkDismounDataBeans);

                    xmkListAdapter2.notifyDataSetChanged();
//                    fuWuView2ListAdapter = new FuWuView1ListAdapter(getActivity(), fuWuDismountBeans);
//                    fuWuView2ListAdapter.setFuWuListOnClickListener(fuWuListOnClickListener2);
//                    fuwuView2List.setAdapter(fuWuView2ListAdapter);
                    String s2 = String.format(getString(R.string.beauty_within_dismount_txt), xmkDismounDataBeans.size());
                    dismountText.setText(s2);
                    String s1 = String.format(getString(R.string.beauty_within_saleing_txt), xmkSaleDataBeans.size());
                    saleText.setText(s1);

                    ToastUtils.showToast(getContext(), "下架成功", true);
                } else {
                    ToastUtils.showToast(getContext(), "下架失败" + e.getMessage().toString(), false);
                }
            }
        });


    }

    SPGLListOnClickListener spglListOnClickListener2 = new SPGLListOnClickListener() {
        @Override
        public void onClickUpdate(View v, int pos, Object object) {
            Log.e(TAG, "onClickUpdate: " + pos);
            FLAG_POSTION = pos;
            saleFlag = false;
            Intent intent = new Intent(getActivity(), XMKDialogActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(Constons.RESULT_UPDATE_REQUEST, (XMKDataBean) object);
            intent.putExtras(bundle);
            startActivityForResult(intent, Constons.RESULT_XMK_CODE_UPDATE_REQUEST);


        }

        @Override
        public void onClickDismount(View v, int pos, Object object) {

        }

        @Override
        public void onClickSale(View v, int pos, Object object) {
            adapter2Sale((XMKDataBean) object, pos);
        }

        @Override
        public void onClickDelete(View v, int pos, Object object) {
            deleteCPDialog((XMKDataBean) object, pos, false);
        }
    };

    private void adapter2Sale(XMKDataBean xmkDataBean, int pos) {
        xmkDataBean.setXmlSaleFlag(true);
        xmkDataBean.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    xmkListAdapter2.deleteView(pos, xmkView2ListView);
                    xmkSaleDataBeans.add(xmkDataBean);

                    setSortList(xmkSaleDataBeans);

                    xmkListAdapter1.notifyDataSetChanged();
//                    fuWuView1ListAdapter = new FuWuView1ListAdapter(getActivity(), fuWuSaleBeans);
//                    fuWuView1ListAdapter.setFuWuListOnClickListener(fuWuListOnClickListener1);
//                    fuwuView1List.setAdapter(fuWuView1ListAdapter);、
                    String s2 = String.format(getString(R.string.beauty_within_dismount_txt), xmkDismounDataBeans.size());
                    dismountText.setText(s2);
                    String s1 = String.format(getString(R.string.beauty_within_saleing_txt), xmkSaleDataBeans.size());
                    saleText.setText(s1);

                    ToastUtils.showToast(getContext(), "上架成功", true);
                } else {
                    ToastUtils.showToast(getContext(), "上架失败" + e.getMessage().toString(), false);
                }
            }
        });


    }


    private void getSelectXmkAll() {


        LoadingDialog.Builder addSignDialogBuild = new LoadingDialog.Builder(getActivity());
        loadingDialog = addSignDialogBuild.createDialog();
        loadingDialog.setCanceledOnTouchOutside(false);
        // 设置点击屏幕Dialog不消失
        loadingDialog.show();

        BmobQuery<XMKDataBean> categoryBmobQuery = new BmobQuery<>();

        categoryBmobQuery.findObjects(new FindListener<XMKDataBean>() {
            @Override
            public void done(List<XMKDataBean> object, BmobException e) {
                if (e == null) {

                    xmkSaleDataBeans.clear();
                    xmkDismounDataBeans.clear();

                    for (int i = 0; i < object.size(); i++) {
                        if (object.get(i).isXmlSaleFlag()) {
                            xmkSaleDataBeans.add(object.get(i));
                        } else {
                            xmkDismounDataBeans.add(object.get(i));
                        }
                    }
                    refreshHandler.sendEmptyMessage(0);

                } else {

                    loadingDialog.dismiss();
                    String s1 = String.format(getString(R.string.beauty_within_saleing_txt), xmkSaleDataBeans.size());
                    saleText.setText(s1);
                    String s2 = String.format(getString(R.string.beauty_within_dismount_txt), xmkDismounDataBeans.size());
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

                    Log.e(TAG, "handleMessage: " + xmkSaleDataBeans.size());
                    //设置数据倒叙
                    setSortList(xmkSaleDataBeans);
//                    cpListAdapter1 = new CPListAdapter(getActivity(), cpSaleDataBeans);
//                    cpListAdapter1.setCpListOnClickListener(spglListOnClickListener1);
//                    cpView1ListView.setAdapter(cpListAdapter1);
                    xmkListAdapter1.notifyDataSetChanged();
                    String s1 = String.format(getString(R.string.beauty_within_saleing_txt), xmkSaleDataBeans.size());
                    saleText.setText(s1);

                    //设置数据倒叙
                    setSortList(xmkDismounDataBeans);
                    xmkListAdapter2.notifyDataSetChanged();
//                    fuWuView2ListAdapter = new FuWuView1ListAdapter(getActivity(), fuWuDismountBeans);
//                    fuWuView2ListAdapter.setFuWuListOnClickListener(spglListOnClickListener2);
//                    fuwuView2List.setAdapter(fuWuView2ListAdapter);
                    String s2 = String.format(getString(R.string.beauty_within_dismount_txt), xmkDismounDataBeans.size());
                    dismountText.setText(s2);

                    loadingDialog.dismiss();
                    break;

                case 1:

                    break;

            }
        }

    };

    //倒叙
    public void setSortList(List<XMKDataBean> list) {
        Collections.sort(list, new Comparator<XMKDataBean>() {
            @Override
            public int compare(XMKDataBean o1, XMKDataBean o2) {
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

        if (resultCode == Constons.RESULT_XMK_CODE_SCUESS_REQUEST) {
            getSelectXmkAll();
        }

    }

    //    重制
    private void getRemakeLayoutCP() {
        xmkTypeSpinner.setSelectedIndex(0);
        xmkNameEdit.setText("");
        getSelectXmkAll();
    }

    private void getQueryList() {
        String serverName = xmkNameEdit.getText().toString();

        String typeString = xmkTypeSpinner.getSelectedItem().toString();

        LoadingDialog.Builder addSignDialogBuild = new LoadingDialog.Builder(getActivity());
        loadingDialog = addSignDialogBuild.createDialog();
        loadingDialog.setCanceledOnTouchOutside(false);
        // 设置点击屏幕Dialog不消失
        loadingDialog.show();


        BmobQuery<XMKDataBean> categoryBmobQuery = new BmobQuery<>();

        if (StringUtils.isEmpty(serverName)) {
            if (!typeString.equals("全部")) {
                BmobQuery<XMKDataBean> eq1 = new BmobQuery<XMKDataBean>();
                eq1.addWhereEqualTo("serverType", typeString);
                List<BmobQuery<XMKDataBean>> queries = new ArrayList<BmobQuery<XMKDataBean>>();
                queries.add(eq1);
                categoryBmobQuery.and(queries);
            }

        } else {
            if (typeString.equals("全部")) {
                BmobQuery<XMKDataBean> eq2 = new BmobQuery<XMKDataBean>();
                eq2.addWhereEqualTo("serverName", serverName);
                List<BmobQuery<XMKDataBean>> queries = new ArrayList<BmobQuery<XMKDataBean>>();
                queries.add(eq2);
                categoryBmobQuery.and(queries);
            } else {
                BmobQuery<XMKDataBean> eq1 = new BmobQuery<XMKDataBean>();
                eq1.addWhereEqualTo("serverType", typeString);
                BmobQuery<XMKDataBean> eq2 = new BmobQuery<XMKDataBean>();
                eq2.addWhereEqualTo("serverName", serverName);
                List<BmobQuery<XMKDataBean>> queries = new ArrayList<BmobQuery<XMKDataBean>>();
                queries.add(eq1);
                queries.add(eq2);
                categoryBmobQuery.and(queries);
            }
        }
        categoryBmobQuery.findObjects(new FindListener<XMKDataBean>() {
            @Override
            public void done(List<XMKDataBean> object, BmobException e) {
                if (e == null) {
                    xmkSaleDataBeans.clear();
                    xmkDismounDataBeans.clear();
                    for (int i = 0; i < object.size(); i++) {
                        if (object.get(i).isXmlSaleFlag()) {
                            xmkSaleDataBeans.add(object.get(i));
                        } else {
                            xmkDismounDataBeans.add(object.get(i));
                        }
                    }
                    refreshHandler.sendEmptyMessage(0);
                } else {

                    loadingDialog.dismiss();
                    String s1 = String.format(getString(R.string.beauty_within_saleing_txt), xmkSaleDataBeans.size());
                    saleText.setText(s1);
                    String s2 = String.format(getString(R.string.beauty_within_dismount_txt), xmkDismounDataBeans.size());
                    dismountText.setText(s2);
                    Log.e("BMOB", e.toString());
                    ToastUtils.showToast(getContext(), "查询失败", false);
                }
            }
        });

    }

}
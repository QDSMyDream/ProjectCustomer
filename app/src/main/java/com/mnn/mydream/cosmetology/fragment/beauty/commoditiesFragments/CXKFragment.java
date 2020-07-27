package com.mnn.mydream.cosmetology.fragment.beauty.commoditiesFragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.smoothcheckbox.SmoothCheckBox;
import com.mnn.mydream.cosmetology.R;
import com.mnn.mydream.cosmetology.activity.CXKDialogActivity;
import com.mnn.mydream.cosmetology.activity.XMKDialogActivity;
import com.mnn.mydream.cosmetology.bean.spglBean.CXKDataBean;
import com.mnn.mydream.cosmetology.bean.spglBean.XMKDataBean;
import com.mnn.mydream.cosmetology.dialog.BeautyDeleteDialog;
import com.mnn.mydream.cosmetology.dialog.LoadingDialog;
import com.mnn.mydream.cosmetology.fragment.beauty.commoditiesFragments.adapter.CxkListAdapter;
import com.mnn.mydream.cosmetology.interfaces.SPGLListOnClickListener;
import com.mnn.mydream.cosmetology.utils.Constons;
import com.mnn.mydream.cosmetology.utils.ToastUtils;
import com.zhy.android.percent.support.PercentLinearLayout;

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

public class CXKFragment extends SupportFragment {
    private String TAG = "CXKFragment";

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
    @BindView(R.id.tip_text)
    TextView tipText;
    private View view;

    BeautyDeleteDialog beautyDeleteDialog;

    CxkListAdapter cxkListAdapter;

    private List<CXKDataBean> cxkDataBeans = new ArrayList<>();

    private LoadingDialog loadingDialog;


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

        cxkListAdapter = new CxkListAdapter(getContext(), cxkDataBeans);
        cxkListAdapter.setCpListOnClickListener(spglListOnClickListener1);
        cxkListview.setAdapter(cxkListAdapter);

        getSelectCxkAll();

    }

    SPGLListOnClickListener spglListOnClickListener1 = new SPGLListOnClickListener() {
        @Override
        public void onClickUpdate(View v, int pos, Object object) {
            Log.e(TAG, "onClickUpdate: " + pos);
            Intent intent = new Intent(getActivity(), CXKDialogActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(Constons.RESULT_UPDATE_REQUEST, (CXKDataBean) object);
            intent.putExtras(bundle);
            startActivityForResult(intent, Constons.RESULT_CXK_CODE_UPDATE_REQUEST);

        }

        @Override
        public void onClickDismount(View v, int pos, Object object) {

        }

        @Override
        public void onClickSale(View v, int pos, Object object) {

        }

        @Override
        public void onClickDelete(View v, int pos, Object object) {
            deleteCPDialog((CXKDataBean) object, pos, true);
        }
    };

    private void deleteCPDialog(CXKDataBean cxkDataBean, int pos, boolean flag) {
        BeautyDeleteDialog.Builder beautyAddServerTypeBuilder = new BeautyDeleteDialog.Builder(getActivity())
                .setTitleMsg("确定删除(" + cxkDataBean.getCxkName() + ")的信息？")
                .setYesOnClick(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        cxkListAdapter.deleteView(pos, cxkListview);

                        cxkDataBean.delete(new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    String s1 = String.format(getString(R.string.beauty_add_service_select_cxk), cxkDataBeans.size());
                                    tipText.setText(s1);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e(TAG, "onActivityResult: " + requestCode);
        Log.e(TAG, "onActivityResult: " + resultCode);
        //界面刷新

        if (resultCode == Constons.RESULT_CXK_CODE_SCUESS_REQUEST) {
            getSelectCxkAll();
        }

    }

    private void getSelectCxkAll() {


        LoadingDialog.Builder addSignDialogBuild = new LoadingDialog.Builder(getActivity());
        loadingDialog = addSignDialogBuild.createDialog();
        loadingDialog.setCanceledOnTouchOutside(false);
        // 设置点击屏幕Dialog不消失
        loadingDialog.show();

        BmobQuery<CXKDataBean> categoryBmobQuery = new BmobQuery<>();

        categoryBmobQuery.findObjects(new FindListener<CXKDataBean>() {
            @Override
            public void done(List<CXKDataBean> object, BmobException e) {
                if (e == null) {
                    cxkDataBeans.clear();
                    cxkDataBeans.addAll(object);
                    refreshHandler.sendEmptyMessage(0);

                } else {
                    loadingDialog.dismiss();
                    String s2 = String.format(getString(R.string.beauty_add_service_select_cxk), cxkDataBeans.size());
                    tipText.setText(s2);
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
                    Log.e(TAG, "handleMessage: " + cxkDataBeans.size());
                    //设置数据倒叙
                    setSortList(cxkDataBeans);
                    cxkListAdapter.notifyDataSetChanged();
                    String s1 = String.format(getString(R.string.beauty_add_service_select_cxk), cxkDataBeans.size());
                    tipText.setText(s1);
                    loadingDialog.dismiss();
                    break;
                case 1:

                    break;

            }
        }

    };

    private void setSortList(List<CXKDataBean> cxkDataBeans) {

        Collections.sort(cxkDataBeans, new Comparator<CXKDataBean>() {
            @Override
            public int compare(CXKDataBean o1, CXKDataBean o2) {
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
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.add_cxk_layout)
    public void onViewClicked() {
        Intent Intent = new Intent(getActivity(), CXKDialogActivity.class);
        startActivityForResult(Intent, Constons.RESULT_CXK_CODE_VIEW_REQUEST);
    }
}
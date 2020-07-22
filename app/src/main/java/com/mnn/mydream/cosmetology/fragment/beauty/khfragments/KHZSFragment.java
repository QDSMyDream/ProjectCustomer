package com.mnn.mydream.cosmetology.fragment.beauty.khfragments;

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

import com.mnn.mydream.cosmetology.R;
import com.mnn.mydream.cosmetology.activity.BeautyKHInfoActivity;
import com.mnn.mydream.cosmetology.bean.khBean.BeautyBeanKh;
import com.mnn.mydream.cosmetology.dialog.BeautyDeleteDialog;
import com.mnn.mydream.cosmetology.dialog.LoadingDialog;
import com.mnn.mydream.cosmetology.eventBus.EventBusAddKhMsg;
import com.mnn.mydream.cosmetology.eventBus.EventBusMsg;
import com.mnn.mydream.cosmetology.fragment.beauty.khfragments.adapter.KHZSListAdapter;
import com.mnn.mydream.cosmetology.interfaces.SetListOnClickListener;
import com.mnn.mydream.cosmetology.utils.Constons;
import com.mnn.mydream.cosmetology.utils.ToastUtils;
import com.mnn.mydream.cosmetology.utils.Tools;
import com.zhy.android.percent.support.PercentLinearLayout;
import com.zhy.android.percent.support.PercentRelativeLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
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
 * 类描述：KHZSFragment 客户总数
 */

public class KHZSFragment extends SupportFragment implements SetListOnClickListener {

    private String TAG = "KHZSFragment";

    @BindView(R.id.search_btn)
    TextView searchBtn;

    @BindView(R.id.remake_layout)
    PercentRelativeLayout remakeLayout;

    @BindView(R.id.kh_name_phone_edit)
    AppCompatEditText khNamePhoneEdit;

    @BindView(R.id.remake)
    TextView remake;

    @BindView(R.id.select_server_all)
    PercentLinearLayout selectServerAll;

    @BindView(R.id.type_text)
    TextView typeText;

    @BindView(R.id.apply_md)
    TextView applyMd;

    @BindView(R.id.add_date)
    TextView addDate;

    @BindView(R.id.source_text)
    TextView sourceText;

    @BindView(R.id.creat_time)
    TextView creatTime;

    @BindView(R.id.operation)
    TextView operation;

    @BindView(R.id.title_layout)
    PercentLinearLayout titleLayout;

    @BindView(R.id.kh_list)
    ListView khList;
    Unbinder unbinder;

    @BindView(R.id.select_text)
    TextView selectText;

    @BindView(R.id.select_layout)
    PercentLinearLayout selectLayout;

    private View view;

    private KHZSListAdapter khzsListAdapter;

    private LoadingDialog loadingDialog;

    private List<BeautyBeanKh> beautyBeanKhs = new ArrayList<>();

    BeautyDeleteDialog beautyDeleteDialog;

    public static KHZSFragment newInstance() {
        Bundle args = new Bundle();
        KHZSFragment fragment = new KHZSFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.khzs_fragment, container, false);
        EventBus.getDefault().register(this);
        unbinder = ButterKnife.bind(this, view);

        initview();

        return view;
    }


    private void initview() {
        LoadingDialog.Builder addSignDialogBuild = new LoadingDialog.Builder(getActivity());
        loadingDialog = addSignDialogBuild.createDialog();
        loadingDialog.setCanceledOnTouchOutside(false);
        // 设置点击屏幕Dialog不消失

        getKhzs();//查询所有
    }


    private void getKhzs() {

        khzsListAdapter = new KHZSListAdapter(getContext(), beautyBeanKhs);
        khzsListAdapter.setListOnClickListener(this);
        khList.setAdapter(khzsListAdapter);

        loadingDialog.show();

        BmobQuery<BeautyBeanKh> categoryBmobQuery = new BmobQuery<>();
//      categoryBmobQuery.addWhereLessThanOrEqualTo("createdAt", bmobCreatedAtDate);
        categoryBmobQuery.findObjects(new FindListener<BeautyBeanKh>() {
            @Override
            public void done(List<BeautyBeanKh> object, BmobException e) {
                if (e == null) {

                    beautyBeanKhs.clear();
                    beautyBeanKhs.addAll(object);
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
                case 1:
                    int beautySize = beautyBeanKhs.size();
                    String s = String.format(getString(R.string.beauty_within_select_all_text), beautySize);
                    selectText.setText(s);
                    Tools.ListSortsKh(beautyBeanKhs);
                    khzsListAdapter.notifyDataSetChanged();

                    loadingDialog.dismiss();

                    break;

            }
        }

    };


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.search_btn, R.id.remake_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.search_btn:
                getSearchKh();
                break;

            case R.id.remake_layout:
                getRemakeLayoutKh();
                break;
        }
    }


    private void getSearchKh() {

        String khString = khNamePhoneEdit.getText().toString();
        if (!khString.equals("")) {
            loadingDialog.show();
            getSelectCustomers(khString);
        } else {
            ToastUtils.showToast(getActivity(), "请输入姓名或手机号进行搜索", false);

        }


    }


    private void getRemakeLayoutKh() {

        khNamePhoneEdit.setText("");

        getKhzs();//查询所有


    }

    //list长安
    @Override
    public void onLongClick(View v, int pos, Object o) {

    }

    //list点击
    @Override
    public void onClick(View v, int pos, Object o) {

        switch (v.getId()) {

            case R.id.update_btn:

                Log.e(TAG, "onClick: " + o.toString());
                break;

            case R.id.info_btn:
                Intent intent = new Intent(getActivity(), BeautyKHInfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constons.RESULT_KG_INFO_code_REQUEST, (BeautyBeanKh) o);
                intent.putExtras(bundle);
                startActivityForResult(intent, Constons.RESULT_KG_INFO_REQUEST);

                Log.e(TAG, "onClick: " + o.toString());
                break;

            default:
                break;


        }


    }

    @Override
    public void onClickDelete(View v, int pos, Object o) {

        deleteFuWuDialog((BeautyBeanKh) o, pos);


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
                    beautyBeanKhs.addAll(object);

                    ToastUtils.showToast(getContext(), "查询成功" + object.size(), true);
                    refreshHandler.sendEmptyMessage(1);
                } else {
                    ToastUtils.showToast(getContext(), "查询失败" + e.toString(), true);
                }
            }
        });
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        Log.e(TAG, "onSupportVisible: ");
        // 设置点击屏幕Dialog不消失
//        loadingDialog.show();
//        getKhzs();//查询所有
    }

    @Override
    public void onSupportInvisible() {
        super.onSupportInvisible();
        Log.e(TAG, "onSupportInvisible: ");
        loadingDialog.dismiss();
    }

    //删除用户
    private void deleteFuWuDialog(BeautyBeanKh beautyBeanKh, int pos) {

        BeautyDeleteDialog.Builder beautyAddServerTypeBuilder = new BeautyDeleteDialog.Builder(getActivity())
                .setTitleMsg("确定删除(" + beautyBeanKh.getName() + ")的信息？")
                .setYesOnClick(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        khzsListAdapter.deleteView(pos, khList);

                        beautyBeanKh.delete(new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    int beautySize = beautyBeanKhs.size();
                                    String s = String.format(getString(R.string.beauty_within_select_all_text), beautySize);
                                    selectText.setText(s);
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
    public void onEventMain(EventBusAddKhMsg event) {
        int flag = event.getMsgInt();

        switch (flag) {

            case Constons.SELECT_SELECT_ADD_KH:

                Log.e(TAG, "onEventMain: " + event.toString());

                getKhzs();//查询所有

                break;
        }
    }
}
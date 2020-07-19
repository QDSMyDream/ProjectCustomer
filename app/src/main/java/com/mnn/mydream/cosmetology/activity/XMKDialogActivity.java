package com.mnn.mydream.cosmetology.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import android.view.View;

import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.support.odps.udf.CodecCheck;
import com.applandeo.materialcalendarview.view.NiceSpinner;
import com.example.smoothcheckbox.SmoothCheckBox;
import com.mnn.mydream.cosmetology.R;
import com.mnn.mydream.cosmetology.bean.fuwuBean.FuWuSaleBean;
import com.mnn.mydream.cosmetology.bean.fuwuBean.ServerTypeBean;
import com.mnn.mydream.cosmetology.bean.fuwuBean.XMKDataBean;
import com.mnn.mydream.cosmetology.bean.fuwuBean.XMKDataOpertionBean;
import com.mnn.mydream.cosmetology.bmob.BeanCallBack;
import com.mnn.mydream.cosmetology.dialog.BeautyAddServerTypeDialog;
import com.mnn.mydream.cosmetology.dialog.BeautyAddServiceDialog;

import com.mnn.mydream.cosmetology.fragment.beauty.commoditiesFragments.adapter.FWOperationRecycleAdapter;

import com.mnn.mydream.cosmetology.interfaces.ServiceOperationRecycleInterface;
import com.mnn.mydream.cosmetology.pickertime.TimePickerPopWin;
import com.mnn.mydream.cosmetology.utils.CommonUtil;
import com.mnn.mydream.cosmetology.utils.Constons;
import com.mnn.mydream.cosmetology.utils.StringUtils;
import com.mnn.mydream.cosmetology.utils.ToastUtils;
import com.mnn.mydream.cosmetology.utils.Tools;

import com.zhy.android.percent.support.PercentLinearLayout;
import com.zhy.android.percent.support.PercentRelativeLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.droidlover.xrecyclerview.XRecyclerView;

/**
 * 创建人 :MyDream
 * 创建时间：2020/5/3 18:18
 * 类描述：添加项目卡
 */
public class XMKDialogActivity extends AppCompatActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.xmk_name)
    AppCompatEditText xmkName;
    @BindView(R.id.xmk_type)
    NiceSpinner xmkType;
    @BindView(R.id.add_img)
    ImageView addImg;
    @BindView(R.id.add_type_layout)
    PercentRelativeLayout addTypeLayout;
    @BindView(R.id.add_img2)
    ImageView addImg2;
    @BindView(R.id.add_server_layout)
    PercentRelativeLayout addServerLayout;
    @BindView(R.id.title1)
    TextView title1;
    @BindView(R.id.title2)
    TextView title2;
    @BindView(R.id.title3)
    TextView title3;
    @BindView(R.id.server_content_title)
    PercentRelativeLayout serverContentTitle;
    @BindView(R.id.server_content)
    PercentRelativeLayout serverContent;
    @BindView(R.id.xmk_money)
    AppCompatEditText xmkMoney;
    @BindView(R.id.xmk_vip_money)
    AppCompatEditText xmkVipMoney;
    @BindView(R.id.vip_check_box)
    SmoothCheckBox vipCheckBox;
    @BindView(R.id.permanent_validity_check_box)
    SmoothCheckBox permanentValidityCheckBox;
    @BindView(R.id.immediate_effect_check_box)
    SmoothCheckBox immediateEffectCheckBox;
    @BindView(R.id.expire_txt_time)
    TextView expireTxtTime;
    @BindView(R.id.expire_txt_img)
    ImageView expireTxtImg;
    @BindView(R.id.empower_check_box)
    SmoothCheckBox empowerCheckBox;
    @BindView(R.id.may_authorize_check_box)
    SmoothCheckBox mayAuthorizeCheckBox;
    @BindView(R.id.xmk_md)
    NiceSpinner xmkMd;
    @BindView(R.id.md_img)
    ImageView mdImg;
    @BindView(R.id.tip1)
    TextView tip1;
    @BindView(R.id.card_cover)
    PercentRelativeLayout cardCover;
    @BindView(R.id.xmk_num)
    AppCompatEditText xmkNum;
    @BindView(R.id.remarks_content)
    AppCompatEditText remarksContent;
    @BindView(R.id.remarks_num)
    TextView remarksNum;
    @BindView(R.id.btn_yes)
    AppCompatButton btnYes;
    @BindView(R.id.btn_cancel)
    AppCompatButton btnCancel;
    @BindView(R.id.myScrollView)
    ScrollView myScrollView;

    @BindView(R.id.check1)
    CheckBox check1;

    @BindView(R.id.check2)
    CheckBox check2;

    @BindView(R.id.check3)
    CheckBox check3;

    @BindView(R.id.check4)
    CheckBox check4;

    @BindView(R.id.check5)
    CheckBox check5;

    @BindView(R.id.check6)
    CheckBox check6;

    @BindView(R.id.check7)
    CheckBox check7;

    @BindView(R.id.check8)
    CheckBox check8;

    @BindView(R.id.check9)
    CheckBox check9;

    @BindView(R.id.total_num_checkbox)
    SmoothCheckBox totalNumCheckbox;

    @BindView(R.id.total_num)
    AppCompatEditText totalNum;

    @BindView(R.id.service_listview)
    XRecyclerView serviceListview;
    @BindView(R.id.title4)
    TextView title4;


    private String TAG = "XMKDialogActivity";

    private Integer flagInt;

    private String picPath = "https://bmob-cdn-28614.bmobpay.com/2020/07/12/49e9500440be379380eff778e5dff13a.png";

    private boolean XMK_FLAG;

    private XMKDataBean xmkDataBean;

    private int FLAG_INDEX;

    private BeautyAddServerTypeDialog beautyAddServerTypeDialog;

    private BeautyAddServiceDialog beautyAddServiceDialog;

    private List<CheckBox> checkBoxList;

    private List<FuWuSaleBean> fuWuSaleBeans = new ArrayList<>();

    private List<XMKDataOpertionBean> xmkDataOpertionBeans = new ArrayList<>();//数据存储

    private FWOperationRecycleAdapter fwOperationRecycleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_add_xmk_dialog);
        ButterKnife.bind(this);
        initData();
        initView();

    }

    private void initData() {
        setCheckedColor();
        setChecked();
        allSetChecked(false);
        xmkMd.attachDataSource(Constons.OPERATION_MD);
        xmkType.attachDataSource(Constons.ServerTypeString);

        //创建布局管理器，垂直设置LinearLayoutManager.VERTICAL，水平设置LinearLayoutManager.HORIZONTAL
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, false);
        fwOperationRecycleAdapter = new FWOperationRecycleAdapter(this, fuWuSaleBeans, totalNumCheckbox.isChecked());
        fwOperationRecycleAdapter.setServiceOperationRecycleInterface(new ServiceOperationRecycleInterface() {
            @Override
            public void onClickDelete(int pos, FuWuSaleBean fuWuSaleBean) {
                fuWuSaleBeans.remove(fuWuSaleBean);

                fwOperationRecycleAdapter.removeData(pos);

                Log.e(TAG, "onClickDelete: " + fuWuSaleBeans.size());
            }
        });
        serviceListview.setLayoutManager(mLinearLayoutManager);
        serviceListview.setAdapter(fwOperationRecycleAdapter);
    }


    //设置卡封面
    private void setCheckedColor() {
        checkBoxList = new ArrayList<>();
        checkBoxList.add(check1);
        checkBoxList.add(check2);
        checkBoxList.add(check3);
        checkBoxList.add(check4);
        checkBoxList.add(check5);
        checkBoxList.add(check6);
        checkBoxList.add(check7);
        checkBoxList.add(check8);
        checkBoxList.add(check9);
    }


    private void initView() {
        xmkDataBean = (XMKDataBean) getIntent().getSerializableExtra(Constons.RESULT_UPDATE_REQUEST);
        if (xmkDataBean != null) {
            XMK_FLAG = false;
            title.setText("修改项目卡内容");
        } else {
            XMK_FLAG = true;
            title.setText("添加项目卡内容");
            vipCheckBox.setChecked(true);
            permanentValidityCheckBox.setChecked(true);
            empowerCheckBox.setChecked(true);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e(TAG, "onActivityResult: requestCode" + requestCode + "----resultCode" + resultCode);
    }


    private void addTypeDialog() {
        BeautyAddServerTypeDialog.Builder beautyAddServerTypeBuilder = new BeautyAddServerTypeDialog.Builder(this)

                .setTitleString("添加服务类型弹窗")
                .setYesOnClick(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AppCompatEditText editText = beautyAddServerTypeDialog.findViewById(R.id.ed_servertype);
                        if (editText.getText() != null && !editText.getText().toString().equals("")) {
                            ServerTypeBean serverTypeBean = new ServerTypeBean();
                            serverTypeBean.setServerTypeString(editText.getText().toString());
                            serverTypeBean.setBmobUser(BmobUser.getCurrentUser(BmobUser.class));
                            addServerType(serverTypeBean);

                        } else {
                            ToastUtils.showToast(getBaseContext(), "请输入服务类型", false);

                        }

                    }
                });
        beautyAddServerTypeDialog = beautyAddServerTypeBuilder.createDialog();
        // 设置点击屏幕Dialog不消失
        beautyAddServerTypeDialog.show();

    }

    private void addServiceDialog() {
        BeautyAddServiceDialog.Builder builder = new BeautyAddServiceDialog.Builder(this).setFuWuSaleBeans(fuWuSaleBeans);
        builder.setBeanCallBack(beanCallBack);
        beautyAddServiceDialog = builder.createDialog();
        beautyAddServiceDialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失

        // 设置点击屏幕Dialog不消失
        beautyAddServiceDialog.show();

    }

    private void saveXmk() {


        if (StringUtils.isEmpty(xmkName.getText().toString())) {
            ToastUtils.showToast(this, "请输入项目卡名称", false);
            return;
        }

        if (Constons.ServerTypeString.size() == 0) {
            ToastUtils.showToast(this, "请选择项目卡类型", false);
            return;
        }

        if (StringUtils.isEmpty(xmkMoney.getText().toString())) {
            ToastUtils.showToast(this, "请输入项目卡价格", false);
            return;
        }

        if (!vipCheckBox.isChecked()) {
            if (StringUtils.isEmpty(xmkVipMoney.getText().toString())) {
                ToastUtils.showToast(this, "请输入项目卡VIP价格", false);
                return;
            }
        }

        if (immediateEffectCheckBox.isChecked()) {
            if (StringUtils.isEmpty(expireTxtTime.getText().toString())) {
                ToastUtils.showToast(this, "请选择有效期", false);
                return;
            }
        }

        if (totalNumCheckbox.isChecked()) {
            if (StringUtils.isEmpty(totalNum.getText().toString())) {
                ToastUtils.showToast(this, "请输入项目卡总次数", false);
                return;
            }
        }

        if (fuWuSaleBeans.size() == 0) {
            ToastUtils.showToast(this, "请选择服务！", false);
            return;
        }


        XMKDataBean xmkDataBean = new XMKDataBean();

        xmkDataBean.setXmkName(xmkName.getText().toString());

        xmkDataBean.setXmkMd(xmkMd.getSelectedItem().toString());

        xmkDataBean.setXmkMoney(Float.parseFloat(xmkMoney.getText().toString()));

        xmkDataBean.setXmkType(xmkType.getSelectedItem().toString());

        if (vipCheckBox.isChecked()) {
            xmkDataBean.setXmkVipMoney(0);
        } else {
            xmkDataBean.setXmkVipMoney(Float.parseFloat(xmkVipMoney.getText().toString()));
        }

        xmkDataBean.setXmkVipFlag(vipCheckBox.isChecked());

        if (totalNumCheckbox.isChecked()) {
            xmkDataBean.setTotalNum(Integer.parseInt(totalNum.getText().toString()));
            for (int i = 0; i < serviceListview.getChildCount(); i++) {
                PercentLinearLayout layout = (PercentLinearLayout) serviceListview.getChildAt(i);// 获得子item的layout
                EditText et = (EditText) layout.findViewById(R.id.total_num);// 从layout中获得控件,根据其id
                xmkDataOpertionBeans.add(new XMKDataOpertionBean(StringUtils.isEmpty(et.getText().toString()) ? 0 : Integer.parseInt(et.getText().toString()), fuWuSaleBeans.get(i)));
            }


        } else {
            xmkDataBean.setTotalNum(0);
            for (int i = 0; i < serviceListview.getChildCount(); i++) {
                PercentLinearLayout layout = (PercentLinearLayout) serviceListview.getChildAt(i);// 获得子item的layout
                EditText et = (EditText) layout.findViewById(R.id.total_num);// 从layout中获得控件,根据其id
                if (StringUtils.isEmpty(et.getText().toString())) {
                    ToastUtils.showToast(this, "服务列表第" + (i + 1) + "未输入", false);
                    return;
                }
                xmkDataOpertionBeans.add(new XMKDataOpertionBean(Integer.parseInt(et.getText().toString()), fuWuSaleBeans.get(i)));
            }

        }
        Log.e(TAG, "saveXmk: " + xmkDataOpertionBeans.size());
        String jsonString = JSON.toJSONString(xmkDataOpertionBeans);
        Log.e(TAG, "saveXmk: " + jsonString);
        xmkDataBean.setFwJson(jsonString);

        xmkDataBean.setTotalNumFlag(totalNumCheckbox.isChecked());
        xmkDataBean.setEffectiveFlag(immediateEffectCheckBox.isChecked());
        xmkDataBean.setEffectiveTime(xmkName.getText().toString());

        xmkDataBean.setEmpowerFlag(mayAuthorizeCheckBox.isChecked());
        //设置预览颜色
        xmkDataBean.setCoverColorStr(check1.isChecked() ? check1.getTag().toString() : check2.isChecked() ? check2.getTag().toString() : check3.isChecked() ? check3.getTag().toString() : check4.isChecked() ? check4.getTag().toString() : check5.isChecked() ? check5.getTag().toString() : check6.isChecked() ? check6.getTag().toString() : check7.isChecked() ? check7.getTag().toString() : check8.isChecked() ? check8.getTag().toString() : check9.isChecked() ? check9.getTag().toString() : "");

        xmkDataBean.setXmkNum(xmkNum.getText().toString());

        xmkDataBean.setCharacteristicStr(remarksContent.getText().toString());

        //是否上架
        xmkDataBean.setXmlSaleFlag(true);
        xmkDataBean.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    ToastUtils.showToast(getBaseContext(), "添加(" + xmkDataBean.getXmkName() + ")项目卡成功!", true);
                    refreshHandler.sendEmptyMessage(1);
                    Log.e("bmob", "成功");
                } else {
                    ToastUtils.showToast(getBaseContext(), "添加(" + xmkDataBean.getXmkName() + ")项目卡失败" + e.toString(), false);
                }

            }
        });


        Log.e(TAG, "saveXmk: " + (check1.isChecked() ? check1.getTag().toString() : check2.isChecked() ? check2.getTag().toString() : check3.isChecked() ? check3.getTag().toString() : check4.isChecked() ? check4.getTag().toString() : check5.isChecked() ? check5.getTag().toString() : check6.isChecked() ? check6.getTag().toString() : check7.isChecked() ? check7.getTag().toString() : check8.isChecked() ? check8.getTag().toString() : check9.isChecked() ? check9.getTag().toString() : ""));
    }


//    /**
//     * serverTypeStrings 服务类型
//     */
//    private void getSelectServerTypeAll() {
//
//        LoadingDialog.Builder addSignDialogBuild = new LoadingDialog.Builder(this);
//        loadingDialog = addSignDialogBuild.createDialog();
//        loadingDialog.setCanceledOnTouchOutside(false);
//        // 设置点击屏幕Dialog不消失
//        loadingDialog.show();
//
////        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
////
////        Date createdAtDate = null;
////        try {
////            createdAtDate = sdf.parse(Tools.getSameDay());
////        } catch (ParseException e) {
////            e.printStackTrace();
////        }
////        BmobDate bmobCreatedAtDate = new BmobDate(createdAtDate);
////
//        BmobQuery<ServerTypeBean> categoryBmobQuery = new BmobQuery<>();
////        categoryBmobQuery.addWhereLessThanOrEqualTo("createdAt", bmobCreatedAtDate);
//        categoryBmobQuery.findObjects(new FindListener<ServerTypeBean>() {
//            @Override
//            public void done(List<ServerTypeBean> object, BmobException e) {
//                if (e == null) {
//                    if (object.size() == 0) {
//                        Constons.ServerTypeString.add("无");
//                    } else {
//                        Constons.ServerTypeString.clear();
//                        serverTypeBeans = object;
//                        for (ServerTypeBean serverTypeBean : serverTypeBeans) {
//                            Constons.ServerTypeString.add(serverTypeBean.getServerTypeString());
//                        }
//                    }
//                    loadingDialog.dismiss();
//                    refreshHandler.sendEmptyMessage(0);
//
//                } else {
//                    loadingDialog.dismiss();
//                    Log.e("BMOB", e.toString());
//                    ToastUtils.showToast(getBaseContext(), "查询失败", false);
//                }
//            }
//        });
//    }


    //添加服务类型
    private void addServerType(ServerTypeBean serverTypeBean) {

        serverTypeBean.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {

                    ToastUtils.showToast(getBaseContext(), "添加服务类型成功", true);
                    Log.e("bmob", "成功");

                    Constons.ServerTypeString.add(serverTypeBean.getServerTypeString());

                    beautyAddServerTypeDialog.dismiss();

                } else {
                    ToastUtils.showToast(getBaseContext(), "添加服务类型失败：" + e.getMessage(), false);
                    Log.e("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                }

            }
        });
    }


    @OnClick({R.id.add_type_layout, R.id.add_server_layout, R.id.expire_txt_img, R.id.btn_yes, R.id.btn_cancel, R.id.check1, R.id.check2, R.id.check3, R.id.check4, R.id.check5, R.id.check6, R.id.check7, R.id.check8, R.id.check9})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.add_type_layout:
                addTypeDialog();
                break;
            case R.id.btn_yes:
                saveXmk();
                break;
            case R.id.btn_cancel:
                finish();
                break;
            case R.id.check1:
                cardCover.setBackgroundResource(R.drawable.beauty_within_gridview_item_layout1);
                checkTrue(check1);

                break;
            case R.id.check2:
                cardCover.setBackgroundResource(R.drawable.beauty_within_gridview_item_layout2);
                checkTrue(check2);
                break;
            case R.id.check3:
                cardCover.setBackgroundResource(R.drawable.beauty_within_gridview_item_layout3);
                checkTrue(check3);
                break;
            case R.id.check4:
                checkTrue(check4);
                cardCover.setBackgroundResource(R.drawable.beauty_within_gridview_item_layout4);
                break;
            case R.id.check5:
                checkTrue(check5);
                cardCover.setBackgroundResource(R.drawable.beauty_within_gridview_item_layout5);
                break;
            case R.id.check6:
                checkTrue(check6);
                cardCover.setBackgroundResource(R.drawable.beauty_within_gridview_item_layout6);
                break;
            case R.id.check7:
                checkTrue(check7);
                cardCover.setBackgroundResource(R.drawable.beauty_within_gridview_item_layout7);
                break;
            case R.id.check8:
                checkTrue(check8);
                cardCover.setBackgroundResource(R.drawable.beauty_within_gridview_item_layout8);
                break;
            case R.id.check9:
                checkTrue(check9);
                cardCover.setBackgroundResource(R.drawable.beauty_within_gridview_item_layout9);
                break;
            case R.id.expire_txt_img:
                setOnclick(true);
                break;

            case R.id.add_server_layout:

                addServiceDialog();
                break;

        }
    }


    private void checkTrue(CheckBox check) {
        CommonUtil.unCheck(checkBoxList);
        check.setChecked(true);
    }


    //默认全为false
    private void allSetChecked(boolean b) {

        if (b) {
            vipCheckBox.setChecked(false);
            permanentValidityCheckBox.setChecked(false);
            immediateEffectCheckBox.setChecked(false);
            empowerCheckBox.setChecked(false);
            mayAuthorizeCheckBox.setChecked(false);

        }

    }


    //设置checkbox点击事件
    private void setChecked() {

        vipCheckBox.setOnCheckedChangeListener(new SmoothCheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SmoothCheckBox checkBox, boolean isChecked) {
                if (isChecked) {
                    xmkVipMoney.setEnabled(false);
                    xmkVipMoney.setAlpha((float) 0.5);
                    xmkVipMoney.setHint(getString(R.string.beauty_not_enabled));
                } else {
                    xmkVipMoney.setEnabled(true);
                    xmkVipMoney.setAlpha((float) 1);
                    xmkVipMoney.setHint(getString(R.string.beauty_xmk_vip_money));
                }
            }
        });

        permanentValidityCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!permanentValidityCheckBox.isChecked()) {

                    permanentValidityCheckBox.setChecked(true);
                    immediateEffectCheckBox.setChecked(false);
                }

            }
        });
        immediateEffectCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!immediateEffectCheckBox.isChecked()) {

                    immediateEffectCheckBox.setChecked(true);
                    permanentValidityCheckBox.setChecked(false);
                }
            }
        });

        mayAuthorizeCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mayAuthorizeCheckBox.isChecked()) {

                    mayAuthorizeCheckBox.setChecked(true);
                    empowerCheckBox.setChecked(false);
                }
            }
        });

        empowerCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!empowerCheckBox.isChecked()) {

                    empowerCheckBox.setChecked(true);
                    mayAuthorizeCheckBox.setChecked(false);
                }
            }
        });

        totalNumCheckbox.setOnCheckedChangeListener(new SmoothCheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SmoothCheckBox checkBox, boolean isChecked) {
                if (!isChecked) {
                    totalNum.setEnabled(false);
                    totalNum.setAlpha((float) 0.5);
                    totalNum.setHint(getString(R.string.beauty_not_enabled));

                } else {
                    totalNum.setEnabled(true);
                    totalNum.setAlpha((float) 1);
                    totalNum.setHint(getString(R.string.beauty_xmk_total_num));
                }
                fwOperationRecycleAdapter.setAllEditText(isChecked);
            }
        });

        totalNum.setEnabled(false);
        totalNum.setAlpha((float) 0.5);
        totalNum.setHint(getString(R.string.beauty_not_enabled));
    }


    //时间选择框
    public void setOnclick(boolean b) {

        TimePickerPopWin timePickerPopWin = new TimePickerPopWin.Builder(this, b, new TimePickerPopWin.OnTimePickListener() {
            @Override
            public void onTimePickCompleted(int year, int month, int day, int hour, int minute, int second, String time) {

                if (b) {
                    Tools.getCurrentDayDate();
                    if (Tools.getCurrentDayDate().getTime() + 86400000 > Tools.date2TimeStamp2(time)) {
                        expireTxtTime.setText(time);
                    } else {
                        ToastUtils.showToast(getBaseContext(), "时间必须大于一天，请重新选择！", false);
                    }
                }

            }
        }).btnTextSize(16)
                .viewTextSize(25)
                .colorCancel(Color.parseColor("#999999"))
                .colorConfirm(Color.parseColor("#009900"))
                .build();
        timePickerPopWin.showPopWin(this);
    }

    //选中回调
    BeanCallBack beanCallBack = new BeanCallBack() {
        @Override
        public void setLists(List lists, int flagInt) {
            Log.e(TAG, "setLists: " + lists.size());
            if (lists.size() > 0) {

                fuWuSaleBeans.clear();
                fuWuSaleBeans = (List<FuWuSaleBean>) lists;
                fwOperationRecycleAdapter.addDatas(fuWuSaleBeans);
            }
        }
    };

    ///刷新Handler
    @SuppressLint("HandlerLeak")
    private Handler refreshHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0://刷新类型
                    break;
                case 1://刷新
                    setResult(Constons.RESULT_XMK_CODE_SCUESS_REQUEST);

                    finish();
                    break;

                default:
                    break;
            }
        }
    };


    public void saveEditData(int position, String str) {
        Toast.makeText(this, str + "----" + position, Toast.LENGTH_LONG).show();
    }

}

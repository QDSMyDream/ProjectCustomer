package com.mnn.mydream.cosmetology.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.applandeo.materialcalendarview.view.NiceSpinner;
import com.example.smoothcheckbox.SmoothCheckBox;
import com.mnn.mydream.cosmetology.R;
import com.mnn.mydream.cosmetology.bean.spglBean.CXKCZFABean;
import com.mnn.mydream.cosmetology.bean.spglBean.CXKDataBean;
import com.mnn.mydream.cosmetology.bean.spglBean.FuWuSaleBean;
import com.mnn.mydream.cosmetology.bean.spglBean.ServerTypeBean;
import com.mnn.mydream.cosmetology.fragment.beauty.commoditiesFragments.adapter.CXKOperationRecycleAdapter;
import com.mnn.mydream.cosmetology.interfaces.ServiceOperationRecycleInterface;
import com.mnn.mydream.cosmetology.utils.CommonUtil;
import com.mnn.mydream.cosmetology.utils.Constons;
import com.mnn.mydream.cosmetology.utils.StringUtils;
import com.mnn.mydream.cosmetology.utils.ToastUtils;
import com.zhy.android.percent.support.PercentLinearLayout;
import com.zhy.android.percent.support.PercentRelativeLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.droidlover.xrecyclerview.XRecyclerView;

/**
 * 创建人 :MyDream
 * 创建时间：2020/5/3 18:18
 * 类描述：添加储蓄卡
 */
public class CXKDialogActivity extends AppCompatActivity {

    private String TAG = "CXKDialogActivity";

    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.cxk_name)
    AppCompatEditText cxkName;

    @BindView(R.id.add_img2)
    ImageView addImg2;

    @BindView(R.id.add_czje)
    PercentRelativeLayout addCzje;

    @BindView(R.id.recyclerView)
    XRecyclerView recyclerView;

    @BindView(R.id.check_kzk1)
    SmoothCheckBox checkKzk1;

    @BindView(R.id.check_kzk2)
    SmoothCheckBox checkKzk2;

    @BindView(R.id.check_kzk3)
    SmoothCheckBox checkKzk3;

    @BindView(R.id.check_kzk4)
    SmoothCheckBox checkKzk4;

    @BindView(R.id.check_kzk5)
    SmoothCheckBox checkKzk5;

    @BindView(R.id.check_kzk6)
    SmoothCheckBox checkKzk6;

    @BindView(R.id.check_ryc1)
    SmoothCheckBox checkRyc1;

    @BindView(R.id.check_ryc2)
    SmoothCheckBox checkRyc2;

    @BindView(R.id.check_box5)
    SmoothCheckBox checkBox5;

    @BindView(R.id.check_box6)
    SmoothCheckBox checkBox6;

    @BindView(R.id.cxk_md)
    NiceSpinner cxkMd;

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

    @BindView(R.id.md_img)
    ImageView mdImg;

    @BindView(R.id.tip1)
    TextView tip1;

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

    @BindView(R.id.card_cover)
    PercentRelativeLayout cardCover;

    @BindView(R.id.fw_siscount)
    AppCompatEditText fwSiscount;

    @BindView(R.id.cp_siscount)
    AppCompatEditText cpSiscount;

    @BindView(R.id.xmk_siscount)
    AppCompatEditText xmkSiscount;

    @BindView(R.id.xmk_num)
    AppCompatEditText xmkNum;

    private CXKDataBean cxkDataBean;

    private boolean FLAG_BOOLEAN;

    private List<CheckBox> checkBoxList;

    private CXKOperationRecycleAdapter cxkOperationRecycleAdapter;

    private List<CXKCZFABean> cxkczfaBeans = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_add_cxk_dialog);
        ButterKnife.bind(this);
        initView();

    }

    private void initView() {

        setChecked();

        setCheckedColor();

        cxkDataBean = (CXKDataBean) getIntent().getSerializableExtra(Constons.RESULT_UPDATE_REQUEST);

        cxkMd.attachDataSource(Constons.OPERATION_MD);

        if (cxkDataBean != null) {
            FLAG_BOOLEAN = true;

            title.setText("修改储蓄卡");
            setDefaultData();

        } else {
            FLAG_BOOLEAN = false;
            title.setText("添加储蓄卡");
        }
        //创建布局管理器，垂直设置LinearLayoutManager.VERTICAL，水平设置LinearLayoutManager.HORIZONTAL
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, false);
        cxkOperationRecycleAdapter = new CXKOperationRecycleAdapter(this, cxkczfaBeans);
        cxkOperationRecycleAdapter.setServiceOperationRecycleInterface(new ServiceOperationRecycleInterface() {
            @Override
            public void onClickDelete(int pos, Object o) {
                cxkOperationRecycleAdapter.removeData(pos);
            }
        });
        recyclerView.setLayoutManager(mLinearLayoutManager);
        recyclerView.setAdapter(cxkOperationRecycleAdapter);


    }

    private void setDefaultData() {

        cxkName.setText(cxkDataBean.getCxkName());
        cxkMd.setSelectedIndex(0);

        if (cxkDataBean.isRyc()) {
            checkRyc1.setChecked(true);
            checkRyc2.setChecked(false);
        } else {
            checkRyc1.setChecked(false);
            checkRyc2.setChecked(true);
        }

        if (cxkDataBean.isKsq()) {
            checkBox5.setChecked(false);
            checkBox6.setChecked(true);
        } else {
            checkBox5.setChecked(true);
            checkBox6.setChecked(false);
        }


    }

    //设置checkbox点击事件
    private void setChecked() {

        checkKzk2.setChecked(true);
        checkKzk4.setChecked(true);
        checkKzk6.setChecked(true);
        checkRyc1.setChecked(true);
        checkBox5.setChecked(true);

        fwSiscount.setEnabled(false);
        fwSiscount.setAlpha((float) 0.5);
        fwSiscount.setHint(getString(R.string.beauty_not_enabled));


        cpSiscount.setEnabled(false);
        cpSiscount.setAlpha((float) 0.5);
        cpSiscount.setHint(getString(R.string.beauty_not_enabled));

        xmkSiscount.setEnabled(false);
        xmkSiscount.setAlpha((float) 0.5);
        xmkSiscount.setHint(getString(R.string.beauty_not_enabled));


        checkKzk1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkKzk1.isChecked()) {
                    checkKzk1.setChecked(true);
                    checkKzk2.setChecked(false);
                    fwSiscount.setEnabled(true);
                    fwSiscount.setAlpha((float) 1);
                    fwSiscount.setHint(getString(R.string.beauty_cxk_discount));
                }

            }
        });
        checkKzk2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!checkKzk2.isChecked()) {
                    checkKzk2.setChecked(true);
                    checkKzk1.setChecked(false);
                    fwSiscount.setEnabled(false);
                    fwSiscount.setAlpha((float) 0.5);
                    fwSiscount.setHint(getString(R.string.beauty_not_enabled));
                }
            }
        });


        checkKzk3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkKzk3.isChecked()) {
                    checkKzk3.setChecked(true);
                    checkKzk4.setChecked(false);
                    cpSiscount.setEnabled(true);
                    cpSiscount.setAlpha((float) 1);
                    cpSiscount.setHint(getString(R.string.beauty_cxk_discount));
                }

            }
        });
        checkKzk4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!checkKzk4.isChecked()) {
                    checkKzk4.setChecked(true);
                    checkKzk3.setChecked(false);
                    cpSiscount.setEnabled(false);
                    cpSiscount.setAlpha((float) 0.5);
                    cpSiscount.setHint(getString(R.string.beauty_not_enabled));
                }
            }
        });


        checkKzk5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkKzk5.isChecked()) {
                    checkKzk5.setChecked(true);
                    checkKzk6.setChecked(false);
                    xmkSiscount.setEnabled(true);
                    xmkSiscount.setAlpha((float) 1);
                    xmkSiscount.setHint(getString(R.string.beauty_cxk_discount));
                }

            }
        });
        checkKzk6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkKzk6.isChecked()) {
                    checkKzk6.setChecked(true);
                    checkKzk5.setChecked(false);
                    xmkSiscount.setEnabled(false);
                    xmkSiscount.setAlpha((float) 0.5);
                    xmkSiscount.setHint(getString(R.string.beauty_not_enabled));
                }
            }
        });


        checkRyc1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkRyc1.isChecked()) {
                    checkRyc1.setChecked(true);
                    checkRyc2.setChecked(false);
                }

            }
        });
        checkRyc2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkRyc2.isChecked()) {
                    checkRyc2.setChecked(true);
                    checkRyc1.setChecked(false);
                }
            }
        });


        checkBox5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkBox5.isChecked()) {
                    checkBox5.setChecked(true);
                    checkBox6.setChecked(false);

                }

            }
        });
        checkBox6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkBox6.isChecked()) {
                    checkBox6.setChecked(true);
                    checkBox5.setChecked(false);
                }
            }
        });


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e(TAG, "onActivityResult: requestCode" + requestCode + "----resultCode" + resultCode);
    }

    @OnClick({R.id.btn_yes, R.id.btn_cancel, R.id.add_czje, R.id.cxk_md, R.id.check1, R.id.check2, R.id.check3, R.id.check4, R.id.check5, R.id.check6, R.id.check7, R.id.check8, R.id.check9, R.id.tip1})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_yes:
                saveCXK();
                break;

            case R.id.btn_cancel:
                finish();
                break;

            case R.id.add_czje:

                Log.e(TAG, "onViewClicked:add_czje ");
                addczfa();
                break;


            case R.id.cxk_md:


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
            case R.id.tip1:

                break;

        }
    }

    private void addczfa() {
        if (cxkczfaBeans.size() < 10) {
            cxkOperationRecycleAdapter.addData(new CXKCZFABean());

        } else {
            ToastUtils.showToast(this, "充值方案最多10个", false);
        }


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

    private void checkTrue(CheckBox check) {
        CommonUtil.unCheck(checkBoxList);
        check.setChecked(true);
    }

    private void saveCXK() {

        if (FLAG_BOOLEAN) {

            if (StringUtils.isEmpty(cxkName.getText().toString())) {
                ToastUtils.showToast(this, "请输入储蓄卡名称", false);
                return;
            }

            if (cxkczfaBeans.size() == 0) {
                ToastUtils.showToast(this, "请添加充值方案", false);
                return;
            }
            if (checkKzk1.isChecked()) {
                ToastUtils.showToast(this, "请输入服务价格折扣", false);
                return;
            }
            if (checkKzk3.isChecked()) {
                ToastUtils.showToast(this, "请输入产品价格折扣", false);
                return;
            }
            if (checkKzk5.isChecked()) {
                ToastUtils.showToast(this, "请输入项目卡价格折扣", false);
                return;
            }

            for (int i = 0; i < recyclerView.getChildCount(); i++) {
                PercentLinearLayout layout = (PercentLinearLayout) recyclerView.getChildAt(i);// 获得子item的layout
                AppCompatEditText czje = (AppCompatEditText) layout.findViewById(R.id.czje);// 从layout中获得控件,根据其id
                AppCompatEditText zsje = (AppCompatEditText) layout.findViewById(R.id.zsje);// 从layout中获得控件,根据其id

                if (StringUtils.isEmpty(czje.getText().toString())) {
                    ToastUtils.showToast(this, "充值列表第" + (i + 1) + "未输入", false);
                    return;
                }

                if (StringUtils.isEmpty(zsje.getText().toString())) {
                    ToastUtils.showToast(this, "赠送列表第" + (i + 1) + "未输入", false);
                    return;
                }

                cxkczfaBeans.get(i).setCzje(Float.parseFloat(czje.getText().toString()));
                cxkczfaBeans.get(i).setZsje(Float.parseFloat(zsje.getText().toString()));
            }


            cxkDataBean.setCxkName(cxkName.getText().toString());

            Log.e(TAG, "saveXmk: " + cxkczfaBeans.size());
            String jsonString = JSON.toJSONString(cxkczfaBeans);
            Log.e(TAG, "saveXmk: " + jsonString);

            cxkDataBean.setCzfa(jsonString);

            cxkDataBean.setKzkFWFlag(checkKzk1.isChecked());
            cxkDataBean.setKzkFW(fwSiscount.getText().toString());

            cxkDataBean.setKzkCPFlag(checkKzk3.isChecked());
            cxkDataBean.setKzkCP(cpSiscount.getText().toString());

            cxkDataBean.setKzkXMKFlag(checkKzk5.isChecked());
            cxkDataBean.setKzkXMK(xmkSiscount.getText().toString());

            cxkDataBean.setCharacteristicStr(remarksContent.getText().toString());

            //设置预览颜色
            cxkDataBean.setFm(check1.isChecked() ? check1.getTag().toString() : check2.isChecked() ? check2.getTag().toString() : check3.isChecked() ? check3.getTag().toString() : check4.isChecked() ? check4.getTag().toString() : check5.isChecked() ? check5.getTag().toString() : check6.isChecked() ? check6.getTag().toString() : check7.isChecked() ? check7.getTag().toString() : check8.isChecked() ? check8.getTag().toString() : check9.isChecked() ? check9.getTag().toString() : "");

            cxkDataBean.setNum(xmkNum.getText().toString());

            cxkDataBean.setMd(cxkMd.getSelectedItem().toString());

            cxkDataBean.setKsq(checkBox5.isChecked() ? false : true);

            cxkDataBean.setRyc(checkRyc1.isChecked() ? true : false);

            setSaveCXKData(cxkDataBean, FLAG_BOOLEAN);


        } else {

            CXKDataBean cxkDataBean = new CXKDataBean();

            if (StringUtils.isEmpty(cxkName.getText().toString())) {
                ToastUtils.showToast(this, "请输入储蓄卡名称", false);
                return;
            }

            if (cxkczfaBeans.size() == 0) {
                ToastUtils.showToast(this, "请添加充值方案", false);
                return;
            }
            if (checkKzk1.isChecked()&&StringUtils.isEmpty(fwSiscount.getText().toString())) {
                ToastUtils.showToast(this, "请输入服务价格折扣", false);
                return;
            }
            if (checkKzk3.isChecked()&&StringUtils.isEmpty(cpSiscount.getText().toString())) {
                ToastUtils.showToast(this, "请输入产品价格折扣", false);
                return;
            }
            if (checkKzk5.isChecked()&&StringUtils.isEmpty(xmkSiscount.getText().toString())) {
                ToastUtils.showToast(this, "请输入项目卡价格折扣", false);
                return;
            }

            for (int i = 0; i < recyclerView.getChildCount(); i++) {
                PercentLinearLayout layout = (PercentLinearLayout) recyclerView.getChildAt(i);// 获得子item的layout
                AppCompatEditText czje = (AppCompatEditText) layout.findViewById(R.id.czje);// 从layout中获得控件,根据其id
                AppCompatEditText zsje = (AppCompatEditText) layout.findViewById(R.id.zsje);// 从layout中获得控件,根据其id

                if (StringUtils.isEmpty(czje.getText().toString())) {
                    ToastUtils.showToast(this, "充值列表第" + (i + 1) + "未输入", false);
                    return;
                }

                if (StringUtils.isEmpty(zsje.getText().toString())) {
                    ToastUtils.showToast(this, "赠送列表第" + (i + 1) + "未输入", false);
                    return;
                }

                cxkczfaBeans.get(i).setCzje(Float.parseFloat(czje.getText().toString()));
                cxkczfaBeans.get(i).setZsje(Float.parseFloat(zsje.getText().toString()));
            }


            cxkDataBean.setCxkName(cxkName.getText().toString());

            Log.e(TAG, "saveXmk: " + cxkczfaBeans.size());
            String jsonString = JSON.toJSONString(cxkczfaBeans);
            Log.e(TAG, "saveXmk: " + jsonString);

            cxkDataBean.setCzfa(jsonString);

            cxkDataBean.setKzkFWFlag(checkKzk1.isChecked());
            cxkDataBean.setKzkFW(fwSiscount.getText().toString());

            cxkDataBean.setKzkCPFlag(checkKzk3.isChecked());
            cxkDataBean.setKzkCP(cpSiscount.getText().toString());

            cxkDataBean.setKzkXMKFlag(checkKzk5.isChecked());
            cxkDataBean.setKzkXMK(xmkSiscount.getText().toString());

            cxkDataBean.setCharacteristicStr(remarksContent.getText().toString());

            //设置预览颜色
            cxkDataBean.setFm(check1.isChecked() ? check1.getTag().toString() : check2.isChecked() ? check2.getTag().toString() : check3.isChecked() ? check3.getTag().toString() : check4.isChecked() ? check4.getTag().toString() : check5.isChecked() ? check5.getTag().toString() : check6.isChecked() ? check6.getTag().toString() : check7.isChecked() ? check7.getTag().toString() : check8.isChecked() ? check8.getTag().toString() : check9.isChecked() ? check9.getTag().toString() : "");

            cxkDataBean.setNum(xmkNum.getText().toString());

            cxkDataBean.setMd(cxkMd.getSelectedItem().toString());

            cxkDataBean.setKsq(checkBox5.isChecked() ? false : true);

            cxkDataBean.setRyc(checkRyc1.isChecked() ? true : false);


            setSaveCXKData(cxkDataBean, FLAG_BOOLEAN);

        }

    }

    private void setSaveCXKData(CXKDataBean cxkDataBean, boolean FLAG_BOOLEAN) {
        if (FLAG_BOOLEAN) {

            cxkDataBean.update(new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    if (e == null) {
                        ToastUtils.showToast(getBaseContext(), "修改(" + cxkDataBean.getCxkName() + ")储蓄卡成功!", true);
                        refreshHandler.sendEmptyMessage(2);
                        Log.e("bmob", "成功");
                    } else {
                        ToastUtils.showToast(getBaseContext(), "修改(" + cxkDataBean.getCxkName() + ")储蓄卡失败" + e.toString(), false);
                    }
                }

            });

        } else {

            cxkDataBean.save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {

                    if (e == null) {
                        ToastUtils.showToast(getBaseContext(), "添加(" + cxkDataBean.getCxkName() + ")储蓄卡成功!", true);
                        refreshHandler.sendEmptyMessage(1);
                        Log.e("bmob", "成功");
                    } else {
                        ToastUtils.showToast(getBaseContext(), "添加(" + cxkDataBean.getCxkName() + ")储蓄卡失败" + e.toString(), false);
                    }

                }
            });
        }


    }

    ///刷新Handler
    @SuppressLint("HandlerLeak")
    private Handler refreshHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0://刷新类型


                    break;
                case 1://刷新
                    setResult(Constons.RESULT_CXK_CODE_VIEW_REQUEST);

                    finish();
                    break;
                case 2://刷新

                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Constons.RESULT_UPDATE_REQUEST, cxkDataBean);
                    intent.putExtras(bundle);
                    setResult(Constons.RESULT_CXK_CODE_UPDATE_REQUEST, intent);


                    finish();
                    break;
                case 3://刷新

                    break;

                default:
                    break;
            }
        }
    };


    //添加服务类型
    private void addServerType(ServerTypeBean serverTypeBean) {

        serverTypeBean.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {

                    ToastUtils.showToast(getBaseContext(), "添加服务类型成功", true);
                    Log.e("bmob", "成功");

                    Constons.ServerTypeString.add(serverTypeBean.getServerTypeString());
                    refreshHandler.sendEmptyMessage(3);

                } else {
                    ToastUtils.showToast(getBaseContext(), "添加服务类型失败：" + e.getMessage(), false);
                    Log.e("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                }


            }
        });
    }

}

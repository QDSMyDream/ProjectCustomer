package com.mnn.mydream.cosmetology.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.applandeo.materialcalendarview.view.NiceSpinner;
import com.mnn.mydream.cosmetology.R;
import com.mnn.mydream.cosmetology.bean.CustomerProjectsItem;
import com.mnn.mydream.cosmetology.utils.CommonUtil;
import com.mnn.mydream.cosmetology.utils.StringUtils;
import com.mnn.mydream.cosmetology.utils.ToastUtils;
import com.zhy.android.percent.support.PercentLinearLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by MyDream on 2020/5/10.
 * 项目弹窗
 */

public class ShowCustomerProjectsDialog extends Dialog {

    public ShowCustomerProjectsDialog(Context context) {
        super(context);
    }

    public ShowCustomerProjectsDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public static class Builder {
        @BindView(R.id.sign_text)
        TextView signText;
        @BindView(R.id.sign_layout)
        PercentLinearLayout signLayout;
        @BindView(R.id.project_text)
        TextView projectText;
        @BindView(R.id.sp_projects_layout)
        PercentLinearLayout spProjectsLayout;
        private String TAG = "ShowCustomerProjectsDialog";
        @BindView(R.id.title)
        TextView title;


        @BindView(R.id.reduce_img)
        ImageView reduceImg;

        @BindView(R.id.plus_img)
        ImageView plusImg;

        @BindView(R.id.sp_card_yes)
        CheckBox spCardYes;

        @BindView(R.id.sp_card_no)
        CheckBox spCardNo;

        @BindView(R.id.tip)
        TextView tip;

        @BindView(R.id.tip_layout)
        PercentLinearLayout tipLayout;

        @BindView(R.id.tip2)
        TextView tip2;

        @BindView(R.id.tip_layout2)
        PercentLinearLayout tipLayout2;

        @BindView(R.id.count_layout)
        PercentLinearLayout countLayout;

        @BindView(R.id.sp_yes)
        CheckBox spYes;

        @BindView(R.id.sp_no)
        CheckBox spNo;

        @BindView(R.id.t_count)
        EditText tCount;

        @BindView(R.id.myScrollView)
        ScrollView myScrollView;

        @BindView(R.id.sp_stages_yes)
        CheckBox spStagesYes;

        @BindView(R.id.sp_stages_no)
        CheckBox spStagesNo;

        @BindView(R.id.stages_money)
        EditText stagesMoney;

        @BindView(R.id.stages_surplus_money_txt)
        TextView stagesSurplusMoneyTxt;

        @BindView(R.id.stages_surplus_money_layout)
        PercentLinearLayout stagesSurplusMoneyLayout;

        @BindView(R.id.spinner_projects1)
        NiceSpinner spinnerProjects1;

        @BindView(R.id.end_time_layout)
        PercentLinearLayout endTimeLayout;

        @BindView(R.id.ed_use_count)
        EditText edUseCount;

        @BindView(R.id.sp_count_layout)
        PercentLinearLayout spCountLayout;

        //@BindView(R.id.sp_to_count_layout)
        //PercentLinearLayout spTCountLayout;

        @BindView(R.id.sp_money)
        TextView spMonely;

        @BindView(R.id.ed_money)
        EditText edMoney;

        @BindView(R.id.sp_time)
        TextView spTime;

        @BindView(R.id.sp_start_img)
        ImageView spStartImg;

        @BindView(R.id.sp_end_time)
        TextView spEndTime;

        @BindView(R.id.sp_end_img)
        ImageView spEndImg;

        @BindView(R.id.btn_cancel)
        TextView btnCancel;

        @BindView(R.id.btn_yes)
        TextView btnYes;

        private Context context;
        private View.OnClickListener yesOnClick, edAddStartTimeOnClick, edAddTimeProjectsOnClick, signOnClick;//取消  修改  签字
        private List<String> spStrings;
        private CustomerProjectsItem customerProjectsItem;

        private String yesString = "确定";

        private String titleString = "添加服务项目记录";

        private String projectsString = "";

        public Builder(Context context) {
            this.context = context;
        }

        public Builder edAddStartTimeOnClick(View.OnClickListener onClickListener) {
            this.edAddStartTimeOnClick = onClickListener;
            return this;
        }

        public Builder setYesOnClick(View.OnClickListener onClickListener) {
            this.yesOnClick = onClickListener;
            return this;
        }


        public Builder edAddEndTimeOnClick(View.OnClickListener onClickListener) {
            this.edAddTimeProjectsOnClick = onClickListener;
            return this;
        }


        public Builder setCustomerItem(CustomerProjectsItem customerItem) {
            this.customerProjectsItem = customerItem;
            return this;
        }

        public Builder setSpData(List<String> spStrings) {
            this.spStrings = spStrings;
            return this;
        }

        public Builder setYesString(String yesBtnString) {
            this.yesString = yesBtnString;
            return this;
        }

        public Builder setTitleString(String titleString) {
            this.titleString = titleString;
            return this;
        }

        public Builder setProjectsString(String string) {
            this.projectsString = string;
            return this;
        }

        public Builder setSignOnClick(View.OnClickListener onClickListener) {
            this.signOnClick = onClickListener;
            return this;
        }

        public ShowCustomerProjectsDialog createDialog() {

            ShowCustomerProjectsDialog dialog = new ShowCustomerProjectsDialog(context, R.style.ShowDetailsCustomer_Dialog);
            View contentView = LayoutInflater.from(context).inflate(R.layout.customer_projects_show_dialog, null);
            dialog.setContentView(contentView);
            ButterKnife.bind(this, contentView);

            //修改
            if (customerProjectsItem != null) {
                title.setText(titleString);

                projectText.setVisibility(View.VISIBLE);
                spProjectsLayout.setVisibility(View.GONE);

                projectText.setText(customerProjectsItem.getCustomerProjectBean().getcProjects());

                if (customerProjectsItem.iscCardYearFlag()) {
                    CommonUtil.itemUnCheck(spNo);
                    spYes.setChecked(true);
                    endTimeLayout.setVisibility(View.VISIBLE);

                } else {
                    CommonUtil.itemUnCheck(spYes);
                    spNo.setChecked(true);
                    endTimeLayout.setVisibility(View.GONE);
                }

                if (customerProjectsItem.iscCardFlag()) {
                    CommonUtil.itemUnCheck(spCardNo);
                    spCardYes.setChecked(true);
                    countLayout.setVisibility(View.VISIBLE);
                    spCountLayout.setVisibility(View.VISIBLE);
                } else {
                    CommonUtil.itemUnCheck(spCardYes);
                    spCardNo.setChecked(true);
                    countLayout.setVisibility(View.GONE);
                    spCountLayout.setVisibility(View.GONE);
                }

                if (customerProjectsItem.iscSignFlag()) {
                    signText.setTextColor(context.getResources().getColor(R.color.text_board));
                    signText.setText("已签字");
                } else {
                    signText.setTextColor(context.getResources().getColor(R.color.primary));
                    signText.setText("未签字");

                }

                if (customerProjectsItem.isStages()) {
                    //默认不分期
                    CommonUtil.itemUnCheck(spStagesNo);
                    spStagesYes.setChecked(true);
                    stagesSurplusMoneyLayout.setVisibility(View.VISIBLE);
                } else {
                    //默认不分期
                    CommonUtil.itemUnCheck(spStagesYes);
                    spStagesNo.setChecked(true);
                    stagesSurplusMoneyLayout.setVisibility(View.GONE);
                }

                edMoney.setText(customerProjectsItem.getcMoney() + "");
                edUseCount.setText(customerProjectsItem.getcUseCount() + "");
                tCount.setText(customerProjectsItem.getcCount() + "");
                stagesMoney.setText(customerProjectsItem.getcStagesMoney() + "");
                stagesSurplusMoneyTxt.setText(customerProjectsItem.getcStagesSurplusMoney() + "");
                spTime.setText(customerProjectsItem.getCreatedAt());
                spEndTime.setText(customerProjectsItem.getcEndTime());
                btnYes.setText(yesString);
                signText.setOnClickListener(signOnClick);


            } else {
                projectText.setVisibility(View.GONE);
                spProjectsLayout.setVisibility(View.VISIBLE);

                //添加时隐藏签字
                signLayout.setVisibility(View.GONE);


                if (spStrings == null) {
                    spStrings = new ArrayList<>();
                    if (spStrings.size() == 0) {
                        spStrings.add("无");
                    }
                }

                spinnerProjects1.attachDataSource(spStrings);

                //默认不年卡
                CommonUtil.itemUnCheck(spYes);
                spNo.setChecked(true);

                //默认不分期
                CommonUtil.itemUnCheck(spStagesYes);
                spStagesNo.setChecked(true);

                //默认不办卡
                CommonUtil.itemUnCheck(spCardYes);
                spCardNo.setChecked(true);


                if (!StringUtils.isEmpty(edMoney.getText().toString()) && !StringUtils.isEmpty(stagesMoney.getText().toString())) {
                    float f = Float.parseFloat(edMoney.getText().toString()) - Float.parseFloat(stagesMoney.getText().toString());
                    if (f > 0) {
                        stagesSurplusMoneyTxt.setText(f + "");
                    } else {
                        stagesSurplusMoneyTxt.setText(0 + "");
                    }
                } else {
                    stagesSurplusMoneyTxt.setText(0 + "");
                }

            }
            edMoney.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    Log.e(TAG, "afterTextChanged:edMoney总额" + s);

                    if (!StringUtils.isEmpty(s.toString())) {
                        float f = Float.parseFloat(s.toString()) - Float.parseFloat(stagesMoney.getText().toString());
                        if (f > 0) {
                            stagesSurplusMoneyTxt.setText(f + "");
                        } else {
                            stagesSurplusMoneyTxt.setText(0 + "");
                        }
                    } else {
                        stagesSurplusMoneyTxt.setText(0 + "");
                    }
                }
            });
            stagesMoney.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    Log.e(TAG, "afterTextChanged:stagesMoney缴费金额" + s);

                    if (!StringUtils.isEmpty(s.toString())) {
                        float f = Float.parseFloat(edMoney.getText().toString()) - Float.parseFloat(s.toString());
                        if (f > 0) {
                            stagesSurplusMoneyTxt.setText(f + "");
                        } else {
                            stagesSurplusMoneyTxt.setText(0 + "");
                        }
                    } else {
                        stagesSurplusMoneyTxt.setText(0 + "");
                    }
                }

            });
            reduceImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (edUseCount.getText() != null && !edUseCount.getText().toString().equals("")) {

                        int i = Integer.parseInt(edUseCount.getText().toString()) - 1;
                        edUseCount.setText(i >= 0 ? "" + i : 0 + "");

                    } else {
                        ToastUtils.showToast(context, "请先输入次数!", false);
                    }
                }
            });
            plusImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (edUseCount.getText() != null && !edUseCount.getText().toString().equals("")) {

                        if (tCount.getText() != null && !tCount.getText().toString().equals("")) {
                            int i = Integer.parseInt(edUseCount.getText().toString()) + 1;
                            int t = Integer.parseInt(tCount.getText().toString());
                            if (i > t) {
                                ToastUtils.showToast(context, "不能大于总次数!", false);
                                edUseCount.setText(tCount.getText().toString());
                            } else {
                                edUseCount.setText(i >= 0 ? "" + i : 0 + "");
                            }
                        } else {
                            int i = Integer.parseInt(edUseCount.getText().toString()) + 1;
                            edUseCount.setText(i >= 0 ? "" + i : 0 + "");
                        }
                    } else {
                        ToastUtils.showToast(context, "请先输入次数!", false);
                    }
                }
            });
            spStartImg.setOnClickListener(edAddStartTimeOnClick);
            spEndImg.setOnClickListener(edAddTimeProjectsOnClick);
//            spStartImg.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    setOnclick(v, 0);
//                }
//            });
//
//            spEndImg.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    setOnclick(v, 1);
//                }
//            });
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            btnYes.setOnClickListener(yesOnClick);


            DisplayMetrics d = context.getResources().getDisplayMetrics();  //为获取屏幕宽、高
            WindowManager.LayoutParams p = dialog.getWindow().getAttributes();  //获取对话框当前的参数值
            p.height = (int) (d.heightPixels * 0.80);   //高度设置为屏幕的0.35
            p.width = (int) (d.widthPixels * 0.90);    //宽度设置为屏幕的0.35
            dialog.getWindow().setAttributes(p);     //设置生效

            return dialog;

        }

        @OnClick({R.id.sp_yes, R.id.sp_no, R.id.sp_stages_yes, R.id.sp_stages_no, R.id.sp_card_yes, R.id.sp_card_no})

        public void onViewClicked(View view) {

            switch (view.getId()) {

                //年卡
                case R.id.sp_yes:
                    CommonUtil.itemUnCheck(spNo);
                    spYes.setChecked(true);
                    String ty = (String) spYes.getTag();
                    Log.e(TAG, "onCheckedChanged:ty " + ty);
                    endTimeLayout.setVisibility(View.VISIBLE);

                    break;

                case R.id.sp_no:
                    CommonUtil.itemUnCheck(spYes);
                    spNo.setChecked(true);
                    String tn = (String) spNo.getTag();
                    Log.e(TAG, "onCheckedChanged:tn " + tn);
                    endTimeLayout.setVisibility(View.GONE);
                    break;

                //分期
                case R.id.sp_stages_yes:
                    CommonUtil.itemUnCheck(spStagesNo);
                    spStagesYes.setChecked(true);
                    String sy = (String) spStagesYes.getTag();
                    Log.e(TAG, "onCheckedChanged:sy " + sy);
                    stagesSurplusMoneyLayout.setVisibility(View.VISIBLE);

                    break;

                case R.id.sp_stages_no:

                    CommonUtil.itemUnCheck(spStagesYes);
                    spStagesNo.setChecked(true);
                    String sn = (String) spStagesNo.getTag();
                    Log.e(TAG, "onCheckedChanged: sn" + sn);
                    stagesSurplusMoneyLayout.setVisibility(View.GONE);

                    break;

                //办卡
                case R.id.sp_card_yes:
                    CommonUtil.itemUnCheck(spCardNo);
                    spCardYes.setChecked(true);
                    String scy = (String) spCardYes.getTag();
                    Log.e(TAG, "onCheckedChanged: scy" + scy);
                    countLayout.setVisibility(View.VISIBLE);
                    spCountLayout.setVisibility(View.VISIBLE);

                    break;

                case R.id.sp_card_no:
                    CommonUtil.itemUnCheck(spCardYes);
                    spCardNo.setChecked(true);
                    String scn = (String) spCardNo.getTag();
                    Log.e(TAG, "onCheckedChanged: scn" + scn);
                    countLayout.setVisibility(View.GONE);
                    spCountLayout.setVisibility(View.GONE);

                    break;


            }
        }


//        //时间选择框
//        public void setOnclick(View v, final int count) {
//
//            TimePickerPopWin timePickerPopWin = new TimePickerPopWin.Builder(context,true, new TimePickerPopWin.OnTimePickListener() {
//                @Override
//                public void onTimePickCompleted(int year, int month, int day, int hour, int minute, int second, String time) {
//                    Toast.makeText(context, time, Toast.LENGTH_SHORT).show();
//                    if (count == 0) {
//                        spTime.setText(time);
//                    } else if (count == 1) {
//                        spEndTime.setText(time);
//                    }
//                }
//            }).btnTextSize(16)
//                    .viewTextSize(25)
//                    .colorCancel(Color.parseColor("#999999"))
//                    .colorConfirm(Color.parseColor("#009900"))
//                    .build();
//            timePickerPopWin.showPopWin((Activity) context);
//        }

    }

}

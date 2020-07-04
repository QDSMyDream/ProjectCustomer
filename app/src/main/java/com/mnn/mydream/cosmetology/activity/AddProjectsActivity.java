package com.mnn.mydream.cosmetology.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.applandeo.materialcalendarview.view.NiceSpinner;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.mnn.mydream.cosmetology.R;
import com.mnn.mydream.cosmetology.adapter.CustomerPeojectsListAdapter;
import com.mnn.mydream.cosmetology.bean.Customer;
import com.mnn.mydream.cosmetology.bean.CustomerAndProject;
import com.mnn.mydream.cosmetology.bean.CustomerProjectBean;
import com.mnn.mydream.cosmetology.bean.CustomerProjectsItem;
import com.mnn.mydream.cosmetology.dialog.AddSignDialog;
import com.mnn.mydream.cosmetology.dialog.AddTimeDialog;
import com.mnn.mydream.cosmetology.dialog.CommonDialog;
import com.mnn.mydream.cosmetology.dialog.DeleteDialog;
import com.mnn.mydream.cosmetology.dialog.ShowCustomerProjectsDialog;
import com.mnn.mydream.cosmetology.pickertime.TimePickerPopWin;
import com.mnn.mydream.cosmetology.utils.CommonUtil;
import com.mnn.mydream.cosmetology.utils.ImageLoader;
import com.mnn.mydream.cosmetology.utils.ToastUtils;
import com.mnn.mydream.cosmetology.utils.Tools;
import com.mnn.mydream.cosmetology.view.CircleImageView;
import com.mnn.mydream.cosmetology.view.DrawView;
import com.mnn.mydream.cosmetology.view.ExpandListView;
import com.zhy.android.percent.support.PercentLinearLayout;
import com.zhy.android.percent.support.PercentRelativeLayout;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;


/**
 * 创建人 :MyDream
 * 创建时间：2020/5/3 18:18
 * 类描述：添加项目信息activity
 */

public class AddProjectsActivity extends AppCompatActivity {
    private String TAG = "AddProjectsActivity";

    @BindView(R.id.add_content)
    EditText mAddContent;
    @BindView(R.id.num)
    TextView mNum;
    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.title_text)
    TextView titleText;
    @BindView(R.id.record_toolbar)
    PercentRelativeLayout recordToolbar;
    @BindView(R.id.img_photo)
    CircleImageView imgPhoto;
    @BindView(R.id.layout_photo)
    PercentRelativeLayout layoutPhoto;
    @BindView(R.id.name_ed)
    AutoCompleteTextView nameEd;
    @BindView(R.id.phone_ed)
    EditText phoneEd;
    @BindView(R.id.name_layout)
    PercentLinearLayout nameLayout;
    @BindView(R.id.male)
    CheckBox male;
    @BindView(R.id.femle)
    CheckBox femle;
    @BindView(R.id.bir_txt)
    TextView birTxt;
    @BindView(R.id.bir_img)
    ImageView birImg;

    @BindView(R.id.age)
    EditText age;

    @BindView(R.id.sp_count)
    EditText spCount;

    @BindView(R.id.count)
    EditText count;

    @BindView(R.id.time_text)
    TextView timeText;

    @BindView(R.id.time_png)
    ImageView timePng;
    @BindView(R.id.time_layout)
    PercentLinearLayout timeLayout;
    @BindView(R.id.add_projects)
    ImageView addProjects;
    @BindView(R.id.title_layout)
    PercentLinearLayout titleLayout;
    @BindView(R.id.sp_list)
    ExpandListView spList;
    @BindView(R.id.projects_layout)
    PercentLinearLayout projectsLayout;
    @BindView(R.id.add_pursh)
    ImageView addPursh;
    @BindView(R.id.sp_pursh_list)
    ExpandListView spPurshList;
    @BindView(R.id.money_ed)
    TextView moneyEd;
    @BindView(R.id.createtime_text)
    TextView createtimeText;
    @BindView(R.id.updatetime_text)
    TextView updatetimeText;
    @BindView(R.id.login_loginBtn)
    Button loginLoginBtn;
    @BindView(R.id.myScrollView)
    ScrollView myScrollView;
    @BindView(R.id.btn_cancel)
    TextView btnCancel;
    @BindView(R.id.btn_yes)
    TextView btnYes;
    @BindView(R.id.complete)
    TextView complete;
    @BindView(R.id.no_projects_layout)
    PercentLinearLayout noProjectsLayout;
    @BindView(R.id.view_line_list)
    View viewLineList;
    @BindView(R.id.no_group_projects_layout)
    PercentLinearLayout noGroupProjectsLayout;

    private CustomerAndProject customerAndProject;

    private CustomerPeojectsListAdapter customerPeojectsListAdapter;

    private List<CustomerProjectsItem> customerProjectsItems = new ArrayList<>();//客户做的项目bean

    private List<CustomerProjectBean> customerProjectsList = new ArrayList<>();//总项目bean

    private ShowCustomerProjectsDialog showCustomerProjectsDialog;//添加项目记录弹窗

    private ShowCustomerProjectsDialog.Builder showCustomerProjectsDialogbuild;

    private List<String> customerProjectsStrings = new ArrayList<>();//总项目名称显示

    private Customer customer;

    //选择时间弹窗
    private AddTimeDialog addTimeDialog;
    private AddTimeDialog.Builder addTimeDialogBuild;

    private DeleteDialog deleteDialog;

    private CustomerProjectsItem selectCustomerProjectsItem;//当前处理的item

    private int selectCustomerProjectsItemId;

    private long signLong = 0;
    //头像地址
    String picPath = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_projects);
        ButterKnife.bind(this);

        customerAndProject = (CustomerAndProject) getIntent().getSerializableExtra("customerAndProject");
        customer = customerAndProject.getCustomer();

        nameEd.setText(customer.getName());
        phoneEd.setText(customer.getPhone());

        if (customer.getSex().equals("男")) {
            CommonUtil.itemUnCheck(femle);
            male.setChecked(true);
        } else {
            CommonUtil.itemUnCheck(male);
            femle.setChecked(true);
        }

        //加载图片
        ImageLoader.displayImageView(this, customer.getCustomer_tx(), imgPhoto);

        birTxt.setText(customer.getBirthday());
        age.setText(customer.getAge() + "");

        if (customer.getRemarks() == null || customer.getRemarks().equals("")) {
            mAddContent.setText("");
            mNum.setText("0");
        } else {
            mAddContent.setText(customer.getRemarks());
            mNum.setText(customer.getRemarks().length() + "");
        }

        mAddContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mNum.setText((s.length()) + "");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //添加项目列表
        if (customerAndProject.getProjectsTexts() != null) {
            customerProjectsItems = customerAndProject.getProjectsTexts();
        }

        //设置数据倒叙
        Collections.reverse(customerProjectsItems);
        customerPeojectsListAdapter = new CustomerPeojectsListAdapter(this, customerProjectsItems);
        spList.setAdapter(customerPeojectsListAdapter);

        //服务项目列表单机显示详情
        spList.setOnItemClickListener(onItemClickListener);

        //服务项目列表长安弹出删除
        spList.setOnItemLongClickListener(onItemLongClickListener);

        //缴费以及项目显示
        float money = 0;

        if (customerAndProject.getProjectsTexts() != null) {

            if (customerAndProject.getProjectsTexts().size() > 0) {

                for (int i = 0; i < customerAndProject.getProjectsTexts().size(); i++) {
                    money = money + customerAndProject.getProjectsTexts().get(i).getcMoney();
                }
                //客户总缴费
                moneyEd.setText(money + "");
                noProjectsLayout.setVisibility(View.GONE);
                viewLineList.setVisibility(View.VISIBLE);
                titleLayout.setVisibility(View.VISIBLE);

            } else {
                moneyEd.setText("无");
                noProjectsLayout.setVisibility(View.VISIBLE);
                viewLineList.setVisibility(View.GONE);
                titleLayout.setVisibility(View.GONE);
            }
        } else {
            //客户总缴费
            moneyEd.setText("无");
            noProjectsLayout.setVisibility(View.VISIBLE);
            viewLineList.setVisibility(View.GONE);
            titleLayout.setVisibility(View.GONE);
        }
        createtimeText.setText(customer.getCreatedAt());
        updatetimeText.setText(customer.getUpdatedAt());

        //创建项目弹窗
        showCustomerProjectsDialogbuild = new ShowCustomerProjectsDialog.Builder(AddProjectsActivity.this)
                .setCustomerItem(null)
                .setTitleString("添加服务项目弹窗")
                .edAddEndTimeOnClick(addTimeEndClickListener)
                .edAddStartTimeOnClick(addTimeStartClickListener)
                .setSignOnClick(signClickListener)
                .setYesString("确定")
                .setYesOnClick(clickListenerYes);

        defaultSelectProjects();//查询所有项目表

        //团购项目表

    }

    //服务项目点击 修改
    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectCustomerProjectsItemId = position;
            selectCustomerProjectsItem = (CustomerProjectsItem) customerPeojectsListAdapter.getItem(position);

            Log.e(TAG, "onItemClick: " + "点击" + selectCustomerProjectsItem.toString());
            //创建项目弹窗
            showCustomerProjectsDialogbuild = new ShowCustomerProjectsDialog.Builder(AddProjectsActivity.this)
                    .setCustomerItem(selectCustomerProjectsItem)
                    .setTitleString("修改服务项目弹窗")
                    .edAddEndTimeOnClick(addTimeEndClickListener)
                    .edAddStartTimeOnClick(addTimeStartClickListener)
                    .setSignOnClick(signClickListener)
                    .setYesString("修改")
                    .setYesOnClick(updateProjectClickListener);
            showCustomerProjectsDialog = showCustomerProjectsDialogbuild.createDialog();
            showCustomerProjectsDialog.show();
        }
    };

    //服务项目长按 删除
    private AdapterView.OnItemLongClickListener onItemLongClickListener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            Log.e(TAG, "onItemClick: " + "长按");
            selectCustomerProjectsItemId = position;
            selectCustomerProjectsItem = (CustomerProjectsItem) customerPeojectsListAdapter.getItem(position);
            DeleteDialog.Builder builder = new DeleteDialog.Builder(AddProjectsActivity.this);

            //设置对话框的标题
            builder.setTitleMsg("删除客户项目信息");

            //设置确定按钮
            builder.setYesOnClick(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteProjectsItem(selectCustomerProjectsItem);
                    deleteDialog.dismiss();
                }
            });

            //设置取消按钮
            builder.setCancelOnClick(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteDialog.dismiss();
                }
            });

            //使用创建器生成一个对话框对象
            deleteDialog = builder.createDialog();
            deleteDialog.show();

            return true;
        }
    };


    //查询总项目列表
    private void defaultSelectProjects() {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date createdAtDate = null;
        try {
            createdAtDate = sdf.parse(Tools.getSameDay());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        BmobDate bmobCreatedAtDate = new BmobDate(createdAtDate);
        BmobQuery<CustomerProjectBean> categoryBmobQuery = new BmobQuery<>();
        categoryBmobQuery.addWhereLessThanOrEqualTo("createdAt", bmobCreatedAtDate);
//        //返回50条数据，如果不加上这条语句，默认返回10条数据
//        categoryBmobQuery.setLimit(50);

        //只返回Person表的objectId这列的值
        BmobQuery<CustomerProjectBean> bmobQuery = new BmobQuery<CustomerProjectBean>();
        bmobQuery.addQueryKeys("cProjects");

        bmobQuery.findObjects(new FindListener<CustomerProjectBean>() {
            @Override
            public void done(List<CustomerProjectBean> object, BmobException e) {
                if (e == null) {
                    Log.e(TAG, "done: " + object.size());
                    customerProjectsList.clear();
                    if (object.size() > 0) {
                        customerProjectsList = object;
                        refreshHandler.sendEmptyMessage(0);
                    }
                } else {
                    ToastUtils.showToast(getBaseContext(), "拉取服务项目列表失败，请检查网络！", false);
//                    Toast.makeText(getBaseContext(), "拉取服务项目列表失败，请检查网络！" + e.getMessage(), Toast.LENGTH_LONG).show();
                    Log.e(TAG, "done: " + e.getMessage().toString());
                }
            }
        });

    }

    @OnClick({R.id.back, R.id.btn_cancel, R.id.btn_yes, R.id.bir_img, R.id.add_projects, R.id.complete, R.id.male, R.id.femle, R.id.img_photo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.img_photo:
                showPickerDialog();
                break;


            case R.id.btn_cancel:
                finish();
                break;

            case R.id.btn_yes:
                Log.e(TAG, "onViewClicked: " + customer.getObjectId());
                updateCustomer();//修改客户信息
                break;

            //提 交
            case R.id.complete:
                Log.e(TAG, "onViewClicked: " + customer.getObjectId());

                updateCustomer();//修改客户信息
                break;
            case R.id.bir_img:
                setOnclick(false);
                break;

            case R.id.add_projects:
                //创建项目弹窗
                showCustomerProjectsDialogbuild = new ShowCustomerProjectsDialog.Builder(AddProjectsActivity.this)
                        .setSpData(customerProjectsStrings)
                        .setCustomerItem(null)
                        .setTitleString("添加服务项目弹窗")
                        .edAddEndTimeOnClick(addTimeEndClickListener)
                        .edAddStartTimeOnClick(addTimeStartClickListener)
                        .setSignOnClick(signClickListener)
                        .setYesString("确定")
                        .setYesOnClick(clickListenerYes);

                showCustomerProjectsDialog = showCustomerProjectsDialogbuild.createDialog();
                showCustomerProjectsDialog.show();

                break;
            case R.id.male:

                CommonUtil.itemUnCheck(femle);
                male.setChecked(true);
                String ty = (String) male.getTag();
                Log.e(TAG, "onCheckedChanged:ty " + ty);

                break;
            case R.id.femle:

                CommonUtil.itemUnCheck(male);
                femle.setChecked(true);
                String tn = (String) femle.getTag();
                Log.e(TAG, "onCheckedChanged:tn " + tn);
                break;
            default:
                break;

        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        customerProjectsItems = savedInstanceState.getParcelableArrayList("cs");
        Log.e(TAG, "onRestoreInstanceState: " + customerProjectsItems.size());
        customerPeojectsListAdapter = new CustomerPeojectsListAdapter(this, customerProjectsItems);
        spList.setAdapter(customerPeojectsListAdapter);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelableArrayList("cs", (ArrayList<? extends Parcelable>) customerProjectsItems);

        Log.e(TAG, "onSaveInstanceState: ");
    }

    //时间选择框
    public void setOnclick(boolean b) {

        TimePickerPopWin timePickerPopWin = new TimePickerPopWin.Builder(this, b, new TimePickerPopWin.OnTimePickListener() {
            @Override
            public void onTimePickCompleted(int year, int month, int day, int hour, int minute, int second, String time) {

                if (Integer.parseInt(Tools.getSameYear()) > year) {
                    age.setText((Integer.parseInt(Tools.getSameYear()) - year) + "");
                    birTxt.setText(time);
                } else {
                    birTxt.setText("");
                    ToastUtils.showToast(getBaseContext(), "生日选择有误,请重新选择", false);
                }

            }
        }).btnTextSize(16)
                .viewTextSize(25)
                .colorCancel(Color.parseColor("#999999"))
                .colorConfirm(Color.parseColor("#009900"))
                .build();
        timePickerPopWin.showPopWin(this);
    }


    ///刷新Handler
    @SuppressLint("HandlerLeak")
    private Handler refreshHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0://1s刷新

                    customerProjectsStrings.clear();
                    for (CustomerProjectBean customerProjects : customerProjectsList) {
                        customerProjectsStrings.add(customerProjects.getcProjects());
                    }
                    //创建项目弹窗
                    showCustomerProjectsDialogbuild.setSpData(customerProjectsStrings);

                    break;

                //添加
                case 1:

                    customerProjectsItems.add(selectCustomerProjectsItem);
                    //设置数据倒叙
                    Collections.reverse(customerProjectsItems);
                    customerPeojectsListAdapter = new CustomerPeojectsListAdapter(getBaseContext(), customerProjectsItems);
                    spList.setAdapter(customerPeojectsListAdapter);


                    //缴费以及项目显示
                    float money = 0;
                    if (customerProjectsItems.size() > 0) {
                        if (customerProjectsItems.size() > 0) {
                            for (int i = 0; i < customerProjectsItems.size(); i++) {
                                money = money + customerProjectsItems.get(i).getcMoney();
                            }
                            //客户总缴费
                            moneyEd.setText(money + "");
                            noProjectsLayout.setVisibility(View.GONE);
                            viewLineList.setVisibility(View.VISIBLE);
                            titleLayout.setVisibility(View.VISIBLE);

                        } else {
                            moneyEd.setText("无");
                            noProjectsLayout.setVisibility(View.VISIBLE);
                            viewLineList.setVisibility(View.GONE);
                            titleLayout.setVisibility(View.GONE);

                        }
                    } else {
                        //客户总缴费
                        moneyEd.setText("无");
                        noProjectsLayout.setVisibility(View.VISIBLE);
                        viewLineList.setVisibility(View.GONE);
                        titleLayout.setVisibility(View.GONE);
                    }

                    break;

                //删除
                case 2:

                    customerPeojectsListAdapter.deleteView(selectCustomerProjectsItemId, spList);

                    //缴费以及项目显示
                    float deleteMoney = 0;
                    if (customerProjectsItems.size() > 0) {
                        if (customerProjectsItems.size() > 0) {
                            for (int i = 0; i < customerProjectsItems.size(); i++) {
                                deleteMoney = deleteMoney + customerProjectsItems.get(i).getcMoney();
                            }
                            //客户总缴费
                            moneyEd.setText(deleteMoney + "");
                            noProjectsLayout.setVisibility(View.GONE);
                            viewLineList.setVisibility(View.VISIBLE);
                            titleLayout.setVisibility(View.VISIBLE);

                        } else {
                            moneyEd.setText("无");
                            noProjectsLayout.setVisibility(View.VISIBLE);
                            viewLineList.setVisibility(View.GONE);
                            titleLayout.setVisibility(View.GONE);

                        }
                    } else {
                        //客户总缴费
                        moneyEd.setText("无");
                        noProjectsLayout.setVisibility(View.VISIBLE);
                        viewLineList.setVisibility(View.GONE);
                        titleLayout.setVisibility(View.GONE);

                    }

                    break;


                //修改
                case 3:
                    customerPeojectsListAdapter.updataView(selectCustomerProjectsItemId, spList, selectCustomerProjectsItem);
                    break;

                //签名
                case 4:
                    setSignProject();
                    break;

                default:
                    break;
            }
        }
    };


    //添加项目 开始时间
    private View.OnClickListener addTimeStartClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //添加项目弹窗
            TextView textView = showCustomerProjectsDialog.findViewById(R.id.sp_time);
            addTimeDialogBuild = new AddTimeDialog.Builder(AddProjectsActivity.this, true, textView);
            addTimeDialog = addTimeDialogBuild.createDialog();
            addTimeDialog.show();
        }
    };

    //添加项目 结束时间
    private View.OnClickListener addTimeEndClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //添加项目弹窗
            TextView textView = showCustomerProjectsDialog.findViewById(R.id.sp_end_time);
            addTimeDialogBuild = new AddTimeDialog.Builder(AddProjectsActivity.this, true, textView);
            addTimeDialog = addTimeDialogBuild.createDialog();
            addTimeDialog.show();
        }
    };

    //签字点击
    private View.OnClickListener signClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //添加项目弹窗
            TextView textView = showCustomerProjectsDialog.findViewById(R.id.sign_text);

            if (textView.getText().toString().equals("未签字")) {

                long secondTime = System.currentTimeMillis();
                if (secondTime - signLong > 2000) {
                    ToastUtils.showToast(getBaseContext(), "再次点击将签名", false);
                    signLong = secondTime;
                    return;

                } else {
                    refreshHandler.sendEmptyMessage(5);
                }

            }

            Log.e(TAG, "onClick: 签字" + textView.getText().toString());
        }
    };

    //添加项目弹窗 确定
    private View.OnClickListener clickListenerYes = new View.OnClickListener() {

        @SuppressLint("ResourceAsColor")
        @Override
        public void onClick(View v) {

            CustomerProjectsItem customerProjectsItem = new CustomerProjectsItem();

            NiceSpinner spinner = showCustomerProjectsDialog.findViewById(R.id.spinner_projects1);//项目名称

            String projects = spinner.getSelectedItem().toString();

            CustomerProjectBean customerProjectBean = new CustomerProjectBean();

            for (CustomerProjectBean customerProjectBeans : customerProjectsList) {

                if (projects.equals(customerProjectBeans.getcProjects())) {
                    customerProjectBean = customerProjectBeans;
                }
            }

            TextView spStartTime = showCustomerProjectsDialog.findViewById(R.id.sp_time);

            TextView spEndTime = showCustomerProjectsDialog.findViewById(R.id.sp_end_time);

            EditText edMoney = showCustomerProjectsDialog.findViewById(R.id.ed_money);//项目总价格

            CheckBox isYearCard = showCustomerProjectsDialog.findViewById(R.id.sp_yes);

            boolean isCardYearFlag = isYearCard.isChecked() ? true : false;//是否年卡

            CheckBox isCard = showCustomerProjectsDialog.findViewById(R.id.sp_card_yes);

            boolean isCardFlag = isCard.isChecked() ? true : false;   //是否办卡

            CheckBox isCardStages = showCustomerProjectsDialog.findViewById(R.id.sp_stages_yes);//是否分期

            boolean isCardStagesFlag = isCardStages.isChecked() ? true : false;//是否是分期

            EditText edUseCount = showCustomerProjectsDialog.findViewById(R.id.ed_use_count);//已用次数

            EditText edTCount = showCustomerProjectsDialog.findViewById(R.id.t_count);//总次数

            TextView stagesSurplusMoneyTxt = showCustomerProjectsDialog.findViewById(R.id.stages_surplus_money_txt);//分期剩余金额

            EditText stagesMoney = showCustomerProjectsDialog.findViewById(R.id.stages_money);//缴费金额


            if (edMoney.getText() == null || edMoney.getText().toString().equals("")) {
                ToastUtils.showToast(getBaseContext(), "请输入项目总价格", false);
                return;
            }

            if (spStartTime.getText() == null || spStartTime.getText().toString().equals("")) {
                ToastUtils.showToast(getBaseContext(), "请选择办理时间", false);
                return;
            }

            if (stagesMoney.getText() == null || stagesMoney.getText().toString().equals("")) {
                ToastUtils.showToast(getBaseContext(), "请输入缴费金额", false);
                return;
            }

            //缴费金额大于总金额
            if (Float.parseFloat(edMoney.getText().toString()) < Float.parseFloat(stagesMoney.getText().toString())) {
                ToastUtils.showToast(getBaseContext(), "缴费金额不能大于总金额", false);
                return;
            }

            //判断是否次卡
            if (isCardFlag) {
                if (edTCount.getText() == null || edTCount.getText().toString().equals("")) {
                    ToastUtils.showToast(getBaseContext(), "请输入总次数", false);
                    return;
                }
                if (edUseCount.getText() == null || edUseCount.getText().toString().equals("")) {
                    ToastUtils.showToast(getBaseContext(), "请输入已用次数", false);
                    return;
                }
                //已用次数不能大于总次数
                if (Integer.parseInt(edTCount.getText().toString()) < Integer.parseInt(edUseCount.getText().toString())) {
                    ToastUtils.showToast(getBaseContext(), "已用次数不能大于总次数", false);
                    return;
                }
                //是否办卡
                customerProjectsItem.setcCardFlag(isCardFlag);
                //已用次数
                customerProjectsItem.setcUseCount(Integer.parseInt(edUseCount.getText().toString()));
                //总次数
                customerProjectsItem.setcCount(Integer.parseInt(edTCount.getText().toString()));
            }

            //是否是年卡
            if (isCardYearFlag) {
                if (spEndTime.getText() == null || spEndTime.getText().toString().equals("")) {
                    ToastUtils.showToast(getBaseContext(), "请选择结束时间", false);
                    return;
                }
                if (Tools.date2TimeStamp2(spEndTime.getText().toString()) < Tools.date2TimeStamp2(spStartTime.getText().toString())) {
                    ToastUtils.showToast(getBaseContext(), "结束时间不能小于办理时间！", false);
                    return;
                }
                //是否是年卡
                customerProjectsItem.setcEndTime(spEndTime.getText().toString());
                //是否是年卡
                customerProjectsItem.setcCardYearFlag(isCardYearFlag);
            }

            //是否是分期
            if (isCardStagesFlag) {
                if (Float.parseFloat(edMoney.getText().toString()) == Float.parseFloat(stagesMoney.getText().toString())) {
                    ToastUtils.showToast(getBaseContext(), "缴费金额与总金额相同,请取消勾选分期!", false);
                    return;
                }
                //分期
                customerProjectsItem.setStages(isCardStagesFlag);
                //分期剩余金额
                customerProjectsItem.setcStagesSurplusMoney(Float.parseFloat(stagesSurplusMoneyTxt.getText().toString()));
            }

            //项目名字
            customerProjectsItem.setCustomerProjectBean(customerProjectBean);

            //项目总金额
            customerProjectsItem.setcMoney(Float.parseFloat(edMoney.getText().toString()));
            //开始时间
            customerProjectsItem.setcStartTime(spStartTime.getText().toString());
            //缴费金额
            customerProjectsItem.setcStagesMoney(Float.parseFloat(stagesMoney.getText().toString()));
            customerProjectsItem.setcSignFlag(false);
            customerProjectsItem.setCustomer(customer);

            Log.e(TAG, "onClick: " + customerProjectsItems.size());
            saveProjectsItem(customerProjectsItem);//添加服务项目

            showCustomerProjectsDialog.dismiss();

        }
    };

    //修改项目弹窗 修改
    private View.OnClickListener updateProjectClickListener = new View.OnClickListener() {

        @SuppressLint("ResourceAsColor")
        @Override
        public void onClick(View v) {

            TextView signText = showCustomerProjectsDialog.findViewById(R.id.sign_text);

            TextView spStartTime = showCustomerProjectsDialog.findViewById(R.id.sp_time);

            TextView spEndTime = showCustomerProjectsDialog.findViewById(R.id.sp_end_time);

            EditText edMoney = showCustomerProjectsDialog.findViewById(R.id.ed_money);//项目总价格

            CheckBox isYearCard = showCustomerProjectsDialog.findViewById(R.id.sp_yes);

            boolean isCardYearFlag = isYearCard.isChecked() ? true : false;//是否年卡

            CheckBox isCard = showCustomerProjectsDialog.findViewById(R.id.sp_card_yes);

            boolean isCardFlag = isCard.isChecked() ? true : false;   //是否办卡

            CheckBox isCardStages = showCustomerProjectsDialog.findViewById(R.id.sp_stages_yes);//是否分期

            boolean isCardStagesFlag = isCardStages.isChecked() ? true : false;//是否是分期

            EditText edUseCount = showCustomerProjectsDialog.findViewById(R.id.ed_use_count);//已用次数

            EditText edTCount = showCustomerProjectsDialog.findViewById(R.id.t_count);//总次数

            TextView stagesSurplusMoneyTxt = showCustomerProjectsDialog.findViewById(R.id.stages_surplus_money_txt);//分期剩余金额

            EditText stagesMoney = showCustomerProjectsDialog.findViewById(R.id.stages_money);//缴费金额


            if (edMoney.getText() == null || edMoney.getText().toString().equals("")) {
                ToastUtils.showToast(getBaseContext(), "请输入项目总价格", false);
                return;
            }

            if (spStartTime.getText() == null || spStartTime.getText().toString().equals("")) {
                ToastUtils.showToast(getBaseContext(), "请选择办理时间", false);
                return;
            }

            if (stagesMoney.getText() == null || stagesMoney.getText().toString().equals("")) {
                ToastUtils.showToast(getBaseContext(), "请输入缴费金额", false);
                return;
            }


            //缴费金额大于总金额
            if (Float.parseFloat(edMoney.getText().toString()) < Float.parseFloat(stagesMoney.getText().toString())) {
                ToastUtils.showToast(getBaseContext(), "缴费金额不能大于总金额", false);
                return;
            }

            //项目总金额
            selectCustomerProjectsItem.setcMoney(Float.parseFloat(edMoney.getText().toString()));
            //开始时间
            selectCustomerProjectsItem.setcStartTime(spStartTime.getText().toString());
            //缴费金额
            selectCustomerProjectsItem.setcStagesMoney(Float.parseFloat(stagesMoney.getText().toString()));

            selectCustomerProjectsItem.setcSignFlag(signText.getText().toString().equals("已签字"));


            //判断是否次卡
            if (isCardFlag) {
                if (edTCount.getText() == null || edTCount.getText().toString().equals("")) {
                    ToastUtils.showToast(getBaseContext(), "请输入总次数", false);
                    return;
                }
                if (edUseCount.getText() == null || edUseCount.getText().toString().equals("")) {
                    ToastUtils.showToast(getBaseContext(), "请输入已用次数", false);
                    return;
                }
                //已用次数不能大于总次数
                if (Integer.parseInt(edTCount.getText().toString()) < Integer.parseInt(edUseCount.getText().toString())) {
                    ToastUtils.showToast(getBaseContext(), "已用次数不能大于总次数", false);
                    return;
                }
                //是否办卡
                selectCustomerProjectsItem.setcCardFlag(isCardFlag);
                //已用次数
                selectCustomerProjectsItem.setcUseCount(Integer.parseInt(edUseCount.getText().toString()));
                //总次数
                selectCustomerProjectsItem.setcCount(Integer.parseInt(edTCount.getText().toString()));
            } else {
                //是否办卡
                selectCustomerProjectsItem.setcCardFlag(isCardFlag);

            }

            //是否是年卡
            if (isCardYearFlag) {
                if (spEndTime.getText() == null || spEndTime.getText().toString().equals("")) {
                    ToastUtils.showToast(getBaseContext(), "请选择结束时间", false);
                    return;
                }
                if (Tools.date2TimeStamp2(spEndTime.getText().toString()) < Tools.date2TimeStamp2(spStartTime.getText().toString())) {
                    ToastUtils.showToast(getBaseContext(), "结束时间不能小于办理时间！", false);
                    return;
                }
                //到期时间
                selectCustomerProjectsItem.setcEndTime(spEndTime.getText().toString());
                //是否是年卡
                selectCustomerProjectsItem.setcCardYearFlag(isCardYearFlag);
            } else {

                //是否是年卡
                selectCustomerProjectsItem.setcCardYearFlag(isCardYearFlag);

            }

            //是否是分期
            if (isCardStagesFlag) {
                if (Float.parseFloat(edMoney.getText().toString()) == Float.parseFloat(stagesMoney.getText().toString())) {
                    ToastUtils.showToast(getBaseContext(), "缴费金额与总金额相同,请取消勾选分期!", false);
                    return;
                }
                //分期
                selectCustomerProjectsItem.setStages(isCardStagesFlag);
                //分期剩余金额
                selectCustomerProjectsItem.setcStagesSurplusMoney(Float.parseFloat(stagesSurplusMoneyTxt.getText().toString()));
            } else {
                //分期
                selectCustomerProjectsItem.setStages(isCardStagesFlag);
            }


            updateProjectsItem(selectCustomerProjectsItem);

            showCustomerProjectsDialog.dismiss();
        }
    };

    //删除客户服务列表
    private void deleteProjectsItem(CustomerProjectsItem customerProjectsItem) {
        Log.e(TAG, "deleteProjectsItem: " + customerProjectsItem.toString());
        customerProjectsItem.delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    ToastUtils.showToast(getBaseContext(), "删除成功", true);
//                    customer.getProjectsTexts().remove(customerProjectsItem);
                    refreshHandler.sendEmptyMessage(2);
                } else {
                    ToastUtils.showToast(getBaseContext(), "删除失败" + e.toString(), true);
                }
            }
        });
    }

    //修改客户服务列表
    private void updateProjectsItem(CustomerProjectsItem customerProjectsItem) {

        Log.e(TAG, "updateProjectsItem:修改 " + customerProjectsItem.toString());
        customerProjectsItem.update(new UpdateListener() {

            @Override
            public void done(BmobException e) {
                if (e == null) {
                    ToastUtils.showToast(getBaseContext(), "修改成功：" + customer.getName(), true);
                    refreshHandler.sendEmptyMessage(3);
                    Log.e("bmob", "成功");
                } else {
                    ToastUtils.showToast(getBaseContext(), "修改失败：" + customer.getName(), true);
                }
            }
        });


    }

    //保存客户服务列表
    private void saveProjectsItem(CustomerProjectsItem customerProjectsItem) {
        selectCustomerProjectsItem = customerProjectsItem;
        Log.e(TAG, "saveProjectsItem: " + customerProjectsItem);
        customerProjectsItem.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    ToastUtils.showToast(getBaseContext(), "添加(" + customerProjectsItem.getCustomerProjectBean().getcProjects() + ")服务项目成功!", true);
                    refreshHandler.sendEmptyMessage(1);
                    Log.e("bmob", "成功");
                } else {
                    ToastUtils.showToast(getBaseContext(), "添加(" + customerProjectsItem.getCustomerProjectBean().getcProjects() + ")服务项目失败" + e.toString(), true);
                }
            }
        });
    }

    //修改客户列表
    private void updateCustomer() {
        String name = nameEd.getText().toString();
        String phone = phoneEd.getText().toString();
        String sex = male.isChecked() ? "男" : "女";
        String bir = birTxt.getText().toString();
        String remarks = mAddContent.getText().toString();
        if (!Tools.getIsNull(name)) {
            ToastUtils.showToast(getBaseContext(), "请输入姓名！", false);
            return;
        }
        if (!Tools.getIsNull(phone)) {
            ToastUtils.showToast(getBaseContext(), "请输入手机号！", false);
            return;
        }
        if (!Tools.getIsNull(bir)) {
            ToastUtils.showToast(getBaseContext(), "请输入生日！", false);
            return;
        }

        int ages = 0;
        if (Tools.getIsNull(age.getText().toString())) {
            ages = Integer.parseInt(age.getText().toString());
        }
        customer.setCustomer_tx(null);
        customer.setName(name);
        customer.setPhone(phone);
        customer.setBirthday(bir);
        customer.setSex(sex);
        customer.setAge(ages);
        customer.setRemarks(remarks);

        customer.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    ToastUtils.showToast(getBaseContext(), "修改成功：" + customer.getName(), true);
                    Log.e("bmob", "成功");
                } else {
                    ToastUtils.showToast(getBaseContext(), "修改失败：" + customer.getName(), true);
                }
                finish();
            }
        });

    }

    //查询项目列表
    private void selsectCustomerProjects(BmobQuery<CustomerProjectBean> bmobQuery) {

        bmobQuery.findObjects(new FindListener<CustomerProjectBean>() {
            @Override
            public void done(List<CustomerProjectBean> object, BmobException e) {
                if (e == null) {
                    Log.e(TAG, "done: " + object.size());
                    customerProjectsList.clear();
                    if (object.size() > 0) {
                        customerProjectsList = object;
                        refreshHandler.sendEmptyMessage(0);
                    }
                } else {
                    ToastUtils.showToast(getBaseContext(), "拉取服务项目列表失败，请检查网络！", false);
//                    Toast.makeText(getBaseContext(), "拉取服务项目列表失败，请检查网络！" + e.getMessage(), Toast.LENGTH_LONG).show();
                    Log.e(TAG, "done: " + e.getMessage().toString());
                }
            }
        });

    }

    //查询项目记录列表
    private void selsectCustomerItem() {

        BmobQuery<CustomerProjectsItem> query = new BmobQuery<CustomerProjectsItem>();
        //用此方式可以构造一个BmobPointer对象。只需要设置objectId就行
        query.addWhereEqualTo("customer", new BmobPointer(customer));
        //希望同时查询该评论的发布者的信息，以及该帖子的作者的信息，这里用到上面`include`的并列对象查询和内嵌对象的查询
//            query.include("user,post.author");
        query.findObjects(new FindListener<CustomerProjectsItem>() {

            @Override
            public void done(List<CustomerProjectsItem> objects, BmobException e) {
                if (e == null) {
                    Log.e(TAG, "done: 成功" + objects.size());
                    customerAndProject.setProjectsTexts(objects);
                } else {
                    Log.e(TAG, "done: 失败" + objects.size());
                }

            }
        });

    }

    //添加项目记录弹窗
    private void setAddProjectDialog() {

    }

    //单个项目签名d
    private void setSignProject() {
        AddSignDialog.Builder addSignDialogBuild = new AddSignDialog.Builder(AddProjectsActivity.this);
        AddSignDialog addSignDialog = addSignDialogBuild.createDialog();
        addSignDialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
        addSignDialog.show();
        TextView textViewYes = addSignDialog.findViewById(R.id.btn_yes);
        DrawView drawView1 = addSignDialog.findViewById(R.id.mainView1);

        textViewYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Tools.saveBitmap(drawView1, customer.getName(), selectCustomerProjectsItem.getCustomerProjectBean().getcProjects());
//                selectCustomerProjectsItem.setcSignFlag(true);
                //添加项目弹窗
                TextView textView = showCustomerProjectsDialog.findViewById(R.id.sign_text);
                textView.setTextColor(getBaseContext().getResources().getColor(R.color.text_board));
                textView.setText("已签字");
                addSignDialog.dismiss();
            }
        });

    }


    /**
     * 显示对话框
     */
    private void showPickerDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.layout_picker_type4picture, null);

        CommonDialog.Builder builder = new CommonDialog.Builder(this);
        final CommonDialog dialog = builder
                .setStyle(R.style.FadeInPopWin)
                .setDialogMode(CommonDialog.DialogMode.MODE_BOTTOM)
                .setWidthPro(1.0f)
                .cancelTouchout(false)
                .setView(view)
                .build();

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.tv_album:
                        dialog.dismiss();
                        pickPictrue();
                        break;
                    case R.id.tv_camera:
                        dialog.dismiss();
                        takePictrue();
                        break;
                    case R.id.tv_cancel:
                        dialog.dismiss();
                        break;
                }
            }
        };

        view.findViewById(R.id.tv_album).setOnClickListener(listener);
        view.findViewById(R.id.tv_camera).setOnClickListener(listener);
        view.findViewById(R.id.tv_cancel).setOnClickListener(listener);


        dialog.show();
    }

    /**
     * 相册
     */
    private void pickPictrue() {
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .theme(R.style.PictureStyle)
                .imageSpanCount(4)// 每行显示个数 int
                .selectionMode(PictureConfig.SINGLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .previewImage(false)// 是否可预览图片 true or false
                .isCamera(false)// 是否显示拍照按钮 true or false
                .sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                .enableCrop(true)// 是否裁剪 true or false
                .withAspectRatio(500, 500)// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                .hideBottomControls(true)// 是否显示uCrop工具栏，默认不显示 true or false
                .freeStyleCropEnabled(true)// 裁剪框是否可拖拽 true or false
                .circleDimmedLayer(true)// 是否圆形裁剪 true or false
                .showCropFrame(false)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
                .previewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                .synOrAsy(true)//同步true或异步false 压缩 默认同步
                .cropWH(500, 500)// 裁剪宽高比，设置如果大于图片本身宽高则无效 int
                .rotateEnabled(true) // 裁剪是否可旋转图片 true or false
                .scaleEnabled(true)// 裁剪是否可放大缩小图片 true or false
                .isDragFrame(true)// 是否可拖动裁剪框(固定)
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code

    }

    /**
     * 拍照
     */
    private void takePictrue() {
        PictureSelector.create(this)
                .openCamera(PictureMimeType.ofImage())
                .theme(R.style.PictureStyle)
                .imageSpanCount(4)// 每行显示个数 int
                .selectionMode(PictureConfig.SINGLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .previewImage(false)// 是否可预览图片 true or false
                .isCamera(false)// 是否显示拍照按钮 true or false
                .sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                .enableCrop(true)// 是否裁剪 true or false
                .withAspectRatio(500, 500)// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                .hideBottomControls(true)// 是否显示uCrop工具栏，默认不显示 true or false
                .freeStyleCropEnabled(true)// 裁剪框是否可拖拽 true or false
                .circleDimmedLayer(true)// 是否圆形裁剪 true or false
                .showCropFrame(false)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
                .previewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                .synOrAsy(true)//同步true或异步false 压缩 默认同步
                .cropWH(500, 500)// 裁剪宽高比，设置如果大于图片本身宽高则无效 int
                .rotateEnabled(true) // 裁剪是否可旋转图片 true or false
                .scaleEnabled(true)// 裁剪是否可放大缩小图片 true or false
                .isDragFrame(true)// 是否可拖动裁剪框(固定)
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片、视频、音频选择结果回调
                    try {
                        List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                        // 例如 LocalMedia 里面返回三种path
                        // 1.media.getPath(); 为原图path
                        // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true  注意：音视频除外
                        // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true  注意：音视频除外
                        // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                        picPath = selectList.get(0).getCutPath();

                        Log.e(TAG, "onActivityResult: " + picPath);
                        if (picPath == null || picPath.equals("")) {
                            ToastUtils.showShort(this, "图片路径获取失败");
                            return;
                        }
                        //加载本地图片
                        ImageLoader.displayLocalImageViewCircle(this, imgPhoto, picPath);

                        //上传图片到服务器
                        uoloadImg(picPath);
                    } catch (Exception e) {
                        ToastUtils.showShort(this, "图片路径获取失败");

                    }
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    private void uoloadImg(String f) {

        BmobFile bmobFile = new BmobFile(new File(f));
        bmobFile.uploadblock(new UploadFileListener() {

            @Override
            public void done(BmobException e) {
                if (e == null) {
                    picPath = bmobFile.getFileUrl();
                    Log.e(TAG, "done: 上传成功" + bmobFile.getFileUrl());
                } else {
                    Log.e(TAG, "上传文件失败：" + e.getMessage());
                }

            }

            @Override
            public void onProgress(Integer value) {
                // 返回的上传进度（百分比）


            }
        });

    }


}

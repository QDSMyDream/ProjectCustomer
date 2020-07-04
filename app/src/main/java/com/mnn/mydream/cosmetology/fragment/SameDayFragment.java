package com.mnn.mydream.cosmetology.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnCalendarPageChangeListener;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.mnn.mydream.cosmetology.R;
import com.mnn.mydream.cosmetology.activity.AddProjectsActivity;
import com.mnn.mydream.cosmetology.adapter.SameDayListAdapter;
import com.mnn.mydream.cosmetology.bean.Customer;
import com.mnn.mydream.cosmetology.bean.CustomerAndProject;
import com.mnn.mydream.cosmetology.bean.CustomerProjectsItem;
import com.mnn.mydream.cosmetology.bean.SelectSignBean;
import com.mnn.mydream.cosmetology.dialog.AddSignDialog;
import com.mnn.mydream.cosmetology.dialog.DeleteDialog;
import com.mnn.mydream.cosmetology.dialog.MultipleSignDialog;
import com.mnn.mydream.cosmetology.interfaces.SetSameDayListOnClickListener;
import com.mnn.mydream.cosmetology.utils.DrawableUtils;
import com.mnn.mydream.cosmetology.utils.ToastUtils;
import com.mnn.mydream.cosmetology.utils.Tools;
import com.mnn.mydream.cosmetology.view.DrawView;
import com.zhy.android.percent.support.PercentLinearLayout;
import com.zhy.android.percent.support.PercentRelativeLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.CountListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.droidlover.xrecyclerview.XRecyclerView;


/**
 * @author My Dream
 * <p>
 * 当天客户记录
 */
@SuppressLint("ValidFragment")
public class SameDayFragment extends Fragment implements SetSameDayListOnClickListener, NavigationView.OnNavigationItemSelectedListener, OnDayClickListener {

    private String TAG = "SameDayFragment";

    private List<EventDay> events = new ArrayList<>();
    int eventsPos = 0;
    @BindView(R.id.total_text)
    TextView totalText;
    @BindView(R.id.total_current_query)
    TextView totalCurrentQuery;
    @BindView(R.id.toolbar_container)
    PercentRelativeLayout toolbarContainer;
    Unbinder unbinder1;
    @BindView(R.id.no_record)
    TextView noRecord;
    @BindView(R.id.same_day_recyclerView)
    XRecyclerView mSameDayRecyclerView;
    @BindView(R.id.item_name)
    TextView mItemName;
    @BindView(R.id.item_phone)
    TextView mItemPhone;
    @BindView(R.id.item_sex)
    TextView mItemSex;
    @BindView(R.id.item_tx)
    TextView mItemTx;
    @BindView(R.id.item_age_barthday)
    TextView mItemAgeBarthday;
    @BindView(R.id.item_age)
    TextView mItemAge;
    @BindView(R.id.item_time)
    TextView mItemTime;
    @BindView(R.id.item_money)
    TextView mItemMoney;
    @BindView(R.id.item_puse)
    TextView mItemPuse;
    @BindView(R.id.item_puse_projects)
    TextView mItemPuseProjects;
    @BindView(R.id.item_peojects)
    TextView mItemPeojects;
    @BindView(R.id.item_count)
    TextView mItemCount;
    @BindView(R.id.item_txt1)
    TextView mItemTxt1;
    @BindView(R.id.item_txt2)
    TextView mItemTxt2;
    @BindView(R.id.title_layout)
    PercentLinearLayout mTitleLayout;
    @BindView(R.id.same_list)
    ListView mSameList;

    NavigationView mNavView;
    @BindView(R.id.same_day_drawer_layout)
    DrawerLayout mSameDayDrawerLayout;

    private View myView;

    private Unbinder unbinder;

//  SameDayRecycleAdapter sameDayRecycleAdapter;

    private List<Customer> selectCustomers = new ArrayList<>();//查询今日所有顾客和项目


    private CalendarView calendarView;//时间控件

    private SameDayListAdapter sameDayListAdapter;

    private int conut;

    private DeleteDialog deleteDialog;

    private int listViewSelectPos = 0;//当前操作的选项item

    private MultipleSignDialog multipleSignDialog;

    private CustomerAndProject signCustomerAndProject;


    public List<SelectSignBean> selectSignBeans = new ArrayList<>();//未签字的项目列表


    private List<CustomerAndProject> customerAndProjects = new ArrayList<>();//显示bean


    @SuppressLint("ValidFragment")
    public SameDayFragment(NavigationView navView) {
        mNavView = navView;
    }

    public SameDayFragment() {

    }
//  public List<CustomerProjectsItem> selectNoSignItem = new ArrayList<>();//所有未签字的项目

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (myView == null) {
            myView = inflater.inflate(R.layout.same_day_fragment, container, false);
            unbinder = ButterKnife.bind(this, myView);
            initView();
        }
// 缓存的viewiew需要判断是否已经被加过parent，
// 如果有parent需要从parent删除，要不然会发生这个view已经有parent的错误。
        ViewGroup parent = (ViewGroup) myView.getParent();
        if (parent != null) {
            parent.removeView(myView);
        }


        unbinder1 = ButterKnife.bind(this, myView);
        return myView;
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initView() {

        calendarView = mNavView.getHeaderView(0).findViewById(R.id.same_day_calendarView);

        Calendar min = Calendar.getInstance();
        min.add(Calendar.MONTH, -36);
        Calendar max = Calendar.getInstance();
        max.add(Calendar.MONTH, 0);
        calendarView.setMinimumDate(min);
        calendarView.setMaximumDate(max);
//      calendarView.setEvents(events);
        calendarView.setDisabledDays(getDisabledDays());
        calendarView.setOnDayClickListener(this);
//        calendarView.setUpCalendarPosition(max);
//        calendarView.setOnForwardPageChangeListener(onCalendarForwardPageChangeListener);
//        calendarView.setOnPreviousPageChangeListener(onCalendarPreviousPageChangeListener);
        sameDayListAdapter = new SameDayListAdapter(getContext(), customerAndProjects, onClickListener, sameDayListOnLongClick);
        mSameList.setAdapter(sameDayListAdapter);

//        Log.e(TAG, "initView: " + calendarView.getFirstSelectedDate().getTimeInMillis());
//        Log.e(TAG, "initView: " + Tools.stampToDate((calendarView.getSelectedDates().get(0).getTimeInMillis() + 86400000)));
//        Log.e(TAG, "initView: "+calendarView.gett );
//        Tools.stampToDate(calendarView, Tools.stampToDate(((calendarView.getFirstSelectedDate().getTimeInMillis() / 1000) + 86400000)));
//
//        selectSameDayCustomer(Tools.getSameYMD() + " 00:00:00", Tools.getSameYMD() + " 23:59:59");
//
//        selectSameDayCustomer(Tools.stampToDate(calendarView.getSelectedDates().get(0).getTimeInMillis()), Tools.stampToDate((calendarView.getSelectedDates().get(0).getTimeInMillis() + 86400000)));
//        totalCurrentQuery.setText("当前查询日期:" + Tools.stampToDate(calendarView.getSelectedDates().get(0).getTimeInMillis()).substring(0, 10));
        Log.e(TAG, "onResume: " + calendarView.getSelectedDates().get(0).getTimeInMillis());

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        Log.e(TAG, "onHiddenChanged: " + hidden);
        super.onHiddenChanged(hidden);

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.e(TAG, "setUserVisibleHint: " + isVisibleToUser);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();

        selectSameDayCustomer(Tools.stampToDate(calendarView.getSelectedDates().get(0).getTimeInMillis()), Tools.stampToDate((calendarView.getSelectedDates().get(0).getTimeInMillis() + 86400000)));
        totalCurrentQuery.setText("当前查询日期:" + Tools.stampToDate(calendarView.getSelectedDates().get(0).getTimeInMillis()).substring(0, 10));

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    //查询某个时间段
    // addWhereLessThanOrEqualTo 小于等于
    // addWhereGreaterThanOrEqualTo 大于等于
    // String startTimes = "2020-05-08 00:00:01";
    // String endTimes = "2020-05-08 23:59:59";

    private void selectSameDayCustomer(String startTimes, String endTimes) {

        conut = 0;
//        noRecord.setText("正在刷新数据 请稍后...");
//        noRecord.setVisibility(View.VISIBLE);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date createdAtDate = null;
        Date createdAtDate2 = null;

        try {
            createdAtDate = sdf.parse(startTimes);
            createdAtDate2 = sdf.parse(endTimes);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        BmobDate bmobCreatedAtDate = new BmobDate(createdAtDate);
        BmobDate bmobCreatedAtDate2 = new BmobDate(createdAtDate2);

        BmobQuery<Customer> eq1 = new BmobQuery<Customer>();
        eq1.addWhereLessThan("createdAt", bmobCreatedAtDate2);

        BmobQuery<Customer> eq2 = new BmobQuery<Customer>();
        eq2.addWhereGreaterThanOrEqualTo("createdAt", bmobCreatedAtDate);

        List<BmobQuery<Customer>> andQuerys = new ArrayList<>();
        andQuerys.add(eq1);
        andQuerys.add(eq2);

        //查询符合整个and条件的人
        BmobQuery<Customer> query = new BmobQuery<>();
        query.and(andQuerys);

        query.findObjects(new FindListener<Customer>() {
            @Override
            public void done(List<Customer> object, BmobException e) {
                if (e == null) {
                    selectCustomers.clear();
                    selectCustomers = object;

                    refreshHandler.sendEmptyMessage(0);

                } else {
                    Toast.makeText(getContext(), "查询失败：" + e.getMessage() + "," + e.getErrorCode(), Toast.LENGTH_LONG).show();
                    Log.e(TAG, "查询失败：" + e.getMessage() + "," + e.getErrorCode());
                }

            }
        });


    }

    private List<Calendar> getDisabledDays() {
        List<Calendar> calendars = new ArrayList<>();
        return calendars;
    }

    ///刷新Handler
    @SuppressLint("HandlerLeak")
    private Handler refreshHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0://1s刷新
                    customerAndProjects.clear();
                    if (selectCustomers.size() == 0) {
//                        noRecord.setText("今日暂无顾客记录");
//                        noRecord.setVisibility(View.VISIBLE);
//                        mSameList.setVisibility(View.GONE);
                        selectCustomers.clear();
                        ToastUtils.showToast(getContext(), "暂无记录！", false);
                        sameDayListAdapter.notifyDataSetChanged();
                    } else {
                        for (Customer customer : selectCustomers) {
                            customerAndProjects.add(new CustomerAndProject(customer, null));
                        }

                        //设置数据倒叙
                        Collections.reverse(customerAndProjects);
                        Log.e(TAG, "handleMessage: ");
                        selectCustomerProjectItems();
                    }
                    break;

                case 1:
//
////                    mSameList.setVisibility(View.VISIBLE);
//                    Log.e(TAG, "handleMessage:1 ");
//                    sameDayListAdapter = new SameDayListAdapter(getContext(), selectCustomers, onClickListener, sameDayListOnLongClick);
//                    mSameList.setAdapter(sameDayListAdapter);
//                    if (selectCustomers.size() == 0) {
////                        noRecord.setText("今日暂无顾客记录");
////                        noRecord.setVisibility(View.VISIBLE);
////                        mSameList.setVisibility(View.GONE);
//                    }
                    break;
                case 2:
                    conut++;
                    if (conut == customerAndProjects.size()) {
                        sameDayListAdapter = new SameDayListAdapter(getContext(), customerAndProjects, onClickListener, sameDayListOnLongClick);
                        mSameList.setAdapter(sameDayListAdapter);
                    }
                    break;

                //删除customer
                case 4:
                    if (sameDayListAdapter != null) {
                        sameDayListAdapter.deleteView(listViewSelectPos, mSameList);
                    }
                    break;

                case 5:
                    AddSignDialog.Builder addSignDialogBuild = new AddSignDialog.Builder(getActivity());
                    AddSignDialog addSignDialog = addSignDialogBuild.createDialog();
                    addSignDialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
                    addSignDialog.show();
                    TextView textViewYes = addSignDialog.findViewById(R.id.btn_yes);
                    DrawView drawView1 = addSignDialog.findViewById(R.id.mainView1);

                    textViewYes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Log.e(TAG, "onClick: " + listViewSelectPos);
                            Log.e(TAG, "onClick: " + selectSignBeans.size());

                            StringBuilder projectName = new StringBuilder();

                            for (int i = 0; i < selectSignBeans.size(); i++) {

                                if (selectSignBeans.get(i).isSignFlag()) {

                                    signCustomerAndProject.getProjectsTexts().get(selectSignBeans.get(i).getId()).setcSignFlag(true);

                                    Log.e(TAG, "onClick: " + signCustomerAndProject.getProjectsTexts().get(selectSignBeans.get(i).getId()).toString());

                                    signCustomerAndProject.getProjectsTexts().get(selectSignBeans.get(i).getId()).update(new UpdateListener() {
                                        @Override
                                        public void done(BmobException e) {
                                            if (e == null) {

                                                ToastUtils.showToast(getContext(), "签字成功", true);
                                            } else {
                                                ToastUtils.showToast(getActivity(), "签字失败" + e.getMessage().toString(), false);
                                            }
                                        }
                                    });

                                }

                                projectName.append(signCustomerAndProject.getProjectsTexts().get(selectSignBeans.get(i).getId()).getCustomerProjectBean().getcProjects() + "_");
                            }

                            Tools.saveBitmap(drawView1, signCustomerAndProject.getCustomer().getName(), projectName.toString());

                            refreshHandler.sendEmptyMessage(6);

                            addSignDialog.dismiss();
                        }
                    });

                    break;


                case 6:
                    //更新数据
//                    sameDayListAdapter.updataView(listViewSelectPos, mSameList, signCustomer);
                    sameDayListAdapter = new SameDayListAdapter(getContext(), customerAndProjects, onClickListener, sameDayListOnLongClick);
                    mSameList.setAdapter(sameDayListAdapter);
//                    sameDayListAdapter.notifyDataSetChanged();
                    break;

                case 7:
                    eventsPos++;
                    if (eventsPos == events.size()) {
                        calendarView.setEvents(events);
                    } else {

                    }

                    break;
                default:
                    break;
            }
        }
    };


    @Override
    public void onDayClick(EventDay eventDay) {

        selectSameDayCustomer(Tools.stampToDate(eventDay.getCalendar().getTimeInMillis()), Tools.stampToDate((eventDay.getCalendar().getTimeInMillis() + 86400000)));
        totalCurrentQuery.setText("当前查询日期:" + Tools.stampToDate(eventDay.getCalendar().getTimeInMillis()).substring(0, 10));

        Log.e(TAG, "onDayClick: " + Tools.stampToDate(eventDay.getCalendar().getTimeInMillis()));
//        selectSameDayCustomer( Tools.stampToDate(eventDay.getCalendar().getTimeInMillis() + ""));

    }

    //list点击事件
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()) {

                case R.id.tv_sign:

                    signCustomerAndProject = (CustomerAndProject) sameDayListAdapter.getItem(mSameList.getPositionForView(v));
                    listViewSelectPos = mSameList.getPositionForView(v);

                    selectSignBeans.clear();

//                    selectSignItems.clear();

                    //判断
                    if (signCustomerAndProject.getProjectsTexts() == null) {
                        ToastUtils.showToast(getActivity(), "暂无未签字的项目", false);
                        return;
                    }

                    for (int i = 0; i < signCustomerAndProject.getProjectsTexts().size(); i++) {
                        if (!signCustomerAndProject.getProjectsTexts().get(i).iscSignFlag()) {
                            selectSignBeans.add(new SelectSignBean(i, signCustomerAndProject.getProjectsTexts().get(i).getCustomerProjectBean().getcProjects(), signCustomerAndProject.getProjectsTexts().get(i).iscSignFlag()));
                        }
                    }


                    Log.e(TAG, "onClick: " + listViewSelectPos + selectSignBeans.size() + signCustomerAndProject.getProjectsTexts().toString());
                    if (selectSignBeans.size() == 0) {
                        ToastUtils.showToast(getActivity(), "暂无未签字的项目", false);
                        return;
                    }


                    MultipleSignDialog.Builder multipleSignDialogBuild = new MultipleSignDialog.Builder(getActivity(), selectSignBeans);
                    multipleSignDialogBuild.setYesOnClick(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            boolean isSign = false;

                            for (SelectSignBean customerProjectsItem : selectSignBeans) {
                                Log.e(TAG, "onClick: selectSignItem" + customerProjectsItem.isSignFlag());
                                if (customerProjectsItem.isSignFlag()) {
                                    isSign = true;
                                }
                            }
                            if (isSign) {
                                //启动签名弹窗
                                refreshHandler.sendEmptyMessage(5);
                                multipleSignDialog.dismiss();

                            } else {

                                ToastUtils.showToast(getActivity(), "请选中项目签字", false);
                            }
                        }
                    });
                    multipleSignDialog = multipleSignDialogBuild.createDialog();
                    multipleSignDialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
                    multipleSignDialog.show();

                    break;
                case R.id.tv_top:

                    break;
                case R.id.tv_delete:


                    listViewSelectPos = mSameList.getPositionForView(v);

                    Log.e(TAG, "onClick: " + listViewSelectPos);
                    Customer customerDelete = selectCustomers.get(mSameList.getPositionForView(v));
                    DeleteDialog.Builder builder = new DeleteDialog.Builder(getActivity());

                    //设置对话框的标题
                    builder.setTitleMsg("删除客户信息以及项目记录");

                    //设置确定按钮
                    builder.setYesOnClick(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            deleteCustomer(customerDelete);

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

                    break;

                //客户详情信息界面
                case R.id.item_layout:

                    Intent Intent = new Intent(getActivity(), AddProjectsActivity.class);
                    Bundle bundle = new Bundle();
                    // Serializable传递对象

                    bundle.putSerializable("customerAndProject", (CustomerAndProject) sameDayListAdapter.getItem(mSameList.getPositionForView(v)));
                    Intent.putExtras(bundle);
                    startActivity(Intent);

                    Log.e(TAG, "onClick: " + sameDayListAdapter.getItem(mSameList.getPositionForView(v)));
                    break;
                default:
                    break;
            }
        }
    };

    //list点击事件
    private View.OnLongClickListener sameDayListOnLongClick = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            ToastUtils.showToast(getActivity(), mSameList.getPositionForView(v) + "这是长按事件", true);
            return false;
        }

    };

    @Override
    public void onLongClick(View v, int pos, Customer customer) {

    }

    @Override
    public void onClick(View v, int pos, Customer customer) {

    }


    //查询所作的项目
    private void selectCustomerProjectItems() {

        for (int i = 0; i < customerAndProjects.size(); i++) {

            CustomerAndProject customerAndProject = customerAndProjects.get(i);

            BmobQuery<CustomerProjectsItem> query = new BmobQuery<CustomerProjectsItem>();
            //用此方式可以构造一个BmobPointer对象。只需要设置objectId就行
            query.addWhereEqualTo("customer", new BmobPointer(customerAndProject.getCustomer()));
            //希望同时查询该评论的发布者的信息，以及该帖子的作者的信息，这里用到上面`include`的并列对象查询和内嵌对象的查询
//            query.include("user,post.author");
            query.include("customerProjectBean");

            query.findObjects(new FindListener<CustomerProjectsItem>() {

                @Override
                public void done(List<CustomerProjectsItem> objects, BmobException e) {
                    if (e == null) {
                        customerAndProject.setProjectsTexts(objects);
                        refreshHandler.sendEmptyMessage(2);
                    } else {


                    }
                }
            });

        }

    }

    //删除客户信息以及所做的项目
    private void deleteCustomer(Customer customer) {

        customer.delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {

                    ToastUtils.showToast(getContext(), "删除成功", true);

                    refreshHandler.sendEmptyMessage(4);

                } else {

                }
            }
        });

    }


    //前进
    private OnCalendarPageChangeListener onCalendarForwardPageChangeListener = new OnCalendarPageChangeListener() {
        @Override
        public void onChange() {
            Log.e(TAG, "onChange: onCalendarForwardPageChangeListener");
            Calendar c = Calendar.getInstance();
            c.set(calendarView.getCurrentPageDate().get(Calendar.YEAR), calendarView.getCurrentPageDate().get(Calendar.MONTH) + 1, 0);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    getEventData(c.get(Calendar.DAY_OF_MONTH), calendarView.getCurrentPageDate().getTimeInMillis(), c.get(Calendar.DAY_OF_MONTH) * (long) 86400000);
                }
            }).start();

        }
    };

    //后退
    private OnCalendarPageChangeListener onCalendarPreviousPageChangeListener = new OnCalendarPageChangeListener() {
        @Override
        public void onChange() {
            Log.e(TAG, "onChange: onCalendarPreviousPageChangeListener");
            Calendar c = Calendar.getInstance();
            c.set(calendarView.getCurrentPageDate().get(Calendar.YEAR), calendarView.getCurrentPageDate().get(Calendar.MONTH) + 1, 0);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    getEventData(c.get(Calendar.DAY_OF_MONTH), calendarView.getCurrentPageDate().getTimeInMillis(), c.get(Calendar.DAY_OF_MONTH) * (long) 86400000);
                }
            }).start();
        }
    };

    private List<EventDay> getEventData(int monDay, long startDayLong, long endDayLong) {

        Log.e(TAG, "getEventData:startDayLong " + startDayLong);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date createdAtDate = null;
        Date createdAtDate2 = null;

        for (int i = 0; i < monDay; i++) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date((startDayLong + ((long) 86400000 * i))));
            try {
                createdAtDate = sdf.parse(Tools.stampToDate(calendar.getTimeInMillis()));
                createdAtDate2 = sdf.parse(Tools.stampToDate(calendar.getTimeInMillis() + 86400000));

            } catch (ParseException e) {
                e.printStackTrace();
            }

            Log.e(TAG, "getEventData:createdAtDate " + createdAtDate.toString());
            Log.e(TAG, "getEventData:createdEndDate " + createdAtDate2.toString());

            BmobDate bmobCreatedAtDate = new BmobDate(createdAtDate);
            BmobDate bmobCreatedAtDate2 = new BmobDate(createdAtDate2);

            BmobQuery<Customer> eq1 = new BmobQuery<Customer>();
            eq1.addWhereLessThanOrEqualTo("createdAt", bmobCreatedAtDate2);

            BmobQuery<Customer> eq2 = new BmobQuery<Customer>();
            eq2.addWhereGreaterThanOrEqualTo("createdAt", bmobCreatedAtDate);

            List<BmobQuery<Customer>> andQuerys = new ArrayList<>();
            andQuerys.add(eq1);
            andQuerys.add(eq2);

            //查询符合整个and条件的人
            BmobQuery<Customer> query = new BmobQuery<>();
            query.and(andQuerys);

            query.count(Customer.class, new CountListener() {
                @Override
                public void done(Integer integer, BmobException e) {
                    if (e == null) {
//                        ToastUtils.showToast(getContext(), "查询成功" + integer, true);
                        if (integer == 0) {
                            events.add(new EventDay(calendar, DrawableUtils.getCircleDrawableWithTextNoText(getContext(), integer)));
                        } else {
                            events.add(new EventDay(calendar, DrawableUtils.getCircleDrawableWithText(getContext(), integer)));
                        }
                        refreshHandler.sendEmptyMessage(7);
                    } else {
                        Toast.makeText(getContext(), "查询失败：" + e.getMessage() + "," + e.getErrorCode(), Toast.LENGTH_LONG).show();
                        Log.e(TAG, "查询失败：" + e.getMessage() + "," + e.getErrorCode());
                    }
                }

            });

        }
        return events;
    }

}

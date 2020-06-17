package com.mnn.mydream.cosmetology.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.mnn.mydream.cosmetology.R;
import com.mnn.mydream.cosmetology.activity.ShowProjectsActivity;
import com.mnn.mydream.cosmetology.adapter.GeneralRecordCardListAdapter;
import com.mnn.mydream.cosmetology.adapter.GeneralRecordListAdapter;
import com.mnn.mydream.cosmetology.adapter.GeneralRecordPeriodListAdapter;
import com.mnn.mydream.cosmetology.adapter.GeneralRecordSingleListAdapter;
import com.mnn.mydream.cosmetology.adapter.GeneralRecordStagesListAdapter;
import com.mnn.mydream.cosmetology.adapter.GeneralRecordYearListAdapter;
import com.mnn.mydream.cosmetology.bean.Customer;
import com.mnn.mydream.cosmetology.bean.CustomerProjectsItem;
import com.mnn.mydream.cosmetology.bean.SectionDecorationRecycleTitle;
import com.mnn.mydream.cosmetology.interfaces.OnItemRecyclerViewClickListener;
import com.mnn.mydream.cosmetology.pickertime.TimePickerPopWin;
import com.mnn.mydream.cosmetology.utils.ToastUtils;
import com.mnn.mydream.cosmetology.utils.Tools;
import com.zhy.android.percent.support.PercentRelativeLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * 创建人 :MyDream
 * 创建时间：2020/5/4 18:18
 * 类描述：总记录fragment
 */
@SuppressLint("ValidFragment")
public class GeneralRecordFragment extends Fragment implements View.OnClickListener, OnItemRecyclerViewClickListener {
    private String TAG = "GeneralRecordFragment";

    @BindView(R.id.searchview)
    SearchView searchview;
    @BindView(R.id.toolbar_container)
    PercentRelativeLayout toolbarContainer;

    //@BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.ceneral_record_drawer)
    DrawerLayout ceneralRecordDrawer;
    @BindView(R.id.total_text)
    TextView totalText;
    @BindView(R.id.total_current_query)
    TextView totalCurrentQuery;
    @BindView(R.id.total_searchview_text)
    TextView totalSearchviewText;
    @BindView(R.id.general_listview)
    ListView generalListview;

    public static int SELECT_STATE = 0;//状态 0总结果 1查询结果 2顾客年卡项目 3顾客办卡项目 4顾客分期项目

    private String yearString, cardString, stagesString;

    private SectionDecorationRecycleTitle sectionDecorationRecycleTitle;

    //当前查询的客户资料列表
    private List<Customer> selectCustomers = new ArrayList<>();

    private Unbinder unbinder;

    private List<CustomerProjectsItem> customerProjectsItems;//查询项目列表   年卡 分期  次卡等

    private GeneralRecordListAdapter generalRecordListAdapter;//查询adapter

    private GeneralRecordYearListAdapter generalRecordYearListAdapter;//查询年卡adapter

    private GeneralRecordCardListAdapter generalRecordCardListAdapter;//次卡

    private GeneralRecordStagesListAdapter generalRecordStagesListAdapter;//分期

    private GeneralRecordPeriodListAdapter generalRecordPeriodListAdapter;

    private GeneralRecordSingleListAdapter generalRecordSingleListAdapter;
    /**
     * navView布局显示
     */
    private TextView textAll, textYear, textCard, textStages, textSingle;

    private TextView textStartTime, textEndTime;

    private ImageView startTimeImg, endTimeImg;

    private float mPosX = 0;
    private float mPosY = 0;

    private float mCurPosX = 0;
    private float mCurPosY = 0;

    public GeneralRecordFragment() {

    }

    public GeneralRecordFragment(NavigationView navView) {
        this.navView = navView;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ceneral_record_fragment, null);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {

        ceneralRecordDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNDEFINED);
        ceneralRecordDrawer.setOnLongClickListener(null);

        textAll = navView.getHeaderView(1).findViewById(R.id.item_txt);
        textYear = navView.getHeaderView(1).findViewById(R.id.item_txt1);
        textCard = navView.getHeaderView(1).findViewById(R.id.item_txt2);
        textStages = navView.getHeaderView(1).findViewById(R.id.item_txt3);
        textSingle = navView.getHeaderView(1).findViewById(R.id.item_txt4);
        textStartTime = navView.getHeaderView(1).findViewById(R.id.start_time_text);
        textEndTime = navView.getHeaderView(1).findViewById(R.id.end_time_text);
        startTimeImg = navView.getHeaderView(1).findViewById(R.id.start_time_img);
        endTimeImg = navView.getHeaderView(1).findViewById(R.id.end_time_img);

        textAll.setOnClickListener(this);
        textYear.setOnClickListener(this);
        textCard.setOnClickListener(this);
        textStages.setOnClickListener(this);
        startTimeImg.setOnClickListener(this);
        endTimeImg.setOnClickListener(this);
        textSingle.setOnClickListener(this);

        textEndTime.setText(Tools.getSameYMD());
        textStartTime.setText(Tools.getOldMonth(Tools.getSameYMD()));

        searchview.setIconified(true);
        // 设置搜索文本监听
        searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // 当点击搜索按钮时触发该方法
            @Override
            public boolean onQueryTextSubmit(String query) {
                totalCurrentQuery.setText("当前查询:" + query);
                Log.e(TAG, "onQueryTextSubmit: " + query);
                return false;
            }

            // 当搜索内容改变时触发该方法
            @Override
            public boolean onQueryTextChange(String newText) {
                Log.e(TAG, "onQueryTextChange: " + newText);
                return false;
            }
        });

//      setListView();

        totalSearchviewText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "onClick: " + searchview.getQuery());
                totalCurrentQuery.setText("当前查询:" + searchview.getQuery());

                //隐藏输入法
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(searchview.getWindowToken(), 0);

                getSelectCustomers(searchview.getQuery().toString());
            }
        });

    }

    //数据源设置  查询
    private void setListView() {
        SELECT_STATE = 1;

        generalRecordListAdapter = new GeneralRecordListAdapter(getActivity(), selectCustomers);
        generalRecordListAdapter.setOnItemRecyclerViewClickListener(this);
        generalListview.setAdapter(generalRecordListAdapter); //设置Adapter
    }

    //数据源设置  年卡
    private void setListViewYearCustomerProjectsItems() {
        SELECT_STATE = 2;

        //设置数据倒叙
        Collections.reverse(customerProjectsItems);
        generalRecordYearListAdapter = new GeneralRecordYearListAdapter(getActivity(), customerProjectsItems);
        generalRecordYearListAdapter.setOnItemRecyclerViewClickListener(this);
        generalListview.setAdapter(generalRecordYearListAdapter); //设置Adapter
    }


    //数据源  次卡
    private void setListViewCardCustomerProjectsItems() {
        SELECT_STATE = 3;

        //设置数据倒叙
        Collections.reverse(customerProjectsItems);
        generalRecordCardListAdapter = new GeneralRecordCardListAdapter(getActivity(), customerProjectsItems);
        generalRecordCardListAdapter.setOnItemRecyclerViewClickListener(this);
        generalListview.setAdapter(generalRecordCardListAdapter); //设置Adapter
    }

    //数据源  分期
    private void setListViewStagesCustomerProjectsItems() {
        SELECT_STATE = 4;

        //设置数据倒叙
        Collections.reverse(customerProjectsItems);
        generalRecordStagesListAdapter = new GeneralRecordStagesListAdapter(getActivity(), customerProjectsItems);
        generalRecordStagesListAdapter.setOnItemRecyclerViewClickListener(this);
        generalListview.setAdapter(generalRecordStagesListAdapter); //设置Adapter
    }


    //数据源  期间搜索
    private void setListViewPeriodCustomerProjectsItems() {
        SELECT_STATE = 0;

        //设置数据倒叙
        Collections.reverse(selectCustomers);
        generalRecordPeriodListAdapter = new GeneralRecordPeriodListAdapter(getActivity(), selectCustomers);
        generalRecordPeriodListAdapter.setOnItemRecyclerViewClickListener(this);
        generalListview.setAdapter(generalRecordPeriodListAdapter); //设置Adapter
    }


    //数据源  单次搜索
    private void setListViewSingleCustomerProjectsItems() {
        SELECT_STATE = 5;

        //设置数据倒叙
        Collections.reverse(customerProjectsItems);
        generalRecordSingleListAdapter = new GeneralRecordSingleListAdapter(getActivity(), customerProjectsItems);
        generalRecordSingleListAdapter.setOnItemRecyclerViewClickListener(this);
        generalListview.setAdapter(generalRecordSingleListAdapter); //设置Adapter
    }


    //按name,phone“或” 查询
    private void getSelectCustomers(String string) {
        Log.e(TAG, "getSelectCustomers: " + string);


        BmobQuery<Customer> eq1 = new BmobQuery<Customer>();
        eq1.addWhereEqualTo("name", string);
        BmobQuery<Customer> eq2 = new BmobQuery<Customer>();
        eq2.addWhereEqualTo("phone", string);
        List<BmobQuery<Customer>> queries = new ArrayList<BmobQuery<Customer>>();
        queries.add(eq1);
        queries.add(eq2);
        BmobQuery<Customer> mainQuery = new BmobQuery<Customer>();
        mainQuery.or(queries);

        mainQuery.findObjects(new FindListener<Customer>() {
            @Override
            public void done(List<Customer> object, BmobException e) {
                if (e == null) {
                    selectCustomers = object;
                    ToastUtils.showToast(getContext(), "查询成功" + object.size(), true);
                    refreshHandler.sendEmptyMessage(0);
                } else {
                    ToastUtils.showToast(getContext(), "查询失败" + e.toString(), true);
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    //list点击事件
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                //客户详情信息界面
                case R.id.item_layout:
                    Log.e(TAG, "onClick: item_layout");
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
            ToastUtils.showToast(getActivity(), "这是长按事件", true);
            return false;
        }

    };

    ///刷新Handler
    @SuppressLint("HandlerLeak")
    private Handler refreshHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {

                case 0://1s刷新
                    setListView();
                    break;

                //年卡
                case 1:
                    setListViewYearCustomerProjectsItems();
                    break;

                //办卡
                case 2:
                    setListViewCardCustomerProjectsItems();
                    break;

                case 3:
                    setListViewStagesCustomerProjectsItems();
                    break;

                //时间内搜索
                case 4:
                    setListViewPeriodCustomerProjectsItems();
                    break;

                //查询单次项目
                case 5:
                    setListViewSingleCustomerProjectsItems();
                    break;

                case 6:

                    break;

                default:
                    break;
            }
        }
    };


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        Log.e(TAG, "setUserVisibleHint: " + isVisibleToUser);
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onItemOnClickListener(View view, int pos) {

        //期间查询
        if (SELECT_STATE == 0) {
            skipActivity(selectCustomers.get(pos));
        }

        //精确搜索
        if (SELECT_STATE == 1) {
            skipActivity(selectCustomers.get(pos));
        }

        //年卡
        if (SELECT_STATE == 2) {


        }

        //顾客办卡项目
        if (SELECT_STATE == 3) {


        }

        //顾客分期项目
        if (SELECT_STATE == 4) {

        }


        Log.e(TAG, "onItemOnClickListener: " + pos);
    }

    @Override
    public void onItemLongOnClickListener(View view, int pos) {

        //总数据
        if (SELECT_STATE == 0) {


        }

        //1查询结果
        if (SELECT_STATE == 1) {


        }

        //2顾客年卡项目
        if (SELECT_STATE == 2) {


        }

        //3顾客办卡项目
        if (SELECT_STATE == 3) {

        }

        //4顾客分期项目
        if (SELECT_STATE == 4) {

        }

        Log.e(TAG, "onItemLongOnClickListener: " + pos);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.start_time_img:
                setOnclick(v, 0);
                break;

            case R.id.end_time_img:

                if (textStartTime.getText() == null || textStartTime.getText().toString().equals("")) {
                    ToastUtils.showToast(getContext(), "请先选择开始时间!", false);
                    return;
                }

                setOnclick(v, 1);

                break;

            //查询所有
            case R.id.item_txt:
                getSelectCustomer();
                totalCurrentQuery.setText("当前查询结果为:" + "查询期间所有顾客");
                Log.e(TAG, "onClick:item_txt ");
                break;

            case R.id.item_txt1:

                getSelectCP("cCardYearFlag", 1);
                totalCurrentQuery.setText("当前查询结果为:" + "查询办理年卡客户项目");
                Log.e(TAG, "onClick:item_txt1 ");

                break;

            case R.id.item_txt2:

                totalCurrentQuery.setText("当前查询结果为:" + "查询办理次卡客户项目");
                getSelectCP("cCardFlag", 2);
                Log.e(TAG, "onClick:item_txt2 ");

                break;

            case R.id.item_txt3:

                totalCurrentQuery.setText("当前查询结果为:" + "查询办理分期客户项目");
                getSelectCP("isStages", 3);
                Log.e(TAG, "onClick:item_txt3");
                break;

            case R.id.item_txt4:

                totalCurrentQuery.setText("当前查询结果为:" + "查询单次客户项目");
                getSelectSingleCP(5);
                Log.e(TAG, "onClick:item_txt4");
                break;


            default:
                break;

        }

    }

    private void setGestureListener() {
        // 是要监听的视图 mAlertImageViewD

        ceneralRecordDrawer.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                switch (event.getAction()) {

                    case MotionEvent.ACTION_DOWN:
                        mPosX = event.getX();
                        mPosY = event.getY();
                        Log.e(TAG, "onTouch: ACTION_DOWN");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        mCurPosX = event.getX();
                        mCurPosY = event.getY();
                        Log.e(TAG, "onTouch: ACTION_MOVE");
                        break;
                    case MotionEvent.ACTION_UP:
//                        if (mCurPosY - mPosY > 0
//                                && (Math.abs(mCurPosY - mPosY) > 25)) {
//                            //向下滑動
//                            tiShi(mContext,"向下");
//
//                        } else if (mCurPosY - mPosY < 0
//                                && (Math.abs(mCurPosY - mPosY) > 25)) {
//                            //向上滑动
//                            tiShi(mContext,"向上");
//                        }

                        if (mCurPosX - mPosX > 0
                                && (Math.abs(mCurPosX - mPosX) > 25)) {
                            //向左滑動
//                            tiShi(mContext,"向左");

                        } else if (mCurPosX - mPosX < 0
                                && (Math.abs(mCurPosX - mPosX) > 25)) {
                            //向右滑动
//                            tiShi(mContext,"向右");
                        }

                        Log.e(TAG, "onTouch: ACTION_UP");
                        break;
                }
                return true;
            }

        });
    }

    //时间选择框
    public void setOnclick(View v, final int count) {

        boolean b = false;

        TimePickerPopWin timePickerPopWin = new TimePickerPopWin.Builder(getActivity(), b, new TimePickerPopWin.OnTimePickListener() {
            @Override
            public void onTimePickCompleted(int year, int month, int day, int hour, int minute, int second, String time) {

                if (count == 0) {

                    if (Tools.sMonOreMon(time, textEndTime.getText().toString()) > 12) {
                        ToastUtils.showToast(getContext(), "日期相差不能大于一年,如有需要请使用精确搜索!", false);
                    } else if ((Tools.date2TimeStamp(textEndTime.getText().toString()) - Tools.date2TimeStamp(time)) < (long) 86400000) {
                        ToastUtils.showToast(getContext(), "日期相差不能小于一天!", false);
                    } else {

                        textStartTime.setText(time);
                    }

                } else if (count == 1) {

                    Log.e(TAG, "onTimePickCompleted: " + Tools.date2TimeStamp(time));
                    if ((Tools.date2TimeStamp(time) - Tools.date2TimeStamp(textStartTime.getText().toString())) < (long) 86400000) {
                        ToastUtils.showToast(getContext(), "日期相差不能小于一天!", false);
                    } else if (Tools.sMonOreMon(textStartTime.getText().toString(), time) > 12) {
                        ToastUtils.showToast(getContext(), "日期相差不能大于一年,如有需要请使用精确搜索!", false);
                    } else {
                        textEndTime.setText(time);
                    }
                }
            }
        }).btnTextSize(16)
                .viewTextSize(25)
                .colorCancel(Color.parseColor("#999999"))
                .colorConfirm(Color.parseColor("#009900"))
                .build();
        timePickerPopWin.showPopWin(getActivity());
    }

    //获取日期内项目数
    private void getSelectCP(String str, int handlerId) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date createdAtDate = null;
        Date createdAtDate2 = null;

        try {
            createdAtDate = sdf.parse(Tools.stampToDate(Tools.date2TimeStamp(textStartTime.getText().toString())));
            createdAtDate2 = sdf.parse(Tools.stampToDate(Tools.date2TimeStamp(textEndTime.getText().toString()) + 86400000));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        BmobDate bmobCreatedAtDate = new BmobDate(createdAtDate);
        BmobDate bmobCreatedAtDate2 = new BmobDate(createdAtDate2);

        BmobQuery<CustomerProjectsItem> eq1 = new BmobQuery<CustomerProjectsItem>();
        eq1.addWhereLessThan("createdAt", bmobCreatedAtDate2);

        BmobQuery<CustomerProjectsItem> eq2 = new BmobQuery<CustomerProjectsItem>();
        eq2.addWhereGreaterThanOrEqualTo("createdAt", bmobCreatedAtDate);

        List<BmobQuery<CustomerProjectsItem>> andQuerys = new ArrayList<>();
        andQuerys.add(eq1);
        andQuerys.add(eq2);

        BmobQuery<CustomerProjectsItem> query = new BmobQuery<CustomerProjectsItem>();
        //用此方式可以构造一个BmobPointer对象。只需要设置objectId就行
        query.addWhereEqualTo(str, true);
        query.and(andQuerys);

        query.include("customer,customerProjectBean");
//        query.include("customerProjectBean");

        query.findObjects(new FindListener<CustomerProjectsItem>() {

            @Override
            public void done(List<CustomerProjectsItem> objects, BmobException e) {
                if (e == null) {
                    Log.e(TAG, "done: 查询成功" + objects.size());
                    customerProjectsItems = objects;
                    refreshHandler.sendEmptyMessage(handlerId);
                } else {

                    Log.e(TAG, "done: 失败" + e.toString());
                }
            }
        });

    }

    //获取日期内单次项目数
    private void getSelectSingleCP(int handlerId) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date createdAtDate = null;
        Date createdAtDate2 = null;

        try {
            createdAtDate = sdf.parse(Tools.stampToDate(Tools.date2TimeStamp(textStartTime.getText().toString())));
            createdAtDate2 = sdf.parse(Tools.stampToDate(Tools.date2TimeStamp(textEndTime.getText().toString()) + 86400000));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        BmobDate bmobCreatedAtDate = new BmobDate(createdAtDate);
        BmobDate bmobCreatedAtDate2 = new BmobDate(createdAtDate2);

        BmobQuery<CustomerProjectsItem> eq1 = new BmobQuery<CustomerProjectsItem>();
        eq1.addWhereLessThan("createdAt", bmobCreatedAtDate2);

        BmobQuery<CustomerProjectsItem> eq2 = new BmobQuery<CustomerProjectsItem>();
        eq2.addWhereGreaterThanOrEqualTo("createdAt", bmobCreatedAtDate);

        List<BmobQuery<CustomerProjectsItem>> andQuerys = new ArrayList<>();
        andQuerys.add(eq1);
        andQuerys.add(eq2);

        BmobQuery<CustomerProjectsItem> query = new BmobQuery<CustomerProjectsItem>();
        //用此方式可以构造一个BmobPointer对象。只需要设置objectId就行
        query.addWhereEqualTo("cCardYearFlag", false);
        query.addWhereEqualTo("cCardFlag", false);
        query.addWhereEqualTo("isStages", false);
        query.and(andQuerys);

        query.include("customer,customerProjectBean");
//        query.include("customerProjectBean");

        query.findObjects(new FindListener<CustomerProjectsItem>() {

            @Override
            public void done(List<CustomerProjectsItem> objects, BmobException e) {
                if (e == null) {
                    Log.e(TAG, "done: 查询成功" + objects.size());
                    customerProjectsItems = objects;
                    refreshHandler.sendEmptyMessage(handlerId);
                } else {

                    Log.e(TAG, "done: 失败" + e.toString());
                }
            }
        });

    }

    //获取日期内顾客
    private void getSelectCustomer() {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date createdAtDate = null;
        Date createdAtDate2 = null;

        try {

            createdAtDate = sdf.parse(Tools.stampToDate(Tools.date2TimeStamp(textStartTime.getText().toString())));
            createdAtDate2 = sdf.parse(Tools.stampToDate(Tools.date2TimeStamp(textEndTime.getText().toString()) + 86400000));

        } catch (ParseException e) {
            e.printStackTrace();
        }

        BmobDate bmobCreatedAtDate = new BmobDate(createdAtDate);
        BmobDate bmobCreatedAtDate2 = new BmobDate(createdAtDate2);

        BmobQuery<Customer> eq1 = new BmobQuery<Customer>();
        eq1.addWhereLessThan("createdAt", bmobCreatedAtDate2);

        BmobQuery<Customer> eq2 = new BmobQuery<Customer>();
        eq2.addWhereGreaterThanOrEqualTo("createdAt", bmobCreatedAtDate);

        List<BmobQuery<Customer>> queries = new ArrayList<BmobQuery<Customer>>();
        queries.add(eq1);
        queries.add(eq2);
        BmobQuery<Customer> query = new BmobQuery<>();
        query.and(queries);

        query.findObjects(new FindListener<Customer>() {
            @Override
            public void done(List<Customer> object, BmobException e) {
                if (e == null) {
                    selectCustomers = object;
                    ToastUtils.showToast(getContext(), "查询成功" + object.size(), true);
                    refreshHandler.sendEmptyMessage(4);
                } else {
                    ToastUtils.showToast(getContext(), "查询失败" + e.toString(), true);
                }
            }
        });


    }

    //期间搜索跳转activity
    private void skipActivity(Customer customer) {
        Log.e(TAG, "skipActivity: " + customer);
        Intent Intent = new Intent(getActivity(), ShowProjectsActivity.class);
        Bundle bundle = new Bundle();
        // Serializable传递对象
        bundle.putSerializable("customer", customer);
        Intent.putExtras(bundle);
        startActivity(Intent);
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e(TAG, "onResume: ");

        //隐藏输入法
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(searchview.getWindowToken(), 0);

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}

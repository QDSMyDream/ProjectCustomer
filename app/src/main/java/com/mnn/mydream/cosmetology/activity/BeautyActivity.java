package com.mnn.mydream.cosmetology.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.mnn.mydream.cosmetology.R;
import com.mnn.mydream.cosmetology.adapter.BeautyContentMenuAdapter;
import com.mnn.mydream.cosmetology.adapter.BeautyListViewAdapter;
import com.mnn.mydream.cosmetology.adapter.fragmentAdapter.FragmentMainAdapter;
import com.mnn.mydream.cosmetology.bean.BeautyContentMenuBean;
import com.mnn.mydream.cosmetology.bean.BeautyListItemBean;
import com.mnn.mydream.cosmetology.bean.BeautyTitleBean;
import com.mnn.mydream.cosmetology.bean.CustomerAndProject;
import com.mnn.mydream.cosmetology.bean.User;
import com.mnn.mydream.cosmetology.dialog.CommonDialog;
import com.mnn.mydream.cosmetology.dialog.DeleteDialog;
import com.mnn.mydream.cosmetology.dialog.LoadingDialog;
import com.mnn.mydream.cosmetology.fragment.beauty.DDGLFragment;
import com.mnn.mydream.cosmetology.fragment.beauty.GZTFragment;
import com.mnn.mydream.cosmetology.fragment.beauty.JYGKFragment;
import com.mnn.mydream.cosmetology.fragment.beauty.KHGLFragment;
import com.mnn.mydream.cosmetology.fragment.beauty.SJZXFragment;
import com.mnn.mydream.cosmetology.fragment.beauty.SPGLFragment;
import com.mnn.mydream.cosmetology.fragment.beauty.XJKHFragment;
import com.mnn.mydream.cosmetology.fragment.beauty.XTSZFragment;
import com.mnn.mydream.cosmetology.fragment.beauty.YXZXFragment;
import com.mnn.mydream.cosmetology.fragment.beauty.YYGLFragment;
import com.mnn.mydream.cosmetology.interfaces.BeautyContentListOnClickListener;
import com.mnn.mydream.cosmetology.utils.Constons;
import com.mnn.mydream.cosmetology.utils.ImageLoader;
import com.mnn.mydream.cosmetology.utils.ToastUtils;
import com.mnn.mydream.cosmetology.utils.Tools;
import com.mnn.mydream.cosmetology.view.CircleImageView;
import com.mnn.mydream.cosmetology.view.MyViewPager;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhy.android.percent.support.PercentLinearLayout;
import com.zhy.android.percent.support.PercentRelativeLayout;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;
import io.reactivex.functions.Consumer;

/**
 * 创建人 :MyDream
 * 创建时间：2020/6/11 18:18
 * 类描述：BeautyActivity
 */


public class BeautyActivity extends AppCompatActivity implements BeautyContentListOnClickListener, View.OnLongClickListener {
    @BindView(R.id.viewpager)
    MyViewPager viewpager;
    private String TAG = "BeautyActivity";
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.title2)
    TextView title2;
    @BindView(R.id.content_title_layout)
    PercentRelativeLayout contentTitleLayout;
    @BindView(R.id.img1)
    ImageView img1;
    @BindView(R.id.text1)
    TextView text1;
    @BindView(R.id.layout1)
    PercentRelativeLayout layout1;
    @BindView(R.id.img2)
    ImageView img2;
    @BindView(R.id.text2)
    TextView text2;
    @BindView(R.id.layout2)
    PercentRelativeLayout layout2;
    @BindView(R.id.img3)
    ImageView img3;
    @BindView(R.id.text3)
    TextView text3;
    @BindView(R.id.layout3)
    PercentRelativeLayout layout3;
    @BindView(R.id.img4)
    ImageView img4;
    @BindView(R.id.text4)
    TextView text4;
    @BindView(R.id.layout4)
    PercentRelativeLayout layout4;
    @BindView(R.id.title3)
    TextView title3;
    @BindView(R.id.content_title_layout2)
    PercentRelativeLayout contentTitleLayout2;
    @BindView(R.id.img5)
    ImageView img5;
    @BindView(R.id.text5)
    TextView text5;
    @BindView(R.id.layout5)
    PercentRelativeLayout layout5;
    @BindView(R.id.img6)
    ImageView img6;
    @BindView(R.id.text6)
    TextView text6;
    @BindView(R.id.layout6)
    PercentRelativeLayout layout6;
    @BindView(R.id.img7)
    ImageView img7;
    @BindView(R.id.text7)
    TextView text7;
    @BindView(R.id.layout7)
    PercentRelativeLayout layout7;
    @BindView(R.id.img8)
    ImageView img8;
    @BindView(R.id.text8)
    TextView text8;
    @BindView(R.id.layout8)
    PercentRelativeLayout layout8;
    @BindView(R.id.title4)
    TextView title4;
    @BindView(R.id.content_title_layout3)
    PercentRelativeLayout contentTitleLayout3;
    @BindView(R.id.img9)
    ImageView img9;
    @BindView(R.id.text9)
    TextView text9;
    @BindView(R.id.layout9)
    PercentRelativeLayout layout9;
    @BindView(R.id.img10)
    ImageView img10;
    @BindView(R.id.text10)
    TextView text10;
    @BindView(R.id.layout10)
    PercentRelativeLayout layout10;
    @BindView(R.id.img11)
    ImageView img11;
    @BindView(R.id.text11)
    TextView text11;
    @BindView(R.id.layout11)
    PercentRelativeLayout layout11;

    @BindView(R.id.menu_img)
    ImageView menuImg;
    @BindView(R.id.menu_listview)
    ListView menuListview;
    @BindView(R.id.layout_left)
    PercentLinearLayout layoutLeft;
    @BindView(R.id.title_add_img)
    ImageView titleAddImg;
    @BindView(R.id.title_add_text)
    TextView titleAddText;
    @BindView(R.id.add_customer_layout)
    PercentRelativeLayout addCustomerLayout;
    @BindView(R.id.title_tx)
    CircleImageView titleTx;
    @BindView(R.id.title_username)
    TextView titleUsername;
    @BindView(R.id.title_sign)
    TextView titleSign;
    @BindView(R.id.title_username_layout)
    PercentLinearLayout titleUsernameLayout;
    @BindView(R.id.user_info_layout)
    PercentRelativeLayout userInfoLayout;
    @BindView(R.id.title_layout)
    PercentRelativeLayout titleLayout;
    @BindView(R.id.title_expression_img)
    ImageView titleExpressionImg;
    @BindView(R.id.flipper_layout)
    PercentRelativeLayout flipperLayout;
    @BindView(R.id.menu_img_listview)
    ListView menuImgListview;
    @BindView(R.id.line1)
    ImageView line1;
    @BindView(R.id.agency_order_text)
    TextView agencyOrderText;
    @BindView(R.id.agency_order_layout)
    PercentRelativeLayout agencyOrderLayout;
    @BindView(R.id.line2)
    ImageView line2;
    @BindView(R.id.agency_appointment_text)
    TextView agencyAppointmentText;
    @BindView(R.id.agency_appointment_layout)
    PercentRelativeLayout agencyAppointmentLayout;
    @BindView(R.id.agency_layout)
    PercentLinearLayout agencyLayout;
    @BindView(R.id.menu_left_layout)
    PercentLinearLayout menuLeftLayout;
    @BindView(R.id.img_question)
    ImageView imgQuestion;
    @BindView(R.id.common_problem_text)
    TextView commonProblemText;
    @BindView(R.id.common_problem_layout)
    PercentRelativeLayout commonProblemLayout;
    @BindView(R.id.menu_right_layout)
    PercentLinearLayout menuRightLayout;
    @BindView(R.id.menu_layout)
    PercentLinearLayout menuLayout;
    @BindView(R.id.layout_content)
    PercentLinearLayout layoutContent;

    private List<BeautyListItemBean> beautyListItemBeans = new ArrayList<>();

    private int leftListViewImgs[] = new int[]{R.mipmap.gzt, R.mipmap.jygk, R.mipmap.gkgl, R.mipmap.ddgl, R.mipmap.yuyue, R.mipmap.sjzx, R.mipmap.yx, R.mipmap.xm, R.mipmap.xtsz};

    private String leftListViewStrings[] = new String[]{"工作台", "经营概况", "顾客管理", "订单管理", "预约管理", "数据中心", "营销中心", "项目管理", "系统设置"};

    private int leftListViewMenuFontColor = R.color.beauty_left_font_bg;

    private int leftListViewImgSelects[] = new int[]{R.mipmap.gzt_select, R.mipmap.jygk_select, R.mipmap.gkgl_select, R.mipmap.ddgl_select, R.mipmap.yuyue_select, R.mipmap.sjzx_select, R.mipmap.yx_select, R.mipmap.xm_select, R.mipmap.xtsz_select};

    private int leftListViewMenuFontSelects = R.color.beauty_left_font_select_bg;

    private BeautyListViewAdapter beautyListViewAdapter;

    private List<String> titleStrings = new ArrayList<>();

    private LoadingDialog loadingDialog;

    private List<BeautyContentMenuBean> beautyContentMenuBeans = new ArrayList<>();
    private BeautyContentMenuAdapter beautyContentMenuAdapter;

    private String contentListViewTitles[] = new String[]{"充值", "消费", "消耗", "其他"};

    public NotificationManager mNotificationManager;//通知
    public NotificationCompat.Builder mBuilder;

    private int notifyId = 100;

    //头像地址
    private String picPath = "";

    private User user = null;//当前登陆账户

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beauty);

        ButterKnife.bind(this);
        initView();
        initViewPager();
        initLogin();
//        selectTitleStrings();

    }


    //初始化viewPager
    private void initViewPager() {

        List<Fragment> fragments = new ArrayList<Fragment>();
        fragments.add(new GZTFragment());
        fragments.add(new JYGKFragment());
        fragments.add(new KHGLFragment());
        fragments.add(new DDGLFragment());
        fragments.add(new YYGLFragment());
        fragments.add(new SJZXFragment());
        fragments.add(new YXZXFragment());
        fragments.add(new SPGLFragment());
        fragments.add(new XTSZFragment());
        fragments.add(new XJKHFragment());

        FragmentMainAdapter adapter = new FragmentMainAdapter(getSupportFragmentManager(), fragments);
        viewpager.setAdapter(adapter);
        //设置主viewpager不能滑动
        viewpager.setOffscreenPageLimit(10);
        getFragment(0);
    }


    //初始化登陆
    private void initLogin() {

        setIsLoginInfo();

    }

    private void initView() {
        //绑定数据
        for (int i = 0; i < leftListViewImgs.length; i++) {
            BeautyListItemBean beautyListItemBean = new BeautyListItemBean(leftListViewImgs[i], leftListViewStrings[i], leftListViewMenuFontColor, leftListViewImgSelects[i], leftListViewMenuFontSelects);
            beautyListItemBeans.add(beautyListItemBean);
        }

        beautyListViewAdapter = new BeautyListViewAdapter(this, beautyListItemBeans, onClickListener);
        menuListview.setAdapter(beautyListViewAdapter);
//
//        LoadingDialog.Builder addSignDialogBuild = new LoadingDialog.Builder(BeautyActivity.this);
//        loadingDialog = addSignDialogBuild.createDialog();
//        loadingDialog.setCanceledOnTouchOutside(false);
//        // 设置点击屏幕Dialog不消失
//        loadingDialog.show();

//        int img1[] = new int[]{R.mipmap.bk, R.mipmap.xck};
//        String strings1[] = new String[]{"首次开卡", "续充卡"};
//
//        int img2[] = new int[]{R.mipmap.dcfw, R.mipmap.gmxmk};
//        String strings2[] = new String[]{"单次/服务", "购买项目卡"};
//
//        int img3[] = new int[]{R.mipmap.hk, R.mipmap.dphx, R.mipmap.dsfhx, R.mipmap.fwhx};
//        String strings3[] = new String[]{"耗卡", "店铺核销", "第三方核销", "服务核销"};
//
//        int img4[] = new int[]{R.mipmap.xfzs, R.mipmap.lpk};
//        String strings4[] = new String[]{"消费赠送", "礼品卡"};
//
//        List<BeautyContentMenuItem> beautyContentMenuItems1 = new ArrayList<>();
//        List<BeautyContentMenuItem> beautyContentMenuItems2 = new ArrayList<>();
//        List<BeautyContentMenuItem> beautyContentMenuItems3 = new ArrayList<>();
//        List<BeautyContentMenuItem> beautyContentMenuItems4 = new ArrayList<>();
//
//
//        for (int i1 = 0; i1 < img1.length; i1++) {
//            BeautyContentMenuItem beautyContentMenuItem = new BeautyContentMenuItem(strings1[i1], img1[i1]);
//            beautyContentMenuItems1.add(beautyContentMenuItem);
//        }
//
//        for (int i2 = 0; i2 < img2.length; i2++) {
//            BeautyContentMenuItem beautyContentMenuItem = new BeautyContentMenuItem(strings2[i2], img2[i2]);
//            beautyContentMenuItems2.add(beautyContentMenuItem);
//        }
//        for (int i3 = 0; i3 < img3.length; i3++) {
//            BeautyContentMenuItem beautyContentMenuItem = new BeautyContentMenuItem(strings3[i3], img3[i3]);
//            beautyContentMenuItems3.add(beautyContentMenuItem);
//        }
//        for (int i4 = 0; i4 < img4.length; i4++) {
//            BeautyContentMenuItem beautyContentMenuItem = new BeautyContentMenuItem(strings4[i4], img4[i4]);
//            beautyContentMenuItems4.add(beautyContentMenuItem);
//        }
//
//
//        BeautyContentMenuBean beautyContentMenuBean1 = new BeautyContentMenuBean(contentListViewTitles[0], beautyContentMenuItems1);
//        BeautyContentMenuBean beautyContentMenuBean2 = new BeautyContentMenuBean(contentListViewTitles[1], beautyContentMenuItems2);
//        BeautyContentMenuBean beautyContentMenuBean3 = new BeautyContentMenuBean(contentListViewTitles[2], beautyContentMenuItems3);
//        BeautyContentMenuBean beautyContentMenuBean4 = new BeautyContentMenuBean(contentListViewTitles[3], beautyContentMenuItems4);
//        beautyContentMenuBeans.add(beautyContentMenuBean1);
//        beautyContentMenuBeans.add(beautyContentMenuBean2);
//        beautyContentMenuBeans.add(beautyContentMenuBean3);
//        beautyContentMenuBeans.add(beautyContentMenuBean4);
        //绑定中间布局
//        beautyContentMenuAdapter = new BeautyContentMenuAdapter(this, beautyContentMenuBeans);
//        beautyContentMenuAdapter.setBeautyContentListOnClickListener(this);
//        menuImgListview.setAdapter(beautyContentMenuAdapter);
        layout1.setOnLongClickListener(this);
        layout2.setOnLongClickListener(this);
        layout3.setOnLongClickListener(this);
        layout4.setOnLongClickListener(this);
        layout5.setOnLongClickListener(this);
        layout6.setOnLongClickListener(this);
        layout7.setOnLongClickListener(this);
        layout8.setOnLongClickListener(this);
        layout9.setOnLongClickListener(this);
        layout10.setOnLongClickListener(this);


        //RX权限获取
        RxPermissions rxPermissions = new RxPermissions(this);

        rxPermissions.request(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA,
                Manifest.permission.CALL_PHONE,
                Manifest.permission.INTERNET,
                Manifest.permission.CHANGE_NETWORK_STATE

        ).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                if (aBoolean) {
                    //申请的权限全部允许
//                    Toast.makeText(HomeActivity.this, "允许了权限!", Toast.LENGTH_SHORT).show();
                } else {
                    //只要有一个权限被拒绝，就会执行
                    ToastUtils.showToast(getBaseContext(), "未授权权限，部分功能不能使用", false);
                }
            }
        });

    }

    @OnClick({R.id.title_tx, R.id.layout1, R.id.layout2, R.id.layout3, R.id.layout4, R.id.layout5, R.id.layout6, R.id.layout7, R.id.layout8, R.id.layout9, R.id.layout10, R.id.add_customer_layout, R.id.user_info_layout, R.id.agency_appointment_layout, R.id.common_problem_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.title_tx:
                if (user != null) {
                    showPickerDialog();
                }

                break;

            case R.id.add_customer_layout:
                getFragment(9);
                break;

            case R.id.user_info_layout:
                Intent intent = new Intent();
                intent.setClass(BeautyActivity.this, LoginActivity.class);
                startActivityForResult(intent,Constons.BEAUTY_RESULT_LONGIN_CODE);
                break;

            case R.id.agency_appointment_layout:
                break;

            case R.id.common_problem_layout:
                break;

            case R.id.layout1:
                Constons.BEAUTY_WITHIN_PREVIOUS_POSTION = 0;

                break;

            case R.id.layout2:
                Constons.BEAUTY_WITHIN_PREVIOUS_POSTION = 1;
                break;

            case R.id.layout3:
                Constons.BEAUTY_WITHIN_PREVIOUS_POSTION = 2;
                break;

            case R.id.layout4:
                Constons.BEAUTY_WITHIN_PREVIOUS_POSTION = 3;
                break;

            case R.id.layout5:
                Constons.BEAUTY_WITHIN_PREVIOUS_POSTION = 4;
                break;

            case R.id.layout6:
                Constons.BEAUTY_WITHIN_PREVIOUS_POSTION = 5;
                break;

            case R.id.layout7:
                Constons.BEAUTY_WITHIN_PREVIOUS_POSTION = 6;
                break;

            case R.id.layout8:
                Constons.BEAUTY_WITHIN_PREVIOUS_POSTION = 7;
                break;

            case R.id.layout9:
                Constons.BEAUTY_WITHIN_PREVIOUS_POSTION = 8;
                break;

            case R.id.layout10:
                Constons.BEAUTY_WITHIN_PREVIOUS_POSTION = 9;
                break;

        }
    }


    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int position = menuListview.getPositionForView(v);
            BeautyListViewAdapter.BEAUTY_SELECT_ITEM = position;

            getFragment(position);

            isAdapter(beautyListViewAdapter);
            Log.e("click positon::", "" + position);

        }
    };

    private void isAdapter(BeautyListViewAdapter adapters) {
        if (adapters != null) {
            adapters.notifyDataSetChanged();
        }
    }

    //查询某个时间前
    private void selectTitleStrings() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date createdAtDate = null;
        try {
            createdAtDate = sdf.parse(Tools.getSameDay());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        BmobDate bmobCreatedAtDate = new BmobDate(createdAtDate);
        BmobQuery<BeautyTitleBean> categoryBmobQuery = new BmobQuery<>();

        Log.e(TAG, "selectMenuItem: " + createdAtDate);
        categoryBmobQuery.addWhereLessThan("createdAt", bmobCreatedAtDate);
        categoryBmobQuery.findObjects(new FindListener<BeautyTitleBean>() {
            @Override
            public void done(List<BeautyTitleBean> object, BmobException e) {
                if (e == null) {
                    Log.e(TAG, "done: " + "查询成功" + object.size());

                    for (BeautyTitleBean beautyTitleBean : object) {
                        titleStrings.add(beautyTitleBean.getTitleString());
                    }
                    loadingDialog.dismiss();
                    refreshHandler.sendEmptyMessage(1);

                } else {
                    Log.e(TAG, "done: " + "查询失败" + e.toString());
                    loadingDialog.dismiss();

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
                case 1:
                    setFlipperString();
                    break;

                case 2:

                    break;

                default:
                    break;
            }
        }
    };

    private void setFlipperString() {
        Log.e(TAG, "setFlipperString: ");
//
//        for (int i = 0; i < titleStrings.size(); i++) {
//            Log.e(TAG, "setFlipperString: " + titleStrings.get(i));
//            View view = LayoutInflater.from(getBaseContext()).inflate(R.layout.beauty_viewflipper_item, null);
//            TextView textView = view.findViewById(R.id.flipper_text);
//            textView.setText(titleStrings.get(i));
//            vfText.addView(view);
//        }

    }


    @Override
    public boolean onLongClick(View v) {

        switch (v.getId()) {
            case R.id.layout1:
                break;
            case R.id.layout2:
                break;
            case R.id.layout3:
                break;
            case R.id.layout4:
                break;
            case R.id.layout5:
                break;
            case R.id.layout6:
                break;
            case R.id.layout7:
                break;
            case R.id.layout8:
                break;
            case R.id.layout9:
                break;
            case R.id.layout10:
                break;
            default:
        }
        return true;
    }

    private void setCostomMsg(String msg) {
        sendNotification(msg);
    }


    //通知
    public void sendNotification(String msg) {
        Intent resultIntent = new Intent(this, BeautyActivity.class);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        //获得通知管理器，通知是一项系统服务
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        //初始化通知对象 p1:通知的图标 p2:通知的状态栏显示的提示 p3:通知显示的时间
        mBuilder = new NotificationCompat.Builder(this)
                //设置小图标
                .setSmallIcon(R.mipmap.ic_launcher)
                //设置通知标题
                .setContentTitle(getString(R.string.app_name) + "通知：" + msg)
                .setContentIntent(pendingIntent);
//              //设置通知内容
//              .setContentText("")

        //设置通知时间，默认为系统发出通知的时间，通常不用设置
        //.setWhen(System.currentTimeMillis());
        //通过builder.build()方法生成Notification对象,并发送通知,id=1
        mNotificationManager.notify(notifyId, mBuilder.build());
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e(TAG, "onActivityResult: " + requestCode);
        Log.e(TAG, "onActivityResult: " + resultCode);


        //成功
        if (requestCode == Constons.BEAUTY_RESULT_LONGIN_CODE && resultCode == Constons.RESULT_LONGIN_SUCCESS) {
            ToastUtils.showToast(BeautyActivity.this, "登陆成功", true);
            setIsLoginInfo();

        }

        //失败
        if (requestCode == Constons.BEAUTY_RESULT_LONGIN_CODE && resultCode == Constons.RESULT_LONGIN_FAIL) {

            ToastUtils.showToast(BeautyActivity.this, "登陆失败请重试", false);
        }

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
                            ToastUtils.showToast(BeautyActivity.this, "图片路径获取失败", false);
                            return;
                        }
                        //加载本地图片
                        ImageLoader.displayLocalImageViewCircle(BeautyActivity.this, titleTx, picPath);
                        uoloadImg(picPath);
                    } catch (Exception e) {
                        ToastUtils.showToast(BeautyActivity.this, "图片路径获取失败", false);

                    }
                    break;
            }
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG, "onStart: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG, "onStop: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy: ");
//        mNotificationManager.cancel(notifyId);
    }

    /**
     * 拍照
     */
    private void takePictrue() {
        PictureSelector.create(BeautyActivity.this)
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

    /**
     * 相册
     */
    private void pickPictrue() {
        PictureSelector.create(BeautyActivity.this)
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
     * 显示对话框
     */
    private void showPickerDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.layout_picker_type4picture, null);

        CommonDialog.Builder builder = new CommonDialog.Builder(this);
        final CommonDialog dialog = builder
                .setStyle(R.style.DialogBottomStyle)
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

    //上传图片到服务器
    private void uoloadImg(String f) {

        BmobFile bmobFile = new BmobFile(new File(f));
        bmobFile.uploadblock(new UploadFileListener() {

            @Override
            public void done(BmobException e) {
                if (e == null) {
                    picPath = bmobFile.getFileUrl();
                    Log.e(TAG, "done: " + picPath);
                    user.setUserTx(picPath);
                    user.update(new UpdateListener() {

                        @Override
                        public void done(BmobException e) {

                            if (e == null) {
                                ToastUtils.showToast(BeautyActivity.this, "上传头像成功", true);
                            } else {
                                ToastUtils.showToast(BeautyActivity.this, "上传头像失败", false);
                            }
                        }


                    });
                } else {
                    Log.e(TAG, "上传文件失败：" + e.getMessage());
                }
            }

            @Override
            public void onProgress(Integer value) {
                // 返回的上传进度（百分比）

                Log.e(TAG, "onProgress: " + value);
            }
        });


    }

    //判断是否登陆
    private void setIsLoginInfo() {

        if (BmobUser.isLogin()) {
            user = BmobUser.getCurrentUser(User.class);
            titleUsername.setText(user.getUsername());
            switch (user.getPower()) {
                case 0:
                    titleSign.setText("超级管理员");
                    break;
                case 1:
                    titleSign.setText("技师");
                    break;
                case 2:
                    titleSign.setText("普通用户");
                    break;
                default:
                    break;
            }


            String userTx = (String) BmobUser.getObjectByKey("userTx");
            Log.e(TAG, "setIsLoginInfo: "+userTx );
            //加载图片
            ImageLoader.displayImageView(this, userTx, titleTx);
            ToastUtils.showToast(getBaseContext(), "已经登陆" + user.getUsername(), true);

        } else {
            ToastUtils.showToast(getBaseContext(), "请先登陆", false);
            Intent intent = new Intent();
            intent.setClass(BeautyActivity.this, LoginActivity.class);
            startActivityForResult(intent,Constons.BEAUTY_RESULT_LONGIN_CODE);
        }


    }

    @Override
    public void onLongClick(View v, int pos, BeautyContentMenuBean beautyContentMenuBean, int flag) {

        switch (v.getId()) {
            case R.id.layout1:
                Log.e(TAG, "onLongClick: " + pos);
                Log.e(TAG, "onLongClick: " + beautyContentMenuBean.getBeautyContentMenuItems().get(flag - 1).getTitleMenuString());

                break;
            case R.id.layout2:
                Log.e(TAG, "onLongClick: " + pos);
                Log.e(TAG, "onLongClick: " + beautyContentMenuBean.getBeautyContentMenuItems().get(flag - 1).getTitleMenuString());
                break;
            case R.id.layout3:
                Log.e(TAG, "onLongClick: " + pos);
                Log.e(TAG, "onLongClick: " + beautyContentMenuBean.getBeautyContentMenuItems().get(flag - 1).getTitleMenuString());
                break;
            case R.id.layout4:
                Log.e(TAG, "onLongClick: " + pos);
                Log.e(TAG, "onLongClick: " + beautyContentMenuBean.getBeautyContentMenuItems().get(flag - 1).getTitleMenuString());
                break;
        }

    }

    @Override
    public void onClick(View v, int pos, BeautyContentMenuBean beautyContentMenuBean, int flag) {
        switch (v.getId()) {
            case R.id.layout1:
                Log.e(TAG, "onClick: " + pos);
                Log.e(TAG, "onLongClick: " + beautyContentMenuBean.getBeautyContentMenuItems().get(flag - 1).getTitleMenuString());
                break;
            case R.id.layout2:
                Log.e(TAG, "onClick: " + pos);
                Log.e(TAG, "onLongClick: " + beautyContentMenuBean.getBeautyContentMenuItems().get(flag - 1).getTitleMenuString());
                break;
            case R.id.layout3:
                Log.e(TAG, "onClick: " + pos);
                Log.e(TAG, "onLongClick: " + beautyContentMenuBean.getBeautyContentMenuItems().get(flag - 1).getTitleMenuString());
                break;
            case R.id.layout4:
                Log.e(TAG, "onClick: " + pos);
                Log.e(TAG, "onLongClick: " + beautyContentMenuBean.getBeautyContentMenuItems().get(flag - 1).getTitleMenuString());
                break;
        }
    }


    public void getFragment(int menuItemId) {
        viewpager.setCurrentItem(menuItemId, false);

    }

    //退出函数
    long firstTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            long secondTime = System.currentTimeMillis();
            if (secondTime - firstTime > 2000) {
                ToastUtils.showToast(getBaseContext(), "再按一次退出程序", false);
                firstTime = secondTime;
                return true;
            } else {
                finish();
                System.exit(0);
            }

        }
        return false;
    }



}


package com.mnn.mydream.cosmetology.activity;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.luck.picture.lib.rxbus2.RxBus;
import com.mnn.mydream.cosmetology.R;
import com.mnn.mydream.cosmetology.anim.KickBackAnimator;
import com.mnn.mydream.cosmetology.bean.CustomerProjectBean;
import com.mnn.mydream.cosmetology.bean.RxMessage;
import com.mnn.mydream.cosmetology.dialog.DeleteDialog;
import com.mnn.mydream.cosmetology.fragment.GeneralRecordFragment;
import com.mnn.mydream.cosmetology.fragment.StatisticsFragment;
import com.mnn.mydream.cosmetology.fragment.SameDayFragment;
import com.mnn.mydream.cosmetology.fragment.SettingFragment;
import com.mnn.mydream.cosmetology.utils.ToastUtils;
import com.mnn.mydream.cosmetology.utils.Tools;
import com.next.easynavigation.constant.Anim;
import com.next.easynavigation.utils.NavigationUtil;
import com.next.easynavigation.view.EasyNavigationBar;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobUser;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

/**
 * 创建人 :MyDream
 * 创建时间：2020/5/3 18:18
 * 类描述：主界面Activity
 */
public class HomeActivity extends AppCompatActivity {

    private String TAG = "HomeActivity";

    @BindView(R.id.navigationBar)
    EasyNavigationBar navigationBar;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.home_drawer_layout)
    DrawerLayout homeDrawerLayout;

    private String[] tabText = {"今日记录", "总记录", "", "数据统计", "账户设置"};

    //未选中icon
    private int[] normalIcon = {R.mipmap.index, R.mipmap.find, R.mipmap.add_image, R.mipmap.message, R.mipmap.me};

    //选中时icon
    private int[] selectIcon = {R.mipmap.index1, R.mipmap.find1, R.mipmap.add_image, R.mipmap.message1, R.mipmap.me1};

    private List<Fragment> fragments = new ArrayList<>();

    //仿微博图片和文字集合

    private int[] menuIconItems = {R.mipmap.pic1, R.mipmap.pic2, R.mipmap.pic3, R.mipmap.pic4};

    private String[] menuTextItems = {"添加顾客", "项目编辑", "团购项目编辑", "添加"};

//    private int[] menuIconItems = {R.mipmap.pic1};
//    private String[] menuTextItems = {"客户记录"};

    private List<CustomerProjectBean> customerProjectsList = new ArrayList<>();
    private LinearLayout menuLayout;
    private View cancelImageView;
    private Handler mHandler = new Handler();

//    private AddProjectsDialog addProjectsDialog;
//    private AddProjectsDialog.Builder addProjectsDialogBuild;
//
//    private AddTimeDialog addTimeDialog;
//    private AddTimeDialog.Builder addTimeDialogBuild;

    private String addTimeStirng = "";

    private View view1, view2;
    //多个权限申请
    String[] permissons = {Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.CHANGE_NETWORK_STATE,
            Manifest.permission.CAMERA,
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.READ_CALENDAR,
            Manifest.permission.ACCESS_CHECKIN_PROPERTIES,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weibo);
        ButterKnife.bind(this);

        //注册EventBus
        //EventBus.getDefault().register(this);

        fragments.add(new SameDayFragment(navView));
        fragments.add(new GeneralRecordFragment(navView));
        fragments.add(new StatisticsFragment());
        fragments.add(new SettingFragment());

        navigationBar.titleItems(tabText)
                .normalIconItems(normalIcon)
                .selectIconItems(selectIcon)
                .fragmentList(fragments)
                .fragmentManager(getSupportFragmentManager())
                .addLayoutRule(EasyNavigationBar.RULE_BOTTOM)
                .addLayoutBottom(100)
                .onTabClickListener(new EasyNavigationBar.OnTabClickListener() {
                    @Override
                    public boolean onTabClickEvent(View view, int position) {

                        if (position == 0) {
                            homeDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                            navView.getHeaderView(0).setVisibility(View.VISIBLE);
                            navView.getHeaderView(1).setVisibility(View.GONE);
                        }
                        if (position == 1) {
                            homeDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                            navView.getHeaderView(0).setVisibility(View.GONE);
                            navView.getHeaderView(1).setVisibility(View.VISIBLE);
                        }

                        if (position == 3) {
                            homeDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                        }

                        //如果点击为加号
                        if (position == 4) {
                            homeDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                            if (BmobUser.isLogin()) {

                                BmobUser user = BmobUser.getCurrentUser(BmobUser.class);
//                                Snackbar.make(view, "已经登录：" + user.getUsername(), Snackbar.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(HomeActivity.this, "请先登录", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent();
                                intent.setClass(HomeActivity.this, LoginActivity.class);
                                startActivity(intent);

                                //return true则拦截事件、不进行页面切换
                                return true;
                            }

                        }
                        //如果点击为加号
                        if (position == 2) {
                            homeDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);

                            if (BmobUser.isLogin()) {
                                showMunu();
                            } else {
                                Toast.makeText(HomeActivity.this, "请先登录", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent();
                                intent.setClass(HomeActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }
                        }
                        return false;
                    }
                })
                .mode(EasyNavigationBar.MODE_ADD)
                .anim(Anim.ZoomIn)
                .build();
        navigationBar.setAddViewLayout(createEjectView());
        view1 = LayoutInflater.from(this).inflate(R.layout.same_day_fragment_nav_header,
                null);
        view2 = LayoutInflater.from(this).inflate(R.layout.general_record_fragment_nav_header,
                null);
        navView.addHeaderView(view1);
        navView.addHeaderView(view2);
        navView.getHeaderView(1).setVisibility(View.GONE);

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
//                    initData();
                } else {
                    //只要有一个权限被拒绝，就会执行
                    Toast.makeText(HomeActivity.this, "未授权权限，部分功能不能使用", Toast.LENGTH_SHORT).show();
                }
            }
        });

//        onRxBusReceived();
    }

    //仿微博弹出菜单
    private View createEjectView() {

        ViewGroup view = (ViewGroup) View.inflate(HomeActivity.this, R.layout.layout_add_view, null);
        menuLayout = view.findViewById(R.id.icon_group);
        cancelImageView = view.findViewById(R.id.cancel_iv);

        cancelImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeAnimation();
            }
        });
        for (int i = 0; i < menuIconItems.length; i++) {

            View itemView = View.inflate(HomeActivity.this, R.layout.item_icon, null);
            ImageView menuImage = itemView.findViewById(R.id.menu_icon_iv);
            TextView menuText = itemView.findViewById(R.id.menu_text_tv);

            menuImage.setImageResource(menuIconItems[i]);
            menuText.setText(menuTextItems[i]);
            final int finalI = i;

            menuImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (finalI) {
                        case 0:
                            closeAnimation();//关闭菜单
                            Intent intent = new Intent();
                            intent.setClass(HomeActivity.this, CustomerActivity.class);
                            startActivity(intent);
                            break;

                        case 1:
                            Intent intent2 = new Intent();
                            intent2.setClass(HomeActivity.this, EditProjectsActivity.class);
                            startActivity(intent2);
                            closeAnimation();//关闭菜单
                            break;

                        case 2:

                            RxBus.getDefault().post(new RxMessage(RxMessage.SSSS));
                            closeAnimation();//关闭菜单

                            break;
                        case 3:

                            closeAnimation();//关闭菜单

                            break;

                    }
                }
            });
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            params.weight = 1;
            itemView.setLayoutParams(params);
            itemView.setVisibility(View.GONE);
            menuLayout.addView(itemView);
        }
        return view;
    }

    //显示菜单
    private void showMunu() {

        startAnimation();

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                //旋转动画
                cancelImageView.animate().rotation(90).setDuration(400);
            }
        });


        //菜单项弹出动画
        for (int i = 0; i < menuLayout.getChildCount(); i++) {
            final View child = menuLayout.getChildAt(i);
            child.setVisibility(View.INVISIBLE);
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    child.setVisibility(View.VISIBLE);
                    ValueAnimator fadeAnim = ObjectAnimator.ofFloat(child, "translationY", 600, 0);
                    fadeAnim.setDuration(500);
                    KickBackAnimator kickAnimator = new KickBackAnimator();
                    kickAnimator.setDuration(500);
                    fadeAnim.setEvaluator(kickAnimator);
                    fadeAnim.start();
                }
            }, i * 50 + 100);
        }
    }

    /**
     * 开启window动画
     */
    private void startAnimation() {

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    //圆形扩展的动画
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        int x = NavigationUtil.getScreenWidth(HomeActivity.this) / 2;
                        int y = (int) (NavigationUtil.getScreenHeith(HomeActivity.this) - NavigationUtil.dip2px(HomeActivity.this, 25));
                        Animator animator = ViewAnimationUtils.createCircularReveal(navigationBar.getAddViewLayout(), x,
                                y, 0, navigationBar.getAddViewLayout().getHeight());
                        animator.addListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationStart(Animator animation) {
                                navigationBar.getAddViewLayout().setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onAnimationEnd(Animator animation) {
                                //							layout.setVisibility(View.VISIBLE);
                            }
                        });
                        animator.setDuration(300);
                        animator.start();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    /**
     * 关闭window动画
     */
    private void closeAnimation() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                cancelImageView.animate().rotation(0).setDuration(400);
            }
        });

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                int x = NavigationUtil.getScreenWidth(this) / 2;
                int y = (NavigationUtil.getScreenHeith(this) - NavigationUtil.dip2px(this, 25));
                Animator animator = ViewAnimationUtils.createCircularReveal(navigationBar.getAddViewLayout(), x,
                        y, navigationBar.getAddViewLayout().getHeight(), 0);
                animator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        //							layout.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        navigationBar.getAddViewLayout().setVisibility(View.GONE);
                        //dismiss();
                    }
                });
                animator.setDuration(300);
                animator.start();
            }
        } catch (Exception e) {
        }
    }

    /**
     * 检查是否存在虚拟按键栏
     *
     * @param context
     * @return
     */
    public static boolean hasNavBar(Context context) {
        Resources res = context.getResources();
        int resourceId = res.getIdentifier("config_showNavigationBar", "bool", "android");
        if (resourceId != 0) {
            boolean hasNav = res.getBoolean(resourceId);
            // check override flag
            String sNavBarOverride = getNavBarOverride();
            if ("1".equals(sNavBarOverride)) {
                hasNav = false;
            } else if ("0".equals(sNavBarOverride)) {
                hasNav = true;
            }
            return hasNav;
        } else { // fallback
            return !ViewConfiguration.get(context).hasPermanentMenuKey();
        }
    }

    /**
     * 判断虚拟按键栏是否重写
     *
     * @return
     */
    private static String getNavBarOverride() {
        String sNavBarOverride = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                Class c = Class.forName("android.os.SystemProperties");
                Method m = c.getDeclaredMethod("get", String.class);
                m.setAccessible(true);
                sNavBarOverride = (String) m.invoke(null, "qemu.hw.mainkeys");
            } catch (Throwable e) {
            }
        }
        return sNavBarOverride;
    }

    public EasyNavigationBar getNavigationBar() {
        return navigationBar;
    }

    //判断用户是否登录
    private boolean isLogins() {
        if (BmobUser.isLogin()) {
            BmobUser user = BmobUser.getCurrentUser(BmobUser.class);
            Log.e(TAG, "isLogins: " + "已经登录" + user.getUsername());
//            Snackbar.make(view, "已经登录：" + user.getUsername(), Snackbar.LENGTH_LONG).show();
            return true;
        } else {
            Toast.makeText(HomeActivity.this, "请先登录", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.setClass(HomeActivity.this, LoginActivity.class);
            startActivity(intent);
            return false;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        Tools.makeDir();//创建文件夹
        Log.e(TAG, "onResume: ");
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    @Override
    protected void onStart() {
        Log.e(TAG, "onStart: ");
        super.onStart();
    }

    @Override
    protected void onStop() {
        Log.e(TAG, "onStop: ");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.e(TAG, "onDestroy: ");
        super.onDestroy();
    }

//
//    //添加项目时间弹窗
//    private View.OnClickListener edAddProjectsOnClick = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            addTimeDialogBuild.setOnSettingListener(settingListener);
//            addTimeDialog.show();
//        }
//    };
//
//    //添加项目弹窗 取消
//    private View.OnClickListener clickListenerCancel = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            addProjectsDialog.dismiss();
//        }
//    };
//
//    //添加项目弹窗 确定
//    private View.OnClickListener clickListenerYes = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            EditText edAddProjects = addProjectsDialog.findViewById(R.id.ed_add_projects);
//            TextView edAddProjectsTime = addProjectsDialog.findViewById(R.id.ed_add_projects_time);
//
//            CustomerProjects customerProjects = new CustomerProjects(edAddProjects.getText().toString(), edAddProjectsTime.getText().toString());
//            customerProjects.save(new SaveListener<String>() {
//                @Override
//                public void done(String customerProjects, BmobException e) {
//                    if (e == null) {
//                        Toast.makeText(getBaseContext(), "添加成功：" + customerProjects, Toast.LENGTH_LONG).show();
//                        Log.e("bmob", "成功");
//
//                    } else {
//                        Toast.makeText(getBaseContext(), "添加失败：" + e.getMessage(), Toast.LENGTH_LONG).show();
//                        Log.e("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
//                    }
//
//                }
//            });
//            addProjectsDialog.dismiss();
//        }
//
//
//    };
//
//    SettingListener settingListener = new SettingListener() {
//        @Override
//        public void onSetting(int year, int month, int day, int hour, int minute, int second, String time) {
//            addTimeStirng = time;
//            TextView edAddProjectsTime = addProjectsDialog.findViewById(R.id.ed_add_projects_time);
//            edAddProjectsTime.setText(addTimeStirng);
//
//
//        }
//    };

    //退出函数
    DeleteDialog exitDialog = null;
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

//
//            DeleteDialog.Builder builder = new DeleteDialog.Builder(HomeActivity.this);
//            //设置对话框的标题
//            builder.setTitleMsg("确定要退出吗？");
//
//            //设置确定按钮
//            builder.setYesOnClick(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    finish();
//                    android.os.Process.killProcess(android.os.Process.myPid());
//                    exitDialog.dismiss();
//                }
//            });
//
//            //设置取消按钮
//            builder.setCancelOnClick(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    exitDialog.dismiss();
//                }
//            });
//            //使用创建器生成一个对话框对象
//            exitDialog = builder.createDialog();
//            exitDialog.show();
        }
        return false;
    }

    //总线上接收
    private void onRxBusReceived() {
//•Schedulers.immediate，默认，当前线程，立即执行
//
//•Schedulers.trampoline，当前线程，要等其他任务先执行
//
//•Schedulers.newThread，启用一个新线程
//
//•Schedulers.io，擅长读写文件、数据库、网络信息，一个无数量上限的线程池
//
//•Schedulers.computation，擅长cpu密集型计算，固定线程池，不要做io操作，会浪费cpu
//
//•AndroidSchedulers.mainThread，Android主线程
//
        RxBus.getDefault().toObservable(RxMessage.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Object>() {

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.e(TAG, "onCompleted: Rx");
                    }

                    @Override
                    public void onSubscribe(Subscription s) {

                        Log.e(TAG, "onSubscribe: " + s);
                    }

                    @Override
                    public void onNext(Object event) {

                        Log.e(TAG, "onNext: " + event.toString());
//                        //如果事件类是呼叫事件类
//                        if (event instanceof IntercomEvent) {
//                            IntercomEvent intercomEvent = (IntercomEvent) event;
//
//                        }
                    }
                });

        //   RxBus.getDefault().send(new IntercomEvent(IntercomEvent.RING_BACK_SATTE));
    }

}

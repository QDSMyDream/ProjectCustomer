package com.mnn.mydream.cosmetology.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;

import com.mnn.mydream.cosmetology.R;
import com.mnn.mydream.cosmetology.adapter.GridVeiwMenuAdapter;
import com.mnn.mydream.cosmetology.bean.ProjectMenuBean;
import com.mnn.mydream.cosmetology.bean.ProjectMenuXBanner;
import com.mnn.mydream.cosmetology.dialog.LoadingDialog;
import com.mnn.mydream.cosmetology.interfaces.MenuItemCallBack;
import com.mnn.mydream.cosmetology.utils.ImageLoader;
import com.mnn.mydream.cosmetology.utils.Tools;
import com.stx.xhb.xbanner.XBanner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;


public class MainActivity extends AppCompatActivity {
    private String TAG = "MainActivity";

    @BindView(R.id.xbanner)
    XBanner mXbanner;
    @BindView(R.id.menu_img)
    ImageView menuImg;
    @BindView(R.id.gridview)
    GridView gridview;
    GridVeiwMenuAdapter gridVeiwMenuAdapter;

    private List<ProjectMenuBean> projectMenuBeans = new ArrayList<>();

    private List<ProjectMenuXBanner> imgesUrl = new ArrayList<>();//轮播图显示

    LoadingDialog loadingDialog;

    private MenuItemCallBack menuItemCallBack;

    private boolean b1 = false, b2 = false;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {

        LoadingDialog.Builder addSignDialogBuild = new LoadingDialog.Builder(MainActivity.this);
        loadingDialog = addSignDialogBuild.createDialog();
        loadingDialog.setCanceledOnTouchOutside(false);
        // 设置点击屏幕Dialog不消失
        loadingDialog.show();

//        selectMenuItem();
//        selectMenuBannerImg();

    }


    //查询某个时间前
    private void selectMenuItem() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date createdAtDate = null;
        try {
            createdAtDate = sdf.parse(Tools.getSameDay());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        BmobDate bmobCreatedAtDate = new BmobDate(createdAtDate);
        BmobQuery<ProjectMenuBean> categoryBmobQuery = new BmobQuery<>();

        Log.e(TAG, "selectMenuItem: " + createdAtDate);
        categoryBmobQuery.addWhereLessThan("createdAt", bmobCreatedAtDate);
        categoryBmobQuery.findObjects(new FindListener<ProjectMenuBean>() {
            @Override
            public void done(List<ProjectMenuBean> object, BmobException e) {
                if (e == null) {
                    Log.e(TAG, "done: " + "查询成功" + object.size());

                    b1 = true;
                    projectMenuBeans = object;
                    refreshHandler.sendEmptyMessage(1);
                } else {
                    Log.e("BMOB", e.toString());
                    Log.e(TAG, "done: " + "查询失败" + e.getMessage());
                    b1 = true;

                }
            }
        });

    }

    //查询某个时间前

    private void selectMenuBannerImg() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date createdAtDate = null;
        try {
            createdAtDate = sdf.parse(Tools.getSameDay());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        BmobDate bmobCreatedAtDate = new BmobDate(createdAtDate);
        BmobQuery<ProjectMenuXBanner> categoryBmobQuery = new BmobQuery<>();

        Log.e(TAG, "selectMenuItem: " + createdAtDate);
        categoryBmobQuery.addWhereLessThan("createdAt", bmobCreatedAtDate);
        categoryBmobQuery.findObjects(new FindListener<ProjectMenuXBanner>() {
            @Override
            public void done(List<ProjectMenuXBanner> object, BmobException e) {
                if (e == null) {
                    Log.e(TAG, "done: " + "查询成功" + object.size());

                    b2 = true;

                    imgesUrl = object;
                    refreshHandler.sendEmptyMessage(2);
                } else {
                    Log.e("BMOB", e.toString());
                    Log.e(TAG, "done: " + "查询失败" + e.getMessage());
                    b2 = true;

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

                    if (b1 && b2) {
                        b2 = false;
                        b1 = false;
                        loadingDialog.dismiss();
                    }

                    gridVeiwMenuAdapter = new GridVeiwMenuAdapter(MainActivity.this, projectMenuBeans);
                    gridview.setAdapter(gridVeiwMenuAdapter);
                    break;

                case 2:
                    if (b1 && b2) {
                        b2 = false;
                        b1 = false;
                        loadingDialog.dismiss();
                    }

                    // 为XBanner绑定数据
                    mXbanner.setData(imgesUrl, null);//第二个参数为提示文字资源集合
                    // XBanner适配数据
                    mXbanner.setmAdapter(new XBanner.XBannerAdapter() {
                        @Override
                        public void loadBanner(XBanner banner, View view, int position) {
                            //加载图片
                            ImageLoader.displayImageView(MainActivity.this, imgesUrl.get(position).getImgUrl(), (ImageView) view,R.mipmap.def_photo);
                        }
                    });

                    break;

                default:
                    break;
            }
        }
    };
}

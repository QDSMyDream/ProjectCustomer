package com.mnn.mydream.cosmetology.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.mnn.mydream.cosmetology.bean.AppUpdateBean;
import com.mnn.mydream.cosmetology.bean.BeautyMdBean;
import com.mnn.mydream.cosmetology.bean.BeautyTitleBean;
import com.mnn.mydream.cosmetology.bean.spglBean.ServerTypeBean;
import com.mnn.mydream.cosmetology.eventBus.EventBusMsg;
import com.mnn.mydream.cosmetology.utils.APKVersionCodeUtils;
import com.mnn.mydream.cosmetology.utils.Constons;
import com.mnn.mydream.cosmetology.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;

/**
 * 创建人：MyDream
 * 创建日期：2020/7/12 3:19
 * 类描述 : 启动service
 */
public class StartService extends Service {

    private String TAG = getClass().getSimpleName();

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "onCreate: ");

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onCreate: " + "onStartCommand");

        selectTitleStrings();//查询工作台title
        selectMdStrings();//查询门店
        selectServerTypeAll();//类型
        initUpdate();//查询更新
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    /**
     * selectTitleStrings  title
     */
    private void selectTitleStrings() {

        BmobQuery<BeautyTitleBean> categoryBmobQuery = new BmobQuery<>();
        categoryBmobQuery.findObjects(new FindListener<BeautyTitleBean>() {
            @Override
            public void done(List<BeautyTitleBean> object, BmobException e) {
                if (e == null) {
                    Constons.titleStrings.clear();
                    for (BeautyTitleBean beautyTitleBean : object) {
                        Constons.titleStrings.add(beautyTitleBean.getTitleString());
                    }
                    EventBus.getDefault().post(new EventBusMsg(Constons.SELECT_TITLE));
                } else {
                    Log.e(TAG, "done: " + "查询失败" + e.toString());
                }
            }
        });

    }

    /**
     * selectMdStrings  门店
     */
    private void selectMdStrings() {

        BmobQuery<BeautyMdBean> categoryBmobQuery = new BmobQuery<>();
        categoryBmobQuery.findObjects(new FindListener<BeautyMdBean>() {
            @Override
            public void done(List<BeautyMdBean> object, BmobException e) {
                if (e == null) {
                    Constons.OPERATION_MD.clear();

                    for (BeautyMdBean beautyMdBean : object) {
                        Constons.OPERATION_MD.add(beautyMdBean.getMdString());
                    }
//                    EventBus.getDefault().post(new EventBusMsg(Constons.SELECT_MD));
                } else {
                    Log.e(TAG, "done: " + "查询失败" + e.toString());
                }
            }
        });

    }

    /**
     * serverTypeStrings 服务类型
     */
    private void selectServerTypeAll() {
        BmobQuery<ServerTypeBean> categoryBmobQuery = new BmobQuery<>();
        categoryBmobQuery.findObjects(new FindListener<ServerTypeBean>() {
            @Override
            public void done(List<ServerTypeBean> object, BmobException e) {
                if (e == null) {
                    if (object.size() == 0) {
                        Constons.ServerTypeString.add("无");
                        Constons.SelectServerTypeString.add("全部");
                    } else {
                        Constons.ServerTypeString.clear();
                        Constons.SelectServerTypeString.add("全部");
                        for (ServerTypeBean serverTypeBean : object) {
                            Constons.ServerTypeString.add(serverTypeBean.getServerTypeString());
                            Constons.SelectServerTypeString.add(serverTypeBean.getServerTypeString());
                        }
                    }


                } else {
                    Log.e("BMOB", e.toString());
                    ToastUtils.showToast(getBaseContext(), "查询失败", false);
                }
            }
        });
    }

    //初始化登陆
    private void initUpdate() {
        int VERSION_CODE = APKVersionCodeUtils.getVersionCode(this);

        BmobQuery<AppUpdateBean> bmobQuery = new BmobQuery<AppUpdateBean>();
        bmobQuery.getObject("Hj7pCCCx", new QueryListener<AppUpdateBean>() {
            @Override
            public void done(AppUpdateBean object, BmobException e) {
                if (e == null) {
                    if (object.getVersion_i() > VERSION_CODE) {
                        Log.e(TAG, "done: " + object.toString());
                        EventBus.getDefault().post(new EventBusMsg(Constons.SELECT_APP_UPDATE));
                    }
                }
            }
        });


    }


}

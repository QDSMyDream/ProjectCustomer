package com.mnn.mydream.cosmetology.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.mnn.mydream.cosmetology.bean.BeautyMdBean;
import com.mnn.mydream.cosmetology.bean.BeautyTitleBean;
import com.mnn.mydream.cosmetology.bean.fuwuBean.ServerTypeBean;
import com.mnn.mydream.cosmetology.dialog.LoadingDialog;
import com.mnn.mydream.cosmetology.eventBus.EventBusMsg;
import com.mnn.mydream.cosmetology.utils.Constons;
import com.mnn.mydream.cosmetology.utils.ToastUtils;
import com.mnn.mydream.cosmetology.utils.Tools;

import org.greenrobot.eventbus.EventBus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * 创建人：MyDream
 * 创建日期：2020/7/12 3:19
 * 类描述 :
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
        getSelectServerTypeAll();//类型

        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }


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
    private void getSelectServerTypeAll() {

        BmobQuery<ServerTypeBean> categoryBmobQuery = new BmobQuery<>();
        categoryBmobQuery.findObjects(new FindListener<ServerTypeBean>() {
            @Override
            public void done(List<ServerTypeBean> object, BmobException e) {
                if (e == null) {
                    if (object.size() == 0) {
                        Constons.ServerTypeString.add("无");
                    } else {
                        Constons.ServerTypeString.clear();
                        for (ServerTypeBean serverTypeBean : object) {
                            Constons.ServerTypeString.add(serverTypeBean.getServerTypeString());
                        }
                    }
                } else {
                    Log.e("BMOB", e.toString());
                    ToastUtils.showToast(getBaseContext(), "查询失败", false);
                }
            }
        });
    }


}

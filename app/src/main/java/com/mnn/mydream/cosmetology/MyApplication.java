package com.mnn.mydream.cosmetology;

import android.app.Application;
import android.util.Log;

import com.mnn.mydream.cosmetology.utils.ConfigDataCons;
import com.mnn.mydream.cosmetology.utils.ConfigDataMethod;
import com.mnn.mydream.cosmetology.utils.Tools;
import com.tencent.bugly.crashreport.CrashReport;

/**
 * 创建人：MyDream
 * 创建日期：2020/5/10 22:13
 * 类描述：Application
 */
public class MyApplication extends Application {
    private String TAG = "MyApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "onCreate: MyApplication");

        ConfigDataMethod.bmobInit(this);//bmob初始化
        CrashReport.UserStrategy userStrategy = new CrashReport.UserStrategy(getApplicationContext());
        Log.e(TAG, "onCreate: " + Tools.packageCode(getApplicationContext()) + "");
        Log.e(TAG, "onCreate: " + Tools.packageName(getApplicationContext()) + "");
        userStrategy.setAppVersion(Tools.packageCode(getApplicationContext()) + "");      //App的版本
        userStrategy.setAppPackageName(Tools.packageName(getApplicationContext()));  //App的包

        CrashReport.initCrashReport(this, ConfigDataCons.BUGLY_ID, true);//Bugly


    }


}

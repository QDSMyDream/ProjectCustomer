package com.mnn.mydream.cosmetology.demo_test;

import android.app.Application;

import cn.bmob.v3.Bmob;

/**
 * Created by nothing on 2016/11/14.
 */

public class MyApplication extends Application {
//
    @Override
    public void onCreate() {
        super.onCreate();
        Bmob.initialize(getApplicationContext(), "3dda9978e1e89ddfe69c65d8162e227f");
    }
}

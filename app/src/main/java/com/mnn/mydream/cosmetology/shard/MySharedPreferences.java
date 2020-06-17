package com.mnn.mydream.cosmetology.shard;

import android.content.Context;
import android.content.SharedPreferences;

public class MySharedPreferences {
 public static final String key = "project_key";

    public MySharedPreferences() {

    }

    public static String sharedName = "project";

    //插入扫码数据
    public static  void setInsertShared(Context context, String key, String values) {
        //可以创建一个新的SharedPreference来对储存的文件进行操作
        SharedPreferences sp = context.getSharedPreferences(sharedName, Context.MODE_PRIVATE);
        //像SharedPreference中写入数据需要使用Editor
        SharedPreferences.Editor editor = sp.edit();
        //类似键值对
        editor.putString(key, values);
        //editor.apply();
        editor.commit();
    }

    //检查是否存在
    public static  boolean setIsQueryShared(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(sharedName, Context.MODE_PRIVATE);
        //检查当前键是否存在
        boolean isContains = sp.contains(key);

        return isContains;
    }


    //查询值
    public static String setSelectShared(Context context, String key) {

        SharedPreferences sp = context.getSharedPreferences(sharedName, Context.MODE_PRIVATE);
        //第一个参数是键名，第二个是默认值
        String data = sp.getString(key, "");

        return data;
    }


    //清除数据
    public static void setDeleteShared(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(sharedName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.commit();
    }

}

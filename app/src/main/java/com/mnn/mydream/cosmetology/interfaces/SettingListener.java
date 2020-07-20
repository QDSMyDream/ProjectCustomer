package com.mnn.mydream.cosmetology.interfaces;

/**
 * 创建人：MyDream
 * 创建日期：2020/5/11 23:41
 * 类描述：时间dialog 传值
 */
public interface SettingListener  {
    void onSetting(int year, int month, int day, int hour, int minute, int second, String time);
}
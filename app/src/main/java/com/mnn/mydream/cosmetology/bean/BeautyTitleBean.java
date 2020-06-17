package com.mnn.mydream.cosmetology.bean;

import cn.bmob.v3.BmobObject;


/**
 * 创建人：MyDream
 * 创建日期：2020/5/10 10:05
 * 类描述：beauty titleString
 */

public class BeautyTitleBean extends BmobObject {

    private String titleImg;
    private String titleString;

    @Override
    public String toString() {
        return "BeautyTitleBean{" +
                "titleImg='" + titleImg + '\'' +
                ", titleString='" + titleString + '\'' +
                '}';
    }

    public BeautyTitleBean(String titleImg, String titleString) {
        this.titleImg = titleImg;
        this.titleString = titleString;
    }

    public String getTitleImg() {
        return titleImg;
    }

    public void setTitleImg(String titleImg) {
        this.titleImg = titleImg;
    }

    public String getTitleString() {
        return titleString;
    }

    public void setTitleString(String titleString) {
        this.titleString = titleString;
    }
}

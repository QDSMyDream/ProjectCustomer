package com.mnn.mydream.cosmetology.bean;

/**
 * 创建人：MyDream
 * 创建日期：2020/6/11 23:32
 * 类描述 : 中间菜单BeautyMenuString
 */
public class BeautyContentMenuItem {

    private String titleMenuString;
    private int titleMenuImg;


    public BeautyContentMenuItem(String titleMenuString, int titleMenuImg) {
        this.titleMenuString = titleMenuString;
        this.titleMenuImg = titleMenuImg;
    }

    public String getTitleMenuString() {
        return titleMenuString;
    }

    public void setTitleMenuString(String titleMenuString) {
        this.titleMenuString = titleMenuString;
    }

    public int getTitleMenuImg() {
        return titleMenuImg;
    }

    public void setTitleMenuImg(int titleMenuImg) {
        this.titleMenuImg = titleMenuImg;
    }
}

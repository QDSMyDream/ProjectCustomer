package com.mnn.mydream.cosmetology.bean;

import cn.bmob.v3.BmobObject;


/**
 * 创建人：MyDream
 * 创建日期：2020/5/10 10:05
 * 类描述：beauty titleString
 */

public class BeautyMdBean extends BmobObject {

    private String mdImg;
    private String mdString;

    @Override
    public String toString() {
        return "BeautyMdBean{" +
                "mdImg='" + mdImg + '\'' +
                ", mdString='" + mdString + '\'' +
                '}';
    }

    public String getMdImg() {
        return mdImg;
    }

    public void setMdImg(String mdImg) {
        this.mdImg = mdImg;
    }

    public String getMdString() {
        return mdString;
    }

    public void setMdString(String mdString) {
        this.mdString = mdString;
    }
}

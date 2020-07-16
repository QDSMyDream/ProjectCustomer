package com.mnn.mydream.cosmetology.bean.khBean;

/**
 * 创建人：MyDream
 * 创建日期：2020/7/15 21:59
 * 类描述 : 客户界面list显示
 */
public class BeautyListBeanKh {

    private String titleStr;
    private String dataStr;

    public BeautyListBeanKh(String titleStr, String dataStr) {
        this.titleStr = titleStr;
        this.dataStr = dataStr;
    }

    @Override
    public String toString() {
        return "BeautyListBeanKh{" +
                "titleStr='" + titleStr + '\'' +
                ", dataStr='" + dataStr + '\'' +
                '}';
    }

    public String getTitleStr() {
        return titleStr;
    }

    public void setTitleStr(String titleStr) {
        this.titleStr = titleStr;
    }

    public String getDataStr() {
        return dataStr;
    }

    public void setDataStr(String dataStr) {
        this.dataStr = dataStr;
    }
}

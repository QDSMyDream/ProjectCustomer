package com.mnn.mydream.cosmetology.bean.fuwuBean;

import java.io.Serializable;


/**
 * 创建人：MyDream
 * 创建日期：2020/07/19 16:57
 * 类描述：
 */
public class XMKDataOpertionBean implements Serializable {

    private int numCount;//代表此服务限制多少次
    private FuWuSaleBean fuWuSaleBean;

    public XMKDataOpertionBean(int num, FuWuSaleBean fuWuSaleBean) {
        this.numCount = num;
        this.fuWuSaleBean = fuWuSaleBean;
    }

    public XMKDataOpertionBean() {
      super();
    }

    public int getNumCount() {
        return numCount;
    }

    public void setNumCount(int numCount) {
        this.numCount = numCount;
    }

    public FuWuSaleBean getFuWuSaleBean() {
        return fuWuSaleBean;
    }

    public void setFuWuSaleBean(FuWuSaleBean fuWuSaleBean) {
        this.fuWuSaleBean = fuWuSaleBean;
    }
}

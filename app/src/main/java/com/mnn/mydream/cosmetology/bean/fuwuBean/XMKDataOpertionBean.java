package com.mnn.mydream.cosmetology.bean.fuwuBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 创建人：MyDream
 * 创建日期：2020/07/19 16:57
 * 类描述：
 */
public class XMKDataOpertionBean implements Serializable {

    private int num;//代表此服务限制多少次
    private FuWuSaleBean fuWuSaleBean;

    public XMKDataOpertionBean(int num, FuWuSaleBean fuWuSaleBean) {
        this.num = num;
        this.fuWuSaleBean = fuWuSaleBean;
    }

    @Override
    public String toString() {
        return "XMKDataOpertionBean{" +
                "money=" + num +
                ", fuWuSaleBean=" + fuWuSaleBean +
                '}';
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public FuWuSaleBean getFuWuSaleBean() {
        return fuWuSaleBean;
    }

    public void setFuWuSaleBean(FuWuSaleBean fuWuSaleBean) {
        this.fuWuSaleBean = fuWuSaleBean;
    }
}

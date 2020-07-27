package com.mnn.mydream.cosmetology.bean.spglBean;

/**
 * 创建人：MyDream
 * 创建日期：2020/07/26 17:11
 * 类描述：储蓄卡充值方案
 */
public class CXKCZFABean {


    private float czje;
    private float zsje;

    public CXKCZFABean() {

    }

    public CXKCZFABean(float czje, float zsje) {
        this.czje = czje;
        this.zsje = zsje;
    }

    @Override
    public String toString() {
        return "CXKCZFABean{" +
                "czje=" + czje +
                ", zsje=" + zsje +
                '}';
    }

    public float getCzje() {
        return czje;
    }

    public void setCzje(float czje) {
        this.czje = czje;
    }

    public float getZsje() {
        return zsje;
    }

    public void setZsje(float zsje) {
        this.zsje = zsje;
    }
}

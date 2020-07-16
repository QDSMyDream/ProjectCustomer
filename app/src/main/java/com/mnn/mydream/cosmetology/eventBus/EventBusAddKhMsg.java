package com.mnn.mydream.cosmetology.eventBus;

import com.mnn.mydream.cosmetology.bean.khBean.BeautyBeanKh;

/**
 * 创建人：MyDream
 * 创建日期：2020/7/12 3:32
 * 类描述 : EventBus添加客户消息类
 */
public class EventBusAddKhMsg {

    private int msgInt;

    private BeautyBeanKh beautyBeanKh;


    public EventBusAddKhMsg(int msgInt,BeautyBeanKh beautyBeanKh) {
        this.msgInt = msgInt;
        this.beautyBeanKh=beautyBeanKh;

    }

    public int getMsgInt() {
        return msgInt;
    }

    public void setMsgInt(int msgInt) {
        this.msgInt = msgInt;
    }

    @Override
    public String toString() {
        return "EventBusAddKhMsg{" +
                "msgInt=" + msgInt +
                ", beautyBeanKh=" + beautyBeanKh +
                '}';
    }

    public BeautyBeanKh getBeautyBeanKh() {
        return beautyBeanKh;
    }

    public void setBeautyBeanKh(BeautyBeanKh beautyBeanKh) {
        this.beautyBeanKh = beautyBeanKh;
    }
}

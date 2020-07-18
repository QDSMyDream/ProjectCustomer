package com.mnn.mydream.cosmetology.eventBus;

import com.mnn.mydream.cosmetology.bean.khBean.BeautyBeanKh;

import java.util.List;

/**
 * 创建人：MyDream
 * 创建日期：2020/7/12 3:32
 * 类描述 : EventBus添加客户消息类
 */
public class EventBusSelectMsg<T> {

    private int msgInt;

    private List<T> lists;


    public EventBusSelectMsg(int msgInt, List<T> lists) {
        this.msgInt = msgInt;
        this.lists=lists;

    }

    public int getMsgInt() {
        return msgInt;
    }

    public void setMsgInt(int msgInt) {
        this.msgInt = msgInt;
    }


    @Override
    public String toString() {
        return "EventBusSelectMsg{" +
                "msgInt=" + msgInt +
                ", lists=" + lists +
                '}';
    }

    public List<T> getLists() {
        return lists;
    }

    public void setLists(List<T> lists) {
        this.lists = lists;
    }
}

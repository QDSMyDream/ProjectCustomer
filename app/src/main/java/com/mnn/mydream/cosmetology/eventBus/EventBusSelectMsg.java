package com.mnn.mydream.cosmetology.eventBus;

import java.util.List;

/**
 * 创建人：MyDream
 * 创建日期：2020/7/12 3:32
 * 类描述 : EventBus消息类
 */
public class EventBusSelectMsg<E> {

    private int msgInt;

    private List<E> lists;

    @Override
    public String toString() {
        return "EventBusSelectMsg{" +
                "msgInt=" + msgInt +
                ", lists=" + lists +
                '}';
    }

    public List<E> getLists() {
        return lists;
    }

    public void setLists(List<E> lists) {
        this.lists = lists;
    }

    public EventBusSelectMsg(int msgInt) {
        this.msgInt = msgInt;
    }

    public int getMsgInt() {
        return msgInt;
    }

    public void setMsgInt(int msgInt) {
        this.msgInt = msgInt;
    }





}

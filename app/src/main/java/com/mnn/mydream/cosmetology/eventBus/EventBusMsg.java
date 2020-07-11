package com.mnn.mydream.cosmetology.eventBus;

import java.util.List;

/**
 * 创建人：MyDream
 * 创建日期：2020/7/12 3:32
 * 类描述 : EventBus消息类
 */
public class EventBusMsg {

    private int msgInt;

    public EventBusMsg(int msgInt) {
        this.msgInt = msgInt;
    }

    public int getMsgInt() {
        return msgInt;
    }

    public void setMsgInt(int msgInt) {
        this.msgInt = msgInt;
    }

    @Override
    public String toString() {
        return "EventBusMsg{" +
                "msgInt=" + msgInt +
                '}';
    }
}

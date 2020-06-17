package com.mnn.mydream.cosmetology.bean;


/**
 * 创建人：MyDream
 * 创建日期：2020/5/10 10:17
 * 类描述：RX消息发送类
 */

public class RxMessage {


    public static final int SSSS = 1;

    private int state;

    public RxMessage(int state) {
        this.state = state;
    }


    public int getState() {
        return state;
    }

}

package com.mnn.mydream.cosmetology.bean;

import cn.bmob.v3.BmobObject;

/**
 * 创建人：MyDream
 * 创建日期：2020/6/8 22:27
 * 类描述 :
 */
public class BeautyWithinCardsBean extends BmobObject {

    private String cardNameString;
    private String cardContentZDDCString;
    private String cardContentCXKString;

    private int cardGradeInt;

    public BeautyWithinCardsBean(String cardNameString, String cardContentZDDCString, String cardContentCXKString, int cardGradeString) {
        this.cardNameString = cardNameString;
        this.cardContentZDDCString = cardContentZDDCString;
        this.cardContentCXKString = cardContentCXKString;
        this.cardGradeInt = cardGradeString;
    }

    @Override
    public String toString() {
        return "BeautyWithinCardsBean{" +
                "cardNameString='" + cardNameString + '\'' +
                ", cardContentZDDCString='" + cardContentZDDCString + '\'' +
                ", cardContentCXKString='" + cardContentCXKString + '\'' +
                ", cardGradeString=" + cardGradeInt +
                '}';
    }

    public String getCardNameString() {
        return cardNameString;
    }

    public void setCardNameString(String cardNameString) {
        this.cardNameString = cardNameString;
    }

    public String getCardContentZDDCString() {
        return cardContentZDDCString;
    }

    public void setCardContentZDDCString(String cardContentZDDCString) {
        this.cardContentZDDCString = cardContentZDDCString;
    }

    public String getCardContentCXKString() {
        return cardContentCXKString;
    }

    public void setCardContentCXKString(String cardContentCXKString) {
        this.cardContentCXKString = cardContentCXKString;
    }


    public int getCardGradeInt() {
        return cardGradeInt;
    }

    public void setCardGradeInt(int cardGradeInt) {
        this.cardGradeInt = cardGradeInt;
    }
}

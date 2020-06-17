package com.mnn.mydream.cosmetology.bean;


import android.os.Parcel;
import android.os.Parcelable;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * 创建人：MyDream
 * 创建日期：2020/5/10 10:05
 * 类描述：客户添加项目表
 */

public class CustomerProjectsItem extends BmobObject implements Parcelable {


    private Customer customer;

    private CustomerProjectBean customerProjectBean;//项目名

    private float cMoney;//项目总金额

    private int cCount;//总次数

    private int cUseCount;//已用次数

    private String cStartTime;//办理日期

    private String cEndTime;//到期日期

    private boolean cCardFlag;//是否办卡

    private boolean cCardYearFlag;//是否是年卡

    private boolean cSignFlag;//是否签字

    private BmobFile signPng;//签字图片

    private boolean isStages;//是否分期

    private float cStagesSurplusMoney;//分期剩余金额

    private float cStagesMoney;//缴费

    public CustomerProjectBean getCustomerProjectBean() {
        return customerProjectBean;
    }

    public void setCustomerProjectBean(CustomerProjectBean customerProjectBean) {
        this.customerProjectBean = customerProjectBean;
    }

    public boolean iscCardYearFlag() {
        return cCardYearFlag;
    }

    public void setcCardYearFlag(boolean cCardYearFlag) {
        this.cCardYearFlag = cCardYearFlag;
    }

    public BmobFile getSignPng() {
        return signPng;
    }

    public void setSignPng(BmobFile signPng) {
        this.signPng = signPng;
    }

    public boolean isStages() {
        return isStages;
    }

    public void setStages(boolean stages) {
        isStages = stages;
    }

    public float getcStagesSurplusMoney() {
        return cStagesSurplusMoney;
    }

    public void setcStagesSurplusMoney(float cStagesSurplusMoney) {
        this.cStagesSurplusMoney = cStagesSurplusMoney;
    }


    public boolean iscSignFlag() {
        return cSignFlag;
    }

    public void setcSignFlag(boolean cSignFlag) {
        this.cSignFlag = cSignFlag;
    }

    @Override
    public String toString() {
        return "CustomerProjectsItem{" +
                "customer=" + customer +
                ", customerProjectBean=" + customerProjectBean +
                ", cMoney=" + cMoney +
                ", cCount=" + cCount +
                ", cUseCount=" + cUseCount +
                ", cStartTime='" + cStartTime + '\'' +
                ", cEndTime='" + cEndTime + '\'' +
                ", cCardFlag=" + cCardFlag +
                ", cCardYearFlag=" + cCardYearFlag +
                ", cSignFlag=" + cSignFlag +
                ", signPng=" + signPng +
                ", isStages=" + isStages +
                ", cStagesSurplusMoney=" + cStagesSurplusMoney +
                ", cStagesMoney=" + cStagesMoney +
                '}';
    }

    public CustomerProjectsItem() {

    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public float getcStagesMoney() {
        return cStagesMoney;
    }

    public void setcStagesMoney(float cStagesMoney) {
        this.cStagesMoney = cStagesMoney;
    }


    public float getcMoney() {
        return cMoney;
    }

    public void setcMoney(float cMoney) {
        this.cMoney = cMoney;
    }

    public int getcCount() {
        return cCount;
    }

    public void setcCount(int cCount) {
        this.cCount = cCount;
    }

    public int getcUseCount() {
        return cUseCount;
    }

    public void setcUseCount(int cUseCount) {
        this.cUseCount = cUseCount;
    }

    public String getcStartTime() {
        return cStartTime;
    }

    public void setcStartTime(String cStartTime) {
        this.cStartTime = cStartTime;
    }


    public String getcEndTime() {
        return cEndTime;
    }

    public void setcEndTime(String cEndTime) {
        this.cEndTime = cEndTime;
    }

    public boolean iscCardFlag() {
        return cCardFlag;
    }

    public void setcCardFlag(boolean cCardFlag) {
        this.cCardFlag = cCardFlag;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeString(cName);
//        dest.writeString(cPhone);
        dest.writeString(cStartTime);

        dest.writeString(customerProjectBean.getcProjects());

        dest.writeString(cEndTime);
        dest.writeFloat(cMoney);
        dest.writeInt(cCount);
        dest.writeInt(cUseCount);
        dest.writeByte((byte) (cCardFlag ? 1 : 0));     //if myBoolean == true, byte == 1


    }

    public static final Parcelable.Creator<CustomerProjectsItem> CREATOR = new Creator<CustomerProjectsItem>() {
        @Override
        public CustomerProjectsItem[] newArray(int size) {
            return new CustomerProjectsItem[size];
        }

        @Override
        public CustomerProjectsItem createFromParcel(Parcel in) {
            return new CustomerProjectsItem(in);
        }
    };

    public CustomerProjectsItem(Parcel in) {

        cStartTime = in.readString();

        cEndTime = in.readString();

        cMoney = in.readFloat();
        cCount = in.readInt();
        cUseCount = in.readInt();
        cCardFlag = in.hasFileDescriptors();

        cCardFlag = in.readByte() != 0;     //myBoolean == true if byte != 0
    }


}

package com.mnn.mydream.cosmetology.bean.fuwuBean;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * 创建人：MyDream
 * 创建日期：2020/6/23 19:01
 * 类描述 : saleBean 上架服务表
 */
public class FuWuSaleBean extends BmobObject {


    private String serverUrl;
    private String serverName;
    private String serverType;
    private String applyMd;
    private float serverMoney;//价钱
    private int serverTime; //是否上架

    private boolean serverSaleFlag; //是否上架

    public FuWuSaleBean() {

    }

    public int getServerTime() {
        return serverTime;
    }

    public void setServerTime(int serverTime) {
        this.serverTime = serverTime;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getServerType() {
        return serverType;
    }

    public void setServerType(String serverType) {
        this.serverType = serverType;
    }

    public String getApplyMd() {
        return applyMd;
    }

    public void setApplyMd(String applyMd) {
        this.applyMd = applyMd;
    }

    public float getServerMoney() {
        return serverMoney;
    }

    public void setServerMoney(float serverMoney) {
        this.serverMoney = serverMoney;
    }

    public boolean isServerSaleFlag() {
        return serverSaleFlag;
    }

    public void setServerSaleFlag(boolean serverSaleFlag) {
        this.serverSaleFlag = serverSaleFlag;
    }


    public String getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public FuWuSaleBean(String serverUrl, String serverName, String serverType, String applyMd, float serverMoney, boolean serverSaleFlag) {
        this.serverUrl = serverUrl;
        this.serverName = serverName;
        this.serverType = serverType;
        this.applyMd = applyMd;
        this.serverMoney = serverMoney;
        this.serverSaleFlag = serverSaleFlag;
    }

    @Override
    public String toString() {
        return "FuWuSaleBean{" +
                "serverUrl='" + serverUrl + '\'' +
                ", serverName='" + serverName + '\'' +
                ", serverType='" + serverType + '\'' +
                ", applyMd='" + applyMd + '\'' +
                ", serverMoney=" + serverMoney +
                ", serverTime=" + serverTime +
                ", serverSaleFlag=" + serverSaleFlag +
                '}';
    }
}

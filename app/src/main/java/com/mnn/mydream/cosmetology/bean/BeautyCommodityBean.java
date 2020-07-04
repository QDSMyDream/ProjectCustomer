package com.mnn.mydream.cosmetology.bean;

import cn.bmob.v3.BmobObject;

/**
 * 创建人：MyDream
 * 创建日期：2020/6/23 19:01
 * 类描述 : saleBean 产品表
 */
public class BeautyCommodityBean extends BmobObject {

    private String spUrl;//图标
    private String spName;//名字
    private String spSpecifications;//规格
    private String spType;//类型
    private String spCompany;//
    private float serverMoney;//价钱
    private boolean serverSaleFlag; //是否上架
    private String spRemarks;//备注

    @Override
    public String toString() {
        return "BeautyCommodityBean{" +
                "spUrl='" + spUrl + '\'' +
                ", spName='" + spName + '\'' +
                ", spSpecifications='" + spSpecifications + '\'' +
                ", spType='" + spType + '\'' +
                ", spCompany='" + spCompany + '\'' +
                ", serverMoney=" + serverMoney +
                ", serverSaleFlag=" + serverSaleFlag +
                ", spRemarks='" + spRemarks + '\'' +
                '}';
    }

    public String getSpUrl() {
        return spUrl;
    }

    public void setSpUrl(String spUrl) {
        this.spUrl = spUrl;
    }

    public String getSpName() {
        return spName;
    }

    public void setSpName(String spName) {
        this.spName = spName;
    }

    public String getSpSpecifications() {
        return spSpecifications;
    }

    public void setSpSpecifications(String spSpecifications) {
        this.spSpecifications = spSpecifications;
    }

    public String getSpType() {
        return spType;
    }

    public void setSpType(String spType) {
        this.spType = spType;
    }

    public String getSpCompany() {
        return spCompany;
    }

    public void setSpCompany(String spCompany) {
        this.spCompany = spCompany;
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

    public String getSpRemarks() {
        return spRemarks;
    }

    public void setSpRemarks(String spRemarks) {
        this.spRemarks = spRemarks;
    }
}

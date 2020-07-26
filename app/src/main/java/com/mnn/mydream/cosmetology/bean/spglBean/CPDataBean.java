package com.mnn.mydream.cosmetology.bean.spglBean;

import cn.bmob.v3.BmobObject;

/**
 * 创建人：MyDream
 * 创建日期：2020/7/12 18:43
 * 类描述 :产品bean
 */
public class CPDataBean extends BmobObject {

    private String cpUrl;

    private String cpName;

    private String cpType;

    private float cpMoney;//价钱

    private String applyMd;//门店

    private int cpNum; //产品销量

    private String cpCharacteristic; //产品特点

    private boolean isOpenSpecifications;//是否启用规格

    private int intSpecifications;//规格

    private boolean isOpenVipMoney;//是否启用会员价

    private float cpVipMoney;//会员价格

    private float cpOriginalPrice;//产品原价

    private boolean cpSaleFlag; //是否上架


    public String getCpUrl() {
        return cpUrl;
    }

    public void setCpUrl(String cpUrl) {
        this.cpUrl = cpUrl;
    }

    public String getCpName() {
        return cpName;
    }

    public void setCpName(String cpName) {
        this.cpName = cpName;
    }

    public String getCpType() {
        return cpType;
    }

    public void setCpType(String cpType) {
        this.cpType = cpType;
    }

    public float getCpMoney() {
        return cpMoney;
    }

    public void setCpMoney(float cpMoney) {
        this.cpMoney = cpMoney;
    }

    public String getApplyMd() {
        return applyMd;
    }

    public void setApplyMd(String applyMd) {
        this.applyMd = applyMd;
    }

    public int getCpNum() {
        return cpNum;
    }

    public void setCpNum(int cpNum) {
        this.cpNum = cpNum;
    }

    public String getCpCharacteristic() {
        return cpCharacteristic;
    }

    public void setCpCharacteristic(String cpCharacteristic) {
        this.cpCharacteristic = cpCharacteristic;
    }

    public boolean isOpenSpecifications() {
        return isOpenSpecifications;
    }

    public void setOpenSpecifications(boolean openSpecifications) {
        isOpenSpecifications = openSpecifications;
    }

    public int getIntSpecifications() {
        return intSpecifications;
    }

    public void setIntSpecifications(int intSpecifications) {
        this.intSpecifications = intSpecifications;
    }

    public boolean isOpenVipMoney() {
        return isOpenVipMoney;
    }

    public void setOpenVipMoney(boolean openVipMoney) {
        isOpenVipMoney = openVipMoney;
    }

    public float getCpVipMoney() {
        return cpVipMoney;
    }

    public void setCpVipMoney(float cpVipMoney) {
        this.cpVipMoney = cpVipMoney;
    }

    public float getCpOriginalPrice() {
        return cpOriginalPrice;
    }

    public void setCpOriginalPrice(float cpOriginalPrice) {
        this.cpOriginalPrice = cpOriginalPrice;
    }

    public boolean isCpSaleFlag() {
        return cpSaleFlag;
    }

    public void setCpSaleFlag(boolean cpSaleFlag) {
        this.cpSaleFlag = cpSaleFlag;
    }

    @Override
    public String toString() {
        return "CPDataBean{" +
                "cpUrl='" + cpUrl + '\'' +
                ", cpName='" + cpName + '\'' +
                ", cpType='" + cpType + '\'' +
                ", cpMoney=" + cpMoney +
                ", applyMd='" + applyMd + '\'' +
                ", cpNum=" + cpNum +
                ", cpCharacteristic='" + cpCharacteristic + '\'' +
                ", isOpenSpecifications=" + isOpenSpecifications +
                ", intSpecifications=" + intSpecifications +
                ", isOpenVipMoney=" + isOpenVipMoney +
                ", cpVipMoney=" + cpVipMoney +
                ", cpOriginalPrice=" + cpOriginalPrice +
                ", cpSaleFlag=" + cpSaleFlag +
                '}';
    }
}

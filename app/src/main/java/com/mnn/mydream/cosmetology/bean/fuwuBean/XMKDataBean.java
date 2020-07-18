package com.mnn.mydream.cosmetology.bean.fuwuBean;

import cn.bmob.v3.BmobObject;

/**
 * 创建人：MyDream
 * 创建日期：2020/7/12 18:43
 * 类描述 :项目卡 bean
 */

public class XMKDataBean extends BmobObject {

    private String xmkName;//项目卡名称

    private String xmkType;//项目卡类型

    private String fwJson;//项目卡服务json数据

    private float xmkMoney;//项目价格

    private float xmkVipMoney;//项目vip价格

    private boolean xmkVipFlag;//是否启用vip价格

    private boolean effectiveFlag;//是否启用有效时间

    private String effectiveTime;//有效时间

    private boolean empowerFlag;//卡授权  是  否

    private String xmkMd;//适用门店

    private String coverColorStr;//项目卡封面颜色   例如 蓝色

    private String xmkNum;

    private String characteristicStr;//特点

    @Override
    public String toString() {
        return "XMKDataBean{" +
                "xmkName='" + xmkName + '\'' +
                ", xmkType='" + xmkType + '\'' +
                ", fwJson='" + fwJson + '\'' +
                ", xmkMoney=" + xmkMoney +
                ", xmkVipMoney=" + xmkVipMoney +
                ", xmkVipFlag=" + xmkVipFlag +
                ", effectiveFlag=" + effectiveFlag +
                ", effectiveTime='" + effectiveTime + '\'' +
                ", empowerFlag=" + empowerFlag +
                ", xmkMd='" + xmkMd + '\'' +
                ", coverColorStr='" + coverColorStr + '\'' +
                ", xmkNum='" + xmkNum + '\'' +
                ", characteristicStr='" + characteristicStr + '\'' +
                '}';
    }

    public String getXmkName() {
        return xmkName;
    }

    public void setXmkName(String xmkName) {
        this.xmkName = xmkName;
    }

    public String getXmkType() {
        return xmkType;
    }

    public void setXmkType(String xmkType) {
        this.xmkType = xmkType;
    }

    public String getFwJson() {
        return fwJson;
    }

    public void setFwJson(String fwJson) {
        this.fwJson = fwJson;
    }

    public float getXmkMoney() {
        return xmkMoney;
    }

    public void setXmkMoney(float xmkMoney) {
        this.xmkMoney = xmkMoney;
    }

    public float getXmkVipMoney() {
        return xmkVipMoney;
    }

    public void setXmkVipMoney(float xmkVipMoney) {
        this.xmkVipMoney = xmkVipMoney;
    }

    public boolean isXmkVipFlag() {
        return xmkVipFlag;
    }

    public void setXmkVipFlag(boolean xmkVipFlag) {
        this.xmkVipFlag = xmkVipFlag;
    }

    public boolean isEffectiveFlag() {
        return effectiveFlag;
    }

    public void setEffectiveFlag(boolean effectiveFlag) {
        this.effectiveFlag = effectiveFlag;
    }

    public String getEffectiveTime() {
        return effectiveTime;
    }

    public void setEffectiveTime(String effectiveTime) {
        this.effectiveTime = effectiveTime;
    }

    public boolean isEmpowerFlag() {
        return empowerFlag;
    }

    public void setEmpowerFlag(boolean empowerFlag) {
        this.empowerFlag = empowerFlag;
    }

    public String getXmkMd() {
        return xmkMd;
    }

    public void setXmkMd(String xmkMd) {
        this.xmkMd = xmkMd;
    }

    public String getCoverColorStr() {
        return coverColorStr;
    }

    public void setCoverColorStr(String coverColorStr) {
        this.coverColorStr = coverColorStr;
    }

    public String getXmkNum() {
        return xmkNum;
    }

    public void setXmkNum(String xmkNum) {
        this.xmkNum = xmkNum;
    }

    public String getCharacteristicStr() {
        return characteristicStr;
    }

    public void setCharacteristicStr(String characteristicStr) {
        this.characteristicStr = characteristicStr;
    }
}

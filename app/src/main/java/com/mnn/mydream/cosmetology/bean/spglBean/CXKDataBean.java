package com.mnn.mydream.cosmetology.bean.spglBean;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

/**
 * 创建人：MyDream
 * 创建日期：2020/6/23 19:01
 * 类描述 : 储蓄卡列表
 */
public class CXKDataBean extends BmobObject implements Serializable {

    private String cxkName;
    private String czfa;

    private String kzkFW;
    private String kzkCP;
    private String kzkXMK;

    private boolean kzkFWFlag;
    private boolean kzkCPFlag;
    private boolean kzkXMKFlag;

    private boolean ryc;
    private boolean ksq;
    private String md;
    private String fm;

    private String num;
    private String characteristicStr;//特点

    @Override
    public String toString() {
        return "CXKDataBean{" +
                "cxkName='" + cxkName + '\'' +
                ", czfa='" + czfa + '\'' +
                ", kzkFW='" + kzkFW + '\'' +
                ", kzkFWFlag=" + kzkFWFlag +
                ", kzkCPFlag=" + kzkCPFlag +
                ", kzkXMKFlag=" + kzkXMKFlag +
                ", kzkCP='" + kzkCP + '\'' +
                ", kzkXMK='" + kzkXMK + '\'' +
                ", ryc=" + ryc +
                ", ksq=" + ksq +
                ", md='" + md + '\'' +
                ", fm='" + fm + '\'' +
                ", num=" + num +
                ", characteristicStr='" + characteristicStr + '\'' +
                '}';
    }

    public String getCxkName() {
        return cxkName;
    }

    public void setCxkName(String cxkName) {
        this.cxkName = cxkName;
    }

    public String getCzfa() {
        return czfa;
    }

    public void setCzfa(String czfa) {
        this.czfa = czfa;
    }

    public String getKzkFW() {
        return kzkFW;
    }

    public void setKzkFW(String kzkFW) {
        this.kzkFW = kzkFW;
    }

    public boolean isKzkFWFlag() {
        return kzkFWFlag;
    }

    public void setKzkFWFlag(boolean kzkFWFlag) {
        this.kzkFWFlag = kzkFWFlag;
    }

    public boolean isKzkCPFlag() {
        return kzkCPFlag;
    }

    public void setKzkCPFlag(boolean kzkCPFlag) {
        this.kzkCPFlag = kzkCPFlag;
    }

    public boolean isKzkXMKFlag() {
        return kzkXMKFlag;
    }

    public void setKzkXMKFlag(boolean kzkXMKFlag) {
        this.kzkXMKFlag = kzkXMKFlag;
    }

    public String getKzkCP() {
        return kzkCP;
    }

    public void setKzkCP(String kzkCP) {
        this.kzkCP = kzkCP;
    }

    public String getKzkXMK() {
        return kzkXMK;
    }

    public void setKzkXMK(String kzkXMK) {
        this.kzkXMK = kzkXMK;
    }

    public boolean isRyc() {
        return ryc;
    }

    public void setRyc(boolean ryc) {
        this.ryc = ryc;
    }

    public boolean isKsq() {
        return ksq;
    }

    public void setKsq(boolean ksq) {
        this.ksq = ksq;
    }

    public String getMd() {
        return md;
    }

    public void setMd(String md) {
        this.md = md;
    }

    public String getFm() {
        return fm;
    }

    public void setFm(String fm) {
        this.fm = fm;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getCharacteristicStr() {
        return characteristicStr;
    }

    public void setCharacteristicStr(String characteristicStr) {
        this.characteristicStr = characteristicStr;
    }
}

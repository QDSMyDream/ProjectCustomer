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
    private String kzk;
    private boolean ryc;
    private boolean ksq;
    private String md;
    private String fm;
    private int num;
    private String characteristicStr;//特点

    @Override
    public String toString() {
        return "CXKDataBean{" +
                "cxkName='" + cxkName + '\'' +
                ", czfa='" + czfa + '\'' +
                ", kzk='" + kzk + '\'' +
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

    public String getKzk() {
        return kzk;
    }

    public void setKzk(String kzk) {
        this.kzk = kzk;
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

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getCharacteristicStr() {
        return characteristicStr;
    }

    public void setCharacteristicStr(String characteristicStr) {
        this.characteristicStr = characteristicStr;
    }
}

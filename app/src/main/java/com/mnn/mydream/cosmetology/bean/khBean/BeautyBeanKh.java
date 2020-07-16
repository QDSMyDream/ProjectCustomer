package com.mnn.mydream.cosmetology.bean.khBean;

import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;

/**
 * 创建人：MyDream
 * 创建日期：2020/6/13 15:49
 * 类描述 : 客户表
 */
public class BeautyBeanKh extends BmobObject {

    private String tx;

    private String phone;

    private String name;

    private String sex;

    private String bir;

    private String remarksContent;

    private String ly;//来源

    private String hy;//会员

    private String md;//门店

    private String js;//技师

    private BmobUser bmobUser;//添加人

    private String xmkJson;//储蓄卡数据

    private String cxkJson;//项目卡数据

    private String lpkJson;//礼品卡数据

    private int dcfw;//单次服务

    private int yhq;//优惠券

    private float zsj;//赠送金

    private float qk;//欠款

    @Override
    public String toString() {
        return "BeautyBeanKh{" +
                "tx='" + tx + '\'' +
                ", phone='" + phone + '\'' +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", bir='" + bir + '\'' +
                ", remarksContent='" + remarksContent + '\'' +
                ", ly='" + ly + '\'' +
                ", hy='" + hy + '\'' +
                ", md='" + md + '\'' +
                ", js='" + js + '\'' +
                ", bmobUser=" + bmobUser +
                ", xmkJson='" + xmkJson + '\'' +
                ", cxkJson='" + cxkJson + '\'' +
                ", lpkJson='" + lpkJson + '\'' +
                ", dcfw=" + dcfw +
                ", yhq=" + yhq +
                ", zsj=" + zsj +
                ", qk=" + qk +
                '}';
    }

    public String getTx() {
        return tx;
    }

    public void setTx(String tx) {
        this.tx = tx;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBir() {
        return bir;
    }

    public void setBir(String bir) {
        this.bir = bir;
    }

    public String getRemarksContent() {
        return remarksContent;
    }

    public void setRemarksContent(String remarksContent) {
        this.remarksContent = remarksContent;
    }

    public String getLy() {
        return ly;
    }

    public void setLy(String ly) {
        this.ly = ly;
    }

    public String getHy() {
        return hy;
    }

    public void setHy(String hy) {
        this.hy = hy;
    }

    public String getMd() {
        return md;
    }

    public void setMd(String md) {
        this.md = md;
    }

    public String getJs() {
        return js;
    }

    public void setJs(String js) {
        this.js = js;
    }

    public BmobUser getBmobUser() {
        return bmobUser;
    }

    public void setBmobUser(BmobUser bmobUser) {
        this.bmobUser = bmobUser;
    }

    public String getXmkJson() {
        return xmkJson;
    }

    public void setXmkJson(String xmkJson) {
        this.xmkJson = xmkJson;
    }

    public String getCxkJson() {
        return cxkJson;
    }

    public void setCxkJson(String cxkJson) {
        this.cxkJson = cxkJson;
    }

    public String getLpkJson() {
        return lpkJson;
    }

    public void setLpkJson(String lpkJson) {
        this.lpkJson = lpkJson;
    }

    public int getDcfw() {
        return dcfw;
    }

    public void setDcfw(int dcfw) {
        this.dcfw = dcfw;
    }

    public int getYhq() {
        return yhq;
    }

    public void setYhq(int yhq) {
        this.yhq = yhq;
    }

    public float getZsj() {
        return zsj;
    }

    public void setZsj(float zsj) {
        this.zsj = zsj;
    }

    public float getQk() {
        return qk;
    }

    public void setQk(float qk) {
        this.qk = qk;
    }
}

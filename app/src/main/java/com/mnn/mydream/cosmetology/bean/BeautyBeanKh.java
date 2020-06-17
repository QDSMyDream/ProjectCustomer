package com.mnn.mydream.cosmetology.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;

/**
 * 创建人：MyDream
 * 创建日期：2020/6/13 15:49
 * 类描述 :
 */
public class BeautyBeanKh extends BmobObject {

    private String tx;

    private String phone;

    private String name;

    private String sex;

    private String bir;

    private String remarksContent;

    private String js;

    private String ly;

    private String hy;

    private String md;
    private BmobUser bmobUser;

    public BmobUser getBmobUser() {
        return bmobUser;
    }

    public void setBmobUser(BmobUser bmobUser) {
        this.bmobUser = bmobUser;
    }


    public BeautyBeanKh() {

    }


    public String getTx() {
        return tx;
    }

    public void setTx(String tx) {
        this.tx = tx;
    }

    public String getMd() {
        return md;
    }

    public void setMd(String md) {
        this.md = md;
    }


    @Override
    public String toString() {
        return "BeautyBeanKh{" +
                "tx='" + tx + '\'' +
                ", phone='" + phone + '\'' +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", bir='" + bir + '\'' +
                ", remarksContent='" + remarksContent + '\'' +
                ", js='" + js + '\'' +
                ", ly='" + ly + '\'' +
                ", hy='" + hy + '\'' +
                ", md='" + md + '\'' +
                ", bmobUser=" + bmobUser +
                '}';
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

    public String getJs() {
        return js;
    }

    public void setJs(String js) {
        this.js = js;
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


}

package com.mnn.mydream.cosmetology.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;

/**
 * 创建人：MyDream
 * 创建日期：2020/5/10 10:05
 * 类描述：客户总信息表
 */

public class Customer extends BmobObject {

    private String customer_tx;//客户头像

    private String name;//客户姓名

    private String sex;//客户性别

    private String birthday;//客户生日

    private int age;//客户年龄

    private String phone;//客户电话

    private float money;//客户所缴的总费用

    private boolean ispurchase;//是否团购

    private String purchase_project;//团购项目

    private String projects;//客户所作的项目

    private int use_count;//客户来了多少次

    private int surplus_count;//剩余多少次

    private int total_count;//总共多少次

    private String come_time;//客户来的时间

    // 仅在客户端使用，不希望被gson序列化提交到后端云，记得用transient修饰
    // private transient Integer count;

// private  List<CustomerProjectsItem> projectsTexts;//预留字段

    private  String remarks;//备注

    private BmobUser bmobUser;

    public BmobUser getBmobUser() {
        return bmobUser;
    }

    public void setBmobUser(BmobUser bmobUser) {
        this.bmobUser = bmobUser;
    }

    public Customer() {

    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customer_tx=" + customer_tx +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", birthday='" + birthday + '\'' +
                ", age=" + age +
                ", phone='" + phone + '\'' +
                ", money=" + money +
                ", ispurchase=" + ispurchase +
                ", purchase_project='" + purchase_project + '\'' +
                ", projects='" + projects + '\'' +
                ", use_count=" + use_count +
                ", surplus_count=" + surplus_count +
                ", total_count=" + total_count +
                ", come_time='" + come_time + '\'' +
                ", remarks='" + remarks + '\'' +
                ", bmobUser=" + bmobUser +
                '}';
    }

    public String getCustomer_tx() {
        return customer_tx;
    }

    public void setCustomer_tx(String customer_tx) {
        this.customer_tx = customer_tx;
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

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public boolean isIspurchase() {
        return ispurchase;
    }

    public void setIspurchase(boolean ispurchase) {
        this.ispurchase = ispurchase;
    }

    public String getPurchase_project() {
        return purchase_project;
    }

    public void setPurchase_project(String purchase_project) {
        this.purchase_project = purchase_project;
    }

    public String getProjects() {
        return projects;
    }

    public void setProjects(String projects) {
        this.projects = projects;
    }

    public int getUse_count() {
        return use_count;
    }

    public void setUse_count(int use_count) {
        this.use_count = use_count;
    }

    public int getSurplus_count() {
        return surplus_count;
    }

    public void setSurplus_count(int surplus_count) {
        this.surplus_count = surplus_count;
    }

    public int getTotal_count() {
        return total_count;
    }

    public void setTotal_count(int total_count) {
        this.total_count = total_count;
    }

    public String getCome_time() {
        return come_time;
    }

    public void setCome_time(String come_time) {
        this.come_time = come_time;
    }


}

package com.mnn.mydream.cosmetology.bean;

import cn.bmob.v3.BmobUser;

/**
 * 创建人：MyDream
 * 创建日期：2020/6/12 21:11
 * 类描述 : 账户表
 */
public class User extends BmobUser {
    private String userTx;
    private int power;
    /**
     * 年龄
     */
    private int age;

    /**
     * 性别
     */
    private int sex;
    /**
     * 国家
     */

    private String country;


    public User() {

    }

    public String getUserTx() {
        return userTx;
    }

    public void setUserTx(String userTx) {
        this.userTx = userTx;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public User(String userTx, int power, int age, int sex, String country) {
        this.userTx = userTx;
        this.power = power;
        this.age = age;
        this.sex = sex;
        this.country = country;
    }

    @Override
    public String toString() {
        return "User{" +
                "userTx='" + userTx + '\'' +
                ", power=" + power +
                ", age=" + age +
                ", sex=" + sex +
                ", country='" + country + '\'' +
                '}';
    }
}

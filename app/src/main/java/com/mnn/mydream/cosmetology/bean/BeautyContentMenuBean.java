package com.mnn.mydream.cosmetology.bean;


import java.util.List;

/**
 * 创建人：MyDream
 * 创建日期：2020/6/10 10:05
 * 类描述：主界面左边listview显示
 */
public class BeautyContentMenuBean {

    private String menuString;

    private List<BeautyContentMenuItem> beautyContentMenuItems;

    public BeautyContentMenuBean(String menuString, List<BeautyContentMenuItem> beautyContentMenuItems) {
        this.menuString = menuString;
        this.beautyContentMenuItems = beautyContentMenuItems;
    }

    public List<BeautyContentMenuItem> getBeautyContentMenuItems() {
        return beautyContentMenuItems;
    }

    public void setBeautyContentMenuItems(List<BeautyContentMenuItem> beautyContentMenuItems) {
        this.beautyContentMenuItems = beautyContentMenuItems;
    }

    @Override
    public String toString() {
        return "BeautyContentMenuBean{" +
                "menuString='" + menuString + '\'' +
                ", beautyContentMenuItems=" + beautyContentMenuItems +
                '}';
    }

    public BeautyContentMenuBean(String menuString) {
        this.menuString = menuString;
    }

    public String getMenuString() {
        return menuString;
    }

    public void setMenuString(String menuString) {
        this.menuString = menuString;
    }
}

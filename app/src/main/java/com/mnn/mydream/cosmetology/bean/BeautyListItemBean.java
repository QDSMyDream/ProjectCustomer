package com.mnn.mydream.cosmetology.bean;


/**
 * 创建人：MyDream
 * 创建日期：2020/6/10 10:05
 * 类描述：主界面左边listview显示
 */
public class BeautyListItemBean {
    private int img;
    private String menuString;
    private int menuFontColor;
    private int imgSelect;
    private int menuSelectFontColor;

    public BeautyListItemBean(int img, String menuString, int menuFontColor, int imgSelect, int menuSelectFontColor) {
        this.img = img;
        this.menuString = menuString;
        this.menuFontColor = menuFontColor;
        this.imgSelect = imgSelect;
        this.menuSelectFontColor = menuSelectFontColor;
    }

    @Override
    public String toString() {
        return "BeautyListItemBean{" +
                "img=" + img +
                ", menuString='" + menuString + '\'' +
                ", menuFontColor=" + menuFontColor +
                ", imgSelect=" + imgSelect +
                ", menuSelectFontColor=" + menuSelectFontColor +
                '}';
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getMenuString() {
        return menuString;
    }

    public void setMenuString(String menuString) {
        this.menuString = menuString;
    }

    public int getMenuFontColor() {
        return menuFontColor;
    }

    public void setMenuFontColor(int menuFontColor) {
        this.menuFontColor = menuFontColor;
    }

    public int getImgSelect() {
        return imgSelect;
    }

    public void setImgSelect(int imgSelect) {
        this.imgSelect = imgSelect;
    }

    public int getMenuSelectFontColor() {
        return menuSelectFontColor;
    }

    public void setMenuSelectFontColor(int menuSelectFontColor) {
        this.menuSelectFontColor = menuSelectFontColor;
    }
}

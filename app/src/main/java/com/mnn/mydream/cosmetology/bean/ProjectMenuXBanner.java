package com.mnn.mydream.cosmetology.bean;

import cn.bmob.v3.BmobObject;

public class ProjectMenuXBanner  extends BmobObject {

    private  String imgUrl="";

    private  String imgText="";

    public ProjectMenuXBanner(String imgUrl, String imgText) {
        this.imgUrl = imgUrl;
        this.imgText = imgText;
    }

    @Override
    public String toString() {
        return "ProjectMenuXBanner{" +
                "imgUrl='" + imgUrl + '\'' +
                ", imgText='" + imgText + '\'' +
                '}';
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getImgText() {
        return imgText;
    }

    public void setImgText(String imgText) {
        this.imgText = imgText;
    }
}

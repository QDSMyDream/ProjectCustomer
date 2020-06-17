package com.mnn.mydream.cosmetology.bean;

import cn.bmob.v3.BmobObject;

/**
 * 创建人：MyDream
 * 创建日期：2020/6/8 22:27
 * 类描述 :
 */
public class ProjectMenuBean extends BmobObject {


    private String projectMenuImg = "";
    private String projectMenuString = "";

    public ProjectMenuBean( String projectMenuImg, String projectMenuString) {

        this.projectMenuImg = projectMenuImg;
        this.projectMenuString = projectMenuString;
    }

    @Override
    public String toString() {
        return "ProjectMenuBean{" +
                ", projectMenuImg='" + projectMenuImg + '\'' +
                ", projectMenuString='" + projectMenuString + '\'' +
                '}';
    }


    public String getProjectMenuImg() {
        return projectMenuImg;
    }

    public void setProjectMenuImg(String projectMenuImg) {
        this.projectMenuImg = projectMenuImg;
    }

    public String getProjectMenuString() {
        return projectMenuString;
    }

    public void setProjectMenuString(String projectMenuString) {
        this.projectMenuString = projectMenuString;
    }
}

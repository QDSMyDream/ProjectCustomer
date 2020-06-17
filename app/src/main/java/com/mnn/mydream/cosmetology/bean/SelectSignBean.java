package com.mnn.mydream.cosmetology.bean;

/**
 * 创建人：MyDream
 * 创建日期：2020/5/18 21:12
 * 类描述 :选中签字的bean
 */
public class SelectSignBean {


    private int id;
    private String projectsString;
    private boolean isSignFlag;

    public SelectSignBean() {
        super();
    }

    public SelectSignBean(int id, String projectsString, boolean isSignFlag) {
        this.id = id;
        this.projectsString = projectsString;
        this.isSignFlag = isSignFlag;
    }

    @Override
    public String toString() {
        return "SelectSignBean{" +
                "id=" + id +
                ", projectsString='" + projectsString + '\'' +
                ", isSignFlag=" + isSignFlag +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProjectsString() {
        return projectsString;
    }

    public void setProjectsString(String projectsString) {
        this.projectsString = projectsString;
    }

    public boolean isSignFlag() {
        return isSignFlag;
    }

    public void setSignFlag(boolean signFlag) {
        isSignFlag = signFlag;
    }
}

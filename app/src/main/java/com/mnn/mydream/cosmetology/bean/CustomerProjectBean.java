package com.mnn.mydream.cosmetology.bean;

import cn.bmob.v3.BmobObject;


/**
 * 创建人：MyDream
 * 创建日期：2020/5/10 10:05
 * 类描述：总项目表
 */

public class CustomerProjectBean extends BmobObject {

    private String cProjects;//项目名称

    @Override
    public String toString() {
        return "CustomerProjects{" +
                "cProjects='" + cProjects + '\'' +
                '}';
    }

    public CustomerProjectBean(String cProjects) {
        this.cProjects = cProjects;

    }

    public CustomerProjectBean() {
        super();
    }

    public String getcProjects() {
        return cProjects;
    }

    public void setcProjects(String cProjects) {
        this.cProjects = cProjects;
    }


}

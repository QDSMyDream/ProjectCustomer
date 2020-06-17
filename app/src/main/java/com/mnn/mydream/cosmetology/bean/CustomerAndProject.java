package com.mnn.mydream.cosmetology.bean;

import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * 创建人：MyDream
 * 创建日期：2020/5/10 10:05
 * 类描述：客户总信息表
 */

public class CustomerAndProject extends BmobObject {

  private Customer customer;

    private List<CustomerProjectsItem> projectsTexts;//
    public CustomerAndProject() {

    }
    public CustomerAndProject(Customer customer, List<CustomerProjectsItem> projectsTexts) {
        this.customer = customer;
        this.projectsTexts = projectsTexts;
    }


    @Override
    public String toString() {
        return "CustomerAndProjects{" +
                "customer=" + customer +
                ", projectsTexts=" + projectsTexts +
                '}';
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<CustomerProjectsItem> getProjectsTexts() {
        return projectsTexts;
    }

    public void setProjectsTexts(List<CustomerProjectsItem> projectsTexts) {
        this.projectsTexts = projectsTexts;
    }
}

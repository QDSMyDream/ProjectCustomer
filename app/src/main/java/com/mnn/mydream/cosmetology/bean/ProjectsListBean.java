package com.mnn.mydream.cosmetology.bean;

public class ProjectsListBean  {

    private String pField;

    private CustomerProjectBean customerProject;
    private int peojectsCount;

    public ProjectsListBean() {

    }

    public String getpField() {
        return pField;
    }

    public void setpField(String pField) {
        this.pField = pField;
    }

    public CustomerProjectBean getCustomerProject() {
        return customerProject;
    }

    public void setCustomerProject(CustomerProjectBean customerProject) {
        this.customerProject = customerProject;
    }

    public int getPeojectsCount() {
        return peojectsCount;
    }

    public void setPeojectsCount(int peojectsCount) {
        this.peojectsCount = peojectsCount;
    }

    @Override
    public String toString() {
        return "ProjectsListBean{" +
                "pField='" + pField + '\'' +
                ", customerProject=" + customerProject +
                ", peojectsCount=" + peojectsCount +
                '}';
    }
}

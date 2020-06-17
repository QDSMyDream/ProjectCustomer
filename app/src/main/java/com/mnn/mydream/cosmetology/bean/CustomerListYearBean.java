package com.mnn.mydream.cosmetology.bean;

import java.util.List;

public class CustomerListYearBean {

    private String pField;

    private List<CustomerProjectsItem> customerProjectsItems;

    public List<CustomerProjectsItem> getCustomerProjectsItems() {
        return customerProjectsItems;
    }

    public void setCustomerProjectsItems(List<CustomerProjectsItem> customerProjectsItems) {
        this.customerProjectsItems = customerProjectsItems;
    }

    public String getpField() {
        return pField;
    }

    public void setpField(String pField) {
        this.pField = pField;
    }
}

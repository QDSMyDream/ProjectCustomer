package com.mnn.mydream.cosmetology.bean;

public class SearchBean {

    private String sPhone;
    private String sName;

    public String getsPhone() {
        return sPhone;
    }

    public void setsPhone(String sPhone) {
        this.sPhone = sPhone;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public SearchBean(String sPhone, String sName) {
        this.sPhone = sPhone;
        this.sName = sName;
    }


}

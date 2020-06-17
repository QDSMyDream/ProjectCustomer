package com.mnn.mydream.cosmetology.bean;

import cn.bmob.v3.BmobObject;

public class CustomerServiceItems  extends BmobObject{

    private String item_projects;
    private String item_time;

    public CustomerServiceItems(String tableName, String item_projects, String item_time) {
        super(tableName);
        this.item_projects = item_projects;
        this.item_time = item_time;
    }

    public CustomerServiceItems(String item_projects, String item_time) {
        this.item_projects = item_projects;
        this.item_time = item_time;
    }

    public String getItem_projects() {
        return item_projects;
    }

    public void setItem_projects(String item_projects) {
        this.item_projects = item_projects;
    }

    public String getItem_time() {
        return item_time;
    }

    public void setItem_time(String item_time) {
        this.item_time = item_time;
    }
}

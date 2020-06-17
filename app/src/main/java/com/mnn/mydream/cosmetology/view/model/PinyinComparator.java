package com.mnn.mydream.cosmetology.view.model;

import com.mnn.mydream.cosmetology.bean.ProjectsListBean;

import java.util.Comparator;


public class PinyinComparator implements Comparator<ProjectsListBean> {


    @Override
    public int compare(ProjectsListBean o1, ProjectsListBean o2) {
        //这里主要是用来对ListView里面的数据依据ABCDEFG...来排序
        if (o1.getpField().equals("@")
                || o2.getpField().equals("#")) {
            return -1;
        } else if (o1.getpField().equals("#")
                || o2.getpField().equals("@")) {
            return 1;
        } else {
            return o1.getpField().compareTo(o2.getpField());
        }
    }
}

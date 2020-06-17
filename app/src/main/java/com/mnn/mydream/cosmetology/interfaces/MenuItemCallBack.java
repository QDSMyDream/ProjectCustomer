package com.mnn.mydream.cosmetology.interfaces;

import com.mnn.mydream.cosmetology.bean.ProjectMenuBean;

import java.util.List;

/**
 * 创建人：MyDream
 * 创建日期：2020/5/11 23:41
 * 类描述：时间dialog 传值
 */
public interface MenuItemCallBack {
    void onSetting(List<ProjectMenuBean> projectMenuBeans);
}
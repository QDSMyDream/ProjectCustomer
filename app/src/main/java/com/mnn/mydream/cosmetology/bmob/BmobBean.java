package com.mnn.mydream.cosmetology.bmob;

import android.os.Handler;

import com.mnn.mydream.cosmetology.bean.fuwuBean.FuWuSaleBean;
import com.mnn.mydream.cosmetology.utils.Constons;

import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * 创建人：MyDream
 * 创建日期：2020/7/17 21:04
 * 类描述 : Bmob数据库处理 基类
 */
public class BmobBean<T> extends BmobObject {


    /**
     * 添加数据库
     */
    public void addBean() {

        this.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {

            }
        });

    }

    /**
     * 删除数据库
     */
    public void dropBean() {


    }





    /**
     * 修改数据库
     */
    public void updateBean() {


    }


}

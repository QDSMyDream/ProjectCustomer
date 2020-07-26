package com.mnn.mydream.cosmetology.bean.spglBean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;

/**
 * 创建人：MyDream
 * 创建日期：2020/7/5 16:56
 * 类描述 :
 */
public class ServerTypeBean extends BmobObject {

    private int serverTypeIndex;
    private String serverTypeString;//类型名称

    private BmobUser bmobUser;//添加人

    @Override
    public String toString() {
        return "ServerTypeBean{" +
                "serverTypeIndex=" + serverTypeIndex +
                ", serverTypeString=" + serverTypeString +
                ", bmobUser=" + bmobUser +
                '}';
    }

    public int getServerTypeIndex() {
        return serverTypeIndex;
    }

    public void setServerTypeIndex(int serverTypeIndex) {
        this.serverTypeIndex = serverTypeIndex;
    }

    public String getServerTypeString() {
        return serverTypeString;
    }

    public void setServerTypeString(String serverTypeString) {
        this.serverTypeString = serverTypeString;
    }

    public BmobUser getBmobUser() {
        return bmobUser;
    }

    public void setBmobUser(BmobUser bmobUser) {
        this.bmobUser = bmobUser;
    }
}

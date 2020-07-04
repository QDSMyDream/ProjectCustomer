package com.mnn.mydream.cosmetology.utils;


import com.mnn.mydream.cosmetology.bean.Customer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Constons {

    public static Customer costorBean;//存储
    public static List<Customer> costorBeans = new ArrayList<>();//存储List
    public static final String SIGN_PNG_PATH_FL = "/storage/emulated/0" + "/customer";
    //    public static final String SIGN_PNG_PATH_FL = Environment.getExternalStorageDirectory().getPath() + "/customer";
    public static final String SIGN_PNG_PATH = SIGN_PNG_PATH_FL + File.separator + "png" + File.separator;


    public static final int STATE_NORMAL = 0;//正常状态
    public static final int STATE_MODIFY = 1;//修改状态
    public static final int STATE_SIGN = 2;//签名状态
    public static final int STATE_DELETE = 3;//删除状态
    public static final int STATE_ADDCUSTOMER = 4;//添加用户状态
    public static final int STATE_ADDPROJECTS = 5;//添加项目状态
    public static final int STATE_MODIFY_PROJECTS = 6;//修改项目信息状态
    public static final int STATE_EDIT_PROJECTS = 7;//编辑项目状态


    public static final int RESULT_LONGIN_SUCCESS = 10002;//登陆成功返回码
    public static final int RESULT_LONGIN_FAIL = -1;//登陆失败或返回
    public static final int BEAUTY_RESULT_LONGIN_CODE = 10001;//beautyActivity请求登陆码


    public static final int RESULT_XJKH_TX_SUCCESS = 10003;//顾客选择头像成功返回码
    public static final int RESULT_XJKH_TX_FAIL = -1;//顾客取消头像设置失败返回码
    public static final int BEAUTY_XJKH_TX_CODE = -10003;//顾客取消头像设置失败返回码

    public static final int RESULT_FUWU_SERVER_CODE_REQUEST = 10005;//添加服务图片请求码
    public static final int RESULT_FUWU_SERVER_CODE_VIEW_REQUEST = 10006;//添加服务请求码

    public static final int RESULT_FUWU_SERVER_CODE_SCUESS_REQUEST = 10007;//添加服务成功请求码
    public static final int RESULT_FUWU_SERVER_CODE_CANCEL_REQUEST = 10008;//添加服务失败或取消请求码


    public static final String RESULT_FUWU_SERVER_STR_UPDATE_REQUEST = "Update";//修改请求码

    public static final int RESULT_FUWU_SERVER_CODE_UPDATE_REQUEST = 10009;//修改请求码

    public static final int RESULT_FUWU_SERVER_CODE_UPDATE2_REQUEST = 10010;//修改请求码


    public static String FUWU_PICPATH = "";


    public static int BEAUTY_FRAGMENT_POSTION = 0;//记录当前fragment位置
    public static final int BEAUTY_FRAGMENT_PREVIOUS_POSTION = 0;//上个fragment位置


    public static int BEAUTY_WITHIN_PREVIOUS_POSTION = 0;//BeautyWithinActivity左侧菜单位置


}

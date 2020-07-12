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


    /**
     * 添加/修改服务
     */
    public static final int RESULT_FUWU_SERVER_CODE_SCUESS_REQUEST = 0x2717;//添加服务成功请求码
    public static final int RESULT_FUWU_SERVER_CODE_CANCEL_REQUEST = 0x2718;//添加服务失败或取消请求码
    public static final String RESULT_FUWU_SERVER_STR_UPDATE_REQUEST = "Update";//修改服务请求标识
    public static final int RESULT_FUWU_SERVER_CODE_UPDATE_REQUEST = 0x2719;//修改服务请求码
    /**
     * 添加/修改产品
     */
    public static final int RESULT_CP_CODE_SCUESS_REQUEST = 0x271a;//添加产品成功请求码
    public static final int RESULT_CP_CODE_CANCEL_REQUEST = 0x271b;//添加产品失败或取消请求码
    public static final String RESULT_CP_STR_UPDATE_REQUEST = "Update";//修改请求标识
    public static final int RESULT_CP_CODE_UPDATE_REQUEST = 0x2719;//修改产品请求码





    public static final int RESULT_USER_INFO_REQUEST = 10010;//管理员账户详情请求码

    public static String FUWU_PICPATH = "";
    public static int BEAUTY_FRAGMENT_POSTION = 0;//记录当前fragment位置
    public static final int BEAUTY_FRAGMENT_PREVIOUS_POSTION = 0;//上个fragment位置
    public static int BEAUTY_WITHIN_PREVIOUS_POSTION = 0;//BeautyWithinActivity左侧菜单位置


    /**
     * EventBus消息分发
     */
    public static final int SELECT_TITLE = 0x64;//修改状态
    public static final int SELECT_MD = 0x65;//修改状态
    public static final int SELECT_FL = 0x66;//修改状态
    public static final int SELECT_APP_UPDATE = 0x67;//修改状态


    /**
     * 工作台顶部滚动title
     */
    public static List<String> titleStrings = new ArrayList<>();

    /**
     * 门店
     */
    public static List<String> OPERATION_MD = new ArrayList<>();

    /**
     * 服务类型
     */
    public static List<String> ServerTypeString = new ArrayList<>();

    /**
     * 查询服务类型
     */
    public static List<String> SelectServerTypeString = new ArrayList<>();

}

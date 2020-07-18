package com.mnn.mydream.cosmetology.utils;


import com.mnn.mydream.cosmetology.bean.Customer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Constons {


    //存储
    public static Customer costorBean;

    //存储List
    public static List<Customer> costorBeans = new ArrayList<>();

    public static final String SIGN_PNG_PATH_FL = "/storage/emulated/0" + "/customer";

    //    public static final String SIGN_PNG_PATH_FL = Environment.getExternalStorageDirectory().getPath() + "/customer";

    public static final String SIGN_PNG_PATH = SIGN_PNG_PATH_FL + File.separator + "png" + File.separator;


    /**
     * 默认pic请求图片
     */
    public static final String PIC_PATH = "https://bmob-cdn-28614.bmobpay.com/2020/07/12/49e9500440be379380eff778e5dff13a.png";


    public static final int STATE_NORMAL = 0;//正常状态
    public static final int STATE_MODIFY = 1;//修改状态
    public static final int STATE_SIGN = 2;//签名状态
    public static final int STATE_DELETE = 3;//删除状态
    public static final int STATE_ADDCUSTOMER = 4;//添加用户状态
    public static final int STATE_ADDPROJECTS = 5;//添加项目状态
    public static final int STATE_MODIFY_PROJECTS = 6;//修改项目信息状态
    public static final int STATE_EDIT_PROJECTS = 7;//编辑项目状态

    public static int BEAUTY_FRAGMENT_POSTION = 0;//记录当前fragment位置
    public static int BEAUTY_WITHIN_PREVIOUS_POSTION = 0;//BeautyWithinActivity左侧菜单位置


    /**
     * 登陆界面常量
     */
    public static final int RESULT_LONGIN_FAIL = -1;//登陆失败或返回

    public static final int BEAUTY_RESULT_LONGIN_CODE = 0x2711;//beautyActivity请求登陆码

    public static final int RESULT_LONGIN_SUCCESS = 0x2712;//登陆成功返回码

    public static final int RESULT_XJKH_TX_SUCCESS = 0x2713;//顾客选择头像成功返回码


    /**
     * 修改请求标识
     */
    public static final String RESULT_UPDATE_REQUEST = "Update";//修改请求标识


    /**
     * 添加/修改服务
     */
    public static final int RESULT_FUWU_SERVER_CODE_REQUEST = 0x2715;//添加服务图片请求码
    public static final int RESULT_FUWU_SERVER_CODE_VIEW_REQUEST = 0x2716;//添加服务请求码

    public static final int RESULT_FUWU_SERVER_CODE_SCUESS_REQUEST = 0x2717;//添加服务成功请求码
    public static final int RESULT_FUWU_SERVER_CODE_CANCEL_REQUEST = 0x2718;//添加服务失败或取消请求码
    public static final int RESULT_FUWU_SERVER_CODE_UPDATE_REQUEST = 0x2719;//修改服务请求码


    /**
     * 添加/修改产品
     */
    public static final int RESULT_CP_CODE_SCUESS_REQUEST = 0x271a;//添加产品成功请求码
    public static final int RESULT_CP_CODE_CANCEL_REQUEST = 0x271b;//添加产品失败或取消请求码
    public static final int RESULT_CP_CODE_UPDATE_REQUEST = 0x271c;//修改产品请求码
    public static final int RESULT_CP_CODE_VIEW_REQUEST = 0x271d;//添加产品请求码






    /**
     * 管理员账户详情请求码
     */
    public static final int RESULT_USER_INFO_REQUEST = 0x271e;//管理员账户详情请求码


    /**
     * 客户详情请求码
     * */
    public static final int RESULT_KG_INFO_REQUEST = 0x271f;//管理员账户详情请求码

    public static final String RESULT_KG_INFO_code_REQUEST = "kh_info";//管理员账户详情传参值


    /**
     * 添加/修改项目卡
     */
    public static final int RESULT_XMK_CODE_SCUESS_REQUEST = 0x2720;//添加项目卡成功请求码
    public static final int RESULT_XMK_CODE_CANCEL_REQUEST = 0x2721;//添加项目卡失败或取消请求码
    public static final int RESULT_XMK_CODE_UPDATE_REQUEST = 0x2722;//修改项目卡请求码
    public static final int RESULT_XMK_CODE_VIEW_REQUEST = 0x2723;//添加项目卡请求码


    /**
     * 添加服务标识
     * */
    public static final int RESULT_ADD_SERVICE_VIEW_REQUEST = 0x2724;//添加项目卡请求码
    public static final int RESULT_ADD_SERVICE_VIEW_REQUEST_CALLBACK = 0x2725;//添加项目卡请求码
    public static final int RESULT_ADD_SERVICE_VIEW_REQUEST_SELECT = 0x2726;//添加项目卡请求码



    /**
     * EventBus消息分发
     */
    public static final int SELECT_TITLE = 0x64;//修改状态
    public static final int SELECT_MD = 0x65;//修改状态
    public static final int SELECT_FL = 0x66;//修改状态
    public static final int SELECT_APP_UPDATE = 0x67;//修改状态

    public static final int SELECT_SELECT_ADD_KH = 0x68;//添加客户

//    public static final int SELECT_APP_UPDATE = 0x69;//修改状态



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

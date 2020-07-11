package com.mnn.mydream.cosmetology.utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.view.Gravity;

import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.cookie.store.MemoryCookieStore;
import com.lzy.okhttputils.cookie.store.PersistentCookieStore;
import com.lzy.okhttputils.model.HttpHeaders;
import com.lzy.okhttputils.model.HttpParams;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobConfig;

/**
 * 项目名称：MyAnimation
 * 创建人：My Dream
 * 创建时间：2017/5/5 11:39
 * @author My Dream
 */

public class ConfigDataMethod {

    /**
     * 初始化Bmob数据库
     *
     * @param context
     */
    public static void bmobInit(Context context) {
        Bmob.initialize(context, ConfigDataCons.APP_BMOB_ID);
    }

    /**
     * 配置Bmob数据库
     **/
    public static void bmobConfig(Context context) {

        //第二：自v3.4.7版本开始,设置BmobConfig,允许设置请求超时时间、文件分片上传时每片的大小、文件的过期时间(单位为秒)，
        BmobConfig config = new BmobConfig.Builder(context)
                //设置appkey
                .setApplicationId(ConfigDataCons.APP_BMOB_ID)
                //请求超时时间（单位为秒）：默认15s
                .setConnectTimeout(30)
                //文件分片上传时每片的大小（单位字节），默认512*1024
                .setUploadBlockSize(1024 * 1024)
                //文件的过期时间(单位为秒)：默认1800s
                .setFileExpiration(2500)
                .build();
        Bmob.initialize(config);

    }

    /**
     * Toast提示
     */
    public static void toastShow(Context context, String msg, AppMsg.Style style) {

        // 创建AppMsg对象，并定义显示的信息和样式
        AppMsg appMsg = AppMsg.makeText((Activity) context, msg, style);
        // 设置Toast显示的位置,默认显示在屏幕的最上方
        appMsg.setLayoutGravity(Gravity.CENTER_VERTICAL);
        appMsg.show();
    }


    /**
     * 初始化网络框架
     */
    public static void OkhttpConfig(Application application) {

        HttpHeaders headers = new HttpHeaders();
        headers.put("commonHeaderKey1", "commonHeaderValue1");    //所有的 header 都 不支持 中文
        headers.put("commonHeaderKey2", "commonHeaderValue2");
        HttpParams params = new HttpParams();
        params.put("commonParamsKey1", "commonParamsValue1");     //所有的 params 都 支持 中文
        params.put("commonParamsKey2", "这里支持中文参数");
        //必须调用初始化
        OkHttpUtils.init(application);
        //以下都不是必须的，根据需要自行选择
        OkHttpUtils.getInstance()//
//              .debug("OkHttpUtils")                                              //是否打开调试
                .setConnectTimeout(OkHttpUtils.DEFAULT_MILLISECONDS)               //全局的连接超时时间
                .setReadTimeOut(OkHttpUtils.DEFAULT_MILLISECONDS)                  //全局的读取超时时间
                .setWriteTimeOut(OkHttpUtils.DEFAULT_MILLISECONDS)                 //全局的写入超时时间
                .setCookieStore(new MemoryCookieStore())                           //cookie使用内存缓存（app退出后，cookie消失）
                .setCookieStore(new PersistentCookieStore())                       //cookie持久化存储，如果cookie不过期，则一直有效
                .addCommonHeaders(headers)                                         //设置全局公共头
                .addCommonParams(params);

    }

}

package com.mnn.mydream.cosmetology.bean;

import java.io.File;

import cn.bmob.v3.datatype.BmobFile;

/**
 * 创建人：MyDream
 * 创建日期：2020/7/7 23:04
 * 类描述 : APP版本更新数据库
 */
public class AppUpdateBean {
    private String update_log;//更新日志
    private String version;//版本名称
    private int version_i;//版本号
    private String platform;//平台，注意："Android"为安卓平台标示，"ios"为ios平台标示
    private String target_size;//Apk文件大小
    private Boolean isforce;//是否强制更新
    private BmobFile path;//Apk文件
    private String android_url;//apk市场地址（path字段和本字段必填其中一个）
    private String channel;//渠道标示
    private String ios_url;//iOS app store地址（如果是ios记录一定要填写）

    @Override
    public String toString() {
        return "AppUpdateBean{" +
                "update_log='" + update_log + '\'' +
                ", version='" + version + '\'' +
                ", version_i=" + version_i +
                ", platform='" + platform + '\'' +
                ", target_size='" + target_size + '\'' +
                ", isforce=" + isforce +
                ", path=" + path +
                ", android_url='" + android_url + '\'' +
                ", channel='" + channel + '\'' +
                ", ios_url='" + ios_url + '\'' +
                '}';
    }

    public String getUpdate_log() {
        return update_log;
    }

    public void setUpdate_log(String update_log) {
        this.update_log = update_log;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getVersion_i() {
        return version_i;
    }

    public void setVersion_i(int version_i) {
        this.version_i = version_i;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getTarget_size() {
        return target_size;
    }

    public void setTarget_size(String target_size) {
        this.target_size = target_size;
    }

    public Boolean getIsforce() {
        return isforce;
    }

    public void setIsforce(Boolean isforce) {
        this.isforce = isforce;
    }

    public BmobFile getPath() {
        return path;
    }

    public void setPath(BmobFile path) {
        this.path = path;
    }

    public String getAndroid_url() {
        return android_url;
    }

    public void setAndroid_url(String android_url) {
        this.android_url = android_url;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getIos_url() {
        return ios_url;
    }

    public void setIos_url(String ios_url) {
        this.ios_url = ios_url;
    }
}

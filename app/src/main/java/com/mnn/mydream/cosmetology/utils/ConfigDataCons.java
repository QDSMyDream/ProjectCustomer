package com.mnn.mydream.cosmetology.utils;

/**
 * 项目名称：MyAnimation
 * 创建人：My Dream
 * 创建时间：2017/5/5 11:39
 */

public class ConfigDataCons {

    //Bmob  APPKEY 云数据库
    public static final String APP_BMOB_ID= "abebd05539f4e25033be3c18eea384cb";
    //Bmob  APPKEY 云数据库
//    public static final String APP_BMOB_ID= "3dda9978e1e89ddfe69c65d8162e227f";


    //腾讯bug反馈
    public static  final  String BUGLY_ID="5757045639";


    //聚合数据Jock  APPKEY
    public static final String APP_JOCK_ID = "83e43b2a0a477e9aaa213297a8940563";

    //聚合数据News  APPKEY
    public static final String APP_NEWS_ID = "029b41508650bef3e40a2256ba09fdec";

    /**
     * top(头条，默认),shehui(社会),guonei(国内),
     * guoji(国际),yule(娱乐),tiyu(体育)
     * junshi(军事),keji(科技),caijing(财经),
     * shishang(时尚)
     */
    //聚合数据新闻头条请求URL
    public static final String APP_NEWS_URL = "http://v.juhe.cn/toutiao/index?type=";
    public static final String APP_NEWS_VALUES = "top";
    public static final String APP_NEWS_URL2 = "&key=029b41508650bef3e40a2256ba09fdec&page=2";

    //聚合数据笑话请求URL
    public static final String APP_JOCK_URL = "http://japi.juhe.cn/joke/content/list.from?key=83e43b2a0a477e9aaa213297a8940563&page=2&pagesize=10&sort=asc&time=1535878544";

    //聚合数据笑话请求URL
    public static String APP_JOCK_URL(int page, int pagersize, boolean sort, String time) {

        String sorts = "asc";//类型，true  desc:指定时间之前发布的，false  asc:指定时间之后发布的
        if (sort) {
            sorts = "desc";
        } else {
            sorts = "asc";
        }
        return "http://japi.juhe.cn/joke/content/list.from?key=" + APP_JOCK_ID + "&page=" + page + "&pagesize=" + pagersize + "&sort=" + sorts + "&time=" + time;
    }

}

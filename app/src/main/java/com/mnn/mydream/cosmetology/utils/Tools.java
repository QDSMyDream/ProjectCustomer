package com.mnn.mydream.cosmetology.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

import com.mnn.mydream.cosmetology.bean.PickTimeBean;
import com.mnn.mydream.cosmetology.pickertime.TimePickerPopWin;
import com.mnn.mydream.cosmetology.view.DrawView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Tools {

    /**
     * dp转px
     *
     * @param context
     * @param dpVal
     * @return
     */
    public static int dp2px(Context context, float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal, context.getResources().getDisplayMetrics());
    }

    //获取当前时间
    public static String getSameDay() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
        return df.format(new Date());
    }


    /**
     * 日期格式字符串转换成时间戳
     *
     * @return
     */
    public static long date2TimeStamp(String date_str) {


        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.parse(date_str).getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 日期格式字符串转换成时间戳
     *
     * @return
     */
    public static long date2TimeStamp2(String date_str) {


        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sdf.parse(date_str).getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


    /**
     * 获取过去一个月的时间
     */
    public static String getOldMonth(String str) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar c = Calendar.getInstance();
        //过去一月
        c.setTime(date);
        c.add(Calendar.MONTH, -1);
        Date m = c.getTime();
        String mon = format.format(m);
        System.out.println("过去一个月：" + mon);

        return mon;
    }

    /**
     * 判断两个日期相差几个月
     */
    public static int sMonOreMon(String str1, String str2) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar bef = Calendar.getInstance();
        Calendar aft = Calendar.getInstance();
        try {
            bef.setTime(sdf.parse(str1));
            aft.setTime(sdf.parse(str2));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int result = aft.get(Calendar.MONTH) - bef.get(Calendar.MONTH);
        int month = (aft.get(Calendar.YEAR) - bef.get(Calendar.YEAR)) * 12;

        System.out.println(Math.abs(month + result));

        return Math.abs(month + result);
    }


    //获取当前时间 Date格式
    public static Date getCurrentDayDate() {

        String createdAt = getSameDay();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date createdAtDate = null;
        try {
            createdAtDate = sdf.parse(createdAt);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return createdAtDate;

    }


    //获取当前时间
    public static String getSameYMD() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
        return df.format(new Date());
    }

    //获取当前年份
    public static String getSameYear() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy");//设置日期格式
        System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
        return df.format(new Date());
    }

    //判断是否为空
    public static boolean getIsNull(String s) {
        if (s == null || s.equals("")) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 判断字符不能为空
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        if (TextUtils.isEmpty(str) || "".equals(str)) {
            return true;
        }
        return false;
    }

    /**
     * 获取屏幕宽度
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.widthPixels;
    }


    /**
     * 将时间戳转换为时间
     */
    public static String stampToDate(Long s) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sd = sdf.format(new Date(s));      // 时间戳转换成时间

        System.out.println(sd);
        return sd;
    }


    /**
     * Transform int to String with prefix "0" if less than 10
     *
     * @param num
     * @return
     */
    public static String format2LenStr(int num) {

        return (num < 10) ? "0" + num : String.valueOf(num);
    }

    //保存文件到指定路径
    public static void saveMyBitmap(String s1, String s2, Bitmap bitmap) {
        File appDir = new File(s1);
        if (!appDir.exists()) {//不存在
            appDir.mkdir();
        }

        File file = new File(appDir, s2);

        try {
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //保存客户签字
    public static String saveBitmap(DrawView view, String name, String projectName) {
        view.setEnabled(false);
        Bitmap bmBitmap = view.getCanvasSnapshot();
        String pathName = FileUtils.createDir(Constons.SIGN_PNG_PATH);
        String picName = System.currentTimeMillis() + ".png";
        Tools.saveMyBitmap(pathName, picName, bmBitmap);
        view.cleanDrawingCache();
        return picName;

    }

    //时间选择框
    public static PickTimeBean setPickTimeOnclickPup(Activity context, View v, boolean b) {
        PickTimeBean pickTimeBean = new PickTimeBean();
        TimePickerPopWin timePickerPopWin = new TimePickerPopWin.Builder(context, b, new TimePickerPopWin.OnTimePickListener() {
            @Override
            public void onTimePickCompleted(int year, int month, int day, int hour, int minute, int second, String time) {
                pickTimeBean.setYear(year);
                pickTimeBean.setMonth(month);
                pickTimeBean.setDay(day);
                pickTimeBean.setHour(hour);
                pickTimeBean.setMin(minute);
                pickTimeBean.setSecond(second);
                pickTimeBean.setTimeString(time);
//
//
//                PickTimeBean pickTimeBean=new
//                return time;
//                if (b) {
//                    if (Integer.parseInt(Tools.getSameYear()) > year) {
//                        mAge.setText((Integer.parseInt(Tools.getSameYear()) - year) + "");
//                        mBirTxt.setText(time);
//                    } else {
//                        ToastUtils.showToast(getBaseContext(), "出生时间错误，请重新输入！", false);
//                        mAge.setText("");
//                        mBirTxt.setText("");
//                    }
//                } else if (count == 1) {
//                    mTimeText.setText(time);
//                }
            }
        }).btnTextSize(16)
                .viewTextSize(25)
                .colorCancel(Color.parseColor("#999999"))
                .colorConfirm(Color.parseColor("#009900"))
                .build();
        timePickerPopWin.showPopWin(context);
        return pickTimeBean;


    }

    //获取版本号
    public static int packageCode(Context context) {
        PackageManager manager = context.getPackageManager();
        int code = 0;
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            code = info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return code;
    }


    ///创建文件夹

    public static void makeDir() {


        File logDir = new File(Constons.SIGN_PNG_PATH);
        if (!logDir.exists()) {
            boolean isCreate = logDir.mkdirs();
            if (!isCreate) {
                System.out.println("创建log文件夹失败");
            }
        }


    }


    //获取程序的包名
    public static String packageName(Context context) {
        PackageManager manager = context.getPackageManager();
        String name = null;
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            name = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return name;
    }


    //转成px
    public static int dpTopx(Context context, float dipValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

}

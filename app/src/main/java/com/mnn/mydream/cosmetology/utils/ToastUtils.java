package com.mnn.mydream.cosmetology.utils;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mnn.mydream.cosmetology.R;

/**
 * Toast统一管理类
 *
 * @author My Dream
 */
public class ToastUtils {
    private static Toast toast;

    /**
     * 短时间显示Toast
     */
    public static void showShort(Context context, CharSequence message) {
        if (null == toast) {
            toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
            // toast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            toast.setText(message);
        }
        toast.show();
    }


    /**
     * 长时间显示Toast
     */
    public static void showLong(Context context, CharSequence message) {
        if (null == toast) {
            toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
            // toast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            toast.setText(message);
        }
        toast.show();
    }


    /**
     * 自定义显示Toast时间
     */
    public static void show(Context context, CharSequence message, int duration) {
        if (null == toast) {
            toast = Toast.makeText(context, message, duration);
            // toast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            toast.setText(message);
        }
        toast.show();
    }


    /**
     * Hide the toast, if any.
     */
    public static void hideToast() {
        if (null != toast) {
            toast.cancel();
        }
    }

    public static void showToast(Context context, String msg, boolean b) {
        if (context == null) {
            return;
        }
        if (context != null && context instanceof Activity) {
            if (((Activity) context).isFinishing()) {
                return;
            }
        }
        if (TextUtils.isEmpty(msg) || "".equals(msg.trim())) {
            return;
        }
        //调用Activity的getLayoutInflater()
        LayoutInflater inflater = LayoutInflater.from(context);
        //加載layout下的布局
        View view = inflater.inflate(R.layout.layout_toast_no_img, null);

        TextView toast_tv = view.findViewById(R.id.toast_tv);

        if (b) {
            toast_tv.setBackgroundResource(R.drawable.toast_select_true);
        } else {
            toast_tv.setBackgroundResource(R.drawable.toast_select_flase);
        }

        //toast的文本内容
        toast_tv.setText(msg);

        Toast toast = new Toast(context);
        //setGravity用来设置Toast显示的位置，相当于xml中的android:gravity或android:layout_gravity
        toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        //setDuration方法：设置持续时间，以毫秒为单位。该方法是设置补间动画时间长度的主要方法
        toast.setDuration(Toast.LENGTH_SHORT);
        //添加视图文件
        toast.setView(view);
        //显示toast
        toast.show();
    }

    public static void showToast(Context context, String msg, Integer rid, boolean b) {
        if (context == null) {
            return;
        }
        if (context != null && context instanceof Activity) {
            if (((Activity) context).isFinishing()) {
                return;
            }
        }
        if (TextUtils.isEmpty(msg) || "".equals(msg.trim())) {
            return;
        }
        //调用Activity的getLayoutInflater()
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        //加載layout下的布局
        View view = inflater.inflate(R.layout.layout_toast_with_img, null);
        ImageView toast_img = view.findViewById(R.id.toast_img);
        TextView toast_tv = view.findViewById(R.id.toast_tv);
        //toast的图片资源
        if (rid == null) {
            toast_img.setVisibility(View.GONE);
        } else {
            toast_img.setImageResource(rid);
        }
        //toast的文本内容
        toast_tv.setText(msg);

        Toast toast = new Toast(context);
        //setGravity用来设置Toast显示的位置，相当于xml中的android:gravity或android:layout_gravity
        toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        //setDuration方法：设置持续时间，以毫秒为单位。该方法是设置补间动画时间长度的主要方法
        toast.setDuration(Toast.LENGTH_SHORT);
        //添加视图文件
        toast.setView(view);
        //显示toast
        toast.show();
    }

}

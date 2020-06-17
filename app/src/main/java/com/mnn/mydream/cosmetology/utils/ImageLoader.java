package com.mnn.mydream.cosmetology.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.mnn.mydream.cosmetology.R;

import java.io.File;

/**
 * 图片加载工具类
 */

public class ImageLoader {

    public static RequestOptions options = new RequestOptions()
            .placeholder(R.mipmap.ic_img_default)    //加载成功之前占位图
            .error(R.mipmap.def_photo)    //加载错误之后的错误图
            .override(100, 100)    //指定图片的尺寸
            .fitCenter()   //指定图片的缩放类型为fitCenter （等比例缩放图片，宽或者是高等于    ImageView的宽或者是高。是指其中一个满足即可不会一定铺满imageview）
            .centerCrop()//指定图片的缩放类型为centerCrop （等比例缩放图片，直到图片的宽高都   大于等于ImageView的宽度，然后截取中间的显示。）
            .skipMemoryCache(true)    //不使用内存缓存
            .diskCacheStrategy(DiskCacheStrategy.ALL)    //缓存所有版本的图像
            .diskCacheStrategy(DiskCacheStrategy.NONE)    //不使用硬盘本地缓存
            .diskCacheStrategy(DiskCacheStrategy.DATA)    //只缓存原来分辨率的图片
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE);   //只缓存最终的图片


    /**
     * 显示图片Imageview
     */
    public static void displayImageView(Context context, String url, ImageView imgeview) {

        Glide.with(context).load(url).apply(options).into(imgeview);
//placeholder 加载中的图片
//error 加载失败后显示的占位图
//thumbnail 缩略图支持。
//override 设置加载尺寸
//asGif 强制转换为gif
//diskCacheStrategy 设置图片缓存策略
//transform 切图，例如实现圆角图片等

    }

    /**
     * 显示圆型图
     */

    public static void displayImageViewCircle(Context context, final ImageView imageView, String url) {
//        Glide.with(context)
//                .load(url)
//                .placeholder(R.mipmap.ic_img_default)
//                .error(R.mipmap.ic_img_failed)
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .into(imageView);
        Glide.with(context).load(url).apply(options).into(imageView);
    }

    /**
     * 显示圆角矩形
     */
    public static void displayImageViewRound(Context context, final ImageView imageView, String url, int radius) {
        Glide.with(context)
                .load(url)
                .into(imageView);
    }

    public static void displayLocalImageView(Context context, final ImageView imageView, int rid) {
        //加载图片
        Glide.with(context)
                .load(rid)
                .into(imageView);
    }

    public static void displayLocalImageView(Context context, final ImageView imageView, String path) {
        if(path==null||path.equals("")){
            return;
        }

        //本地文件
        File file = new File(path);
        //加载图片
        Glide.with(context)
                .load(file)

                .into(imageView);
    }

    public static void displayLocalImageViewCircle(Context context, final ImageView imageView, String path) {
        //本地文件
        File file = new File(path);
        //加载图片
        Glide.with(context)
                .load(file)

                .into(imageView);
    }

}

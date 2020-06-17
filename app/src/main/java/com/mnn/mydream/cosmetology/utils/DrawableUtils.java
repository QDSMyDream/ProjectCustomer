package com.mnn.mydream.cosmetology.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;
import android.support.v4.content.ContextCompat;

import com.applandeo.materialcalendarview.CalendarUtils;
import com.mnn.mydream.cosmetology.R;

/**
 * Created by Mateusz Kornakiewicz on 02.08.2018.
 */

public final class DrawableUtils {

    //添加事件
    public static Drawable getCircleDrawableWithText(Context context, int pos) {
        Drawable background = null;
        Drawable text = null;
        if (pos >= 1 && pos < 3) {
            background = ContextCompat.getDrawable(context, R.drawable.sample_circle);
            text = CalendarUtils.getDrawableText(context, pos + "", null, android.R.color.white, 12);

        }
        if (pos >= 3 && pos < 6) {
            background = ContextCompat.getDrawable(context, R.drawable.sample_circle_green);
            text = CalendarUtils.getDrawableText(context, pos + "", null, android.R.color.white, 12);

        }
        if (pos >= 6 && pos < 9) {
            background = ContextCompat.getDrawable(context, R.drawable.sample_circle_blue);
            text = CalendarUtils.getDrawableText(context, pos + "", null, android.R.color.white, 12);

        }
        if (pos > 9) {
            background = ContextCompat.getDrawable(context, R.drawable.sample_circle_red);
            text = CalendarUtils.getDrawableText(context, pos + "", null, android.R.color.white, 12);
        }

        Drawable[] layers = {background, text};
        return new LayerDrawable(layers);

    }

    //添加事件
    public static Drawable getCircleDrawableWithTextNoText(Context context, int pos) {

        Drawable background = ContextCompat.getDrawable(context, R.drawable.sample_circle);
        Drawable text = CalendarUtils.getDrawableText(context, pos + "", null, android.R.color.white, 12);
        Drawable[] layers = {background, text};
        return null;
    }

    public static Drawable getThreeDots(Context context) {
        Drawable drawable = ContextCompat.getDrawable(context, R.drawable.sample_three_icons);

        //Add padding to too large icon
        return new InsetDrawable(drawable, 100, 0, 100, 0);
    }

    private DrawableUtils() {

    }

}


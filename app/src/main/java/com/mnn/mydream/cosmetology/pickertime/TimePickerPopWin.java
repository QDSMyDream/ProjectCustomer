package com.mnn.mydream.cosmetology.pickertime;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.mnn.mydream.cosmetology.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TimePickerPopWin extends PopupWindow implements View.OnClickListener {

    private Button cancelBtn;
    private Button confirmBtn;


    private TextView h, m, s;
    private LoopView yearLoopView;
    private LoopView monthLoopView;
    private LoopView dayLoopView;

    private LoopView hourLoopView;
    private LoopView minuteLoopView;
    private LoopView secondLoopView;


    private View pickerContainerV;
    private View contentView;


    private Context mContext;
    private String textCancel;
    private String textConfirm;
    private int colorCancel;
    private int colorConfirm;
    private int btnTextsize;
    private int viewTextSize;

    List<String> yearList = new ArrayList();
    List<String> monthList = new ArrayList();
    List<String> dayList = new ArrayList();

    List<String> hourList = new ArrayList();
    List<String> minList = new ArrayList();
    List<String> secondList = new ArrayList();
    private boolean isVisibilityFlag;


    public static class Builder {
        private boolean isVisibilityFlag;
        private Context context;
        private OnTimePickListener listener;

        public Builder(Context context, boolean b, OnTimePickListener listener) {
            this.context = context;
            this.listener = listener;
            this.isVisibilityFlag = b;


        }

        private int colorCancel = Color.parseColor("#999999");
        private int colorConfirm = Color.parseColor("#303F9F");
        private int btnTextSize = 16;//text btnTextsize of cancel and confirm button
        private int viewTextSize = 25;


        public Builder colorCancel(int colorCancel) {
            this.colorCancel = colorCancel;
            return this;
        }

        public Builder colorConfirm(int colorConfirm) {
            this.colorConfirm = colorConfirm;
            return this;
        }

        public Builder btnTextSize(int textSize) {
            this.btnTextSize = textSize;
            return this;
        }

        public Builder viewTextSize(int textSize) {
            this.viewTextSize = textSize;
            return this;
        }

        public TimePickerPopWin build() {
            return new TimePickerPopWin(this, isVisibilityFlag);
        }
    }

    public TimePickerPopWin(Builder builder, boolean b) {
        this.mContext = builder.context;
        this.mListener = builder.listener;
        this.colorCancel = builder.colorCancel;
        this.colorConfirm = builder.colorConfirm;
        this.btnTextsize = builder.btnTextSize;
        this.viewTextSize = builder.viewTextSize;
        this.isVisibilityFlag = b;

        initView();
    }

    private OnTimePickListener mListener;

    @SuppressLint("WrongViewCast")
    private void initView() {

        contentView = LayoutInflater.from(mContext).inflate(R.layout.layout_time_picker, null);

        cancelBtn = (Button) contentView.findViewById(R.id.btn_cancel);

//        cancelBtn.setTextColor(colorCancel);
//        cancelBtn.setTextSize(btnTextsize);

        confirmBtn = (Button) contentView.findViewById(R.id.btn_confirm);
//        confirmBtn.setTextColor(colorConfirm);
//        confirmBtn.setTextSize(btnTextsize);

        yearLoopView = (LoopView) contentView.findViewById(R.id.picker_year);
        monthLoopView = (LoopView) contentView.findViewById(R.id.picker_month);
        dayLoopView = (LoopView) contentView.findViewById(R.id.picker_day);

        h = contentView.findViewById(R.id.hour);
        m = contentView.findViewById(R.id.min);
        s = contentView.findViewById(R.id.ss);

        hourLoopView = (LoopView) contentView.findViewById(R.id.picker_hour);
        minuteLoopView = (LoopView) contentView.findViewById(R.id.picker_minute);
        secondLoopView = (LoopView) contentView.findViewById(R.id.picker_second);


        h.setVisibility(isVisibilityFlag ? View.VISIBLE : View.GONE);
        m.setVisibility(isVisibilityFlag ? View.VISIBLE : View.GONE);
        s.setVisibility(isVisibilityFlag ? View.VISIBLE : View.GONE);

        hourLoopView.setVisibility(isVisibilityFlag ? View.VISIBLE : View.GONE);
        minuteLoopView.setVisibility(isVisibilityFlag ? View.VISIBLE : View.GONE);
        secondLoopView.setVisibility(isVisibilityFlag ? View.VISIBLE : View.GONE);

        pickerContainerV = contentView.findViewById(R.id.container_picker);

        initPickerViews();  // init hour and minute loop view

        cancelBtn.setOnClickListener(this);

        confirmBtn.setOnClickListener(this);

        contentView.setOnClickListener(this);

        if (!TextUtils.isEmpty(textConfirm)) {
            confirmBtn.setText(textConfirm);
        }

        if (!TextUtils.isEmpty(textCancel)) {
            cancelBtn.setText(textCancel);
        }

        setTouchable(true);
        setFocusable(true);

        setBackgroundDrawable(new BitmapDrawable());
        setAnimationStyle(R.style.FadeInPopWin);
        setContentView(contentView);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);

    }

    private void initPickerViews() {

        //年
        for (int i = 1950; i <= 2105; i++) {
            yearList.add(format2LenStr(i));
        }
        //月
        for (int i = 1; i <= 12; i++) {
            monthList.add(format2LenStr(i) + "");
        }
        //日
        for (int i = 1; i <= 31; i++) {
            dayList.add(format2LenStr(i) + "");
        }
        //时
        for (int i = 0; i < 24; i++) {
            hourList.add(format2LenStr(i));
        }
        //分
        for (int j = 0; j <= 59; j++) {
            minList.add(format2LenStr(j));
        }
        //秒
        for (int j = 0; j <= 59; j++) {
            secondList.add(format2LenStr(j));
        }

        Date t = new Date();
        SimpleDateFormat yearDf = new SimpleDateFormat("yyyy");
        String year = yearDf.format(t);
        SimpleDateFormat monthDf = new SimpleDateFormat("MM");
        String month = monthDf.format(t);
        SimpleDateFormat dayDf = new SimpleDateFormat("dd");
        String day = dayDf.format(t);
        SimpleDateFormat hourDf = new SimpleDateFormat("HH");
        String hour = hourDf.format(t);
        SimpleDateFormat minuteDf = new SimpleDateFormat("mm");
        String minute = minuteDf.format(t);
        SimpleDateFormat secondDf = new SimpleDateFormat("ss");
        String second = secondDf.format(t);

        yearLoopView.setDataList(yearList);
        yearLoopView.setCanLoop(false);
        yearLoopView.setInitPosition((Integer.parseInt(year) - 1950));

        monthLoopView.setDataList(monthList);
        monthLoopView.setCanLoop(false);
        monthLoopView.setInitPosition((Integer.parseInt(month) - 1));

        dayLoopView.setDataList(dayList);
        dayLoopView.setCanLoop(false);
        dayLoopView.setInitPosition((Integer.parseInt(day) - 1));


        hourLoopView.setDataList(hourList);
        hourLoopView.setCanLoop(false);
        hourLoopView.setInitPosition((Integer.parseInt(hour)) - 1);

        minuteLoopView.setDataList(minList);
        minuteLoopView.setCanLoop(false);
        minuteLoopView.setInitPosition((Integer.parseInt(minute)));

        secondLoopView.setDataList(secondList);
        secondLoopView.setCanLoop(false);
        secondLoopView.setInitPosition((Integer.parseInt(second)));
    }

    @Override
    public void onClick(View v) {

        if (v == contentView || v == cancelBtn) {
            dismissPopWin();
        } else if (v == confirmBtn) {

            if (null != mListener) {

                StringBuffer sb = new StringBuffer();
                sb.append(String.valueOf(yearList.get(yearLoopView.getSelectedItem())));
                sb.append("-");
                sb.append(String.valueOf(monthList.get(monthLoopView.getSelectedItem())));
                sb.append("-");
                sb.append(String.valueOf(dayList.get(dayLoopView.getSelectedItem())));

                if (isVisibilityFlag) {
                    sb.append(" ");
                    sb.append(String.valueOf(hourList.get(hourLoopView.getSelectedItem())));
                    sb.append(":");
                    sb.append(String.valueOf(minList.get(minuteLoopView.getSelectedItem())));
                    sb.append(":");
                    sb.append(String.valueOf(secondList.get(secondLoopView.getSelectedItem())));
                }

                mListener.onTimePickCompleted(Integer.parseInt(yearList.get(yearLoopView.getSelectedItem())),
                        Integer.parseInt(monthList.get(monthLoopView.getSelectedItem())),
                        Integer.parseInt(dayList.get(dayLoopView.getSelectedItem())),
                        Integer.parseInt(hourList.get(hourLoopView.getSelectedItem())),
                        Integer.parseInt(minList.get(minuteLoopView.getSelectedItem())),
                        Integer.parseInt(secondList.get(secondLoopView.getSelectedItem())),
                        sb.toString());
            }
            dismissPopWin();
        }
    }

    /**
     * Show time picker popWindow
     *
     * @param activity
     */
    public void showPopWin(Activity activity) {

        if (null != activity) {

            TranslateAnimation trans = new TranslateAnimation(
                    Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF,
                    0, Animation.RELATIVE_TO_SELF, 1,
                    Animation.RELATIVE_TO_SELF, 0);

            showAtLocation(activity.getWindow().getDecorView(), Gravity.BOTTOM,
                    0, 0);
            trans.setDuration(400);
            trans.setInterpolator(new AccelerateDecelerateInterpolator());

            pickerContainerV.startAnimation(trans);
        }
    }

    /**
     * Dismiss time picker popWindow
     */
    public void dismissPopWin() {

        TranslateAnimation trans = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 1);

        trans.setDuration(400);
        trans.setInterpolator(new AccelerateInterpolator());
        trans.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {

                dismiss();
            }
        });

        pickerContainerV.startAnimation(trans);
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

    public interface OnTimePickListener {

        /**
         * Listener when date been selected
         */
        void onTimePickCompleted(int year, int month, int day, int hour, int minute, int second, String time);


    }
}

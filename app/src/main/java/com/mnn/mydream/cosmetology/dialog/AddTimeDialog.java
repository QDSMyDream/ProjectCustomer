package com.mnn.mydream.cosmetology.dialog;

import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.mnn.mydream.cosmetology.R;
import com.mnn.mydream.cosmetology.interfaces.SettingListener;
import com.mnn.mydream.cosmetology.pickertime.LoopView;
import com.mnn.mydream.cosmetology.utils.Tools;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by MyDream on 2020/5/10.
 * 项目弹窗
 */

public class AddTimeDialog extends Dialog {

    public AddTimeDialog(Context context) {
        super(context);
    }

    public AddTimeDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public static class Builder {

        List<String> yearList = new ArrayList();
        List<String> monthList = new ArrayList();
        List<String> dayList = new ArrayList();
        List<String> hourList = new ArrayList();
        List<String> minList = new ArrayList();
        List<String> secondList = new ArrayList();

        private String TAG = "ShowCustomerProjectsDialog";
        private Context context;
        private boolean isVisibility;

        private View.OnClickListener onClickListener;
        private SettingListener mSListener = null;

        private TextView textViewTime;

        public Builder(Context context, boolean b, TextView textView) {
            this.context = context;
            this.isVisibility = b;
            this.textViewTime = textView;
        }

        public AddTimeDialog createDialog() {

            AddTimeDialog dialog = new AddTimeDialog(context, R.style.ShowDetailsCustomer_Dialog);
            View contentView = LayoutInflater.from(context).inflate(R.layout.add_layout_time_picker, null);
            dialog.setContentView(contentView);

            Button mBtnCancel = contentView.findViewById(R.id.btn_cancel);
            Button mBtnConfirm = contentView.findViewById(R.id.btn_confirm);

            LoopView mPickerYear = contentView.findViewById(R.id.picker_year);
            LoopView mPickerMonth = contentView.findViewById(R.id.picker_month);
            LoopView mPickerDay = contentView.findViewById(R.id.picker_day);
            LoopView mPickerHour = contentView.findViewById(R.id.picker_hour);
            TextView mTh = contentView.findViewById(R.id.th);
            LoopView mPickerMinute = contentView.findViewById(R.id.picker_minute);
            TextView mTm = contentView.findViewById(R.id.tm);
            LoopView mPickerSecond = contentView.findViewById(R.id.picker_second);
            TextView mTs = contentView.findViewById(R.id.ts);


            initPickerViews(mPickerYear, mPickerMonth, mPickerDay, mPickerHour, mPickerMinute, mPickerSecond);

            mBtnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            mBtnConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    StringBuffer sb = new StringBuffer();
                    sb.append(String.valueOf(yearList.get(mPickerYear.getSelectedItem())));
                    sb.append("-");
                    sb.append(String.valueOf(monthList.get(mPickerMonth.getSelectedItem())));
                    sb.append("-");
                    sb.append(String.valueOf(dayList.get(mPickerDay.getSelectedItem())));

                    if (isVisibility) {
                        sb.append(" ");
                        sb.append(String.valueOf(hourList.get(mPickerHour.getSelectedItem())));
                        sb.append(":");
                        sb.append(String.valueOf(minList.get(mPickerMinute.getSelectedItem())));
                        sb.append(":");
                        sb.append(String.valueOf(secondList.get(mPickerSecond.getSelectedItem())));
                    }
//
//                    listener.onTimePickCompleted(Integer.parseInt(yearList.get(mPickerYear.getSelectedItem())),
//                            Integer.parseInt(monthList.get(mPickerMonth.getSelectedItem())),
//                            Integer.parseInt(dayList.get(mPickerDay.getSelectedItem())),
//                            Integer.parseInt(hourList.get(mPickerHour.getSelectedItem())),
//                            Integer.parseInt(minList.get(mPickerMinute.getSelectedItem())),
//                            Integer.parseInt(secondList.get(mPickerSecond.getSelectedItem())),
//                            sb.toString());
                    if (mSListener != null) {
                        mSListener.onSetting(Integer.parseInt(yearList.get(mPickerYear.getSelectedItem())),
                                Integer.parseInt(monthList.get(mPickerMonth.getSelectedItem())),
                                Integer.parseInt(dayList.get(mPickerDay.getSelectedItem())),
                                Integer.parseInt(hourList.get(mPickerHour.getSelectedItem())),
                                Integer.parseInt(minList.get(mPickerMinute.getSelectedItem())),
                                Integer.parseInt(secondList.get(mPickerSecond.getSelectedItem())),
                                sb.toString());
                    }

                    textViewTime.setText(sb);
                    dialog.dismiss();


                }
            });

            mTh.setVisibility(isVisibility ? View.VISIBLE : View.GONE);
            mTm.setVisibility(isVisibility ? View.VISIBLE : View.GONE);
            mTs.setVisibility(isVisibility ? View.VISIBLE : View.GONE);
            mPickerHour.setVisibility(isVisibility ? View.VISIBLE : View.GONE);
            mPickerMinute.setVisibility(isVisibility ? View.VISIBLE : View.GONE);
            mPickerSecond.setVisibility(isVisibility ? View.VISIBLE : View.GONE);


            DisplayMetrics d = context.getResources().getDisplayMetrics();  //为获取屏幕宽、高
            WindowManager.LayoutParams p = dialog.getWindow().getAttributes();  //获取对话框当前的参数值
            p.height = (int) (d.heightPixels * 0.80);   //高度设置为屏幕的0.35
            p.width = (int) (d.widthPixels * 0.90);    //宽度设置为屏幕的0.35
            dialog.getWindow().setAttributes(p);     //设置生效

            return dialog;

        }

        private void initPickerViews(LoopView mPickerYear, LoopView mPickerMonth, LoopView mPickerDay, LoopView mPickerHour, LoopView mPickerMinute, LoopView mPickerSecond) {

            //年
            for (int i = 1950; i <= 2105; i++) {
                yearList.add(Tools.format2LenStr(i));
            }
            //月
            for (int i = 1; i <= 12; i++) {
                monthList.add(Tools.format2LenStr(i) + "");
            }
            //日
            for (int i = 1; i <= 31; i++) {
                dayList.add(Tools.format2LenStr(i) + "");
            }
            //时
            for (int i = 0; i < 24; i++) {
                hourList.add(Tools.format2LenStr(i));
            }
            //分
            for (int j = 0; j <= 59; j++) {
                minList.add(Tools.format2LenStr(j));
            }
            //秒
            for (int j = 0; j <= 59; j++) {
                secondList.add(Tools.format2LenStr(j));
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

            mPickerYear.setDataList(yearList);
            mPickerYear.setCanLoop(false);
            mPickerYear.setInitPosition((Integer.parseInt(year) - 1950));

            mPickerMonth.setDataList(monthList);
            mPickerMonth.setCanLoop(false);
            mPickerMonth.setInitPosition((Integer.parseInt(month) - 1));

            mPickerDay.setDataList(dayList);
            mPickerDay.setCanLoop(false);
            mPickerDay.setInitPosition((Integer.parseInt(day) - 1));

            mPickerHour.setDataList(hourList);
            mPickerHour.setCanLoop(false);
            mPickerHour.setInitPosition((Integer.parseInt(hour)) - 1);

            mPickerMinute.setDataList(minList);
            mPickerMinute.setCanLoop(false);
            mPickerMinute.setInitPosition((Integer.parseInt(minute)));

            mPickerSecond.setDataList(secondList);
            mPickerSecond.setCanLoop(false);
            mPickerSecond.setInitPosition((Integer.parseInt(second)));

        }

        public void setOnSettingListener(SettingListener listener) {
            mSListener = listener;
        }

//        public void setBackString(TextView textViewTime) {
//            textViewTimes = textViewTime;
//        }
    }


}

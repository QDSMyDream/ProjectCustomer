package com.mnn.mydream.cosmetology.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mnn.mydream.cosmetology.R;
import com.zhy.android.percent.support.PercentLinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by MyDream on 2020/5/10.
 * APP更新弹窗
 */

public class APPUpdateDialog extends Dialog {

    public APPUpdateDialog(Context context) {
        super(context);
    }

    public APPUpdateDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public static class Builder {


        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.content)
        AppCompatTextView content;
        @BindView(R.id.btn_cancel)
        AppCompatButton btnCancel;
        @BindView(R.id.btn_commit)
        AppCompatButton btnCommit;
        @BindView(R.id.content1)
        AppCompatTextView content1;
        @BindView(R.id.content2)
        AppCompatTextView content2;
        @BindView(R.id.content3)
        AppCompatTextView content3;
        @BindView(R.id.progressBar)
        ProgressBar progressBar;
        @BindView(R.id.progressBar_layout)
        PercentLinearLayout progressBarLayout;
        private Context context;
        private View.OnClickListener yesOnClick, canOnClick;//确定 取消


        private String titleString = "检测到有新版本";

        private String contentString1, contentString2, contentString3;


        public Builder(Context context) {
            this.context = context;
        }


        public Builder setYesOnClick(View.OnClickListener onClickListener) {
            this.yesOnClick = onClickListener;
            return this;
        }

        public Builder setCanlOnClick(View.OnClickListener onClickListener) {
            this.canOnClick = onClickListener;
            return this;
        }

        public Builder setTitleString(String titleString) {
            this.titleString = titleString;
            return this;
        }


        /**
         * 参数1 更新标题
         * 参数2 更新说明版本 大小等
         * 参数3 更新内容
         */
        public Builder setContentString(String str1, String str2, String str3) {
            this.contentString1 = str1;
            this.contentString2 = str2;
            this.contentString3 = str3;
            return this;
        }


        public APPUpdateDialog createDialog() {

            APPUpdateDialog dialog = new APPUpdateDialog(context, R.style.ShowDetailsCustomer_Dialog);
            View contentView = LayoutInflater.from(context).inflate(R.layout.app_update_dialog, null);
            dialog.setContentView(contentView);
            ButterKnife.bind(this, contentView);

            title.setText(titleString);
            content1.setText(contentString1);
            content2.setText(contentString2);
            content3.setText(contentString3);

            btnCancel.setOnClickListener(canOnClick);
            btnCommit.setOnClickListener(yesOnClick);


            DisplayMetrics d = context.getResources().getDisplayMetrics();  //为获取屏幕宽、高
            WindowManager.LayoutParams p = dialog.getWindow().getAttributes();  //获取对话框当前的参数值
            p.height = (int) (d.heightPixels * 0.25);   //高度设置为屏幕的0.35
            p.width = (int) (d.widthPixels * 0.50);    //宽度设置为屏幕的0.35
            dialog.getWindow().setAttributes(p);     //设置生效

            return dialog;

        }


    }

}

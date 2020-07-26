package com.mnn.mydream.cosmetology.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.mnn.mydream.cosmetology.R;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by MyDream on 2020/5/10.
 * 添加类型弹窗
 */

public class BeautyAddServerTypeDialog extends Dialog {

    public BeautyAddServerTypeDialog(Context context) {
        super(context);
    }

    public BeautyAddServerTypeDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public static class Builder {

        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.ed_servertype)
        AppCompatEditText edServertype;
        @BindView(R.id.btn_cancel)
        AppCompatButton btnCancel;
        @BindView(R.id.btn_commit)
        AppCompatButton btnCommit;

        private Context context;
        private View.OnClickListener yesOnClick;//确定

        private String yesString = "确定";

        private String titleString = "添加服务项目类型";
         LayoutInflater mLayoutInflater;

        public Builder(Context context) {
            this.context = context;
            mLayoutInflater = LayoutInflater.from(context);
        }


        public Builder setYesOnClick(View.OnClickListener onClickListener) {
            this.yesOnClick = onClickListener;
            return this;
        }

        public Builder setTitleString(String titleString) {
            this.titleString = titleString;
            return this;
        }


        public BeautyAddServerTypeDialog createDialog() {

            BeautyAddServerTypeDialog dialog = new BeautyAddServerTypeDialog(context, R.style.ShowDetailsCustomer_Dialog);
            View contentView = mLayoutInflater.inflate(R.layout.beauty_add_servertype_dialog, null);
            dialog.setContentView(contentView);
            ButterKnife.bind(this, contentView);

            title.setText(titleString);

            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            btnCommit.setOnClickListener(yesOnClick);


            DisplayMetrics d = context.getResources().getDisplayMetrics();  //为获取屏幕宽、高
            WindowManager.LayoutParams p = dialog.getWindow().getAttributes();  //获取对话框当前的参数值
            p.height = (int) (d.heightPixels * 0.25);   //高度设置为屏幕的0.35
            p.width = (int) (d.widthPixels * 0.40);    //宽度设置为屏幕的0.35
            dialog.getWindow().setAttributes(p);     //设置生效

            return dialog;

        }


    }

}

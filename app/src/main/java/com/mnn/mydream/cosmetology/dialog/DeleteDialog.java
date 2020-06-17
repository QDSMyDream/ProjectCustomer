package com.mnn.mydream.cosmetology.dialog;

import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.mnn.mydream.cosmetology.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MyDream on 2020/5/16.
 * <p>
 *  删除弹窗
 */

public class DeleteDialog extends Dialog {

    public DeleteDialog(Context context) {
        super(context);
    }

    public DeleteDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public static class Builder {

        private String TAG = "ShowCustomerProjectsDialog";
        private Context context;
        private View.OnClickListener cancelOnClick = null;
        private View.OnClickListener yesOnClick = null;

        private String titleString = "删除客户项目信息";

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setCancelOnClick(View.OnClickListener onClickListener) {
            this.cancelOnClick = onClickListener;

            return this;

        }

        public Builder setTitleMsg(String title) {
            this.titleString = title;

            return this;

        }

        public Builder setYesOnClick(View.OnClickListener onClickListener) {
            this.yesOnClick = onClickListener;

            return this;

        }


        public DeleteDialog createDialog() {

            DeleteDialog dialog = new DeleteDialog(context, R.style.ShowDetailsCustomer_Dialog);
            View contentView = LayoutInflater.from(context).inflate(R.layout.delete_dialog_layout, null);
            dialog.setContentView(contentView);

            ViewHolder viewHolder = new ViewHolder(contentView);

            viewHolder.btnCancel.setOnClickListener(cancelOnClick);
            viewHolder.btnYes.setOnClickListener(yesOnClick);
            viewHolder.deleteTitle.setText(titleString);


            DisplayMetrics d = context.getResources().getDisplayMetrics();  //为获取屏幕宽、高
            WindowManager.LayoutParams p = dialog.getWindow().getAttributes();  //获取对话框当前的参数值
            p.height = (int) (d.heightPixels * 0.30);   //高度设置为屏幕的0.35
            p.width = (int) (d.widthPixels * 0.40);    //宽度设置为屏幕的0.35
            dialog.getWindow().setAttributes(p);     //设置生效
            return dialog;
        }


        static class ViewHolder {
            @BindView(R.id.delete_title)
            TextView deleteTitle;
            @BindView(R.id.btn_cancel)
            TextView btnCancel;
            @BindView(R.id.btn_yes)
            TextView btnYes;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }


}

package com.mnn.mydream.cosmetology.dialog;

import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.mnn.mydream.cosmetology.R;
import com.mnn.mydream.cosmetology.bean.Customer;
import com.mnn.mydream.cosmetology.view.DrawType;
import com.mnn.mydream.cosmetology.view.DrawView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MyDream on 2020/5/10.
 * <p>
 * 添加签名
 */

public class AddSignDialog extends Dialog {

    public AddSignDialog(Context context) {
        super(context);
    }

    public AddSignDialog(Context context, int themeResId) {
        super(context, themeResId);
    }


    public static class Builder {


        private String TAG = "ShowCustomerProjectsDialog";

        private Context context;
        private Customer customer;


        private String projectName;

        public Builder(Context context) {
            this.context = context;
        }


        public Builder setProjectName(String s) {
            this.projectName = s;
            return this;
        }


        public AddSignDialog createDialog() {

            AddSignDialog dialog = new AddSignDialog(context, R.style.ShowDetailsCustomer_Dialog);
            View contentView = LayoutInflater.from(context).inflate(R.layout.add_sign_dialog_layout, null);
            dialog.setContentView(contentView);

            ViewHolder viewHolder = new ViewHolder(contentView);

            if (projectName != null && !projectName.equals("")) {

                viewHolder.title.setText("项目(" + projectName + ")签名");

            }

            viewHolder.btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });


            viewHolder.cancelEmpty.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewHolder.mainView1.clearAll();
                }
            });


            viewHolder.mainView1.drawType = DrawType.brush;

            DisplayMetrics d = context.getResources().getDisplayMetrics();  //为获取屏幕宽、高
            WindowManager.LayoutParams p = dialog.getWindow().getAttributes();  //获取对话框当前的参数值
            p.height = (int) (d.heightPixels * 0.80);   //高度设置为屏幕的0.35
            p.width = (int) (d.widthPixels * 0.90);    //宽度设置为屏幕的0.35
            dialog.getWindow().setAttributes(p);     //设置生效
            return dialog;
        }

        static class ViewHolder {
            @BindView(R.id.mainView1)
            DrawView mainView1;
            @BindView(R.id.cancel_empty)
            ImageView cancelEmpty;
            @BindView(R.id.btn_cancel)
            TextView btnCancel;
            @BindView(R.id.btn_yes)
            TextView btnYes;
            @BindView(R.id.title)
            TextView title;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }


    }


}

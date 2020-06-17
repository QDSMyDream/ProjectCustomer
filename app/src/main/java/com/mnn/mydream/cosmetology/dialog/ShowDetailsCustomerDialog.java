package com.mnn.mydream.cosmetology.dialog;

import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.mnn.mydream.cosmetology.R;
import com.mnn.mydream.cosmetology.bean.Customer;


/**
 * Created by Administrator on 2017/2/16.
 * 选择性对话框，供两项选择
 */

public class ShowDetailsCustomerDialog extends Dialog {


    public ShowDetailsCustomerDialog(Context context) {
        super(context);
    }

    public ShowDetailsCustomerDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public static class Builder {

        private Context context;
        private Button audio_not, audio_save;
        private Customer costorBean;
        private View.OnClickListener cancel, modify;//取消  修改

        public Builder(Context context, Customer costorBean) {
            this.context = context;
            this.costorBean = costorBean;

        }

        public ShowDetailsCustomerDialog createDialog() {

            ShowDetailsCustomerDialog dialog = new ShowDetailsCustomerDialog(context, R.style.ShowDetailsCustomer_Dialog);
            View contentView = LayoutInflater.from(context).inflate(R.layout.customer_details_show_dialog, null);

            dialog.setContentView(contentView);
            DisplayMetrics d = context.getResources().getDisplayMetrics();  //为获取屏幕宽、高
            android.view.WindowManager.LayoutParams p = dialog.getWindow().getAttributes();  //获取对话框当前的参数值
            p.height = (int) (d.heightPixels * 0.80);   //高度设置为屏幕的0.35
            p.width = (int) (d.widthPixels * 0.90);    //宽度设置为屏幕的0.35
            dialog.getWindow().setAttributes(p);     //设置生效
            return dialog;
        }


    }

}

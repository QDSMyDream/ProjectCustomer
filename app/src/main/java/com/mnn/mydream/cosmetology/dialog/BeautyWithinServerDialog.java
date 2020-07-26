package com.mnn.mydream.cosmetology.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.applandeo.materialcalendarview.view.NiceSpinner;
import com.mnn.mydream.cosmetology.R;
import com.mnn.mydream.cosmetology.bean.CustomerProjectsItem;
import com.mnn.mydream.cosmetology.bean.spglBean.FuWuSaleBean;
import com.zhy.android.percent.support.PercentRelativeLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by MyDream on 2020/5/10.
 * 添加服务弹窗
 */

public class BeautyWithinServerDialog extends Dialog {

    public BeautyWithinServerDialog(Context context) {
        super(context);
    }

    public BeautyWithinServerDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public static class Builder {
        private String TAG = "BeautyWithinServerDialog";

        private Context context;
        private View.OnClickListener yesOnClick, photoClick;//确定  图片点击
        private List<String> spStrings;
        private CustomerProjectsItem customerProjectsItem;

        private String yesString = "确定";

        private String titleString = "添加服务项目记录";

        private String projectsString = "";

        private FuWuSaleBean fuWuSaleBean;

        private List<String> serverTypeStrings, serverMdStrings;

        private ImageView imageView;


        public Builder(Context context) {
            this.context = context;
        }

        public Builder(Context context, FuWuSaleBean fuWuSaleBean) {
            this.context = context;
            this.fuWuSaleBean = fuWuSaleBean;
        }

        public Builder setYesOnClick(View.OnClickListener onClickListener) {
            this.yesOnClick = onClickListener;
            return this;
        }

        public Builder setPhotoClickOnClick(View.OnClickListener onClickListener) {
            this.photoClick = onClickListener;
            return this;
        }

        public Builder setCustomerItem(CustomerProjectsItem customerItem) {
            this.customerProjectsItem = customerItem;
            return this;
        }

        public Builder setSpData(List<String> spStrings) {
            this.spStrings = spStrings;
            return this;
        }

        public Builder setYesString(String yesBtnString) {
            this.yesString = yesBtnString;
            return this;
        }

        public Builder setTitleString(String titleString) {
            this.titleString = titleString;
            return this;
        }

        public Builder setProjectsString(String string) {
            this.projectsString = string;
            return this;
        }

        public Builder setServerTypeData(List<String> serverTypeStrings) {
            this.serverTypeStrings = serverTypeStrings;
            return this;
        }


        public Builder setServerMdData(List<String> serverMdStrings) {
            this.serverMdStrings = serverMdStrings;
            return this;
        }


        public ImageView getServerImageView() {


            return imageView;
        }


        public BeautyWithinServerDialog createDialog() {

            BeautyWithinServerDialog dialog = new BeautyWithinServerDialog(context, R.style.ShowDetailsCustomer_Dialog);

            View contentView = LayoutInflater.from(context).inflate(R.layout.beauty_within_server_dialog, null);

            dialog.setContentView(contentView);

            ViewHolder viewHolder = new ViewHolder(contentView);

            if (fuWuSaleBean == null) {

                viewHolder.title.setText("添加服务项目");

                viewHolder.btnYes.setOnClickListener(yesOnClick);

            } else {
                viewHolder.title.setText("修改服务项目");

                viewHolder.btnYes.setOnClickListener(yesOnClick);
            }
            viewHolder.btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });


            imageView = viewHolder.serverImgPhoto;

            viewHolder.layoutPhoto.setOnClickListener(photoClick);
            viewHolder.serverMd.attachDataSource(serverMdStrings);
            viewHolder.serverType.attachDataSource(serverTypeStrings);

            DisplayMetrics d = context.getResources().getDisplayMetrics();  //为获取屏幕宽、高
            WindowManager.LayoutParams p = dialog.getWindow().getAttributes();  //获取对话框当前的参数值
            p.height = (int) (d.heightPixels * 0.60);   //高度设置为屏幕的0.35
            p.width = (int) (d.widthPixels * 0.70);    //宽度设置为屏幕的0.35
            dialog.getWindow().setAttributes(p);     //设置生效

            return dialog;

        }

        static class ViewHolder {
            @BindView(R.id.title)
            TextView title;
            @BindView(R.id.server_img_photo)
            ImageView serverImgPhoto;
            @BindView(R.id.layout_photo)
            PercentRelativeLayout layoutPhoto;
            @BindView(R.id.server_name)
            AppCompatEditText serverName;
            @BindView(R.id.server_type)
            NiceSpinner serverType;
            @BindView(R.id.server_money)
            AppCompatEditText serverMoney;
            @BindView(R.id.server_md)
            NiceSpinner serverMd;
            @BindView(R.id.btn_cancel)
            TextView btnCancel;
            @BindView(R.id.btn_yes)
            TextView btnYes;
            @BindView(R.id.myScrollView)
            ScrollView myScrollView;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }

            public ImageView getServerImageView() {
                return serverImgPhoto;
            }

        }
    }

}

package com.mnn.mydream.cosmetology.dialog;

import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.mnn.mydream.cosmetology.R;
import com.mnn.mydream.cosmetology.bean.CustomerProjectBean;
import com.zhy.android.percent.support.PercentLinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by MyDream on 2020/5/10.
 * <p>
 * 项目弹窗
 */

public class AddProjectsDialog extends Dialog {


    public AddProjectsDialog(Context context) {
        super(context);
    }

    public AddProjectsDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public static class Builder {
        private String TAG = "ShowCustomerProjectsDialog";

        private Context context;

        private View.OnClickListener yesOnclick;//修改

        private CustomerProjectBean customerProject;//项目表

        private String title = "添加服务项目";

        private String projectString = "添加项目";

        private String projectHintString = "请输入项目名";

        private String yesTextString = "确 定";

        private boolean b = false;


        public Builder(Context context, CustomerProjectBean customerProjects) {
            this.context = context;
            this.customerProject = customerProjects;
        }

        //设置是否是修改  隐藏状态栏
        public Builder setVisble(boolean b) {
            this.b = b;
            return this;
        }

        //设置第二编辑
        public Builder setString(String titleString, String projectString, String projectHintString, String yesTextString) {
            this.title = titleString;
            this.projectString = projectString;
            this.projectHintString = projectHintString;
            this.yesTextString = yesTextString;
            return this;
        }

        //确定按钮监听
        public Builder setYesOnClick(View.OnClickListener yesOnClick) {
            this.yesOnclick = yesOnClick;
            return this;
        }

        public AddProjectsDialog createDialog() {

            AddProjectsDialog dialog = new AddProjectsDialog(context, R.style.ShowDetailsCustomer_Dialog);

            View contentView = LayoutInflater.from(context).inflate(R.layout.customer_add_projects_show_dialog, null);

            dialog.setContentView(contentView);

            ViewHolder viewHolder = new ViewHolder(contentView);

            viewHolder.btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            viewHolder.tvAddProjects.setText(projectString);

            viewHolder.edAddProjects.setHint(projectHintString);

            viewHolder.btnYes.setText(yesTextString);

            viewHolder.btnYes.setOnClickListener(yesOnclick);

            viewHolder.currentProjectsLayout.setVisibility(b ? View.VISIBLE : View.GONE);

            viewHolder.projectsTitle.setText(title);

            if (customerProject != null) {
                viewHolder.currentProjects2.setText(customerProject.getcProjects());
            }

            DisplayMetrics d = context.getResources().getDisplayMetrics();  //为获取屏幕宽、高
            WindowManager.LayoutParams p = dialog.getWindow().getAttributes();  //获取对话框当前的参数值
            p.height = (int) (d.heightPixels * 0.50);   //高度设置为屏幕的0.35
            p.width = (int) (d.widthPixels * 0.60);    //宽度设置为屏幕的0.35
            dialog.getWindow().setAttributes(p);     //设置生效

            return dialog;

        }

        static class ViewHolder {

            @BindView(R.id.projects_title)
            TextView projectsTitle;
            @BindView(R.id.current_projects)
            TextView currentProjects;
            @BindView(R.id.current_projects2)
            TextView currentProjects2;
            @BindView(R.id.current_projects_layout)
            PercentLinearLayout currentProjectsLayout;
            @BindView(R.id.tv_add_projects)
            TextView tvAddProjects;
            @BindView(R.id.ed_add_projects)
            EditText edAddProjects;
            @BindView(R.id.tv_add_projects_time)
            TextView tvAddProjectsTime;
            @BindView(R.id.ed_add_projects_time)
            TextView edAddProjectsTime;
            @BindView(R.id.ed_add_projects_img)
            ImageView edAddProjectsImg;
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

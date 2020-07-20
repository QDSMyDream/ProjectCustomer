package com.mnn.mydream.cosmetology.dialog;

import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.smoothcheckbox.SmoothCheckBox;
import com.mnn.mydream.cosmetology.R;
import com.mnn.mydream.cosmetology.adapter.SelectCheckBoxListAdapter;
import com.mnn.mydream.cosmetology.bean.SelectSignBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MyDream on 2020/5/16.
 * <p>
 * 删除弹窗
 */

public class RefreshRotateDialog extends Dialog {

    public RefreshRotateDialog(Context context) {
        super(context);
    }

    public RefreshRotateDialog(Context context, int themeResId) {
        super(context, themeResId);
    }


    public static class Builder {

        private String TAG = "RefreshRotateDialog";
        private Context context;


        public Builder(Context context) {
            this.context = context;

        }

        public RefreshRotateDialog createDialog() {

            RefreshRotateDialog dialog = new RefreshRotateDialog(context, R.style.ShowDetailsCustomer_Dialog);
            View contentView = LayoutInflater.from(context).inflate(R.layout.refresh_rotate_dialog_layout, null);
            dialog.setContentView(contentView);
            ViewHolder viewHolder = new ViewHolder(contentView);

            Animation mAnimation = AnimationUtils.loadAnimation(context, R.anim.rotate);
            viewHolder.btnCancel.startAnimation(mAnimation);


            DisplayMetrics d = context.getResources().getDisplayMetrics();  //为获取屏幕宽、高
            WindowManager.LayoutParams p = dialog.getWindow().getAttributes();  //获取对话框当前的参数值
            p.height = (int) (d.heightPixels * 0.20);   //高度设置为屏幕的0.35
            p.width = (int) (d.widthPixels * 0.40);    //宽度设置为屏幕的0.35
            dialog.getWindow().setAttributes(p);     //设置生效
            return dialog;
        }


        static class ViewHolder {
            @BindView(R.id.btn_cancel)
            ImageView btnCancel;
            @BindView(R.id.btn_yes)
            TextView btnYes;
            @BindView(R.id.layout2)
            LinearLayout layout2;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
//        SmoothCheckBox.OnCheckedChangeListener onCheckedChangeListener = new SmoothCheckBox.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(SmoothCheckBox checkBox, boolean isChecked) {
//
//                Log.e(TAG, "onCheckedChanged: " + viewHolder.selectSignListview.getPositionForView(checkBox));
//                customerProjectsItems.get(viewHolder.selectSignListview.getPositionForView(checkBox)).setcSignFlag(isChecked);
//
//                boolean isAll = true;
//                for (CustomerProjectsItem customerProjectsItem : customerProjectsItems) {
//                    if (!customerProjectsItem.iscSignFlag()) {
//                        isAll = false;
//                    }
//                }
//                viewHolder.selectAll.setChecked(isAll);
//            }
//        };

    }


}

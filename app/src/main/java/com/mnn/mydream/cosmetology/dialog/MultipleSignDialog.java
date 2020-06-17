package com.mnn.mydream.cosmetology.dialog;

import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
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

public class MultipleSignDialog extends Dialog {

    public MultipleSignDialog(Context context) {
        super(context);
    }

    public MultipleSignDialog(Context context, int themeResId) {
        super(context, themeResId);
    }


    public static class Builder {

        private String TAG = "ShowCustomerProjectsDialog";
        private Context context;
        private View.OnClickListener cancelOnClick = null;
        private View.OnClickListener yesOnClick = null;

        boolean isAllData = false;
        private String titleString = "删除客户项目信息";
        private SelectCheckBoxListAdapter selectCheckBoxListAdapter;

        private List<SelectSignBean> selectSignItem;//选中的item  数据处理

        public Builder(Context context, List<SelectSignBean> integers) {
            this.context = context;

            this.selectSignItem = integers;
        }

        public Builder setTitleMsg(String title) {
            this.titleString = title;

            return this;

        }

        public Builder setYesOnClick(View.OnClickListener onClickListener) {
            this.yesOnClick = onClickListener;

            return this;

        }

        public MultipleSignDialog createDialog() {

            MultipleSignDialog dialog = new MultipleSignDialog(context, R.style.ShowDetailsCustomer_Dialog);
            View contentView = LayoutInflater.from(context).inflate(R.layout.select_sign_dialog_layout, null);
            dialog.setContentView(contentView);


            ViewHolder viewHolder = new ViewHolder(contentView);

            View.OnClickListener onClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    SmoothCheckBox smoothCheckBox = v.findViewById(R.id.scb);

                    if (smoothCheckBox.isChecked()) {
                        smoothCheckBox.setChecked(false);

                    } else {
                        smoothCheckBox.setChecked(true);
                    }

                    selectSignItem.get(viewHolder.selectSignListview.getPositionForView(v)).setSignFlag(smoothCheckBox.isChecked());
                    boolean isAll = true;
                    for (SelectSignBean customerProjectsItem : selectSignItem) {
                        if (!customerProjectsItem.isSignFlag()) {
                            isAll = false;
                        }
                    }

                    viewHolder.selectAll.setChecked(isAll);
                }
            };
            selectCheckBoxListAdapter = new SelectCheckBoxListAdapter(context, selectSignItem, onClickListener);


            viewHolder.selectSignListview.setAdapter(selectCheckBoxListAdapter);
            viewHolder.selectSignListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    SmoothCheckBox smoothCheckBox = view.findViewById(R.id.scb);
                    if (smoothCheckBox.isChecked()) {
                        smoothCheckBox.setChecked(false);
                    } else {
                        smoothCheckBox.setChecked(true);
                    }
                    selectSignItem.get(viewHolder.selectSignListview.getPositionForView(view)).setSignFlag(smoothCheckBox.isChecked());

                    boolean isAll = true;
                    for (SelectSignBean customerProjectsItem : selectSignItem) {
                        if (!customerProjectsItem.isSignFlag()) {
                            isAll = false;
                        }
                    }
                    viewHolder.selectAll.setChecked(isAll);
                }
            });


            viewHolder.btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    for (SelectSignBean customerProjectsItem : selectSignItem) {
                        customerProjectsItem.setSignFlag(false);
                    }

                    dialog.dismiss();
                }
            });
            viewHolder.selectAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (viewHolder.selectAll.isChecked()) {
                        viewHolder.selectAll.setChecked(false);

                    } else {
                        viewHolder.selectAll.setChecked(true);
                    }


                    if (viewHolder.selectAll.isChecked()) {
                        for (SelectSignBean recoderData : selectSignItem) {
                            recoderData.setSignFlag(true);
                            isAllData = true;
                        }
                    } else {
                        for (SelectSignBean recoderData : selectSignItem) {
                            recoderData.setSignFlag(false);
                            isAllData = false;
                        }
                    }
                    selectCheckBoxListAdapter.notifyDataSetChanged();
                }
            });
//            viewHolder.selectAll.setOnCheckedChangeListener(new SmoothCheckBox.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(SmoothCheckBox checkBox, boolean isChecked) {
//
//                    Log.e(TAG, "onCheckedChanged: " + isChecked);
//                    if (isChecked) {
//                        for (CustomerProjectsItem recoderData : customerProjectsItems) {
//                            recoderData.setcSignFlag(true);
//                            isAllData = true;
//                        }
//                    } else {
//                        for (CustomerProjectsItem recoderData : customerProjectsItems) {
//                            recoderData.setcSignFlag(false);
//                            isAllData = false;
//                        }
//                    }
//                    selectCheckBoxListAdapter.notifyDataSetChanged();
//                }
//            });
            viewHolder.btnYes.setOnClickListener(yesOnClick);

            DisplayMetrics d = context.getResources().getDisplayMetrics();  //为获取屏幕宽、高
            WindowManager.LayoutParams p = dialog.getWindow().getAttributes();  //获取对话框当前的参数值
            p.height = (int) (d.heightPixels * 0.60);   //高度设置为屏幕的0.35
            p.width = (int) (d.widthPixels * 0.70);    //宽度设置为屏幕的0.35
            dialog.getWindow().setAttributes(p);     //设置生效
            return dialog;
        }


        static class ViewHolder {
            @BindView(R.id.tv)
            TextView tv;
            @BindView(R.id.select_all)
            SmoothCheckBox selectAll;
            @BindView(R.id.select_sign_listview)
            ListView selectSignListview;
            @BindView(R.id.btn_cancel)
            TextView btnCancel;
            @BindView(R.id.btn_yes)
            TextView btnYes;

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

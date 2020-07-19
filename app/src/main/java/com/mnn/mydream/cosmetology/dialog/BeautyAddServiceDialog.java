package com.mnn.mydream.cosmetology.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.applandeo.materialcalendarview.view.NiceSpinner;
import com.example.smoothcheckbox.SmoothCheckBox;
import com.mnn.mydream.cosmetology.R;
import com.mnn.mydream.cosmetology.bean.SelectSignBean;
import com.mnn.mydream.cosmetology.bean.fuwuBean.FuWuSaleBean;
import com.mnn.mydream.cosmetology.bmob.BeanCallBack;
import com.mnn.mydream.cosmetology.fragment.beauty.commoditiesFragments.adapter.AddFWListAdapter;
import com.mnn.mydream.cosmetology.interfaces.AddServiceOnCheckedChangeListener;
import com.mnn.mydream.cosmetology.utils.Constons;
import com.mnn.mydream.cosmetology.utils.StringUtils;
import com.mnn.mydream.cosmetology.utils.ToastUtils;
import com.zhy.android.percent.support.PercentLinearLayout;
import com.zhy.android.percent.support.PercentRelativeLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.droidlover.xrecyclerview.XRecyclerView;

/**
 * Created by MyDream on 2020/5/16.
 * 查询服务弹窗
 */
public class BeautyAddServiceDialog extends Dialog {

    public BeautyAddServiceDialog(Context context) {
        super(context);
    }

    public BeautyAddServiceDialog(Context context, int themeResId) {
        super(context, themeResId);
    }


    public static class Builder {

        private String TAG = "ShowCustomerProjectsDialog";

        private Context context;

        private List<FuWuSaleBean> fuWuSaleBeans = new ArrayList<>();

        private List<FuWuSaleBean> selectFuWuSaleBeans = new ArrayList<>();

        private List<FuWuSaleBean> fuWuSaleBeans2;

        public Builder(Context context) {
            this.context = context;

        }

        public Builder setFuWuSaleBeans(List<FuWuSaleBean> fuWuSaleBeans2) {
            this.fuWuSaleBeans2 = fuWuSaleBeans2;
            return this;
        }

        public BeanCallBack beanCallBack;

        public Builder setBeanCallBack(BeanCallBack beanCallBack) {
            this.beanCallBack = beanCallBack;
            return this;
        }

        public BeautyAddServiceDialog createDialog() {

            BeautyAddServiceDialog dialog = new BeautyAddServiceDialog(context, R.style.ShowDetailsCustomer_Dialog);
            View contentView = LayoutInflater.from(context).inflate(R.layout.beauty_add_service_dialog_layout, null);
            dialog.setContentView(contentView);

            //FuWuSaleBean 查询
            ViewHolder viewHolder = new ViewHolder(contentView);
            FuWuSaleBean fuWuSaleBean = new FuWuSaleBean();

            fuWuSaleBean.selectBean(new BeanCallBack() {
                @Override
                public void setLists(List lists, int flagInt) {

                    if (flagInt == Constons.RESULT_ADD_SERVICE_VIEW_REQUEST) {

                        for (FuWuSaleBean fuWuSaleBean : (List<FuWuSaleBean>) lists) {

                            if (fuWuSaleBean.isServerSaleFlag()) {

                                fuWuSaleBeans.add(fuWuSaleBean);

                                String selectFuwuTextStr = String.format(context.getString(R.string.beauty_add_service_select_fuwu), fuWuSaleBeans.size());
                                viewHolder.selectFuwuText.setText(selectFuwuTextStr);
                            }
                        }
                    }
                }


            }, Constons.RESULT_ADD_SERVICE_VIEW_REQUEST);


            View.OnClickListener onClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e(TAG, "onClick: ");
                    SmoothCheckBox smoothCheckBox = v.findViewById(R.id.check_box);
                    if (smoothCheckBox.isChecked()) {
                        smoothCheckBox.setChecked(false);
                    } else {
                        smoothCheckBox.setChecked(true);
                    }

                }
            };
            //创建布局管理器，垂直设置LinearLayoutManager.VERTICAL，水平设置LinearLayoutManager.HORIZONTAL
            LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);

            AddFWListAdapter addFWListAdapter = new AddFWListAdapter(context, fuWuSaleBeans, fuWuSaleBeans2, onClickListener);

            addFWListAdapter.setAddServiceOnCheckedChangeListener(new AddServiceOnCheckedChangeListener() {
                @Override
                public void addOnCheckedBean(FuWuSaleBean fuWuSaleBean) {
                    selectFuWuSaleBeans.add(fuWuSaleBean);
                    Log.e(TAG, "addOnCheckedBean: ");
                    String selectFuwuTextStr = String.format(context.getString(R.string.beauty_add_service_choice_fuwu), selectFuWuSaleBeans.size());
                    viewHolder.choiceFuwuText.setText(selectFuwuTextStr);
                }

                @Override
                public void removeOnCheckedBean(FuWuSaleBean fuWuSaleBean) {
                    selectFuWuSaleBeans.remove(fuWuSaleBean);
                    Log.e(TAG, "removeOnCheckedBean: ");
                    String selectFuwuTextStr = String.format(context.getString(R.string.beauty_add_service_choice_fuwu), selectFuWuSaleBeans.size());
                    viewHolder.choiceFuwuText.setText(selectFuwuTextStr);
                }
            });

            String selectFuwuTextStr = String.format(context.getString(R.string.beauty_add_service_choice_fuwu), selectFuWuSaleBeans.size());
            viewHolder.choiceFuwuText.setText(selectFuwuTextStr);


            viewHolder.fwList.setLayoutManager(mLinearLayoutManager);
            viewHolder.fwList.setAdapter(addFWListAdapter);

            viewHolder.serverTypeSpinner.attachDataSource(Constons.SelectServerTypeString);

            viewHolder.btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            viewHolder.btnCommit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Log.e(TAG, "onClick: "+fuWuSaleBeans2.size());
                    Log.e(TAG, "onClick: "+selectFuWuSaleBeans.size());


                    if (selectFuWuSaleBeans.size() > 5 || (fuWuSaleBeans2.size() + selectFuWuSaleBeans.size()) > 5) {

                        ToastUtils.showToast(context, "项目列表超过5个，请重新选择", false);
                        return;
                    }

                    beanCallBack.setLists(selectFuWuSaleBeans, Constons.RESULT_ADD_SERVICE_VIEW_REQUEST_CALLBACK);
                    dialog.dismiss();
                }
            });

            viewHolder.searchServerBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    selectFuWuSaleBeans.clear();

                    fuWuSaleBean.selectBeanStr(new BeanCallBack() {
                        @Override
                        public void setLists(List lists, int flagInt) {
                            Log.e(TAG, "setLists: ");
                            fuWuSaleBeans.clear();
                            for (FuWuSaleBean fuWuSaleBean : (List<FuWuSaleBean>) lists) {

                                if (fuWuSaleBean.isServerSaleFlag()) {
                                    fuWuSaleBeans.add(fuWuSaleBean);
                                    String selectFuwuTextStr = String.format(context.getString(R.string.beauty_add_service_select_fuwu), fuWuSaleBeans.size());
                                    viewHolder.selectFuwuText.setText(selectFuwuTextStr);
                                }
                            }

                            addFWListAdapter.notifyDataSetChanged();
                        }
                    }, Constons.RESULT_ADD_SERVICE_VIEW_REQUEST_SELECT, viewHolder.serverNameEdit.getText().toString(), viewHolder.serverTypeSpinner.getSelectedItem().toString());


                    String selectFuwuTextStr = String.format(context.getString(R.string.beauty_add_service_choice_fuwu), selectFuWuSaleBeans.size());
                    viewHolder.choiceFuwuText.setText(selectFuwuTextStr);


                }

            });
            viewHolder.remakeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    selectFuWuSaleBeans.clear();

                    fuWuSaleBean.selectBean(new BeanCallBack() {
                        @Override
                        public void setLists(List lists, int flagInt) {

                            fuWuSaleBeans.clear();
                            if (flagInt == Constons.RESULT_ADD_SERVICE_VIEW_REQUEST) {

                                for (FuWuSaleBean fuWuSaleBean : (List<FuWuSaleBean>) lists) {

                                    if (fuWuSaleBean.isServerSaleFlag()) {

                                        fuWuSaleBeans.add(fuWuSaleBean);

                                        String selectFuwuTextStr = String.format(context.getString(R.string.beauty_add_service_select_fuwu), fuWuSaleBeans.size());
                                        viewHolder.selectFuwuText.setText(selectFuwuTextStr);
                                    }
                                }
                            }
                            addFWListAdapter.notifyDataSetChanged();
                        }
                    }, Constons.RESULT_ADD_SERVICE_VIEW_REQUEST);


                    String selectFuwuTextStr = String.format(context.getString(R.string.beauty_add_service_choice_fuwu), selectFuWuSaleBeans.size());
                    viewHolder.choiceFuwuText.setText(selectFuwuTextStr);
                }
            });


            DisplayMetrics d = context.getResources().getDisplayMetrics();  //为获取屏幕宽、高
            WindowManager.LayoutParams p = dialog.getWindow().getAttributes();  //获取对话框当前的参数值
            p.height = (int) (d.heightPixels * 0.80);   //高度设置为屏幕的0.35
            p.width = (int) (d.widthPixels * 0.80);    //宽度设置为屏幕的0.35
            dialog.getWindow().setAttributes(p);     //设置生效
            return dialog;
        }


        static class ViewHolder {
            @BindView(R.id.select_fuwu_text)
            TextView selectFuwuText;
            @BindView(R.id.server_name_edit)
            AppCompatEditText serverNameEdit;
            @BindView(R.id.server_type_spinner)
            NiceSpinner serverTypeSpinner;
            @BindView(R.id.search_server_btn)
            TextView searchServerBtn;
            @BindView(R.id.remake)
            TextView remake;
            @BindView(R.id.remake_layout)
            PercentRelativeLayout remakeLayout;
            @BindView(R.id.choice_fuwu_text)
            TextView choiceFuwuText;
            @BindView(R.id.select_server_all)
            PercentLinearLayout selectServerAll;
            @BindView(R.id.type_text)
            TextView typeText;
            @BindView(R.id.apply_md)
            TextView applyMd;
            @BindView(R.id.add_date)
            TextView addDate;
            @BindView(R.id.operation)
            TextView operation;
            @BindView(R.id.title_layout)
            PercentLinearLayout titleLayout;
            @BindView(R.id.fw_list)
            XRecyclerView fwList;
            @BindView(R.id.btn_cancel)
            AppCompatButton btnCancel;
            @BindView(R.id.btn_commit)
            AppCompatButton btnCommit;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }


    }


}

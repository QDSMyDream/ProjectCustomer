package com.mnn.mydream.cosmetology.fragment.beauty.commoditiesFragments.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.smoothcheckbox.SmoothCheckBox;
import com.mnn.mydream.cosmetology.R;
import com.mnn.mydream.cosmetology.bean.fuwuBean.FuWuSaleBean;
import com.mnn.mydream.cosmetology.bean.fuwuBean.XMKDataOpertionBean;
import com.mnn.mydream.cosmetology.interfaces.AddServiceOnCheckedChangeListener;
import com.mnn.mydream.cosmetology.interfaces.SPGLListOnClickListener;
import com.mnn.mydream.cosmetology.utils.ImageLoader;
import com.mnn.mydream.cosmetology.utils.ToastUtils;
import com.zhy.android.percent.support.PercentLinearLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.droidlover.xrecyclerview.XRecyclerView;

/**
 * 创建人 :MyDream
 * 创建时间：2020/5/3 18:18
 * 类描述：FuWu 服务列表
 */

public class AddFWListAdapter extends XRecyclerView.Adapter {

    private String TAG = "FWListAdapter";
    private List<FuWuSaleBean> fuWuSaleBeans;
    private final Context mContext;
    private View.OnClickListener onClickListener;
    LayoutInflater mLayoutInflater;
    private List<XMKDataOpertionBean> xmkDataOpertionBeans;

    public AddFWListAdapter(Context mContext, List<FuWuSaleBean> fuWuSaleBeans, List<XMKDataOpertionBean> xmkDataOpertionBeans, View.OnClickListener onClickListener) {
        this.mContext = mContext;
        this.fuWuSaleBeans = fuWuSaleBeans;
        this.onClickListener = onClickListener;
        this.xmkDataOpertionBeans = xmkDataOpertionBeans;
        mLayoutInflater = LayoutInflater.from(mContext);

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(mLayoutInflater.inflate(R.layout.add_fuwu_list_item, null));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder myholder = (ViewHolder) viewHolder;
        myholder.setData(i);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return fuWuSaleBeans.size();
    }

    public AddServiceOnCheckedChangeListener addServiceOnCheckedChangeListener;


    public void setAddServiceOnCheckedChangeListener(AddServiceOnCheckedChangeListener addServiceOnCheckedChangeListener) {
        this.addServiceOnCheckedChangeListener = addServiceOnCheckedChangeListener;
    }


    class ViewHolder extends XRecyclerView.ViewHolder {
        @BindView(R.id.iv_server)
        ImageView ivServer;
        @BindView(R.id.serverName)
        TextView serverName;
        @BindView(R.id.server_money)
        TextView serverMoney;
        @BindView(R.id.select_server_all)
        PercentLinearLayout selectServerAll;
        @BindView(R.id.type_text)
        TextView typeText;
        @BindView(R.id.apply_md)
        TextView applyMd;
        @BindView(R.id.add_date)
        TextView addDate;
        @BindView(R.id.check_box)
        SmoothCheckBox checkBox;
        @BindView(R.id.title_layout)
        PercentLinearLayout titleLayout;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void setData(int position) {
            FuWuSaleBean fuWuSaleBean = fuWuSaleBeans.get(position);

            //加载图片
            ImageLoader.displayImageView(mContext, fuWuSaleBean.getServerUrl(), ivServer, R.mipmap.ic_img_default);

            serverName.setText(fuWuSaleBean.getServerName() + "");
            serverMoney.setText(fuWuSaleBean.getServerMoney() + "");
            applyMd.setText(fuWuSaleBean.getApplyMd() + "");
            typeText.setText(fuWuSaleBean.getServerType() + "");
            addDate.setText(fuWuSaleBean.getCreatedAt() + "");


            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (checkBox.isChecked()) {
                        checkBox.setChecked(false);
                        addServiceOnCheckedChangeListener.removeOnCheckedBean(fuWuSaleBean);

                    } else {
                        Log.e(TAG, "onClick: " + fuWuSaleBean.toString());
                        if (xmkDataOpertionBeans != null && xmkDataOpertionBeans.size() > 0) {

                            for (XMKDataOpertionBean xmkDataOpertionBean : xmkDataOpertionBeans) {
                                if (fuWuSaleBean.getObjectId().equals(xmkDataOpertionBean.getFuWuSaleBean().getObjectId())) {
                                    ToastUtils.showToast(mContext, "此服务已经添加", false);
                                    return;
                                }

                            }
                            checkBox.setChecked(true);
                            addServiceOnCheckedChangeListener.addOnCheckedBean(fuWuSaleBean);

                        } else {
                            checkBox.setChecked(true);
                            addServiceOnCheckedChangeListener.addOnCheckedBean(fuWuSaleBean);

                        }


                    }


                }
            });
        }


    }
}

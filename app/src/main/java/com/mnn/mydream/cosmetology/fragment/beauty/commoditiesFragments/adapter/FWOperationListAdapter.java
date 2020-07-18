package com.mnn.mydream.cosmetology.fragment.beauty.commoditiesFragments.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smoothcheckbox.SmoothCheckBox;
import com.mnn.mydream.cosmetology.R;
import com.mnn.mydream.cosmetology.bean.fuwuBean.FuWuSaleBean;
import com.mnn.mydream.cosmetology.interfaces.AddServiceOnCheckedChangeListener;
import com.mnn.mydream.cosmetology.utils.ImageLoader;
import com.zhy.android.percent.support.PercentLinearLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 创建人 :MyDream
 * 创建时间：2020/5/3 18:18
 * 类描述：FuWu 服务详情
 */

public class FWOperationListAdapter extends BaseAdapter {

    private String TAG = "FWListAdapter";
    private List<FuWuSaleBean> fuWuSaleBeans;
    private final Context mContext;
    private View.OnClickListener onClickListener;

    public FWOperationListAdapter(Context mContext, List<FuWuSaleBean> fuWuSaleBeans, View.OnClickListener onClickListener) {
        this.mContext = mContext;
        this.fuWuSaleBeans = fuWuSaleBeans;

        this.onClickListener = onClickListener;

    }

    @Override
    public int getCount() {
        return fuWuSaleBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return fuWuSaleBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.fuwu_operation_list_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        FuWuSaleBean fuWuSaleBean = fuWuSaleBeans.get(position);

        //加载图片
        ImageLoader.displayImageView(mContext, fuWuSaleBean.getServerUrl(), holder.ivServer, R.mipmap.ic_img_default);

        holder.title1.setText(fuWuSaleBean.getServerName() + "");
        holder.title3.setText(fuWuSaleBean.getServerMoney() + "");
        holder.title2.setText(fuWuSaleBean.getServerType() + "");

   holder.delete.setOnClickListener(onClickListener);



        return convertView;
    }

    public AddServiceOnCheckedChangeListener addServiceOnCheckedChangeListener;


    public void setAddServiceOnCheckedChangeListener(AddServiceOnCheckedChangeListener addServiceOnCheckedChangeListener) {
        this.addServiceOnCheckedChangeListener = addServiceOnCheckedChangeListener;
    }


    static class ViewHolder {
        @BindView(R.id.iv_server)
        ImageView ivServer;
        @BindView(R.id.title1)
        TextView title1;
        @BindView(R.id.title2)
        TextView title2;
        @BindView(R.id.title3)
        TextView title3;
        @BindView(R.id.tip1)
        TextView tip1;
        @BindView(R.id.total_num)
        AppCompatEditText totalNum;
        @BindView(R.id.tip2)
        TextView tip2;
        @BindView(R.id.delete)
        ImageView delete;
        @BindView(R.id.title_layout)
        PercentLinearLayout titleLayout;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

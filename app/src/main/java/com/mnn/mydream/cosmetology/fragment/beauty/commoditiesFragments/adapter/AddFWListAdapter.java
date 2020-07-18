package com.mnn.mydream.cosmetology.fragment.beauty.commoditiesFragments.adapter;

import android.content.Context;
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
import com.mnn.mydream.cosmetology.interfaces.AddServiceOnCheckedChangeListener;
import com.mnn.mydream.cosmetology.interfaces.SPGLListOnClickListener;
import com.mnn.mydream.cosmetology.utils.ImageLoader;
import com.zhy.android.percent.support.PercentLinearLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 创建人 :MyDream
 * 创建时间：2020/5/3 18:18
 * 类描述：FuWu 服务列表
 */

public class AddFWListAdapter extends BaseAdapter {

    private String TAG = "FWListAdapter";
    private List<FuWuSaleBean> fuWuSaleBeans;
    private final Context mContext;
    private View.OnClickListener onClickListener;

    public AddFWListAdapter(Context mContext, List<FuWuSaleBean> fuWuSaleBeans,View.OnClickListener onClickListener) {
        this.mContext = mContext;
        this.fuWuSaleBeans = fuWuSaleBeans;

        this.onClickListener=onClickListener;

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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.add_fuwu_list_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        FuWuSaleBean fuWuSaleBean = fuWuSaleBeans.get(position);

        //加载图片
        ImageLoader.displayImageView(mContext, fuWuSaleBean.getServerUrl(), holder.ivServer, R.mipmap.ic_img_default);

        holder.serverName.setText(fuWuSaleBean.getServerName() + "");
        holder.serverMoney.setText(fuWuSaleBean.getServerMoney() + "");
        holder.applyMd.setText(fuWuSaleBean.getApplyMd() + "");
        holder.typeText.setText(fuWuSaleBean.getServerType() + "");
        holder.addDate.setText(fuWuSaleBean.getCreatedAt() + "");

        holder.checkBox.setOnCheckedChangeListener(new SmoothCheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SmoothCheckBox checkBox, boolean isChecked) {
                if (isChecked) {
                    addServiceOnCheckedChangeListener.addOnCheckedBean(fuWuSaleBean);
                } else {
                    addServiceOnCheckedChangeListener.removeOnCheckedBean(fuWuSaleBean);
                }

            }
        });

        holder.checkBox.setOnClickListener(onClickListener);



        return convertView;
    }

    public AddServiceOnCheckedChangeListener addServiceOnCheckedChangeListener;


    public void setAddServiceOnCheckedChangeListener(AddServiceOnCheckedChangeListener addServiceOnCheckedChangeListener) {
        this.addServiceOnCheckedChangeListener = addServiceOnCheckedChangeListener;
    }


    static class ViewHolder {
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
            ButterKnife.bind(this, view);
        }
    }
}

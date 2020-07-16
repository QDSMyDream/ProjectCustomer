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

import com.mnn.mydream.cosmetology.R;
import com.mnn.mydream.cosmetology.bean.fuwuBean.CPDataBean;
import com.mnn.mydream.cosmetology.bean.fuwuBean.FuWuSaleBean;
import com.mnn.mydream.cosmetology.interfaces.CPListOnClickListener;
import com.mnn.mydream.cosmetology.interfaces.SPGLListOnClickListener;
import com.mnn.mydream.cosmetology.utils.ImageLoader;
import com.zhy.android.percent.support.PercentLinearLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 创建人 :MyDream
 * 创建时间：2020/5/3 18:18
 * 类描述：产品列表
 */

public class CPListAdapter extends BaseAdapter {

    private String TAG = "FuWuView1ListAdapter";
    private List<CPDataBean> cpDataBeans;
    private final Context mContext;

    public CPListAdapter(Context mContext, List<CPDataBean> cpDataBeans) {
        this.mContext = mContext;
        this.cpDataBeans = cpDataBeans;
    }

    @Override
    public int getCount() {
        return cpDataBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return cpDataBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.cp_list_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        CPDataBean cpDataBean = cpDataBeans.get(position);

        //加载图片
        ImageLoader.displayImageView(mContext, cpDataBean.getCpUrl(), holder.ivServer, R.mipmap.ic_img_default);
        holder.serverName.setText(cpDataBean.getCpName() + "");
        holder.serverMoney.setText(cpDataBean.getCpMoney() + "");
        holder.applyMd.setText(cpDataBean.getApplyMd() + "");
        holder.typeText.setText(cpDataBean.getCpType() + "");
        holder.addDate.setText(cpDataBean.getCreatedAt() + "");
        holder.applySpecifications.setText(cpDataBean.isOpenSpecifications()?cpDataBean.getIntSpecifications() + "支":"无");

        if (cpDataBean.isCpSaleFlag()) {
            holder.saleBtn.setVisibility(View.GONE);
            holder.dismountBtn.setVisibility(View.VISIBLE);

        } else {
            holder.dismountBtn.setVisibility(View.GONE);
            holder.saleBtn.setVisibility(View.VISIBLE);
        }


        holder.saleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spglListOnClickListener.onClickSale(v, position, cpDataBean);
            }
        });

        holder.dismountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spglListOnClickListener.onClickDismount(v, position, cpDataBean);
            }
        });

        holder.updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spglListOnClickListener.onClickUpdate(v, position, cpDataBean);
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spglListOnClickListener.onClickDelete(v, position, cpDataBean);
            }
        });


        return convertView;
    }

    public SPGLListOnClickListener spglListOnClickListener;


    public void setCpListOnClickListener(SPGLListOnClickListener spglListOnClickListener) {
        this.spglListOnClickListener = spglListOnClickListener;
    }


    public void updataView(int posi, ListView listView, FuWuSaleBean fuWuSaleBean) {

        int visibleFirstPosi = listView.getFirstVisiblePosition();
        int visibleLastPosi = listView.getLastVisiblePosition();

        if (posi >= visibleFirstPosi && posi <= visibleLastPosi) {
            View view = listView.getChildAt(posi - visibleFirstPosi);

            notifyDataSetInvalidated();

        }

    }


    //删除单个view
    public void deleteView(int posi, ListView listView) {

        int visibleFirstPosi = listView.getFirstVisiblePosition();
        int visibleLastPosi = listView.getLastVisiblePosition();
        if (posi >= visibleFirstPosi && posi <= visibleLastPosi) {
            View view = listView.getChildAt(posi - visibleFirstPosi);
            ViewHolder holder = (ViewHolder) view.getTag();
            Log.e(TAG, "deleteView: ");
            cpDataBeans.remove(posi);
            notifyDataSetChanged();
        }
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
        @BindView(R.id.apply_specifications)
        TextView applySpecifications;
        @BindView(R.id.add_date)
        TextView addDate;
        @BindView(R.id.update_btn)
        TextView updateBtn;
        @BindView(R.id.dismount_btn)
        TextView dismountBtn;
        @BindView(R.id.sale_btn)
        TextView saleBtn;
        @BindView(R.id.delete)
        ImageView delete;
        @BindView(R.id.title_layout)
        PercentLinearLayout titleLayout;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

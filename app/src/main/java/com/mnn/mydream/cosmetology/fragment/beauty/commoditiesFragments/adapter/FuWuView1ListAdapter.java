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

import com.makeramen.roundedimageview.RoundedImageView;
import com.mnn.mydream.cosmetology.R;
import com.mnn.mydream.cosmetology.bean.Customer;
import com.mnn.mydream.cosmetology.bean.ProjectsListBean;
import com.mnn.mydream.cosmetology.bean.fuwuBean.FuWuSaleBean;
import com.mnn.mydream.cosmetology.interfaces.FuWuListOnClickListener;
import com.mnn.mydream.cosmetology.interfaces.OnItemRecyclerViewClickListener;
import com.mnn.mydream.cosmetology.utils.ImageLoader;
import com.mnn.mydream.cosmetology.utils.StringUtils;
import com.mnn.mydream.cosmetology.utils.ToastUtils;
import com.zhy.android.percent.support.PercentLinearLayout;
import com.zhy.android.percent.support.PercentRelativeLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 创建人 :MyDream
 * 创建时间：2020/5/3 18:18
 * 类描述：FuWu 服务列表
 */

public class FuWuView1ListAdapter extends BaseAdapter {

    private String TAG = "FuWuView1ListAdapter";
    private List<FuWuSaleBean> fuWuSaleBeans;
    private final Context mContext;

    public FuWuView1ListAdapter(Context mContext, List<FuWuSaleBean> fuWuSaleBeans) {
        this.mContext = mContext;
        this.fuWuSaleBeans = fuWuSaleBeans;
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.fuwu_view1_list_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        FuWuSaleBean fuWuSaleBean = fuWuSaleBeans.get(position);

        //加载图片
        ImageLoader.displayImageView(mContext, fuWuSaleBean.getServerUrl(), holder.ivServer);
        holder.serverName.setText(fuWuSaleBean.getServerName() + "");
        holder.serverMoney.setText(fuWuSaleBean.getServerMoney() + "");
        holder.applyMd.setText(fuWuSaleBean.getApplyMd() + "");
        holder.typeText.setText(fuWuSaleBean.getServerType() + "");
        holder.addDate.setText(fuWuSaleBean.getCreatedAt() + "");

        if (fuWuSaleBean.isServerSaleFlag()) {
            holder.saleBtn.setVisibility(View.GONE);
            holder.dismountBtn.setVisibility(View.VISIBLE);

        } else {
            holder.dismountBtn.setVisibility(View.GONE);
            holder.saleBtn.setVisibility(View.VISIBLE);
        }


        holder.saleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fuWuListOnClickListener.onClickSale(v, position, fuWuSaleBean);
            }
        });

        holder.dismountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fuWuListOnClickListener.onClickDismount(v, position, fuWuSaleBean);
            }
        });

        holder.updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fuWuListOnClickListener.onClickUpdate(v, position, fuWuSaleBean);
            }
        });


        return convertView;
    }

    public FuWuListOnClickListener fuWuListOnClickListener;


    public void setFuWuListOnClickListener(FuWuListOnClickListener fuWuListOnClickListener) {
        this.fuWuListOnClickListener = fuWuListOnClickListener;
    }


    public void updataView(int posi, ListView listView, FuWuSaleBean fuWuSaleBean) {

        int visibleFirstPosi = listView.getFirstVisiblePosition();
        int visibleLastPosi = listView.getLastVisiblePosition();

        if (posi >= visibleFirstPosi && posi <= visibleLastPosi) {
            View view = listView.getChildAt(posi - visibleFirstPosi);
//            ViewHolder holder = (ViewHolder) view.getTag();
            TextView serverName = view.findViewById(R.id.serverName);
            TextView serverMoney = view.findViewById(R.id.server_money);
            TextView applyMd = view.findViewById(R.id.apply_md);
            TextView typeText = view.findViewById(R.id.type_text);
            TextView addDate = view.findViewById(R.id.add_date);
            ImageView ivServer = view.findViewById(R.id.iv_server);
            //加载图片
            ImageLoader.displayImageView(mContext, fuWuSaleBean.getServerUrl(), ivServer);
            serverName.setText(fuWuSaleBean.getServerName() + "");
            serverMoney.setText(fuWuSaleBean.getServerMoney() + "");
            applyMd.setText(fuWuSaleBean.getApplyMd() + "");
            typeText.setText(fuWuSaleBean.getServerType() + "");
            addDate.setText(fuWuSaleBean.getCreatedAt() + "");

//            if (fuWuSaleBean.isServerSaleFlag()) {
//                saleBtn.setVisibility(View.GONE);
//                dismountBtn.setVisibility(View.VISIBLE);
//
//            } else {
//               dismountBtn.setVisibility(View.GONE);
//               saleBtn.setVisibility(View.VISIBLE);
//            }

            notifyDataSetInvalidated();
//            notifyDataSetChanged();
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
            fuWuSaleBeans.remove(posi);
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
        @BindView(R.id.add_date)
        TextView addDate;
        @BindView(R.id.update_btn)
        TextView updateBtn;
        @BindView(R.id.dismount_btn)
        TextView dismountBtn;
        @BindView(R.id.sale_btn)
        TextView saleBtn;
        @BindView(R.id.title_layout)
        PercentLinearLayout titleLayout;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);


        }
    }
}

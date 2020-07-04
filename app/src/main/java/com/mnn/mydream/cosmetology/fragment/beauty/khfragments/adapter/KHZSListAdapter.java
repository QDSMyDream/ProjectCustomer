package com.mnn.mydream.cosmetology.fragment.beauty.khfragments.adapter;

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
import com.mnn.mydream.cosmetology.bean.BeautyBeanKh;
import com.mnn.mydream.cosmetology.bean.fuwuBean.FuWuSaleBean;
import com.mnn.mydream.cosmetology.interfaces.FuWuListOnClickListener;
import com.mnn.mydream.cosmetology.interfaces.SetListOnClickListener;
import com.mnn.mydream.cosmetology.interfaces.SetSameDayListOnClickListener;
import com.mnn.mydream.cosmetology.utils.ImageLoader;
import com.zhy.android.percent.support.PercentLinearLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 创建人 :MyDream
 * 创建时间：2020/5/3 18:18
 * 类描述：客户总数
 */

public class KHZSListAdapter extends BaseAdapter {

    private String TAG = "FuWuView1ListAdapter";
    private List<BeautyBeanKh> beautyBeanKhs;
    private final Context mContext;

    public KHZSListAdapter(Context mContext, List<BeautyBeanKh> fuWuSaleBeans) {
        this.mContext = mContext;
        this.beautyBeanKhs = fuWuSaleBeans;
    }

    @Override
    public int getCount() {
        return beautyBeanKhs.size();
    }

    @Override
    public Object getItem(int position) {
        return beautyBeanKhs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.khzs_list_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        BeautyBeanKh beautyBeanKh = beautyBeanKhs.get(position);

        holder.name.setText(beautyBeanKh.getName());
        holder.bir.setText(beautyBeanKh.getBir());
        holder.sex.setText(beautyBeanKh.getSex());
        holder.creatTime.setText(beautyBeanKh.getCreatedAt());
        holder.phone.setText(beautyBeanKh.getPhone());
        holder.identity.setText(beautyBeanKh.getHy());
        holder.source.setText(beautyBeanKh.getLy());

        holder.updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setListOnClickListener.onClick(v, position, beautyBeanKh);
            }
        });


        holder.infoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setListOnClickListener.onClick(v, position, beautyBeanKh);
            }
        });


        return convertView;
    }

    public SetListOnClickListener setListOnClickListener;


    public void setListOnClickListener(SetListOnClickListener fuWuListOnClickListener) {
        this.setListOnClickListener = fuWuListOnClickListener;
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
            beautyBeanKhs.remove(posi);
            notifyDataSetChanged();
        }
    }


    static class ViewHolder {
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.phone)
        TextView phone;
        @BindView(R.id.select_server_all)
        PercentLinearLayout selectServerAll;
        @BindView(R.id.sex)
        TextView sex;
        @BindView(R.id.bir)
        TextView bir;
        @BindView(R.id.identity)
        TextView identity;
        @BindView(R.id.source)
        TextView source;
        @BindView(R.id.creat_time)
        TextView creatTime;
        @BindView(R.id.info_btn)
        TextView infoBtn;
        @BindView(R.id.update_btn)
        TextView updateBtn;

        @BindView(R.id.title_layout)
        PercentLinearLayout titleLayout;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

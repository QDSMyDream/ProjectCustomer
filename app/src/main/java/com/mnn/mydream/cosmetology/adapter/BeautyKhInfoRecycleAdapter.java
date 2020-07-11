package com.mnn.mydream.cosmetology.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.mnn.mydream.cosmetology.R;
import com.mnn.mydream.cosmetology.bean.Customer;
import com.mnn.mydream.cosmetology.interfaces.OnItemRecyclerViewClickListener;
import com.zhy.android.percent.support.PercentLinearLayout;
import com.zhy.android.percent.support.PercentRelativeLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.droidlover.xrecyclerview.XRecyclerView;

/**
 * 创建人 :MyDream
 * 创建时间：2020/5/3 18:18
 * 类描述：客户详情信息显示界面
 */
public class BeautyKhInfoRecycleAdapter extends XRecyclerView.Adapter {

    private String TAG = "BeautyKhInfoRecycleAdapter";
    private final Context mContext;
    private final LayoutInflater mLayoutInflater;

    public BeautyKhInfoRecycleAdapter(Context mContext) {
        this.mContext = mContext;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public XRecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new KhInfoTopViewHolder(mLayoutInflater.inflate(R.layout.beauty_kh_info_recycle_item_layout1, null));
//        return new KhInfoTopViewHolder(mLayoutInflater.inflate(R.layout.beauty_kh_info_recycle_item_layout2, null));
//        return new KhInfoTopViewHolder(mLayoutInflater.inflate(R.layout.beauty_kh_info_recycle_item_layout3, null));
    }

    @Override
    public void onBindViewHolder(XRecyclerView.ViewHolder holder, int position) {
        KhInfoTopViewHolder myholder = (KhInfoTopViewHolder) holder;

    }

    @Override
    public int getItemCount() {
        return 3;
    }


    public OnItemRecyclerViewClickListener onItemRecyclerViewClickListener;

    public void setOnItemRecyclerViewClickListener(OnItemRecyclerViewClickListener onItemRecyclerViewClickListener) {
        this.onItemRecyclerViewClickListener = onItemRecyclerViewClickListener;
    }


    /**
     * top Adapter
     */
    static class KhInfoTopViewHolder extends XRecyclerView.ViewHolder {

        @BindView(R.id.kh_name)
        TextView khName;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.top_list1)
        ListView topList1;
        @BindView(R.id.top_layout1)
        PercentRelativeLayout topLayout1;
        @BindView(R.id.top_list2)
        ListView topList2;
        @BindView(R.id.top_layout2)
        PercentLinearLayout topLayout2;
        @BindView(R.id.top_list3)
        ListView topList3;
        @BindView(R.id.top_layout3)
        PercentLinearLayout topLayout3;
        @BindView(R.id.label_text)
        TextView labelText;
        @BindView(R.id.top_list4)
        ListView topList4;
        @BindView(R.id.top_layout4)
        PercentLinearLayout topLayout4;
        @BindView(R.id.user_info_layout)
        PercentLinearLayout userInfoLayout;
        @BindView(R.id.xrecyclerview)
        XRecyclerView xrecyclerview;

        KhInfoTopViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }



}

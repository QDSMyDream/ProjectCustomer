package com.mnn.mydream.cosmetology.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.mnn.mydream.cosmetology.R;
import com.mnn.mydream.cosmetology.bean.CustomerProjectsItem;
import com.mnn.mydream.cosmetology.interfaces.OnItemRecyclerViewClickListener;
import com.mnn.mydream.cosmetology.utils.StringUtils;
import com.mnn.mydream.cosmetology.view.SwipeListLayout;
import com.zhy.android.percent.support.PercentLinearLayout;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 创建人 :MyDream
 * 创建时间：2020/5/3 18:18
 * 类描述：总记录客户记录列表适配器
 */
public class GeneralRecordYearCardRecycleAdapter extends RecyclerView.Adapter {

    private List<CustomerProjectsItem> comingslist;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private Set<SwipeListLayout> sets = new HashSet();
    View.OnClickListener onClickListenerLayout;
    View.OnLongClickListener onLongClickListenerLayout;

    public GeneralRecordYearCardRecycleAdapter(Context mContext, List<CustomerProjectsItem> comingslist, View.OnClickListener onClickListener, View.OnLongClickListener onLongClickListenerLayout) {
        this.onClickListenerLayout = onClickListener;
        this.onLongClickListenerLayout = onLongClickListenerLayout;
        this.mContext = mContext;
        this.comingslist = comingslist;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.e("onCreateViewHolder", "onCreateViewHolder: "+viewType );
        if (viewType == 0) {
            return new HeaderViewHolder(mLayoutInflater.inflate(R.layout.general_record_year_card_recycle_item_hoder2_layout, null));
        } else {

            return new RecycleViewHolder(mLayoutInflater.inflate(R.layout.general_record_year_card_item_layout, null));
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof HeaderViewHolder) {
            return;
        }
        if (holder instanceof RecycleViewHolder) {
            RecycleViewHolder myholder = (RecycleViewHolder) holder;
            myholder.setData(position);
        }


    }

    @Override
    public int getItemCount() {
        return comingslist.size();
    }


    class RecycleViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tx_roundedImageView)
        RoundedImageView txRoundedImageView;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.age)
        TextView age;
        @BindView(R.id.phone)
        TextView phone;
        @BindView(R.id.item_layout)
        PercentLinearLayout itemLayout;
        @BindView(R.id.item_projects)
        TextView itemProjects;
        @BindView(R.id.money_project)
        TextView moneyProject;
        @BindView(R.id.item_money)
        TextView itemMoney;
        @BindView(R.id.is_stages)
        TextView isStages;
        @BindView(R.id.stages_money)
        TextView stagesMoney;
        @BindView(R.id.year_card)
        TextView yearCard;
        @BindView(R.id.item_peojects_start_time)
        TextView itemPeojectsStartTime;
        @BindView(R.id.item_peojects_end_time)
        TextView itemPeojectsEndTime;
        @BindView(R.id.swipelistlayout)
        PercentLinearLayout swipelistlayout;
        @BindView(R.id.time)
        TextView time;

        RecycleViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void setData(int position) {

            CustomerProjectsItem costorBean = comingslist.get(position);
            itemProjects.setText(costorBean.getCustomerProjectBean().getcProjects());
            moneyProject.setText(costorBean.getcMoney() + "");
            itemMoney.setText(costorBean.getcStagesMoney() + "");
            stagesMoney.setText(costorBean.getcStagesSurplusMoney() + "");
            yearCard.setText(costorBean.iscCardYearFlag() ? "是" : "否");
            itemPeojectsStartTime.setText(costorBean.getcStartTime());
            itemPeojectsEndTime.setText(costorBean.getcEndTime());
//            time.setText(costorBean.getCome_time());
//            holder.count.setText(costorBean.getTotal_count() + "");
//            holder.item_txt1.setText(costorBean.getmTxt1());
//            holder.item_txt2.setText(costorBean.getmTxt2());
            itemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemRecyclerViewClickListener.onItemOnClickListener(v, position);
                }
            });
            itemLayout.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onItemRecyclerViewClickListener.onItemLongOnClickListener(v, position);

                    return false;
                }
            });

        }

        public String setAgeSize(String age) {
            String ages = "";
            if (StringUtils.isEmpty(age)) {
                return ages;
            }
            if (age.length() == 1) {
                ages = "0" + age;
                return ages;
            } else {
                return age;
            }
        }


    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {

        HeaderViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? 0 : 1;
    }

    public OnItemRecyclerViewClickListener onItemRecyclerViewClickListener;

    public void setOnItemRecyclerViewClickListener(OnItemRecyclerViewClickListener onItemRecyclerViewClickListener) {
        this.onItemRecyclerViewClickListener = onItemRecyclerViewClickListener;
    }


}


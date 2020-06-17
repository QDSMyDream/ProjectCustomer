package com.mnn.mydream.cosmetology.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.mnn.mydream.cosmetology.R;
import com.mnn.mydream.cosmetology.bean.CustomerProjectsItem;
import com.mnn.mydream.cosmetology.interfaces.OnItemRecyclerViewClickListener;
import com.mnn.mydream.cosmetology.utils.ImageLoader;
import com.zhy.android.percent.support.PercentLinearLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 创建人 :MyDream
 * 创建时间：2020/5/3 18:18
 * 类描述：办卡列表适配器
 */

public class GeneralRecordCardListAdapter extends BaseAdapter {
    private String TAG = "GeneralRecordCardListAdapter";
    private final List<CustomerProjectsItem> comingslist;
    private final Context mContext;
    private final LayoutInflater mLayoutInflater;

    public GeneralRecordCardListAdapter(Context mContext, List<CustomerProjectsItem> comingslist) {

        this.mContext = mContext;
        this.comingslist = comingslist;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return comingslist.size();
    }

    @Override
    public Object getItem(int position) {
        return comingslist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.general_record_card_item_layout, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        CustomerProjectsItem costorBean = comingslist.get(position);

        //如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
        if (position == getPositionForSection(costorBean.getCustomer().getName())) {

            holder.itemLayout1.setVisibility(View.VISIBLE);
            holder.itemLayout2.setVisibility(View.VISIBLE);
            //加载图片
            ImageLoader.displayImageView(mContext,costorBean.getCustomer().getCustomer_tx(),holder.txRoundedImageView);

            holder.name.setText(costorBean.getCustomer().getName());
            holder.sex.setText(costorBean.getCustomer().getSex());
            holder.phone.setText(costorBean.getCustomer().getPhone());
            holder.age.setText(costorBean.getCustomer().getAge() + "");
            holder.time.setText(costorBean.getCreatedAt());
            holder.lines1.setVisibility(View.VISIBLE);
            holder.lines2.setVisibility(View.VISIBLE);
        } else {
            holder.itemLayout1.setVisibility(View.GONE);
            holder.itemLayout2.setVisibility(View.GONE);
            holder.lines1.setVisibility(View.GONE);
            holder.lines2.setVisibility(View.GONE);
        }

        holder.itemProjects.setText(costorBean.getCustomerProjectBean().getcProjects());
        holder.moneyProject.setText(costorBean.getcMoney() + "");
        holder.isCard.setText(costorBean.iscCardFlag() ? "是" : "否");
        holder.itemMoney.setText(costorBean.getcStagesMoney() + "");
        holder.useCount.setText(costorBean.getcUseCount() + "");
        holder.tCount.setText(costorBean.getcCount() + "");
        holder.itemPeojectsStartTime.setText(costorBean.getcStartTime());
        holder.itemPeojectsUpdateTime.setText(costorBean.getUpdatedAt());

//        holder.itemLayout1.setVisibility(View.VISIBLE);
//        if (costorBean.isStages()) {
//            holder.itemLayout.setBackgroundResource(R.drawable.list_stages_lelector);
//        } else {
//            holder.itemLayout.setBackgroundResource(R.drawable.list_bg_lelector);
//
//        }
        holder.itemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemRecyclerViewClickListener.onItemOnClickListener(v, position);
            }
        });
        holder.itemLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onItemRecyclerViewClickListener.onItemLongOnClickListener(v, position);
                return false;
            }
        });
        return convertView;
    }

    public OnItemRecyclerViewClickListener onItemRecyclerViewClickListener;

    public void setOnItemRecyclerViewClickListener(OnItemRecyclerViewClickListener onItemRecyclerViewClickListener) {
        this.onItemRecyclerViewClickListener = onItemRecyclerViewClickListener;
    }

    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     */
    public int getPositionForSection(String s) {
        for (int i = 0; i < getCount(); i++) {
            String sortStr = comingslist.get(i).getCustomer().getName();
            if (sortStr.equals(s)) {
                return i;
            }
        }
        return -1;
    }

    static class ViewHolder {
        @BindView(R.id.tx_roundedImageView)
        RoundedImageView txRoundedImageView;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.sex)
        TextView sex;
        @BindView(R.id.age)
        TextView age;
        @BindView(R.id.phone)
        TextView phone;
        @BindView(R.id.item_layout1)
        PercentLinearLayout itemLayout1;
        @BindView(R.id.item_layout2)
        PercentLinearLayout itemLayout2;
        @BindView(R.id.item_projects)
        TextView itemProjects;
        @BindView(R.id.money_project)
        TextView moneyProject;
        @BindView(R.id.is_card)
        TextView isCard;
        @BindView(R.id.item_money)
        TextView itemMoney;
        @BindView(R.id.use_count)
        TextView useCount;
        @BindView(R.id.t_count)
        TextView tCount;
        @BindView(R.id.item_peojects_start_time)
        TextView itemPeojectsStartTime;
        @BindView(R.id.item_peojects_update_time)
        TextView itemPeojectsUpdateTime;
        @BindView(R.id.item_layout)
        PercentLinearLayout itemLayout;
        @BindView(R.id.swipelistlayout)
        PercentLinearLayout swipelistlayout;
        @BindView(R.id.lines1)
        View lines1;
        @BindView(R.id.lines2)
        View lines2;

        @BindView(R.id.time)
        TextView time;


        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

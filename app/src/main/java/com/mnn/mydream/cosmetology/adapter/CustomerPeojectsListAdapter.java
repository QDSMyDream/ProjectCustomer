package com.mnn.mydream.cosmetology.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.mnn.mydream.cosmetology.R;
import com.mnn.mydream.cosmetology.bean.CustomerProjectsItem;
import com.zhy.android.percent.support.PercentLinearLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 创建人 :MyDream
 * 创建时间：2020/5/3 18:18
 * 类描述：今日添加客户记录列表适配器
 */

public class CustomerPeojectsListAdapter extends BaseAdapter {

    private String TAG = "CustomerPeojectsListAdapter";

    private List<CustomerProjectsItem> mList;

    private Context mContext;

    public CustomerPeojectsListAdapter(Context context, List<CustomerProjectsItem> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.customer_projects_list_item, null);
            holder = new ViewHolder(convertView);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (mList.size() > position) {

            CustomerProjectsItem customerProjectsItem = mList.get(position);

            holder.itemProjects.setText(customerProjectsItem.getCustomerProjectBean().getcProjects());

            holder.yearCard.setText(customerProjectsItem.iscCardYearFlag() ? "是" : "否");//是否年卡
            holder.isStages.setText(customerProjectsItem.isStages() ? "是" : "否");//是否分期
            holder.isSign.setText(customerProjectsItem.iscSignFlag() ? "是" : "否");//是否签字
            holder.stagesMoney.setText("无");//分期
            holder.isCard.setText(customerProjectsItem.iscCardFlag() ? "是" : "否");//是否办卡
            holder.useCount.setText("无");
            holder.toCount.setText("无");
            holder.itemPeojectsEndTime.setText("无");
            holder.itemMoney.setTextColor(mContext.getResources().getColor(R.color.primary));
            holder.itemMoney.setText(customerProjectsItem.getcStagesMoney() + "");

            holder.itemPeojectsStartTime.setText(customerProjectsItem.getcStartTime());
            holder.moneyProject.setText(customerProjectsItem.getcMoney() + "");


            //是否签名
            if (customerProjectsItem.iscSignFlag()) {
                holder.isSign.setTextColor(mContext.getResources().getColor(R.color.text_board));
            }

            //是否是年卡
            if (customerProjectsItem.iscCardYearFlag()) {
                holder.itemPeojectsEndTime.setTextColor(mContext.getResources().getColor(R.color.primary));
                holder.itemPeojectsEndTime.setText(customerProjectsItem.getcEndTime());
                holder.yearCard.setTextColor(mContext.getResources().getColor(R.color.primary));
            }

            //是否是次卡
            if (customerProjectsItem.iscCardFlag()) {
                holder.useCount.setText(customerProjectsItem.getcUseCount() + "");
                holder.toCount.setText(customerProjectsItem.getcCount() + "");
                holder.toCount.setTextColor(mContext.getResources().getColor(R.color.primary));
                holder.isCard.setTextColor(mContext.getResources().getColor(R.color.primary));
            }

            //是否是分期
            if (customerProjectsItem.isStages()) {
                holder.stagesMoney.setText(customerProjectsItem.getcStagesSurplusMoney() + "");
                holder.stagesMoney.setTextColor(mContext.getResources().getColor(R.color.primary));
                holder.isStages.setTextColor(mContext.getResources().getColor(R.color.primary));

            }

        }


        return convertView;
    }


    //更新列表
    public void updataView(int posi, ListView listView, CustomerProjectsItem customerProjectsItem) {

        int visibleFirstPosi = listView.getFirstVisiblePosition();
        int visibleLastPosi = listView.getLastVisiblePosition();
        if (posi >= visibleFirstPosi && posi <= visibleLastPosi) {
            View view = listView.getChildAt(posi - visibleFirstPosi);
            ViewHolder holder = (ViewHolder) view.getTag();

            holder.itemProjects.setText(customerProjectsItem.getCustomerProjectBean().getcProjects());
            holder.yearCard.setText(customerProjectsItem.iscCardYearFlag() ? "是" : "否");//是否年卡
            holder.isStages.setText(customerProjectsItem.isStages() ? "是" : "否");//是否分期
            holder.isSign.setText(customerProjectsItem.iscSignFlag() ? "是" : "否");//是否签字
            holder.stagesMoney.setText("无");//分期
            holder.isCard.setText(customerProjectsItem.iscCardFlag() ? "是" : "否");//是否办卡
            holder.useCount.setText("无");
            holder.toCount.setText("无");
            holder.itemPeojectsEndTime.setText("无");
            holder.itemMoney.setTextColor(mContext.getResources().getColor(R.color.primary));
            holder.itemMoney.setText(customerProjectsItem.getcStagesMoney() + "");

            holder.itemPeojectsStartTime.setText(customerProjectsItem.getcStartTime());
            holder.moneyProject.setText(customerProjectsItem.getcMoney() + "");


            //是否签名
            if (customerProjectsItem.iscSignFlag()) {
                holder.isSign.setTextColor(mContext.getResources().getColor(R.color.text_board));
            }

            //是否是年卡
            if (customerProjectsItem.iscCardYearFlag()) {
                holder.itemPeojectsEndTime.setTextColor(mContext.getResources().getColor(R.color.primary));
                holder.itemPeojectsEndTime.setText(customerProjectsItem.getcEndTime());
                holder.yearCard.setTextColor(mContext.getResources().getColor(R.color.primary));
            }

            //是否是次卡
            if (customerProjectsItem.iscCardFlag()) {
                holder.useCount.setText(customerProjectsItem.getcUseCount() + "");
                holder.toCount.setText(customerProjectsItem.getcCount() + "");
                holder.toCount.setTextColor(mContext.getResources().getColor(R.color.primary));
                holder.isCard.setTextColor(mContext.getResources().getColor(R.color.primary));
            }

            //是否是分期
            if (customerProjectsItem.isStages()) {
                holder.stagesMoney.setText(customerProjectsItem.getcStagesSurplusMoney() + "");
                holder.stagesMoney.setTextColor(mContext.getResources().getColor(R.color.primary));
                holder.isStages.setTextColor(mContext.getResources().getColor(R.color.primary));

            }

            notifyDataSetInvalidated();

//  notifyDataSetChanged();

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

            mList.remove(posi);
            notifyDataSetChanged();
        }
    }


    static class ViewHolder {
        @BindView(R.id.item_projects)
        TextView itemProjects;
        @BindView(R.id.money_project)
        TextView moneyProject;
        @BindView(R.id.is_card)
        TextView isCard;
        @BindView(R.id.use_count)
        TextView useCount;
        @BindView(R.id.to_count)
        TextView toCount;
        @BindView(R.id.is_stages)
        TextView isStages;
        @BindView(R.id.item_money)
        TextView itemMoney;
        @BindView(R.id.stages_money)
        TextView stagesMoney;
        @BindView(R.id.year_card)
        TextView yearCard;
        @BindView(R.id.item_peojects_start_time)
        TextView itemPeojectsStartTime;
        @BindView(R.id.item_peojects_end_time)
        TextView itemPeojectsEndTime;
        @BindView(R.id.is_sign)
        TextView isSign;
        @BindView(R.id.content_layout)
        PercentLinearLayout contentLayout;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

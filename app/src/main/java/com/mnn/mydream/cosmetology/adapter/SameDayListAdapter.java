package com.mnn.mydream.cosmetology.adapter;

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
import com.mnn.mydream.cosmetology.bean.CustomerAndProject;
import com.mnn.mydream.cosmetology.utils.ImageLoader;
import com.mnn.mydream.cosmetology.utils.StringUtils;
import com.mnn.mydream.cosmetology.utils.ToastUtils;
import com.mnn.mydream.cosmetology.view.OnSlipStatusListener;
import com.mnn.mydream.cosmetology.view.SwipeListLayout;
import com.zhy.android.percent.support.PercentLinearLayout;
import com.zhy.android.percent.support.PercentRelativeLayout;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 创建人 ： MyDream
 * 创建时间：2020/5/3 18:18
 * 类描述：今日添加客户记录列表适配器
 */

public class SameDayListAdapter extends BaseAdapter {

    private String TAG = "SameDayListAdapter";
    private List<CustomerAndProject> mList;

    private Context mContext;
    private Set<SwipeListLayout> sets = new HashSet();

    View.OnClickListener onClickListenerLayout;
    View.OnLongClickListener onLongClickListenerLayout;
    String s = "";
    String countNoSign = "";

    public SameDayListAdapter(Context context, List<CustomerAndProject> list, View.OnClickListener onClickListenerLayout, View.OnLongClickListener onLongClickListener) {

        this.mContext = context;
        this.mList = list;
        this.onClickListenerLayout = onClickListenerLayout;
        this.onLongClickListenerLayout = onLongClickListener;

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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.same_day_list_item_layout, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);

//            AbsListView.LayoutParams param = new AbsListView.LayoutParams(
//                    ViewGroup.LayoutParams.MATCH_PARENT, parent.getHeight() / 10);
//            convertView.setLayoutParams(param);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Log.e(TAG, "getView:mList " + mList.size());
        if (mList.size() > position) {

            CustomerAndProject customerAndProject = mList.get(position);

            Customer costorBean = mList.get(position).getCustomer();

            holder.name.setText(costorBean.getName());

            //加载图片
            ImageLoader.displayImageView(mContext,costorBean.getCustomer_tx(),holder.txRoundedImageView);

            holder.projectsTitle.setText(costorBean.getPhone());
            holder.sex.setText(costorBean.getSex());
            holder.age.setText(setAgeSize(costorBean.getAge() + ""));
            holder.time.setText(costorBean.getCreatedAt());
            holder.swipeListLayout.setOnSwipeStatusListener(new OnSlipStatusListener(holder.swipeListLayout, sets));
            holder.mTvTop.setOnClickListener(onClickListenerLayout);
            holder.mTvDelete.setOnClickListener(onClickListenerLayout);
            holder.mTvSign.setOnClickListener(onClickListenerLayout);
            holder.itemLayout.setOnClickListener(onClickListenerLayout);
            holder.itemLayout.setOnLongClickListener(onLongClickListenerLayout);

            holder.projectsImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtils.showToast(mContext, "这是电话图标点击", true);
                }
            });


            if (customerAndProject.getProjectsTexts() != null) {
                s = "";
                countNoSign = "";
                holder.signLayout.setVisibility(View.VISIBLE);

                if (customerAndProject.getProjectsTexts().size() == 0) {
                    holder.projectsText.setText("暂无服务记录");
                    holder.signLayout.setVisibility(View.INVISIBLE);
                    holder.mTvSign.setVisibility(View.GONE);

                } else if (customerAndProject.getProjectsTexts().size() > 0) {

                    Log.e(TAG, "getView: " + customerAndProject.getProjectsTexts().size());

                    for (int i = 0; i < customerAndProject.getProjectsTexts().size(); i++) {

                        s = s + customerAndProject.getProjectsTexts().get(i).getCustomerProjectBean().getcProjects() + "、";

                        if (!customerAndProject.getProjectsTexts().get(i).iscSignFlag()) {
                            countNoSign = customerAndProject.getProjectsTexts().get(i).getCustomerProjectBean().getcProjects();
                        }
                    }

                    Log.e(TAG, "getView: " + countNoSign);
                    if (!countNoSign.equals("")) {
                        holder.signLayout.setVisibility(View.VISIBLE);
                        holder.tvSignText.setTextColor(mContext.getResources().getColor(R.color.primary));
                        holder.tvSignText.setText("项目<" + countNoSign + ">未签字");
                        holder.mTvSign.setVisibility(View.VISIBLE);
                    } else {
                        holder.tvSignText.setTextColor(mContext.getResources().getColor(R.color.text_board));
                        holder.tvSignText.setText("已签字");
                        holder.tvSignCount.setText(countNoSign);
                        holder.mTvSign.setVisibility(View.GONE);
                    }
                    holder.projectsText.setText(s.substring(0, s.length() - 1));
                }
            } else {
                holder.projectsText.setText("暂无服务记录");
                holder.signLayout.setVisibility(View.INVISIBLE);
                holder.mTvSign.setVisibility(View.GONE);
            }
        }
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tv_sign)
        TextView mTvSign;
        @BindView(R.id.tv_top)
        TextView mTvTop;
        @BindView(R.id.tv_delete)
        TextView mTvDelete;
        @BindView(R.id.tx_roundedImageView)
        RoundedImageView txRoundedImageView;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.sex)
        TextView sex;
        @BindView(R.id.age)
        TextView age;
        @BindView(R.id.projects_title)
        TextView projectsTitle;
        @BindView(R.id.projects_img)
        ImageView projectsImg;
        @BindView(R.id.projects_text)
        TextView projectsText;
        @BindView(R.id.time)
        TextView time;
        @BindView(R.id.tv_sign_count)
        TextView tvSignCount;
        @BindView(R.id.swipelistlayout)
        SwipeListLayout swipeListLayout;
        @BindView(R.id.item_layout)
        PercentLinearLayout itemLayout;
        @BindView(R.id.tv_sign_text)
        TextView tvSignText;
        @BindView(R.id.sign_layout)
        PercentRelativeLayout signLayout;

        @BindView(R.id.slip_layout)
        PercentLinearLayout slipLayout;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public void updataView(int posi, ListView listView, CustomerAndProject customer) {

        int visibleFirstPosi = listView.getFirstVisiblePosition();
        int visibleLastPosi = listView.getLastVisiblePosition();
        if (posi >= visibleFirstPosi && posi <= visibleLastPosi) {
            View view = listView.getChildAt(posi - visibleFirstPosi);
            ViewHolder holder = (ViewHolder) view.getTag();
            if (customer.getProjectsTexts() != null) {
                s = "";
                countNoSign = "";
                holder.signLayout.setVisibility(View.VISIBLE);

                if (customer.getProjectsTexts().size() == 0) {
                    holder.projectsText.setText("暂无服务记录");
                    holder.signLayout.setVisibility(View.INVISIBLE);
                    holder.mTvSign.setVisibility(View.GONE);
                } else if (customer.getProjectsTexts().size() > 0) {

                    Log.e(TAG, "getView: " + customer.getProjectsTexts().size());

                    for (int i = 0; i < customer.getProjectsTexts().size(); i++) {

                        s = s + customer.getProjectsTexts().get(i).getCustomerProjectBean().getcProjects() + "、";

                        if (!customer.getProjectsTexts().get(i).iscSignFlag()) {
                            countNoSign = (i + 1) + "、";
                        }
                    }

                    Log.e(TAG, "getView: " + countNoSign);
                    if (!countNoSign.equals("")) {
                        holder.signLayout.setVisibility(View.VISIBLE);
                        holder.tvSignText.setTextColor(mContext.getResources().getColor(R.color.primary));
                        holder.tvSignText.setText("项目" + countNoSign.substring(0, countNoSign.length() - 1) + "未签字");
                        holder.mTvSign.setVisibility(View.VISIBLE);
                    } else {
                        holder.tvSignText.setTextColor(mContext.getResources().getColor(R.color.text_board));
                        holder.tvSignText.setText("已签字");
                        holder.tvSignCount.setText(countNoSign);
                        holder.mTvSign.setVisibility(View.GONE);
                    }
                    holder.projectsText.setText(s.substring(0, s.length() - 1));
                }

            } else {
                holder.projectsText.setText("暂无服务记录");
                holder.signLayout.setVisibility(View.INVISIBLE);
                holder.mTvSign.setVisibility(View.GONE);
            }
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
//            if (customer.isSignFlag()) {
//                holder.tvSignText.setTextColor(mContext.getResources().getColor(R.color.text_board));
//                holder.tvSignText.setText("已签字");
//            } else {
//                holder.tvSignText.setTextColor(mContext.getResources().getColor(R.color.primary));
//                holder.tvSignText.setText("未签字");
//            }
            mList.remove(posi);
            notifyDataSetChanged();
        }
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

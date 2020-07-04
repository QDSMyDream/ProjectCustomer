package com.mnn.mydream.cosmetology.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.mnn.mydream.cosmetology.R;
import com.mnn.mydream.cosmetology.bean.ProjectsListBean;
import com.mnn.mydream.cosmetology.interfaces.OnItemRecyclerViewClickListener;
import com.zhy.android.percent.support.PercentLinearLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 创建人 ： MyDream
 * 创建时间：2020/5/3 18:18
 * 类描述：今日添加客户记录列表适配器
 */

public class ProjectsShowListAdapter extends BaseAdapter implements SectionIndexer {

    private String TAG = "ProjectsShowListAdapter";
    private List<ProjectsListBean> mList;

    private Context mContext;


    public ProjectsShowListAdapter(Context context, List<ProjectsListBean> list) {
        this.mContext = context;
        this.mList = list;

    }

    /**
     * 当ListView数据发生变化时,调用此方法来更新ListView
     *
     * @param list
     */
    public void updateListView(List<ProjectsListBean> list) {
        this.mList = list;
        notifyDataSetChanged();
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
        final ProjectsListBean projectsListBean = mList.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.projects_show_list_item_layout, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);

//            AbsListView.LayoutParams param = new AbsListView.LayoutParams(
//                    ViewGroup.LayoutParams.MATCH_PARENT, parent.getHeight() / 10);
//            convertView.setLayoutParams(param);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        //根据position获取分类的首字母的char ascii值
        int section = getSectionForPosition(position);

        //如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
        if (position == getPositionForSection(section)) {
            holder.catalog.setVisibility(View.VISIBLE);
            holder.catalog.setText(projectsListBean.getpField());
        } else {
            holder.catalog.setVisibility(View.GONE);
        }

        holder.peojectsManager.setText(projectsListBean.getCustomerProject().getcProjects());
        holder.id.setText((position + 1) + "、");
        holder.startDate.setText(projectsListBean.getCustomerProject().getCreatedAt());

//
//        if (projectsListBean.getPeojectsCount() >= 1 && projectsListBean.getPeojectsCount() < 3) {
//            holder.count.setTextColor(mContext.getResources().getColor(R.color.text_board));
//        }
//        if (projectsListBean.getPeojectsCount() >= 3 && projectsListBean.getPeojectsCount() < 6) {
//            holder.count.setTextColor(mContext.getResources().getColor(R.color.info));
//        }
//
//        if (projectsListBean.getPeojectsCount() >= 6 && projectsListBean.getPeojectsCount() < 9) {
//            holder.count.setTextColor(mContext.getResources().getColor(R.color.blue));
//        }
//
//        if (projectsListBean.getPeojectsCount() > 9) {
//            holder.count.setTextColor(mContext.getResources().getColor(R.color.primary_darker));
//        }

        holder.count.setText(projectsListBean.getPeojectsCount() + "");


        holder.upDate.setText(
                projectsListBean.getCustomerProject().getUpdatedAt() == null
                        || projectsListBean.getCustomerProject().getUpdatedAt().equals("")
                        ? projectsListBean.getCustomerProject().getCreatedAt()
                        : projectsListBean.getCustomerProject().getUpdatedAt());
        holder.titleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemRecyclerViewClickListener.onItemOnClickListener(v, position);
            }
        });
        holder.titleLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onItemRecyclerViewClickListener.onItemLongOnClickListener(v, position);
                return true;
            }
        });

        return convertView;
    }


    public void updataView(int posi, ListView listView, ProjectsListBean projectsListBean) {

        Log.e(TAG, "updataView: "+posi );
        Log.e(TAG, "updataView: "+projectsListBean.toString() );
        int visibleFirstPosi = listView.getFirstVisiblePosition();
        int visibleLastPosi = listView.getLastVisiblePosition();
        if (posi >= visibleFirstPosi && posi <= visibleLastPosi) {
            View view = listView.getChildAt(posi - visibleFirstPosi);
            ViewHolder holder = (ViewHolder) view.getTag();

            Log.e(TAG, "updataView: "+view.getId());
            Log.e(TAG, "updataView: "+holder.peojectsManager);
            holder.peojectsManager.setText(projectsListBean.getCustomerProject().getcProjects());
            holder.startDate.setText(projectsListBean.getCustomerProject().getCreatedAt());
            holder.upDate.setText(projectsListBean.getCustomerProject().getUpdatedAt());

            //根据position获取分类的首字母的char ascii值
            int section = getSectionForPosition(posi);

            //如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
            if (posi == getPositionForSection(section)) {
                holder.catalog.setVisibility(View.VISIBLE);
                holder.catalog.setText(projectsListBean.getpField());
            } else {
                holder.catalog.setVisibility(View.GONE);
            }

            notifyDataSetInvalidated();
//            notifyDataSetChanged();
        }

    }

    public void addView(ProjectsListBean projectsListBean) {

        mList.add(projectsListBean);


        notifyDataSetChanged();

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


    static class ViewHolder {
        @BindView(R.id.id)
        TextView id;
        @BindView(R.id.peojects_manager)
        TextView peojectsManager;
        @BindView(R.id.start_date)
        TextView startDate;
        @BindView(R.id.up_date)
        TextView upDate;
        @BindView(R.id.count)
        TextView count;
        @BindView(R.id.title_layout)
        PercentLinearLayout titleLayout;

        @BindView(R.id.catalog)
        TextView catalog;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    /**
     * 根据ListView的当前位置获取分类的首字母的Char ascii值
     */
    @Override
    public int getSectionForPosition(int position) {
        return mList.get(position).getpField().charAt(0);
    }


    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     */
    @Override
    public int getPositionForSection(int section) {
        for (int i = 0; i < getCount(); i++) {
            String sortStr = mList.get(i).getpField();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }

        return -1;
    }

    /**
     * 提取英文的首字母，非英文字母用#代替。
     *
     * @param str
     * @return
     */
    private String getAlpha(String str) {
        String sortStr = str.trim().substring(0, 1).toUpperCase();
        // 正则表达式，判断首字母是否是英文字母
        if (sortStr.matches("[A-Z]")) {
            return sortStr;
        } else {
            return "#";
        }
    }

    @Override
    public Object[] getSections() {
        return null;
    }


    public OnItemRecyclerViewClickListener onItemRecyclerViewClickListener;

    public void setOnItemRecyclerViewClickListener(OnItemRecyclerViewClickListener onItemRecyclerViewClickListener) {
        this.onItemRecyclerViewClickListener = onItemRecyclerViewClickListener;
    }
}

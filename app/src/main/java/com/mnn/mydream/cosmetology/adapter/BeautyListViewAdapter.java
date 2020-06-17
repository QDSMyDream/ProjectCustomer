package com.mnn.mydream.cosmetology.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mnn.mydream.cosmetology.R;
import com.mnn.mydream.cosmetology.bean.BeautyListItemBean;
import com.zhy.android.percent.support.PercentLinearLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BeautyListViewAdapter extends BaseAdapter {
    private String TAG = "BeautyListViewAdapter";
    private Context context;
    private List<BeautyListItemBean> beautyListItemBeans;
    private View.OnClickListener onClickListener;
    public static int BEAUTY_SELECT_ITEM = 0;

    public BeautyListViewAdapter(Context context, List<BeautyListItemBean> beautyListItemBeans, View.OnClickListener onClickListener) {
        this.beautyListItemBeans = beautyListItemBeans;
        this.context = context;
        this.onClickListener = onClickListener;
    }

    @Override
    public int getCount() {
        return beautyListItemBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return beautyListItemBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.beauty_listview_item_layout, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        BeautyListItemBean beautyListItemBean = beautyListItemBeans.get(position);

        holder.menuString.setText(beautyListItemBean.getMenuString());

        if (position == BEAUTY_SELECT_ITEM) {
            holder.slipLayout.setBackgroundResource(R.color.beauty_left_select_bg);
            holder.menuImg.setImageResource(beautyListItemBean.getImgSelect());
            holder.menuString.setTextColor(context.getResources().getColor(beautyListItemBean.getMenuSelectFontColor()));

        } else {
            holder.slipLayout.setBackground(null);
            holder.menuImg.setImageResource(beautyListItemBean.getImg());
            holder.menuString.setTextColor(context.getResources().getColor(beautyListItemBean.getMenuFontColor()));
        }
        holder.slipLayout.setOnClickListener(onClickListener);

//        holder.projectTime.setText(customerProjects.getcProjectsStartTime());

        return convertView;
    }


    static class ViewHolder {
        @BindView(R.id.menu_img)
        ImageView menuImg;
        @BindView(R.id.menu_string)
        TextView menuString;
        @BindView(R.id.slip_layout)
        PercentLinearLayout slipLayout;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
package com.mnn.mydream.cosmetology.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mnn.mydream.cosmetology.R;
import com.mnn.mydream.cosmetology.bean.BeautyListItemBean;
import com.zhy.android.percent.support.PercentLinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
/**
 * 创建人 :MyDream
 * 创建时间：2020/5/3 18:18
 * 类描述：BeautyWithinActivity 适配左侧菜单
 */

public class BeautyWithinListViewAdapter extends BaseAdapter {
    private String TAG = "BeautyWithinListViewAdapter";
    private Context context;
    private String[] strings;
    private View.OnClickListener onClickListener;
    public static int BEAUTY_SELECT_ITEM = 0;

    public BeautyWithinListViewAdapter(Context context, String[] strings, View.OnClickListener onClickListener) {
        this.strings = strings;
        this.context = context;
        this.onClickListener = onClickListener;
    }

    @Override
    public int getCount() {
        return strings.length;
    }

    @Override
    public Object getItem(int position) {
        return strings[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.beauty_within_listview_item_layout, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.menuString.setText(strings[position]);


        if (position == BEAUTY_SELECT_ITEM) {
            holder.slipLayout.setBackgroundResource(R.color.beauty_within_select_font_color);
            holder.menuString.setTextColor(context.getResources().getColor(R.color.beauty_within_select_color));

        } else {
            holder.slipLayout.setBackgroundResource(R.color.white);
            holder.menuString.setTextColor(context.getResources().getColor(R.color.beauty_within_font_color));
        }


        holder.slipLayout.setOnClickListener(onClickListener);

        if(strings[position].equals("充值")||strings[position].equals("消费")||strings[position].equals("消耗")||strings[position].equals("其它")){
            holder.menuString.setTextColor(context.getResources().getColor(R.color.beauty_within_title_color));
            holder.slipLayout.setOnClickListener(null);
        }

        return convertView;
    }


    static class ViewHolder {

        @BindView(R.id.menu_string)
        TextView menuString;
        @BindView(R.id.slip_layout)
        PercentLinearLayout slipLayout;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
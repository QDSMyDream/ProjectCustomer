package com.mnn.mydream.cosmetology.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mnn.mydream.cosmetology.R;
import com.mnn.mydream.cosmetology.bean.khBean.BeautyListBeanKh;
import com.zhy.android.percent.support.PercentRelativeLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 创建人 :MyDream
 * 创建时间：2020/5/3 18:18
 * 类描述：客户详情信息显示界面
 */
public class BeautyKhInfoListAdapter extends BaseAdapter {

    private String TAG = "BeautyKhInfoListAdapter";
    private Context context;
    private List<BeautyListBeanKh> beautyListBeanKhs;

    private int flagInt;

    public BeautyKhInfoListAdapter(Context context, List<BeautyListBeanKh> beautyListBeanKhs, int flagInt) {
        switch (flagInt) {
            case 1:
                this.beautyListBeanKhs = beautyListBeanKhs.subList(0, 3);
                break;
            case 2:
                this.beautyListBeanKhs = beautyListBeanKhs.subList(3, 7);

                break;
            case 3:
                this.beautyListBeanKhs = beautyListBeanKhs.subList(7, 11);
                break;
        }
        this.flagInt = flagInt;
        this.context = context;
    }

    @Override
    public int getCount() {
        return beautyListBeanKhs.size();
    }

    @Override
    public Object getItem(int position) {
        return beautyListBeanKhs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.beauty_kh_info_list_item_layout, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        BeautyListBeanKh beautyListBeanKh = beautyListBeanKhs.get(position);
        holder.str1.setText(beautyListBeanKh.getTitleStr());
        holder.str2.setText(beautyListBeanKh.getDataStr());

        ///设置gridview每個子项的高度和宽度
        AbsListView.LayoutParams param = new AbsListView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, parent.getHeight() / 4);
        convertView.setLayoutParams(param);
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.str1)
        TextView str1;
        @BindView(R.id.str2)
        TextView str2;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

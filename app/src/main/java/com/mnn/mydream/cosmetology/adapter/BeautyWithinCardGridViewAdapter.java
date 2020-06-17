package com.mnn.mydream.cosmetology.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mnn.mydream.cosmetology.R;
import com.mnn.mydream.cosmetology.bean.BeautyWithinCardsBean;
import com.zhy.android.percent.support.PercentLinearLayout;
import com.zhy.android.percent.support.PercentRelativeLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 创建人 :MyDream
 * 创建时间：2020/5/3 18:18
 * 类描述：卡包gradview
 */
public class BeautyWithinCardGridViewAdapter extends BaseAdapter {

    private String TAG = "BeautyWithinCardGridViewAdapter";

    private Context mContext;
    private List<BeautyWithinCardsBean> beautyWithinCardsBeans;



    public BeautyWithinCardGridViewAdapter(Context context, List<BeautyWithinCardsBean> list) {
        this.mContext = context;
        this.beautyWithinCardsBeans = list;

        Log.e(TAG, "BeautyWithinCardGridViewAdapter: " + list.size());
    }


    public void setActivityManageBean(List<BeautyWithinCardsBean> list) {
        this.beautyWithinCardsBeans = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return beautyWithinCardsBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return beautyWithinCardsBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.layouts, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        if (beautyWithinCardsBeans.size() > position) {
            viewHolder.titleText.setText(beautyWithinCardsBeans.get(position).getCardNameString());
            viewHolder.contentText1.setText(beautyWithinCardsBeans.get(position).getCardContentZDDCString());
            viewHolder.contentText2.setText(beautyWithinCardsBeans.get(position).getCardContentCXKString());

            if (beautyWithinCardsBeans.get(position).getCardGradeInt() == 0) {

                viewHolder.layoutItem.setBackgroundResource(R.drawable.beauty_within_gridview_item_layout2);
            } else {
                viewHolder.layoutItem.setBackgroundResource(R.drawable.beauty_within_gridview_item_layout);

            }


        }


        return convertView;
    }


    static class ViewHolder {
        @BindView(R.id.title_text)
        TextView titleText;
        @BindView(R.id.tip1)
        TextView tip1;
        @BindView(R.id.content_text1)
        TextView contentText1;
        @BindView(R.id.title_layout2)
        LinearLayout titleLayout2;
        @BindView(R.id.content_text2)
        TextView contentText2;
        @BindView(R.id.layout_item)
        LinearLayout layoutItem;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

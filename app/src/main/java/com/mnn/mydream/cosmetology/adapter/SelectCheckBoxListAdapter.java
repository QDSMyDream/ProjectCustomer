package com.mnn.mydream.cosmetology.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.smoothcheckbox.SmoothCheckBox;
import com.mnn.mydream.cosmetology.R;
import com.mnn.mydream.cosmetology.bean.CustomerProjectsItem;
import com.mnn.mydream.cosmetology.bean.SelectSignBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 创建人 ： MyDream
 * 创建时间：2020/5/3 18:18
 * 类描述：未签名列表
 */

public class SelectCheckBoxListAdapter extends BaseAdapter {

    private String TAG = "SameDayListAdapter";

    private Context mContext;
    View.OnClickListener onClickListenerLayout;
    View.OnLongClickListener onLongClickListenerLayout;
    List<SelectSignBean> customerProjectsItems;
    View.OnClickListener onCheckedChangeListener;
    public SelectCheckBoxListAdapter(Context context, List<SelectSignBean> customerProjectsItems, View.OnClickListener onCheckedChangeListener) {
        this.mContext = context;
        this.customerProjectsItems = customerProjectsItems;
        this.onCheckedChangeListener=onCheckedChangeListener;
    }



    @Override
    public int getCount() {
        return customerProjectsItems.size();
    }

    @Override
    public Object getItem(int position) {
        return customerProjectsItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.select_check_layout_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);


        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (customerProjectsItems.size() > position) {

            holder.tv.setText((position + 1) + "、" + customerProjectsItems.get(position).getProjectsString());

            if (!customerProjectsItems.get(position).isSignFlag()) {
//                holder.tvSignText.setVisibility(View.GONE);
                holder.scb.setChecked(false);

            } else {
                holder.scb.setChecked(true);
//                holder.tvSignText.setVisibility(View.VISIBLE);
//                holder.tvSignText.setTextColor(mContext.getResources().getColor(R.color.text_board));
//                holder.tvSignText.setText("已签字");
            }

            holder.scb.setOnClickListener(onCheckedChangeListener);


        }


        return convertView;
    }


    static class ViewHolder {
        @BindView(R.id.tv)
        TextView tv;
        @BindView(R.id.scb)
        SmoothCheckBox scb;

        @BindView(R.id.tv_sign_text)
        TextView tvSignText;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    //更新组件状态
    public boolean updataView(int posi, ListView listView, CustomerProjectsItem customerProjectsItem) {
        boolean b = false;

        int visibleFirstPosi = listView.getFirstVisiblePosition();
        int visibleLastPosi = listView.getLastVisiblePosition();
        if (posi >= visibleFirstPosi && posi <= visibleLastPosi) {
            View view = listView.getChildAt(posi - visibleFirstPosi);
            ViewHolder holder = (ViewHolder) view.getTag();

            if (holder.scb.isChecked()) {
                b = false;
                holder.scb.setChecked(false);
            } else {
                b = true;
                holder.scb.setChecked(true);
            }

        }
        return b;
    }


}

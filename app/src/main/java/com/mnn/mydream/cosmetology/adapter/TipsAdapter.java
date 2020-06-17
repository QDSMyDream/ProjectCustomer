package com.mnn.mydream.cosmetology.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.mnn.mydream.cosmetology.R;

import com.mnn.mydream.cosmetology.bean.Customer;

import java.util.ArrayList;
import java.util.List;


/**
 * 创建人：MyDream
 * 创建时间：2020/5/7 18:18
 * 类描述：
 */

public class TipsAdapter extends BaseAdapter implements Filterable {

    private String TAG = "TipsAdapter";
    private List<Customer> mList;
    private ArrayList<Customer> mArray;// 标题列表
    private Context mContext;

    private String setStringColor = "";//要变色的字符串

    public TipsAdapter(Context context, List<Customer> list) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_tiplist, null);
            holder = new ViewHolder();

            holder.indexTv1 = (TextView) convertView.findViewById(R.id.item_indexTv1);
            holder.indexTv2 = convertView.findViewById(R.id.item_indexTv2);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.indexTv1.setText(changeText(mList.get(position).getName(), setStringColor));
        holder.indexTv2.setText(mList.get(position).getPhone());
        return convertView;
    }

    class ViewHolder {
        TextView indexTv1;
        TextView indexTv2;
    }

    @TargetApi(Build.VERSION_CODES.M)
    private CharSequence changeText(String content, String redTag) {
        if (TextUtils.isEmpty(content)) {
            return "";
        }
        SpannableStringBuilder result = new SpannableStringBuilder(content);
        for (int i = 0; i < content.length(); i++) {
            if (content.regionMatches(false, i, redTag, 0, redTag.length())) {
                result.setSpan(new ForegroundColorSpan(Color.CYAN),
                        i, i + redTag.length(), SpannableStringBuilder.SPAN_INCLUSIVE_INCLUSIVE);
            }
        }
        return result;
    }

    @Override
    public Filter getFilter() {

        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                setStringColor = constraint.toString().toLowerCase();
                FilterResults results = new FilterResults();
                if (mArray == null) {
                    mArray = new ArrayList<Customer>(mList);
                }

                if (constraint == null || constraint.length() == 0) {
                    ArrayList<Customer> list = mArray;
                    results.values = list;
                    results.count = list.size();
                } else {
                    String prefixString = constraint.toString().toLowerCase();

                    ArrayList<Customer> unfilteredValues = mArray;
                    int count = unfilteredValues.size();

                    ArrayList<Customer> newValues = new ArrayList<Customer>(count);

                    for (int i = 0; i < count; i++) {
                        Customer costorBean = unfilteredValues.get(i);
                        if (costorBean != null) {
                            if (costorBean.getName() != null && costorBean.getName().indexOf(prefixString) != -1) {
                                newValues.add(costorBean);
                                Log.e(TAG, "performFiltering:1 " + costorBean);
                            }
//                            else if (costorBean.getmPhone() != null && costorBean.getmPhone().indexOf(prefixString) != -1) {
//                                newValues.add(costorBean);
//                                Log.e(TAG, "performFiltering:2" + costorBean);
//                            }
                        }
                    }

                    results.values = newValues;
                    results.count = newValues.size();
                }

                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                mList = (ArrayList<Customer>) results.values;
                if (results.count > 0) {
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }

            }
        };
        return filter;

    }


}

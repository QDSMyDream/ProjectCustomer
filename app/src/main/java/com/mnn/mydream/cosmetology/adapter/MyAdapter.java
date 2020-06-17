//package com.mnn.mydream.cosmetology.adapter;
//
//import android.content.Context;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.Filter;
//import android.widget.Filterable;
//import android.widget.TextView;
//
//import com.brucetoo.pickview.R;
//import com.brucetoo.pickview.UserBean;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * ListView适配器
// *
// * @author zihao
// */
//public class MyAdapter extends BaseAdapter implements Filterable {
//
//    private String TAG = "MyAdapter";
//    private List<UserBean> mTitleArray;// 标题列表
//
//    private ArrayList<UserBean> mTitleArray2;// 标题列表
//
//
//    private ArrayList<String> mTitleArrays;// 标题列表
//    private LayoutInflater inflater = null;
//    private Context mContext;
//
//    /**
//     * Adapter构造方法
//     *
//     * @param mTitleArrays
//     * @param titleArray
//     */
//    public MyAdapter(Context context, ArrayList<String> mTitleArrays, List<UserBean> titleArray) {
//        // TODO Auto-generated constructor stub
//        this.mTitleArray = titleArray;
//        this.mContext = context;
//        this.mTitleArrays = mTitleArrays;
//        inflater = (LayoutInflater) mContext
//                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//    }
//
//    /**
//     * 获取总数
//     */
//    @Override
//    public int getCount() {
//        // TODO Auto-generated method stub
//        return mTitleArray.size();
//    }
//
//    /**
//     * 获取Item内容
//     */
//    @Override
//    public String getItem(int position) {
//        // TODO Auto-generated method stub
//        Log.e("", "getItem: " + mTitleArray.get(position).getName());
//        return mTitleArray.get(position).getName();
//    }
//
//    /**
//     * 获取Item的ID
//     */
//    @Override
//    public long getItemId(int position) {
//        // TODO Auto-generated method stub
//        return position;
//    }
//
//    @Override
//    public View getView(final int position, View convertView, ViewGroup parent) {
//        // TODO Auto-generated method stub
//
//        ViewHolder holder;
//
//        if (convertView == null) {
//            holder = new ViewHolder();
//            convertView = inflater.inflate(R.layout.list_item_layout, null);
//            holder.titleTv = (TextView) convertView.findViewById(R.id.title_tv);
//            holder.titlePhone = (TextView) convertView.findViewById(R.id.title_phone);
//            convertView.setTag(holder);
//        } else {
//            holder = (ViewHolder) convertView.getTag();
//        }
//
//
//        // 设置
//        holder.titleTv.setText(mTitleArray.get(position).getName());
//        holder.titlePhone.setText(mTitleArray.get(position).getPhone());
//
//        return convertView;
//    }
//
//    @Override
//    public Filter getFilter() {
//
//        Filter filter = new Filter() {
//            @Override
//            protected FilterResults performFiltering(CharSequence constraint) {
//
//                FilterResults results = new FilterResults();
//
//                if (mTitleArray2 == null) {
//                    mTitleArray2 = new ArrayList<UserBean>(mTitleArray);
//                }
//
//                if (constraint == null || constraint.length() == 0) {
//                    ArrayList<UserBean> list = mTitleArray2;
//                    results.values = list;
//                    results.count = list.size();
//                } else {
//                    String prefixString = constraint.toString().toLowerCase();
//
//                    ArrayList<UserBean> unfilteredValues = mTitleArray2;
//                    int count = unfilteredValues.size();
//
//                    ArrayList<UserBean> newValues = new ArrayList<UserBean>(count);
//
//                    for (int i = 0; i < count; i++) {
//                        UserBean pc = unfilteredValues.get(i);
//                        if (pc != null) {
//
//                            if(pc.getName()!=null && pc.getName().indexOf(prefixString)!=-1){
//                                newValues.add(pc);
//                                Log.e(TAG, "performFiltering:1 "+pc );
//                            }else if(pc.getPhone()!=null && pc.getPhone().indexOf(prefixString)!=-1){
//                                newValues.add(pc);
//                                Log.e(TAG, "performFiltering:2"+pc );
//                            }
//
//                        }
//                    }
//
//                    results.values = newValues;
//                    results.count = newValues.size();
//                }
//
//                return results;
//            }
//
//            @Override
//            protected void publishResults(CharSequence constraint, FilterResults results) {
//
//                mTitleArray= (ArrayList<UserBean>) results.values;
//                if (results.count > 0) {
//                    notifyDataSetChanged();
//                } else {
//                    notifyDataSetInvalidated();
//                }
//
//            }
//        };
//        return filter;
//
//    }
//
//    static class ViewHolder {
//        TextView titleTv, titlePhone;
//    }
//
//}
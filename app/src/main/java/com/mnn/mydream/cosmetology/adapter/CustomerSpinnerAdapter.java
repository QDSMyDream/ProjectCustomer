package com.mnn.mydream.cosmetology.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.mnn.mydream.cosmetology.R;
import com.mnn.mydream.cosmetology.bean.CustomerProjectBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CustomerSpinnerAdapter extends BaseAdapter implements SpinnerAdapter {
    private Context context;
    private List<CustomerProjectBean> lists;

    public CustomerSpinnerAdapter(Context context, List<CustomerProjectBean> list_bsl) {
        this.lists = list_bsl;
        this.context = context;
    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int position) {
        return lists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.customer_projects_spinner_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        CustomerProjectBean customerProjects = lists.get(position);
        holder.projectName.setText(customerProjects.getcProjects());
//        holder.projectTime.setText(customerProjects.getcProjectsStartTime());

        return convertView;
    }

    class ViewHolder {
        @BindView(R.id.project_name)
        TextView projectName;
        @BindView(R.id.project_time)
        TextView projectTime;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
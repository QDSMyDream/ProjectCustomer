package com.mnn.mydream.cosmetology.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mnn.mydream.cosmetology.R;
import com.mnn.mydream.cosmetology.bean.ProjectMenuBean;
import com.mnn.mydream.cosmetology.utils.ImageLoader;
import com.zhy.android.percent.support.PercentLinearLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者：${lxl} on 2019/6/26 0026 17:05
 * 类的描述：
 */
public class GridVeiwMenuAdapter extends BaseAdapter {

    private String TAG="GridVeiwMenuAdapter";

    private Context mContext;
    private List<ProjectMenuBean> projectMenuBeans;


    public GridVeiwMenuAdapter(Context context, List<ProjectMenuBean> list) {
        this.mContext = context;
        this.projectMenuBeans = list;

    }

    public void setActivityManageBean(List<ProjectMenuBean> list) {
        this.projectMenuBeans = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return projectMenuBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_menu, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (projectMenuBeans.size() > position) {
            viewHolder.txtIcon.setText(projectMenuBeans.get(position).getProjectMenuString());

            Log.e(TAG, "getView: "+ projectMenuBeans.get(position).getProjectMenuImg());
            //加载图片
            ImageLoader.displayImageView(mContext, projectMenuBeans.get(position).getProjectMenuImg(), viewHolder.imgIcon,R.mipmap.ic_img_default);
        }


        return convertView;
    }


    static class ViewHolder {
        @BindView(R.id.img_icon)
        ImageView imgIcon;
        @BindView(R.id.txt_icon)
        TextView txtIcon;
        @BindView(R.id.layout_item)
        PercentLinearLayout layoutItem;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

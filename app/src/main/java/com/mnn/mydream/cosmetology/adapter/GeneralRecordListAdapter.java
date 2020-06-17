package com.mnn.mydream.cosmetology.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.mnn.mydream.cosmetology.R;
import com.mnn.mydream.cosmetology.bean.Customer;
import com.mnn.mydream.cosmetology.interfaces.OnItemRecyclerViewClickListener;
import com.mnn.mydream.cosmetology.utils.ImageLoader;
import com.mnn.mydream.cosmetology.utils.StringUtils;
import com.mnn.mydream.cosmetology.utils.ToastUtils;
import com.zhy.android.percent.support.PercentLinearLayout;
import com.zhy.android.percent.support.PercentRelativeLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 创建人 :MyDream
 * 创建时间：2020/5/3 18:18
 * 类描述：总记录列表适配器
 */

public class GeneralRecordListAdapter extends BaseAdapter {

    private String TAG = "GeneralRecordListAdapter";
    private final List<Customer> comingslist;
    private final Context mContext;
    private final LayoutInflater mLayoutInflater;


    public GeneralRecordListAdapter(Context mContext, List<Customer> comingslist) {

        this.mContext = mContext;
        this.comingslist = comingslist;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return comingslist.size();
    }

    @Override
    public Object getItem(int position) {
        return comingslist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.general_record_recycle_item_layout, null);
            holder = new ViewHolder(convertView);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.setData(position);

        return convertView;
    }


    public OnItemRecyclerViewClickListener onItemRecyclerViewClickListener;

    public void setOnItemRecyclerViewClickListener(OnItemRecyclerViewClickListener onItemRecyclerViewClickListener) {
        this.onItemRecyclerViewClickListener = onItemRecyclerViewClickListener;
    }

    class ViewHolder {
        @BindView(R.id.tx_roundedImageView)
        RoundedImageView txRoundedImageView;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.sex)
        TextView sex;
        @BindView(R.id.age)
        TextView age;
        @BindView(R.id.projects_title)
        TextView projectsTitle;
        @BindView(R.id.projects_img)
        ImageView projectsImg;
        @BindView(R.id.phone_txt)
        TextView phoneTxt;
        @BindView(R.id.phone_img)
        ImageView phoneImg;
        @BindView(R.id.time)
        TextView time;
        @BindView(R.id.tv_sign_count)
        TextView tvSignCount;
        @BindView(R.id.tv_sign_text)
        TextView tvSignText;
        @BindView(R.id.sign_layout)
        PercentRelativeLayout signLayout;
        @BindView(R.id.item_layout)
        PercentLinearLayout itemLayout;
        @BindView(R.id.swipelistlayout)
        PercentLinearLayout swipelistlayout;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        public void setData(int position) {

            Customer costorBean = comingslist.get(position);
            //加载图片
            ImageLoader.displayImageView(mContext,costorBean.getCustomer_tx(),txRoundedImageView);


            name.setText(costorBean.getName());

            sex.setText(costorBean.getSex());

            age.setText(setAgeSize(costorBean.getAge() + ""));

            time.setText(costorBean.getCreatedAt());

            phoneTxt.setText(costorBean.getPhone());

            itemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemRecyclerViewClickListener.onItemOnClickListener(v, position);
                }
            });
            itemLayout.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onItemRecyclerViewClickListener.onItemLongOnClickListener(v, position);
                    return false;
                }
            });


            projectsImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtils.showToast(mContext, "这是电话图标点击", true);
                }

            });


        }

        public String setAgeSize(String age) {
            String ages = "";
            if (StringUtils.isEmpty(age)) {
                return ages;
            }
            if (age.length() == 1) {
                ages = "0" + age;
                return ages;
            } else {
                return age;
            }
        }


    }
}

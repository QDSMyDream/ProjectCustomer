package com.mnn.mydream.cosmetology.fragment.beauty.commoditiesFragments.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.example.smoothcheckbox.SmoothCheckBox;
import com.mnn.mydream.cosmetology.R;
import com.mnn.mydream.cosmetology.bean.spglBean.CXKDataBean;
import com.mnn.mydream.cosmetology.bean.spglBean.FuWuSaleBean;
import com.mnn.mydream.cosmetology.bean.spglBean.XMKDataBean;
import com.mnn.mydream.cosmetology.interfaces.SPGLListOnClickListener;
import com.zhy.android.percent.support.PercentLinearLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 创建人 :MyDream
 * 创建时间：2020/5/3 18:18
 * 类描述：储蓄卡列表
 */

public class CxkListAdapter extends BaseAdapter {

    private String TAG = "CxkListAdapter";
    private List<CXKDataBean> cxkDataBeans;
    private final Context mContext;

    public CxkListAdapter(Context mContext, List<CXKDataBean> cxkDataBeans) {
        this.mContext = mContext;
        this.cxkDataBeans = cxkDataBeans;
    }

    @Override
    public int getCount() {
        return cxkDataBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return cxkDataBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.cxk_list_item_layout, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        CXKDataBean cxkDataBean = cxkDataBeans.get(position);

        //加载图片
        holder.cxkName.setText(cxkDataBean.getCxkName() + "");

        if (cxkDataBean.isKzkFWFlag()) {
            holder.fwSiscount.setText(cxkDataBean.getKzkFW() + "折");
        } else {
            holder.fwSiscount.setText("无");
        }

        if (cxkDataBean.isKzkCPFlag()) {
            holder.cpSiscount.setText(cxkDataBean.getKzkCP() + "折");
        } else {
            holder.cpSiscount.setText("无");

        }

        if (cxkDataBean.isKzkXMKFlag()) {
            holder.xmkSiscount.setText(cxkDataBean.getKzkXMK() + "折");
        } else {
            holder.xmkSiscount.setText("无");
        }

        holder.num.setText(cxkDataBean.getNum().equals("") ? "无" : cxkDataBean.getNum());
        holder.ryc.setText(cxkDataBean.isRyc() ? "是" : "否");
        holder.addDate.setText(cxkDataBean.getMd());

        holder.updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spglListOnClickListener.onClickUpdate(v, position, cxkDataBean);
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spglListOnClickListener.onClickDelete(v, position, cxkDataBean);
            }
        });


        return convertView;
    }

    public SPGLListOnClickListener spglListOnClickListener;


    public void setCpListOnClickListener(SPGLListOnClickListener spglListOnClickListener) {
        this.spglListOnClickListener = spglListOnClickListener;
    }


    //删除单个view
    public void deleteView(int posi, ListView listView) {

        int visibleFirstPosi = listView.getFirstVisiblePosition();
        int visibleLastPosi = listView.getLastVisiblePosition();
        if (posi >= visibleFirstPosi && posi <= visibleLastPosi) {
            View view = listView.getChildAt(posi - visibleFirstPosi);
            ViewHolder holder = (ViewHolder) view.getTag();
            Log.e(TAG, "deleteView: ");
            cxkDataBeans.remove(posi);
            notifyDataSetChanged();
        }
    }


    static class ViewHolder {
        @BindView(R.id.checkbox_cxk)
        SmoothCheckBox checkboxCxk;
        @BindView(R.id.iv_server)
        ImageView ivServer;
        @BindView(R.id.cxkName)
        TextView cxkName;
        @BindView(R.id.fw_siscount)
        TextView fwSiscount;
        @BindView(R.id.cp_siscount)
        TextView cpSiscount;
        @BindView(R.id.xmk_siscount)
        TextView xmkSiscount;
        @BindView(R.id.num)
        TextView num;
        @BindView(R.id.ryc)
        TextView ryc;
        @BindView(R.id.add_date)
        TextView addDate;
        @BindView(R.id.update_btn)
        TextView updateBtn;
        @BindView(R.id.delete)
        ImageView delete;
        @BindView(R.id.title_layout)
        PercentLinearLayout titleLayout;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

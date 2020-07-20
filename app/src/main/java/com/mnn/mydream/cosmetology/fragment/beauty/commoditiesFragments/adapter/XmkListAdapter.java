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
import com.alibaba.fastjson.JSONObject;

import com.alibaba.fastjson.TypeReference;
import com.mnn.mydream.cosmetology.R;
import com.mnn.mydream.cosmetology.bean.User;
import com.mnn.mydream.cosmetology.bean.fuwuBean.FuWuSaleBean;
import com.mnn.mydream.cosmetology.bean.fuwuBean.XMKDataBean;
import com.mnn.mydream.cosmetology.bean.fuwuBean.XMKDataOpertionBean;
import com.mnn.mydream.cosmetology.interfaces.SPGLListOnClickListener;
import com.zhy.android.percent.support.PercentLinearLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 创建人 :MyDream
 * 创建时间：2020/5/3 18:18
 * 类描述：项目卡列表
 */

public class XmkListAdapter extends BaseAdapter {

    private String TAG = "FuWuView1ListAdapter";
    private List<XMKDataBean> xmkDataBeans;
    private final Context mContext;

    public XmkListAdapter(Context mContext, List<XMKDataBean> xmkDataBeans) {
        this.mContext = mContext;
        this.xmkDataBeans = xmkDataBeans;
    }

    @Override
    public int getCount() {
        return xmkDataBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return xmkDataBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.xmk_list_item_layout, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        XMKDataBean xmkDataBean = xmkDataBeans.get(position);

        //加载图片
        holder.serverName.setText(xmkDataBean.getXmkName() + "");
        holder.serverMoney.setText(xmkDataBean.getXmkMoney() + "");
        holder.applyMd.setText(xmkDataBean.getXmkMd() + "");
        holder.typeText.setText(xmkDataBean.getXmkType() + "");
        holder.addDate.setText(xmkDataBean.getCreatedAt() + "");
        holder.projectText.setText(xmkDataBean.isTotalNumFlag() ? "是" : "否");

        Log.e(TAG, "getView: " + xmkDataBean.getFwJson());

        JSONArray jsonArray = JSONArray.parseArray(xmkDataBean.getFwJson());

        int num = 0;
        String json = null;

        for (int j = 0; j < jsonArray.size(); j++) {
            num = num + JSON.parseObject(jsonArray.get(j).toString()).getInteger("num");
            json = JSON.parseObject(jsonArray.get(j).toString()).getString("fuWuSaleBean");
            Log.e(TAG, "getView: " + json);
        }
        JSONObject jsonObject = JSON.parseObject(json);
        FuWuSaleBean fuWuSaleBean = JSON.parseObject(jsonObject.toJSONString(), new TypeReference<FuWuSaleBean>() {
        });

        Log.e(TAG, "getView: " + fuWuSaleBean.toString());

//        JSON.parse(xmkDataBean.getFwJson());
//        List<XMKDataOpertionBean> xmkDataOpertionBeans = (List<XMKDataOpertionBean>) JSONArray.parseArray(xmkDataBean.getFwJson(), XMKDataOpertionBean.class);
//        Log.e(TAG, "getView: " + xmkDataOpertionBeans.size());
//        int num=0 ;
//        for (XMKDataOpertionBean xmkDataOpertionBean : xmkDataOpertionBeans) {
//            num = num + xmkDataOpertionBean.getMoney();
//        }

        holder.applySpecifications.setText(num + "");

        if (xmkDataBean.isXmlSaleFlag()) {
            holder.saleBtn.setVisibility(View.GONE);
            holder.dismountBtn.setVisibility(View.VISIBLE);

        } else {
            holder.dismountBtn.setVisibility(View.GONE);
            holder.saleBtn.setVisibility(View.VISIBLE);
        }


        holder.saleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spglListOnClickListener.onClickSale(v, position, xmkDataBean);
            }
        });

        holder.dismountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spglListOnClickListener.onClickDismount(v, position, xmkDataBean);
            }
        });

        holder.updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spglListOnClickListener.onClickUpdate(v, position, xmkDataBean);
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spglListOnClickListener.onClickDelete(v, position, xmkDataBean);
            }
        });


        return convertView;
    }

    public SPGLListOnClickListener spglListOnClickListener;


    public void setCpListOnClickListener(SPGLListOnClickListener spglListOnClickListener) {
        this.spglListOnClickListener = spglListOnClickListener;
    }


    public void updataView(int posi, ListView listView, FuWuSaleBean fuWuSaleBean) {

        int visibleFirstPosi = listView.getFirstVisiblePosition();
        int visibleLastPosi = listView.getLastVisiblePosition();

        if (posi >= visibleFirstPosi && posi <= visibleLastPosi) {
            View view = listView.getChildAt(posi - visibleFirstPosi);

            notifyDataSetInvalidated();

        }

    }


    //删除单个view
    public void deleteView(int posi, ListView listView) {

        int visibleFirstPosi = listView.getFirstVisiblePosition();
        int visibleLastPosi = listView.getLastVisiblePosition();
        if (posi >= visibleFirstPosi && posi <= visibleLastPosi) {
            View view = listView.getChildAt(posi - visibleFirstPosi);
            ViewHolder holder = (ViewHolder) view.getTag();
            Log.e(TAG, "deleteView: ");
            xmkDataBeans.remove(posi);
            notifyDataSetChanged();
        }
    }


    static class ViewHolder {
        @BindView(R.id.iv_server)
        ImageView ivServer;
        @BindView(R.id.serverName)
        TextView serverName;
        @BindView(R.id.server_money)
        TextView serverMoney;
        @BindView(R.id.select_server_all)
        PercentLinearLayout selectServerAll;
        @BindView(R.id.type_text)
        TextView typeText;
        @BindView(R.id.project_text)
        TextView projectText;
        @BindView(R.id.apply_md)
        TextView applyMd;
        @BindView(R.id.apply_specifications)
        TextView applySpecifications;
        @BindView(R.id.add_date)
        TextView addDate;
        @BindView(R.id.update_btn)
        TextView updateBtn;
        @BindView(R.id.dismount_btn)
        TextView dismountBtn;
        @BindView(R.id.sale_btn)
        TextView saleBtn;
        @BindView(R.id.delete)
        ImageView delete;
        @BindView(R.id.title_layout)
        PercentLinearLayout titleLayout;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

package com.mnn.mydream.cosmetology.fragment.beauty.commoditiesFragments.adapter;//package com.mnn.mydream.cosmetology.adapter;

import android.app.Activity;
import android.support.v7.widget.AppCompatEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mnn.mydream.cosmetology.R;
import com.mnn.mydream.cosmetology.activity.CXKDialogActivity;
import com.mnn.mydream.cosmetology.bean.spglBean.CXKCZFABean;
import com.mnn.mydream.cosmetology.bean.spglBean.CXKDataBean;
import com.mnn.mydream.cosmetology.bean.spglBean.XMKDataOpertionBean;
import com.mnn.mydream.cosmetology.interfaces.ServiceOperationRecycleInterface;
import com.zhy.android.percent.support.PercentLinearLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.droidlover.xrecyclerview.XRecyclerView;

/**
 * 创建人 :MyDream
 * 创建时间：2020/5/3 18:18
 * 类描述：储蓄卡充值方案
 */
public class CXKOperationRecycleAdapter extends XRecyclerView.Adapter {

    private String TAG = "CXKOperationRecycleAdapter";
    private final List<CXKCZFABean> cxkczfaBeans;
    private final Activity mContext;
    private final LayoutInflater mLayoutInflater;


    public CXKOperationRecycleAdapter(Activity mContext, List<CXKCZFABean> cxkczfaBeans) {
        this.mContext = mContext;
        this.cxkczfaBeans = cxkczfaBeans;

        mLayoutInflater = LayoutInflater.from(mContext);
    }


    @Override
    public XRecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mLayoutInflater.inflate(R.layout.cxk_operation_list_item, null));
    }

    @Override
    public void onBindViewHolder(XRecyclerView.ViewHolder holder, int position) {
        ViewHolder myholder = (ViewHolder) holder;
        myholder.setData(position);
    }

    @Override
    public int getItemCount() {
        return cxkczfaBeans.size();
    }

    //  删除数据
    public void removeData(int position) {
        cxkczfaBeans.remove(position);
        notifyDataSetChanged();
    }

    //  添加数据
    public void addData(CXKCZFABean cxkDataBean) {
//      在list中添加数据，并通知条目加入一条
        cxkczfaBeans.add(cxkDataBean);

        notifyDataSetChanged();
    }

    public void setAllEditText(boolean b) {
        notifyDataSetChanged();

    }


    //  添加数据
    public void addDatas(List<CXKCZFABean> cxkDataBeans) {
//      在list中添加数据，并通知条目加入一条
        cxkczfaBeans.addAll(cxkDataBeans);

        notifyDataSetChanged();
    }

    //  添加数据
    public void addDatass(List<XMKDataOpertionBean> xmkDataOpertionBeans) {
//      在list中添加数据，并通知条目加入一条

        notifyDataSetChanged();
    }


    public ServiceOperationRecycleInterface serviceOperationRecycleInterface;


    public void setServiceOperationRecycleInterface(ServiceOperationRecycleInterface fuWuListOnClickListener) {
        this.serviceOperationRecycleInterface = fuWuListOnClickListener;
    }


    class ViewHolder extends XRecyclerView.ViewHolder {
        @BindView(R.id.czje)
        AppCompatEditText czje;
        @BindView(R.id.zsje)
        AppCompatEditText zsje;
        @BindView(R.id.delete)
        ImageView delete;
        @BindView(R.id.title_layout)
        PercentLinearLayout titleLayout;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void setData(int position) {
            CXKCZFABean cxkczfaBean = cxkczfaBeans.get(position);
            czje.setText(cxkczfaBean.getCzje() + "");
            zsje.setText(cxkczfaBean.getZsje() + "");
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    serviceOperationRecycleInterface.onClickDelete(position, cxkczfaBean);
                }
            });


        }
    }
}

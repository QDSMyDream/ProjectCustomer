package com.mnn.mydream.cosmetology.fragment.beauty.commoditiesFragments.adapter;//package com.mnn.mydream.cosmetology.adapter;

import android.app.Activity;
import android.support.v7.widget.AppCompatEditText;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mnn.mydream.cosmetology.R;
import com.mnn.mydream.cosmetology.activity.MainActivity;
import com.mnn.mydream.cosmetology.activity.XMKDialogActivity;
import com.mnn.mydream.cosmetology.bean.fuwuBean.FuWuSaleBean;
import com.mnn.mydream.cosmetology.interfaces.ServiceOperationRecycleInterface;
import com.mnn.mydream.cosmetology.utils.ImageLoader;
import com.zhy.android.percent.support.PercentLinearLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.droidlover.xrecyclerview.XRecyclerView;

/**
 * 创建人 :MyDream
 * 创建时间：2020/5/3 18:18
 * 类描述：总记录客户记录列表适配器
 */
public class FWOperationRecycleAdapter extends XRecyclerView.Adapter {

    private String TAG = "FWOperationRecycleAdapter";
    private final List<FuWuSaleBean> fuWuSaleBeans;
    private final Activity mContext;
    private final LayoutInflater mLayoutInflater;
    private boolean aBoolean;

    public FWOperationRecycleAdapter(Activity mContext, List<FuWuSaleBean> fuWuSaleBeans, boolean b) {
        this.mContext = mContext;
        this.fuWuSaleBeans = fuWuSaleBeans;
        this.aBoolean = b;

        mLayoutInflater = LayoutInflater.from(mContext);
    }


    @Override
    public XRecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mLayoutInflater.inflate(R.layout.fuwu_operation_list_item, null));
    }

    @Override
    public void onBindViewHolder(XRecyclerView.ViewHolder holder, int position) {
        ViewHolder myholder = (ViewHolder) holder;
        myholder.setData(position);
    }

    @Override
    public int getItemCount() {
        return fuWuSaleBeans.size();
    }

    //  删除数据
    public void removeData(int position) {
        fuWuSaleBeans.remove(position);
        notifyDataSetChanged();
    }

    //  添加数据
    public void addData(FuWuSaleBean fuWuSaleBean) {
//      在list中添加数据，并通知条目加入一条
        fuWuSaleBeans.add(fuWuSaleBean);

        notifyDataSetChanged();
    }

    public void setAllEditText(boolean b) {
        aBoolean = b;
        notifyDataSetChanged();

    }


    //  添加数据
    public void addDatas(List<FuWuSaleBean> fuWuSaleBeanList) {
//      在list中添加数据，并通知条目加入一条
        fuWuSaleBeans.addAll(fuWuSaleBeanList);

        notifyDataSetChanged();
    }

    public ServiceOperationRecycleInterface serviceOperationRecycleInterface;


    public void setServiceOperationRecycleInterface(ServiceOperationRecycleInterface fuWuListOnClickListener) {
        this.serviceOperationRecycleInterface = fuWuListOnClickListener;
    }

    class ViewHolder extends XRecyclerView.ViewHolder {
        @BindView(R.id.iv_server)
        ImageView ivServer;
        @BindView(R.id.title1)
        TextView title1;
        @BindView(R.id.title2)
        TextView title2;
        @BindView(R.id.title3)
        TextView title3;
        @BindView(R.id.tip1)
        TextView tip1;
        @BindView(R.id.total_num)
        AppCompatEditText totalNum;
        @BindView(R.id.tip2)
        TextView tip2;
        @BindView(R.id.delete)
        ImageView delete;
        @BindView(R.id.title_layout)
        PercentLinearLayout titleLayout;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void setData(int position) {
            FuWuSaleBean fuWuSaleBean = fuWuSaleBeans.get(position);
            //加载图片
            ImageLoader.displayImageView(mContext, fuWuSaleBean.getServerUrl(), ivServer, R.mipmap.ic_img_default);
            title1.setText(fuWuSaleBean.getServerName() + "");
            title3.setText(fuWuSaleBean.getServerMoney() + "");
            title2.setText(fuWuSaleBean.getServerType() + "");
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    serviceOperationRecycleInterface.onClickDelete(position, fuWuSaleBean);
                }
            });

            if (aBoolean) {
                totalNum.setEnabled(false);
                totalNum.setAlpha((float) 0.5);
                totalNum.setHint(R.string.beauty_xmk_infinite_num);


            } else {
                totalNum.setEnabled(true);
                totalNum.setAlpha((float) 1);
                totalNum.setHint(R.string.beauty_xmk_frequency_num);

            }

            totalNum.setTag(position);
            totalNum.addTextChangedListener(new TextSwitcher(this));


        }

    }


    class TextSwitcher implements TextWatcher {
        private ViewHolder mHolder;

        public TextSwitcher(ViewHolder mHolder) {
            this.mHolder = mHolder;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            int position = (int) mHolder.totalNum.getTag();//取tag值
            ((XMKDialogActivity) mContext).saveEditData(position, s.toString());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }


}
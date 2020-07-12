package com.mnn.mydream.cosmetology.interfaces;

import android.view.View;

import com.mnn.mydream.cosmetology.bean.fuwuBean.CPDataBean;
import com.mnn.mydream.cosmetology.bean.fuwuBean.FuWuSaleBean;

public interface CPListOnClickListener {
    void onClickUpdate(View v, int pos, CPDataBean cpDataBean);

    void onClickDismount(View v, int pos, CPDataBean cpDataBean);

    void onClickSale(View v, int pos, CPDataBean cpDataBean);

}
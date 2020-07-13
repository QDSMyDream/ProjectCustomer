package com.mnn.mydream.cosmetology.interfaces;

import android.view.View;

import com.mnn.mydream.cosmetology.bean.Customer;
import com.mnn.mydream.cosmetology.bean.fuwuBean.FuWuSaleBean;

public interface FuWuListOnClickListener {
    void onClickUpdate(View v, int pos, FuWuSaleBean fuWuSaleBean);

    void onClickDismount(View v, int pos, FuWuSaleBean fuWuSaleBean);

    void onClickSale(View v, int pos, FuWuSaleBean fuWuSaleBean);


    void onClickDelete(View v, int pos, FuWuSaleBean fuWuSaleBean);
}

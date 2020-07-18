package com.mnn.mydream.cosmetology.interfaces;

import android.view.View;

import com.mnn.mydream.cosmetology.bean.fuwuBean.FuWuSaleBean;

public interface AddServiceOnCheckedChangeListener {

    void addOnCheckedBean(FuWuSaleBean fuWuSaleBean);

    void removeOnCheckedBean(FuWuSaleBean fuWuSaleBean);
}

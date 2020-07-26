package com.mnn.mydream.cosmetology.interfaces;

import android.view.View;

import com.mnn.mydream.cosmetology.bean.spglBean.CPDataBean;

public interface CPListOnClickListener {
    void onClickUpdate(View v, int pos, CPDataBean cpDataBean);

    void onClickDismount(View v, int pos, CPDataBean cpDataBean);

    void onClickSale(View v, int pos, CPDataBean cpDataBean);
    
    void onClickDelete(View v, int pos, CPDataBean cpDataBean);
}

package com.mnn.mydream.cosmetology.interfaces;

import android.view.View;

import com.mnn.mydream.cosmetology.bean.BeautyContentMenuBean;

public interface BeautyContentListOnClickListener {
    void onLongClick(View v, int pos, BeautyContentMenuBean beautyContentMenuBean,int flag);

    void onClick(View v, int pos, BeautyContentMenuBean beautyContentMenuBean,int flag);
}

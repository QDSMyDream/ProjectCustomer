package com.mnn.mydream.cosmetology.interfaces;

import android.view.View;

/**
 * 创建人：MyDream
 * 创建日期：2020/5/22 20:15
 * 类描述 : recyclerView实现点击长按
 */
public interface  OnItemRecyclerViewClickListener {

    void onItemOnClickListener(View view,int pos );

    void onItemLongOnClickListener(View view,int pos );
}

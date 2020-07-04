package com.mnn.mydream.cosmetology.interfaces;

import android.view.View;

import com.mnn.mydream.cosmetology.bean.Customer;

public interface SetListOnClickListener {

    void onLongClick(View v, int pos, Object o);

    void onClick(View v, int pos, Object o);
}

package com.mnn.mydream.cosmetology.interfaces;

import android.view.View;

import com.mnn.mydream.cosmetology.bean.Customer;

public interface SetSameDayListOnClickListener {
    void onLongClick(View v, int pos, Customer customer);

    void onClick(View v, int pos, Customer customer);
}

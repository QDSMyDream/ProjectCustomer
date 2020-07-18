package com.mnn.mydream.cosmetology.interfaces;

import android.view.View;


public interface SetListOnClickListener {

    void onLongClick(View v, int pos, Object o);

    void onClick(View v, int pos, Object o);

    void onClickDelete(View v, int pos, Object o);

}

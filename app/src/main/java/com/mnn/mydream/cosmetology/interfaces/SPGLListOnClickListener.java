package com.mnn.mydream.cosmetology.interfaces;

import android.view.View;

public interface SPGLListOnClickListener {
    void onClickUpdate(View v, int pos, Object object);

    void onClickDismount(View v, int pos, Object object);

    void onClickSale(View v, int pos, Object object);

    void onClickDelete(View v, int pos, Object object);
}

//package com.lonbon.icum.mydemoyxs.popwindow;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageButton;
//import android.widget.PopupWindow;
//
//import com.lonbon.icum.mydemoyxs.R;
//
///**
// * Created by Administrator on 2017/1/4.
// */
//
//public class TimeChoiceWindow extends PopupWindow {
//
//    private Context mContext;
//
//    private View view;
//
//    private ImageButton hangUpBtn;
//
//    public TimeChoiceWindow(Context context) {
//        mContext = context;
//        initView();
//    }
//
//    private void initView() {
//        view = LayoutInflater.from(mContext).inflate(R.layout.time_choice_popwindow_layout, null);
//
//        hangUpBtn = (ImageButton) view.findViewById(R.id.pop_hang_up_btn);
//
//        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
//        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
//        setAnimationStyle(R.style.bottom_window_anim);
//        setContentView(view);
//        update();
//    }
//
//    public void setHangUpListener(View.OnClickListener listener) {
//        hangUpBtn.setOnClickListener(listener);
//    }
//
//
//
//}

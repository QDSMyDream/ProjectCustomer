package com.mnn.mydream.cosmetology.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.mnn.mydream.cosmetology.R;

import butterknife.ButterKnife;

/**
 * 创建人 :MyDream
 * 创建时间：2020/5/3 18:18
 * 类描述：结账界面
 */
public class BeautyCheckOutActivity extends AppCompatActivity {
    private String TAG = "BeautyCheckOutActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_check_out_layout);
        ButterKnife.bind(this);
        initView();

    }

    private void initView() {


    }


}

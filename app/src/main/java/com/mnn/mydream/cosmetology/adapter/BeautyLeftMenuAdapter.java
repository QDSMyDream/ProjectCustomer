package com.mnn.mydream.cosmetology.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;

import com.example.verticaltablayout.QTabView;
import com.example.verticaltablayout.TabAdapter;

import java.util.List;

/**
 * 创建人 :MyDream
 * 创建时间：2020/5/3 18:18
 * 类描述：主界面左侧选项卡
 */
public class BeautyLeftMenuAdapter implements TabAdapter {
    private String[] titles;
    private Context mContext;
    private int[] menuDefaultImg;
    private int[] menuSelectedImg;
    int textDefaultColor;
    int textSelectedColor;

    public BeautyLeftMenuAdapter(Context mContext, String[] titles, int[] menuDefaultImg, int[] menuSelectedImg, int textDefaultColor, int textSelectedColor) {
        this.titles = titles;
        this.mContext = mContext;
        this.menuDefaultImg = menuDefaultImg;
        this.menuSelectedImg = menuSelectedImg;
        this.textDefaultColor = textDefaultColor;
        this.textSelectedColor = textSelectedColor;

    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public int getBadge(int position) {

        return 0;

    }

    @Override
    public QTabView.TabIcon getIcon(int position) {

        return new QTabView.TabIcon.Builder().setIcon(menuSelectedImg[position], menuDefaultImg[position])
                .setIconSize(30, 30)
                .setIconGravity(Gravity.LEFT)
                .setIconMargin(10)
                .build();
    }

    @Override
    public QTabView.TabTitle getTitle(int position) {
        return new QTabView.TabTitle.Builder(mContext)
                .setContent(titles[position])
                .setTextSize(20)
                .setTextColor(mContext.getResources().getColor(textSelectedColor),mContext.getResources().getColor(textDefaultColor) )
                .build();
    }

    @Override
    public int getBackground(int pos) {


        return 0;


    }


}

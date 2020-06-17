package com.mnn.mydream.cosmetology.view;


import android.content.Context;
import android.support.v4.view.ViewConfigurationCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.view.ViewHelper;


/**
 * Created by yinwei on 2015-12-01.
 */
public class SCrollListView extends ListView {
    private int mScreenWidth;    // 屏幕宽度
    private int mDownX;            // 按下点的x值
    private int mDownY;            // 按下点的y值
    private int mDeleteBtnWidth;// 操作view的宽度

    private boolean isDeleteShown;    // 删除按钮是否正在显示

    private ViewGroup mPointChild;    // 当前处理的item
    private LinearLayout.LayoutParams mLayoutParams;    // 当前处理的item的LayoutParams

    private int touchSlop;//最小偏移量超过这个值才处理滑动事件

    private int scroll;//偏移的距离

    private int openedIntemPosition = -1;//记录已经打开的item的位置

    private int childPosition;//手指落下位置的item的position

    private boolean iswiping = false;//手指是否正在滑动

    public SCrollListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SCrollListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        // 获取屏幕宽度
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        mScreenWidth = dm.widthPixels;
        final ViewConfiguration configuration = ViewConfiguration.get(getContext());
        touchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(configuration);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                performActionDown(ev);
                break;
            case MotionEvent.ACTION_MOVE:
                return performActionMove(ev);
            case MotionEvent.ACTION_UP:
                performActionUp();
                break;
        }

        return super.onTouchEvent(ev);
    }

    // 处理action_down事件
    private void performActionDown(MotionEvent ev) {


        mDownX = (int) ev.getX();
        mDownY = (int) ev.getY();
        // 获取当前点的item
        childPosition = pointToPosition(mDownX, mDownY);
        if (childPosition == AdapterView.INVALID_POSITION) {

            return;

        }

        if (isDeleteShown && openedIntemPosition != childPosition - getFirstVisiblePosition()) {

            turnToNormal();
        }

        openedIntemPosition = childPosition - getFirstVisiblePosition();

        mPointChild = (ViewGroup) getChildAt(openedIntemPosition);
        // 获取操作view宽度
        mDeleteBtnWidth = mPointChild.getChildAt(1).getLayoutParams().width;
        mLayoutParams = (LinearLayout.LayoutParams) mPointChild.getChildAt(0)
                .getLayoutParams();
        // 为什么要重新设置layout_width 等于屏幕宽度
        // 因为match_parent时，不管你怎么滑，都不会显示删除按钮
        // why？ 因为match_parent时，ViewGroup就不去布局剩下的view
        mLayoutParams.width = mScreenWidth;
        mPointChild.getChildAt(0).setLayoutParams(mLayoutParams);
    }

    // 处理action_move事件
    private boolean performActionMove(MotionEvent ev) {
        int nowX = (int) ev.getX();
        int nowY = (int) ev.getY();
        if (Math.abs(nowX - mDownX) > touchSlop && Math.abs(nowY - mDownY) < touchSlop) {

            iswiping = true;

            //当手指滑动item,取消item的点击事件，不然我们滑动Item也伴随着item点击事件的发生
            MotionEvent cancelEvent = MotionEvent.obtain(ev);
            cancelEvent.setAction(MotionEvent.ACTION_CANCEL |
                    (ev.getActionIndex() << MotionEvent.ACTION_POINTER_INDEX_SHIFT));
            onTouchEvent(cancelEvent);

        }

        if (iswiping) {

            scroll = (nowX - mDownX);

            if (scroll != 0 && Math.abs(scroll) <= mDeleteBtnWidth) {

                if (scroll < 0 && !isDeleteShown) {

                    ViewHelper.setTranslationX(mPointChild.getChildAt(0), scroll);

                    ViewHelper.setTranslationX(mPointChild.getChildAt(1), scroll);

                } else if (scroll > 0 && isDeleteShown) {

                    ViewHelper.setTranslationX(mPointChild.getChildAt(0), scroll - mDeleteBtnWidth);
                    ViewHelper.setTranslationX(mPointChild.getChildAt(1), scroll - mDeleteBtnWidth);

                }

            }

            return true;
        }

        return super.onTouchEvent(ev);
    }

    /**
     * 隐藏操作view
     */
    private void hiden() {

        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mPointChild.getChildAt(0), "translationX", 0);

        ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(mPointChild.getChildAt(1),
                "translationX", mDeleteBtnWidth);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(objectAnimator, objectAnimator1);
        animatorSet.setDuration(150);
        animatorSet.start();
    }

    /**
     * 显示操作view
     */
    private void show() {

        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mPointChild.getChildAt(0), "translationX",
                -mDeleteBtnWidth);

        ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(mPointChild.getChildAt(1), "translationX",
                -mDeleteBtnWidth);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(objectAnimator, objectAnimator1);
        animatorSet.setDuration(150);
        animatorSet.start();
    }

    // 处理action_up事件
    private void performActionUp() {

        if (!iswiping) {

            return;
        }
        // 偏移量大于操作view的一半，则显示
        // 否则恢复默认
        //向左滑动
        if (scroll < 0) {

            if (-scroll >= mDeleteBtnWidth / 2) {
                show();
                isDeleteShown = true;


            } else if (-scroll <= mDeleteBtnWidth / 2) {

                turnToNormal();

            }

        } else if (scroll > 0) {//向右滑动


            if (scroll >= mDeleteBtnWidth / 2) {

                turnToNormal();

            } else if (scroll <= mDeleteBtnWidth / 2) {

                show();
                isDeleteShown = true;

            }

        }

        iswiping = false;

    }

    /**
     * 变为正常状态
     */
    public void turnToNormal() {
        hiden();
        isDeleteShown = false;
    }

}

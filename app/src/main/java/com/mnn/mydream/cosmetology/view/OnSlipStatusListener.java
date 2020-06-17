package com.mnn.mydream.cosmetology.view;

import java.util.HashSet;
import java.util.Set;

/**
 * 创建人：MyDream
 * 创建日期：2020/5/12 22:11
 * 类描述：
 */
public class OnSlipStatusListener implements SwipeListLayout.OnSwipeStatusListener {

    private Set<SwipeListLayout> sets;
    private SwipeListLayout swipeListLayout;


    public OnSlipStatusListener(SwipeListLayout slipListLayout, Set<SwipeListLayout> sets) {
        this.swipeListLayout = slipListLayout;
        this.sets = sets;
    }

    @Override
    public void onStatusChanged(SwipeListLayout.Status status) {
        if (status == SwipeListLayout.Status.Open) {

            //若有其他的item的状态为Open，则Close，然后移除
            if (sets.size() > 0) {
                for (SwipeListLayout s : sets) {
                    s.setStatus(SwipeListLayout.Status.Close, true);
                    sets.remove(s);
                }
            }
            sets.add(swipeListLayout);
        } else {
            if (sets.contains(swipeListLayout)) {
                sets.remove(swipeListLayout);
            }
        }
    }

    @Override
    public void onStartCloseAnimation() {

    }

    @Override
    public void onStartOpenAnimation() {

    }
}

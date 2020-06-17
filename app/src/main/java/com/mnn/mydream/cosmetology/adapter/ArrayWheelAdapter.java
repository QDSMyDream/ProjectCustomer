package com.mnn.mydream.cosmetology.adapter;

import com.contrarywind.adapter.WheelAdapter;

import java.util.List;

public class ArrayWheelAdapter implements WheelAdapter {
    private List<String> lists;

    public ArrayWheelAdapter(List<String> lists) {
        this.lists = lists;
    }

    @Override
    public int getItemsCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int index) {
        return lists.get(index);
    }

    @Override
    public int indexOf(Object o) {
        return lists.indexOf(o);
    }
}

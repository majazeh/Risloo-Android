package com.majazeh.risloo.Utils.Widgets;

import androidx.recyclerview.widget.RecyclerView;

public interface ItemTouchHelperAdapter {

    void onItemMoved(int fromPosition, int toPosition);

    void onItemSwiped(int position);

    void onItemSelect(RecyclerView.ViewHolder viewHolder);

    void onItemClear(RecyclerView.ViewHolder viewHolder);

}
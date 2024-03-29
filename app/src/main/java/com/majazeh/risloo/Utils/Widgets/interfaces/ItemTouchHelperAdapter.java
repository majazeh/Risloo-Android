package com.majazeh.risloo.utils.widgets.interfaces;

import androidx.recyclerview.widget.RecyclerView;

public interface ItemTouchHelperAdapter {

    void onItemMove(RecyclerView.ViewHolder viewHolder, int fromPosition, int toPosition);

    void onItemSwiped(RecyclerView.ViewHolder viewHolder, int position);

    void onItemSelect(RecyclerView.ViewHolder viewHolder);

    void onItemClear(RecyclerView.ViewHolder viewHolder);

}
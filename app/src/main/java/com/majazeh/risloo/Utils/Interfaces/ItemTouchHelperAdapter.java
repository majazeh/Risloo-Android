package com.majazeh.risloo.Utils.Interfaces;

import androidx.recyclerview.widget.RecyclerView;

public interface ItemTouchHelperAdapter {

    void onItemMove(int fromPosition, int toPosition);

    void onItemSwiped(int position);

    void onItemSelect(RecyclerView.ViewHolder viewHolder);

    void onItemClear(RecyclerView.ViewHolder viewHolder);

}
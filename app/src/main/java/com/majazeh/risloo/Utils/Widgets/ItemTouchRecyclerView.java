package com.majazeh.risloo.Utils.Widgets;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class ItemTouchRecyclerView extends ItemTouchHelper.SimpleCallback {

    // Objects
    private final OnItemTouchListener onItemTouchListener;

    public ItemTouchRecyclerView(int dragDirs, int swipeDirs, OnItemTouchListener onItemTouchListener) {
        super(dragDirs, swipeDirs);
        this.onItemTouchListener = onItemTouchListener;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        onItemTouchListener.onSwiped(viewHolder, direction, viewHolder.getAdapterPosition());
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return true;
    }

//    @Override
//    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
//        if (viewHolder != null) {
//            View foregroundView = (?????) viewHolder).foreGroundView;
//            getDefaultUIUtil().onSelected(foregroundView);
//        }
//    }
//
//    @Override
//    public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
//        View foregroundView = (?????) viewHolder).foreGroundView;
//        getDefaultUIUtil().clearView(foregroundView);
//    }
//
//    @Override
//    public void onChildDraw(@NonNull Canvas canvas, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
//        View foregroundView = (?????) viewHolder).foreGroundView;
//        getDefaultUIUtil().onDraw(canvas, recyclerView, foregroundView, dX, dY, actionState, isCurrentlyActive);
//    }
//
//    @Override
//    public void onChildDrawOver(@NonNull Canvas canvas, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
//        View foregroundView = (?????) viewHolder).foreGroundView;
//        getDefaultUIUtil().onDrawOver(canvas, recyclerView, foregroundView, dX, dY, actionState, isCurrentlyActive);
//    }

    @Override
    public int convertToAbsoluteDirection(int flags, int layoutDirection) {
        return super.convertToAbsoluteDirection(flags, layoutDirection);
    }

    public interface OnItemTouchListener {
        void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position);
    }

}
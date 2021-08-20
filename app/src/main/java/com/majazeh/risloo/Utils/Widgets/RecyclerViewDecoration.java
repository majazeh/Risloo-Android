package com.majazeh.risloo.Utils.Widgets;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewDecoration extends RecyclerView.ItemDecoration {

    // Vars
    private final String layoutManager;
    private final int marginTop, marginBottom, marginInner, marginSide;

    public RecyclerViewDecoration(String layoutManager, int marginTop, int marginBottom, int marginInner, int marginSide) {
        this.layoutManager = layoutManager;
        this.marginTop = marginTop;
        this.marginBottom = marginBottom;
        this.marginInner = marginInner;
        this.marginSide = marginSide;
    }

    @Override
    public void getItemOffsets(@NonNull Rect rect, @NonNull View view, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.State state) {
        super.getItemOffsets(rect, view, recyclerView, state);

        int position = recyclerView.getChildAdapterPosition(view);
        int count = state.getItemCount();

        switch (layoutManager) {
            case "verticalLayout":

                if (position == 0) {
                    if (marginTop != 0)
                        rect.top = marginTop;
                } else {
                    if (marginInner != 0)
                        rect.top = marginInner;
                }

                if (count > 0 && position == count - 1) {
                    if (marginBottom != 0)
                        rect.bottom = marginBottom;
                } else {
                    if (marginInner != 0)
                        rect.bottom = marginInner;
                }

                if (marginSide != 0) {
                    rect.right = marginSide;
                    rect.left = marginSide;
                }

                break;
            case "horizontalLayout":

                if (marginTop != 0)
                    rect.top = marginTop;

                if (marginBottom != 0)
                    rect.bottom = marginBottom;

                if (position == 0) {
                    if (marginSide != 0)
                        rect.right = marginSide;
                } else {
                    if (marginInner != 0)
                        rect.right = marginInner;
                }

                if (count > 0 && position == count - 1) {
                    if (marginSide != 0)
                        rect.left = marginSide;
                } else {
                    if (marginInner != 0)
                        rect.left = marginInner;
                }

                break;
            case "gridLayout":

                if (position == 0 || position == 1) {
                    if (marginTop != 0)
                        rect.top = marginTop;
                } else {
                    if (marginInner != 0)
                        rect.top = marginInner;
                }

                if (count > 0 && position == count - 1) {
                    if (marginBottom != 0)
                        rect.bottom = marginBottom;
                } else if (count > 0 && position == count - 2) {
                    if (position % 2 == 0) {
                        if (marginBottom != 0)
                            rect.bottom = marginBottom;
                    } else {
                        if (marginInner != 0)
                            rect.bottom = marginInner;
                    }
                } else {
                    if (marginInner != 0)
                        rect.bottom = marginInner;
                }

                if (position % 2 == 0) {
                    if (marginSide != 0)
                        rect.right = marginSide;

                    if (marginInner != 0)
                        rect.left = marginInner;
                } else {
                    if (marginInner != 0)
                        rect.right = marginInner;

                    if (marginSide != 0)
                        rect.left = marginSide;
                }

                break;
        }
    }

}
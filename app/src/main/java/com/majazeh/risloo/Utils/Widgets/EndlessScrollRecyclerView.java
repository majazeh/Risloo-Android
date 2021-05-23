package com.majazeh.risloo.Utils.Widgets;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

public abstract class EndlessScrollRecyclerView extends RecyclerView.OnScrollListener {

    // Objects
    private LinearLayoutManager layoutManager;

    // Vars
    private int previousTotal = 0, visibleThreshold = 15, currentPage = 1;
    private boolean loading = true;

    public EndlessScrollRecyclerView(LinearLayoutManager linearLayoutManager) {
        this.layoutManager = linearLayoutManager;
    }

    @Override
    public void onScrolled(@NotNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        int visibleItemCount = layoutManager.getChildCount();
        int totalItemCount = layoutManager.getItemCount();
        int pastVisiblesItems = layoutManager.findFirstVisibleItemPosition();
        if (loading) {
            if (totalItemCount > previousTotal) {
                loading = false;
                previousTotal = totalItemCount;
            }
        }

        if (!loading && (visibleItemCount + pastVisiblesItems) >= totalItemCount) {
            currentPage++;
            onLoadMore(currentPage);
            loading = true;
        }
    }

    public abstract void onLoadMore(int currentPage);

}
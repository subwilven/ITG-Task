package com.islam.basepropject.project_base.base.adapters;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.islam.basepropject.project_base.views.MyRecyclerView;

public class AdapterDataObservation extends RecyclerView.AdapterDataObserver {

    MyRecyclerView recyclerView;

    public AdapterDataObservation(MyRecyclerView recyclerView) {
        super();
        this.recyclerView = recyclerView;
    }

    private void checkAdapterHasData() {
        if (recyclerView.getAdapter().getItemCount() == 0)
            recyclerView.showEmptyView(true);
        else
            recyclerView.showEmptyView(false);
    }

    @Override
    public void onChanged() {
        super.onChanged();
        checkAdapterHasData();
    }

    @Override
    public void onItemRangeChanged(int positionStart, int itemCount) {
        super.onItemRangeChanged(positionStart, itemCount);
        checkAdapterHasData();
    }

    @Override
    public void onItemRangeChanged(int positionStart, int itemCount, @Nullable Object payload) {
        super.onItemRangeChanged(positionStart, itemCount, payload);
        checkAdapterHasData();
    }

    @Override
    public void onItemRangeInserted(int positionStart, int itemCount) {
        super.onItemRangeInserted(positionStart, itemCount);
        checkAdapterHasData();
    }

    @Override
    public void onItemRangeRemoved(int positionStart, int itemCount) {
        super.onItemRangeRemoved(positionStart, itemCount);
        checkAdapterHasData();
    }

    @Override
    public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
        super.onItemRangeMoved(fromPosition, toPosition, itemCount);
        checkAdapterHasData();
    }
}

package com.islam.basepropject.project_base.base.adapters;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;

import com.islam.basepropject.R;
import com.islam.basepropject.project_base.base.POJO.NetworkRequestState;
import com.islam.basepropject.project_base.views.MyRecyclerView;


public abstract class BasePagingAdapter<T,VH extends BaseViewHolder> extends PagedListAdapter<T,BaseViewHolder<T>> {



    private static final int TYPE_PROGRESS = 0;
    private static final int TYPE_ITEM = 1;

    private NetworkRequestState networkState;

    static DiffUtil.ItemCallback<Object> callback = new DiffUtil.ItemCallback<Object>() {
        @Override
        public boolean areItemsTheSame(@NonNull Object oldItem, @NonNull Object newItem) {
            return false;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Object oldItem, @NonNull Object newItem) {
            return false;
        }

    };

    protected BasePagingAdapter() {
        super((DiffUtil.ItemCallback<T>) callback);
    }

    public void registerAdapterDataObservertion(MyRecyclerView recyclerView) {
        registerAdapterDataObserver(new AdapterDataObservation(recyclerView));
    }


    public abstract VH getViewHolder(ViewGroup viewGroup);

    @NonNull
    @Override
    public BaseViewHolder<T> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_PROGRESS:
                return new LoadingViewHolder(parent);
            default:
                return getViewHolder(parent);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder<T> holder, int position) {
        holder.onBind(getItem(position));
    }

    @Override
    public int getItemViewType(int position) {
        if (hasExtraRow() && position == getItemCount() - 1) {
            return TYPE_PROGRESS;
        } else {
            return TYPE_ITEM;
        }
    }

    private boolean hasExtraRow() {
        if (networkState != null && networkState != NetworkRequestState.COMPLETE) {
            return true;
        } else {
            return false;
        }
    }

    public void setNetworkState(NetworkRequestState newNetworkState) {
        NetworkRequestState previousState = this.networkState;
        boolean previousExtraRow = hasExtraRow();
        this.networkState = newNetworkState;
        boolean newExtraRow = hasExtraRow();
        if (previousExtraRow != newExtraRow) {
            if (previousExtraRow) {
                notifyItemRemoved(getItemCount());
            } else {
                notifyItemInserted(getItemCount());
            }
        } else if (newExtraRow && previousState != newNetworkState) {
            notifyItemChanged(getItemCount() - 1);
        }
    }

    public class LoadingViewHolder extends BaseViewHolder<T> {

        public LoadingViewHolder(@NonNull ViewGroup itemView) {
            super(itemView, R.layout.item_loading);
        }


        @Override
        public void onBind(T item) {

        }
    }

}

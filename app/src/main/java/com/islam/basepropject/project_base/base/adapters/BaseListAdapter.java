package com.islam.basepropject.project_base.base.adapters;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

public abstract class BaseListAdapter<T, VH extends BaseViewHolder<T>> extends ListAdapter<T, VH> {

    protected BaseListAdapter(@NonNull DiffUtil.ItemCallback<T> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public abstract VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType);

    @Override
    public final void onBindViewHolder(@NonNull VH holder, int position) {
        holder.onBind(getItem(position));
    }

}

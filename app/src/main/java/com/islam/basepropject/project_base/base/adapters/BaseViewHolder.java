package com.islam.basepropject.project_base.base.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.islam.basepropject.R;

public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder {

    public BaseViewHolder(ViewGroup viewGroup,int layoutId) {
        super(LayoutInflater.from(viewGroup.getContext()).inflate(layoutId,viewGroup,false));
    }

    public abstract void onBind(T item);
}
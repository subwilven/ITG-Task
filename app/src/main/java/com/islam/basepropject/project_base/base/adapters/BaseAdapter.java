package com.islam.basepropject.project_base.base.adapters;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.islam.basepropject.project_base.views.MyRecyclerView;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseAdapter<T, VH extends BaseViewHolder<T>> extends RecyclerView.Adapter<VH> {

    protected List<T> list;

    public BaseAdapter() {
    }

    public BaseAdapter(List<T> list) {
        this.list = list;
    }

    public void registerAdapterDataObservertion(MyRecyclerView recyclerView) {
        registerAdapterDataObserver(new AdapterDataObservation(recyclerView));
    }

    @NonNull
    @Override
    public abstract VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType);

    @Override
    public final void onBindViewHolder(@NonNull VH holder, int position) {
        holder.onBind(list.get(position));
    }

    @Override
    public int getItemCount() {
        if (list != null)
            return list.size();
        return 0;
    }

    public void setData(List<T> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void addItem(T item) {
        initList();
        list.add(item);
        notifyItemInserted(getItemCount() - 1);
    }

    public void addItem(T item, int position) {
        initList();
        list.add(position, item);
        notifyItemInserted(position);
    }

    public void updateItem(T item) {
        int index = list.indexOf(item);
        if (index != -1)
            updateItem(item, index);
    }

    public void updateItem(T item, int position) {
        initList();
        list.set(position, item);
        notifyItemChanged(position);
    }

    public void removeItem(T item) {
        int index = list.indexOf(item);
        if (index != -1)
            removeItem(index);
    }

    public void removeItem(int position) {
        initList();
        list.remove(position);
        notifyItemRemoved(position);
    }

    public void clear() {
        initList();
        list.clear();
        notifyDataSetChanged();
    }

    private void initList() {
        if (list == null)
            list = new ArrayList<>();
    }
}

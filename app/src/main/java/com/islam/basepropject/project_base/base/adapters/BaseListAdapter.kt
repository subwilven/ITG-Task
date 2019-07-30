package com.islam.basepropject.project_base.base.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

import com.islam.basepropject.project_base.views.MyRecyclerView

abstract class BaseListAdapter<T, VH : BaseViewHolder<T>> protected constructor(diffCallback: DiffUtil.ItemCallback<T>) : ListAdapter<T, VH>(diffCallback) {

    fun registerAdapterDataObservertion(recyclerView: MyRecyclerView) {
        registerAdapterDataObserver(AdapterDataObservation(recyclerView))
    }

    abstract override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBind(getItem(position))
    }

}

package com.islam.basepropject.project_base.base.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import com.islam.basepropject.project_base.views.MyRecyclerView

import java.util.ArrayList

abstract class BaseAdapter<T, VH : BaseViewHolder<T>> : RecyclerView.Adapter<VH> {

    protected var list: MutableList<T>? = null

    constructor() {}

    constructor(list: MutableList<T>) {
        this.list = list
    }

    fun registerAdapterDataObservertion(recyclerView: MyRecyclerView) {
        registerAdapterDataObserver(AdapterDataObservation(recyclerView))
    }

    abstract override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBind(list!![position])
    }

    override fun getItemCount(): Int {
        return if (list != null) list!!.size else 0
    }

    fun setData(list: MutableList<T>) {
        this.list = list
        notifyDataSetChanged()
    }

    fun addItem(item: T) {
        initList()
        list!!.add(item)
        notifyItemInserted(itemCount - 1)
    }

    fun addItem(item: T, position: Int) {
        initList()
        list!!.add(position, item)
        notifyItemInserted(position)
    }

    fun updateItem(item: T) {
        val index = list!!.indexOf(item)
        if (index != -1)
            updateItem(item, index)
    }

    fun updateItem(item: T, position: Int) {
        initList()
        list!![position] = item
        notifyItemChanged(position)
    }

    fun removeItem(item: T) {
        val index = list!!.indexOf(item)
        if (index != -1)
            removeItem(index)
    }

    fun removeItem(position: Int) {
        initList()
        list!!.removeAt(position)
        notifyItemRemoved(position)
    }

    fun clear() {
        initList()
        list!!.clear()
        notifyDataSetChanged()
    }

    private fun initList() {
        if (list == null)
            list = ArrayList()
    }
}
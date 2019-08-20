package com.islam.basepropject.project_base.base.adapters

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil

import com.islam.basepropject.R
import com.islam.basepropject.project_base.POJO.NetworkRequestState
import com.islam.basepropject.project_base.views.MyRecyclerView


abstract class BasePagingAdapter<T, VH : BaseViewHolder<T>> protected constructor() : PagedListAdapter<T, BaseViewHolder<T>>(callback as DiffUtil.ItemCallback<T>) {

    private var networkState: NetworkRequestState? = null

    fun registerAdapterDataObservertion(recyclerView: MyRecyclerView) {
        registerAdapterDataObserver(AdapterDataObservation(recyclerView))
    }


    abstract fun getViewHolder(viewGroup: ViewGroup): VH

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T> {
        when (viewType) {
            TYPE_PROGRESS -> return LoadingViewHolder(parent)
            else -> return getViewHolder(parent)
        }

    }

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        holder.onBind(getItem(position)!!)
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            TYPE_PROGRESS
        } else {
            TYPE_ITEM
        }
    }

    private fun hasExtraRow(): Boolean {
        return if (networkState != null && networkState != NetworkRequestState.COMPLETE) {
            true
        } else {
            false
        }
    }

    fun setNetworkState(newNetworkState: NetworkRequestState) {
        val previousState = this.networkState
        val previousExtraRow = hasExtraRow()
        this.networkState = newNetworkState
        val newExtraRow = hasExtraRow()
        if (previousExtraRow != newExtraRow) {
            if (previousExtraRow) {
                notifyItemRemoved(itemCount)
            } else {
                notifyItemInserted(itemCount)
            }
        } else if (newExtraRow && previousState != newNetworkState) {
            notifyItemChanged(itemCount - 1)
        }
    }

    inner class LoadingViewHolder(itemView: ViewGroup) : BaseViewHolder<T>(itemView, R.layout.item_loading) {


        override fun onBind(item: T) {

        }
    }

    companion object {


        private val TYPE_PROGRESS = 0
        private val TYPE_ITEM = 1

        internal var callback: DiffUtil.ItemCallback<Any> = object : DiffUtil.ItemCallback<Any>() {
            override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
                return false
            }

            override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean {
                return false
            }

        }
    }

}

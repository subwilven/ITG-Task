package com.islam.basepropject.ui.ExRecyclerView

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.islam.basepropject.R
import com.islam.basepropject.project_base.base.adapters.BaseListAdapter
import com.islam.basepropject.project_base.base.adapters.BaseViewHolder
import kotlinx.android.synthetic.main.item_string.*


class Adapter : BaseListAdapter<String, Adapter.ViewHolder>(object : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }
}) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent, R.layout.item_string)
    }

    inner class ViewHolder(viewGroup: ViewGroup, layoutId: Int) : BaseViewHolder<String>(viewGroup, layoutId) {
        override fun onBind(item: String) {
            textView2.text = item
        }
    }
}
package com.islam.basepropject.ui.ExRecyclerView

import android.view.ViewGroup
import android.widget.TextView

import com.islam.basepropject.R
import com.islam.basepropject.project_base.base.adapters.BaseAdapter
import com.islam.basepropject.project_base.base.adapters.BaseViewHolder


class Adapter : BaseAdapter<String, Adapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent, R.layout.item_string)
    }

    inner class ViewHolder(viewGroup: ViewGroup, layoutId: Int) : BaseViewHolder<String>(viewGroup, layoutId) {
        internal var textView: TextView

        init {
            textView = itemBaseView.findViewById(R.id.textView2)
        }

        override fun onBind(item: String) {
            textView.text = item
        }
    }
}
package com.islam.basepropject.ui.ExRecyclerView

import android.view.ViewGroup
import com.islam.basepropject.R
import com.islam.basepropject.project_base.base.adapters.BaseAdapter
import com.islam.basepropject.project_base.base.adapters.BaseViewHolder
import kotlinx.android.synthetic.main.item_string.*


class Adapter : BaseAdapter<String, Adapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent, R.layout.item_string)
    }

    inner class ViewHolder(viewGroup: ViewGroup, layoutId: Int) : BaseViewHolder<String>(viewGroup, layoutId) {
        override fun onBind(item: String) {
            textView2.text = item
        }
    }
}
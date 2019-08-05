package com.islam.basepropject.ui.ExPaging

import android.view.ViewGroup
import android.widget.TextView

import com.islam.basepropject.R
import com.islam.basepropject.project_base.base.adapters.BasePagingAdapter
import com.islam.basepropject.project_base.base.adapters.BaseViewHolder
import kotlinx.android.synthetic.main.item_string.*

class ExAdapter : BasePagingAdapter<String, ExAdapter.ViewHolder>() {


    override fun getViewHolder(viewGroup: ViewGroup): ViewHolder {
        return ViewHolder(viewGroup, R.layout.item_string)
    }


    inner class ViewHolder(viewGroup: ViewGroup, layoutId: Int) : BaseViewHolder<String>(viewGroup, layoutId) {
        override fun onBind(item: String) {
            textView2.text = item
        }
    }
}

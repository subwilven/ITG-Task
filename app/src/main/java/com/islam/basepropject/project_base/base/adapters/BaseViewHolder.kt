package com.islam.basepropject.project_base.base.adapters

import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


abstract class BaseViewHolder<T>(viewGroup: ViewGroup, layoutId: Int) : RecyclerView.ViewHolder((LayoutInflater.from(viewGroup.context).inflate(layoutId, viewGroup, false))) {

    init {
        super.itemView.isClickable =true
        super.itemView.addRippleEffect()
    }

    abstract fun onBind(item: T)


    private fun View.addRippleEffect() = with(TypedValue()) {
        context.theme.resolveAttribute(android.R.attr.selectableItemBackground, this, true)
        setBackgroundResource(resourceId)
    }
}
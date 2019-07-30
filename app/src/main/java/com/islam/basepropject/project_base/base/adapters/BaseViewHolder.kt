package com.islam.basepropject.project_base.base.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView

import com.islam.basepropject.R

abstract class BaseViewHolder<T>(viewGroup: ViewGroup, layoutId: Int) :
        RecyclerView.ViewHolder(LayoutInflater.from(viewGroup.context).inflate(layoutId, viewGroup, false)) {

    val itemBaseView: View
        get() { return super.itemView}

    abstract fun onBind(item: T)
}
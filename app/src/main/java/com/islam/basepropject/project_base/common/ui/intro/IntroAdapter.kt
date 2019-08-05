package com.islam.basepropject.project_base.common.ui.intro


import android.view.ViewGroup
import com.islam.basepropject.R
import com.islam.basepropject.pojo.Intro
import com.islam.basepropject.project_base.base.adapters.BaseAdapter
import com.islam.basepropject.project_base.base.adapters.BaseViewHolder

class IntroAdapter(list: MutableList<Intro>) : BaseAdapter<Intro, IntroAdapter.ViewHolder>(list) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent, R.layout.item_intro)
    }


    inner class ViewHolder(viewGroup: ViewGroup, layoutId: Int) : BaseViewHolder<Intro>(viewGroup, layoutId) {

        init{

        }
        override fun onBind(item: Intro) {

        }

    }
}
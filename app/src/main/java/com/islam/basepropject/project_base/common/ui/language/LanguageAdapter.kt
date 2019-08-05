package com.islam.basepropject.project_base.common.ui.language

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.islam.basepropject.R
import com.islam.basepropject.project_base.base.adapters.BaseAdapter
import com.islam.basepropject.project_base.base.adapters.BaseViewHolder
import kotlinx.android.synthetic.main.item_language.*


class LanguageAdapter(viewModel: LanguageViewModel, stringArray: Array<String>?) : BaseAdapter<String, LanguageAdapter.ViewHolder>(stringArray!!.toMutableList()) {

    val mViewModel :LanguageViewModel = viewModel

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent, R.layout.item_language)
    }

    inner class ViewHolder(viewGroup: ViewGroup, layoutId: Int) : BaseViewHolder<String>(viewGroup, layoutId),View.OnClickListener{
        init {
            itemView.setOnClickListener(this)
        }

        override fun onBind(item: String) {
            tv_language.text =item
        }

        override fun onClick(p0: View?) {
            mViewModel.onLanguageClicked(adapterPosition)
        }
    }
}
package com.islam.task.ui.marvels

import android.view.View
import android.view.ViewGroup
import com.islam.task.R
import com.islam.task.pojo.Marvel
import com.islam.task.project_base.base.adapters.BasePagingAdapter
import com.islam.task.project_base.base.adapters.BaseViewHolder
import com.islam.task.project_base.utils.ImageHandler.loadImage
import kotlinx.android.synthetic.main.item_marvel.*
import kotlinx.android.synthetic.main.item_string.*

class MarvelsAdapter(val viewModel: MarvelsViewModel) : BasePagingAdapter<Marvel, MarvelsAdapter.ViewHolder>() {


    override fun getViewHolder(viewGroup: ViewGroup): ViewHolder {
        return ViewHolder(viewGroup, R.layout.item_marvel)
    }


    inner class ViewHolder(viewGroup: ViewGroup, layoutId: Int) : BaseViewHolder<Marvel>(viewGroup, layoutId), View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        override fun onBind(item: Marvel) {
            iv_marvel_thumb.loadImage(item.thumbnail.path + "." + item.thumbnail.extension)
            tv_marvel_name.text = item.name
        }

        override fun onClick(p0: View?) {
            viewModel.navigateToMarvelDetails.value = getItem(adapterPosition)
        }
    }
}

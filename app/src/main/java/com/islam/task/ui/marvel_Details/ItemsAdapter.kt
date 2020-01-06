package com.islam.task.ui.marvel_Details

import android.view.View
import android.view.ViewGroup
import com.islam.task.R
import com.islam.task.pojo.Item
import com.islam.task.project_base.base.adapters.BaseAdapter
import com.islam.task.project_base.base.adapters.BaseViewHolder
import com.islam.task.ui.marvels.MarvelsAdapter
import com.islam.task.project_base.utils.ImageHandler.loadImage
import kotlinx.android.synthetic.main.item_marvel_details.*


class ItemsAdapter(val viewModel: MarvelDetailViewModel) : BaseAdapter<Item, ItemsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent, R.layout.item_marvel_details)
    }


    inner class ViewHolder(viewGroup: ViewGroup, layoutId: Int) : BaseViewHolder<Item>(viewGroup, layoutId), View.OnClickListener {


        init {
            itemView.setOnClickListener(this)
        }

        override fun onBind(item: Item) {
            item.thumbnail?.let {
                iv_details_thumbnail.loadImage(item.thumbnail.path + "." + item.thumbnail.extension)
            }
        }


        override fun onClick(p0: View?) {
            viewModel.showImageFlipper.value = Pair(adapterPosition,list!!.toMutableList())
        }

    }
}
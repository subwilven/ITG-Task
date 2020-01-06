package com.islam.task.ui.marvel_Details

import android.view.ViewGroup
import com.islam.task.R
import com.islam.task.pojo.Item
import com.islam.task.project_base.base.adapters.BaseAdapter
import com.islam.task.project_base.base.adapters.BaseViewHolder
import com.islam.task.project_base.utils.ImageHandler.loadImage
import kotlinx.android.synthetic.main.item_flipper.*
import kotlinx.android.synthetic.main.item_marvel_details.*

class ItemsFlipperAdapter : BaseAdapter<Item, ItemsFlipperAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent, R.layout.item_flipper)
    }


    inner class ViewHolder(viewGroup: ViewGroup, layoutId: Int) : BaseViewHolder<Item>(viewGroup, layoutId) {
        override fun onBind(item: Item) {
                imageView5.loadImage(item.thumbnail.path + "." + item.thumbnail.extension)
        }

    }
}
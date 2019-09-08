package com.islam.basepropject.ui.ExPaging

import android.view.View
import android.view.ViewGroup
import com.islam.basepropject.R
import com.islam.basepropject.pojo.Order
import com.islam.basepropject.project_base.POJO.AdatperItemLoading
import com.islam.basepropject.project_base.base.adapters.BasePagingAdapter
import com.islam.basepropject.project_base.base.adapters.BaseViewHolder
import com.islam.basepropject.project_base.views.OnViewStatusChange
import kotlinx.android.synthetic.main.item_string.*

class ExAdapter(val mViewModel: ExViewModel) : BasePagingAdapter<Order, ExAdapter.ViewHolder>() {


    override fun getViewHolder(viewGroup: ViewGroup): ViewHolder {
        return ViewHolder(viewGroup, R.layout.item_string)
    }


    inner class ViewHolder(viewGroup: ViewGroup, layoutId: Int) : BaseViewHolder<Order>(viewGroup, layoutId), View.OnClickListener {


        override fun onBind(item: Order) {
            textView2.text = item.title
            if(item.isLoading){
                (btn_submit as OnViewStatusChange).showLoading(true)
            }else{
                (btn_submit as OnViewStatusChange).showLoading(false)
                btn_submit.text = "Add to cart"
            }

            btn_submit.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            mViewModel.addToCart(getItem(adapterPosition)!! )
        }
    }
}

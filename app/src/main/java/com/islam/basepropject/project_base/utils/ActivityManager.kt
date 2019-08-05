package com.islam.basepropject.project_base.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView

import com.islam.basepropject.MyApplication
import com.islam.basepropject.project_base.base.POJO.Message
import com.islam.basepropject.project_base.base.adapters.BaseViewHolder

object ActivityManager {

    fun setVisibility(v: Int, vararg views: View?) {
        for (view in views) {
            if (view != null)
                view.visibility = v
        }
    }

    fun showToastShort(context :Context?,message: Message) {
        showToast(context,message.getValue(context), Toast.LENGTH_SHORT)
    }

    fun showToastLong(context :Context?,message: Message) {
        showToast(context,message.getValue(context), Toast.LENGTH_LONG)
    }

    private fun showToast(context :Context?,string: String, duration: Int) {
        Toast.makeText(context, string, duration).show()
    }

    fun <T : View> Activity.bind(@IdRes idRes: Int): Lazy<T> {
        return unsafeLazy { findViewById<T>(idRes) }
    }

    fun <T : View> View.bind(@IdRes idRes: Int): Lazy<T> {
        return unsafeLazy { findViewById<T>(idRes) }
    }

    fun <T : View> ViewGroup.bind(@IdRes idRes: Int): Lazy<T> {
        return unsafeLazy { findViewById<T>(idRes) }
    }

    fun <T : View> RecyclerView.ViewHolder.bind(@IdRes idRes: Int): Lazy<T> {
        return unsafeLazy { itemView.findViewById<T>(idRes) }
    }

    private fun <T> unsafeLazy(initializer: () -> T) = lazy(LazyThreadSafetyMode.NONE, initializer)

}

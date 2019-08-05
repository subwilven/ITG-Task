package com.islam.basepropject.project_base.utils

import android.content.Context
import android.view.View
import android.widget.Toast
import com.islam.basepropject.project_base.base.POJO.Message

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

}

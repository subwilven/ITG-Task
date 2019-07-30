package com.islam.basepropject.project_base.utils

import android.content.Context
import android.view.View
import android.widget.Toast

import com.islam.basepropject.MyApplication

object ActivityManager {

    fun setVisibility(v: Int, vararg views: View?) {
        for (view in views) {
            if (view != null)
                view.visibility = v
        }
    }

    fun showToastShort(stringId: Int) {
        val context = MyApplication.instance!!.getApplicationContext()
        showToast(context.getString(stringId), Toast.LENGTH_SHORT)
    }

    fun showToastLong(stringId: Int) {
        val context = MyApplication.instance!!.getApplicationContext()
        showToast(context.getString(stringId), Toast.LENGTH_LONG)
    }

    fun showToastShort(string: String) {
        showToast(string, Toast.LENGTH_SHORT)
    }

    fun showToastLong(string: String) {
        showToast(string, Toast.LENGTH_LONG)
    }

    private fun showToast(string: String, duration: Int) {
        Toast.makeText(MyApplication.instance!!.getApplicationContext(), string, duration).show()
    }
}

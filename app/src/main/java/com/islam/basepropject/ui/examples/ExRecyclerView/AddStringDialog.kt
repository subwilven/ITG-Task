package com.islam.basepropject.ui.examples.ExRecyclerView

import android.os.Bundle
import android.view.View
import android.widget.Button
import com.google.android.material.textfield.TextInputLayout
import com.islam.basepropject.R
import com.islam.basepropject.project_base.base.dialogs.BaseCustomDialog
import com.islam.basepropject.project_base.utils.ActivityManager.getText

class AddStringDialog :BaseCustomDialog(){
    override var layoutId = R.layout.dialog_add_string

    override fun onDialogCreated(view: View?, savedInstanceState: Bundle?) {
        val editText = view?.findViewById<TextInputLayout>(R.id.textInputLayout)
        val button = view?.findViewById<Button>(R.id.button)
        button?.setOnClickListener{
            (mViewModel!! as ViewModel).run{
                val data = this.data.value
                val newData = mutableListOf<String>()
                newData.addAll(data!!)
                newData.add(editText?.getText()!!)
                this.data.value = newData
            }
        }
    }

}
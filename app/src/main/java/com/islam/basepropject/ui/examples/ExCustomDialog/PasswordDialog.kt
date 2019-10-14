package com.islam.basepropject.ui.examples.ExCustomDialog

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.islam.basepropject.R
import com.islam.basepropject.project_base.base.dialogs.BaseCustomDialog

class PasswordDialog : BaseCustomDialog() {


    override var layoutId = R.layout.dialog_custom

    override fun onDialogCreated(view: View?, savedInstanceState: Bundle?) {

        val btn = view?.findViewById<Button>(R.id.btn_confirm)
        val textView = view?.findViewById<TextView>(R.id.textView5)

        btn?.apply{text = "ffsafsf"}
        textView?.apply{text = "ffsafsf"}
    }


}
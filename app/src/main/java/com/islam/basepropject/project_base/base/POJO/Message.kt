package com.islam.basepropject.project_base.base.POJO

import android.content.Context
import com.islam.basepropject.MyApplication

class Message {
    var text: String = ""
    var resource: Int = -1

    constructor(text: String) {
        this.text = text
    }

    constructor(resource: Int) {
        this.resource = resource
    }

    fun getValue(context :Context?) :String{
        if (text.isNotEmpty())
            return text
        else
            return context?.getString(resource) ?:""
    }


}
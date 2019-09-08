package com.islam.basepropject.pojo

import com.islam.basepropject.project_base.POJO.AdatperItemLoading

data class Order(  var title :String,override var isLoading: Boolean = false) : AdatperItemLoading
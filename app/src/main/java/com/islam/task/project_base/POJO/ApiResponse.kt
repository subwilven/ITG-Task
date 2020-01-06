package com.islam.task.project_base.POJO

import com.islam.task.pojo.Marvel

data class ApiResponse<T> (val status:String,val data: MarvelResponse<T>)
data class MarvelResponse<T>(val results:T)
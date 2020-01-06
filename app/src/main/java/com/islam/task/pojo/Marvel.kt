package com.islam.task.pojo

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

data class Marvel(val id:Int,val name:String,val description:String,val thumbnail:Thumbnail):Serializable

data class Thumbnail(val path:String,val extension :String):Serializable

@Parcelize
data class Item(val id: Int,val title:String,val thumbnail:Thumbnail):Serializable, Parcelable

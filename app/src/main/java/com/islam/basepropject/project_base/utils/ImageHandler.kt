package com.islam.basepropject.project_base.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView

import androidx.annotation.DrawableRes

import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder

import java.io.File

//TODO Setup Monthly Schedule for Cleaning or Setup Cache Limit
object ImageHandler {

    private fun initGlideinstance(context: Context, url: String): RequestBuilder<Drawable> {
        return Glide.with(context).load(url)
    }

    private fun initGlideinstance(context: Context, file: File): RequestBuilder<Drawable> {
        return Glide.with(context).load(file)
    }

    @JvmOverloads
    fun loadImageFromFile(imageView: ImageView, file: File, size: Int = -1) {
        val requestBuilder = initGlideinstance(imageView.context, file)

        if (size != -1)
            requestBuilder.override(size)

        requestBuilder.into(imageView)
    }

    @JvmOverloads
    fun loadImageFromNetwork(imageView: ImageView, url: String, @DrawableRes placeHolder: Int = -1, @DrawableRes error: Int = -1, size: Int = -1) {

        val requestBuilder = initGlideinstance(imageView.context, url)

        if (size != -1)
            requestBuilder.override(size)
        if (placeHolder != -1)
            requestBuilder.placeholder(placeHolder)
        if (error != -1)
            requestBuilder.error(error)

        requestBuilder.into(imageView)
    }

}

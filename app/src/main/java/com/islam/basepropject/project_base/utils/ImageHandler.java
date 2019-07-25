package com.islam.basepropject.project_base.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;

import java.io.File;

//TODO Setup Monthly Schedule for Cleaning or Setup Cache Limit
public final class ImageHandler {

    private static RequestBuilder<Drawable> initGlideinstance(Context context, String url) {
        return Glide.with(context).load(url);
    }

    private static RequestBuilder<Drawable> initGlideinstance(Context context, File file) {
        return Glide.with(context).load(file);
    }

    public static void loadImageFromFile(ImageView imageView, File file) {
        loadImageFromFile(imageView, file, -1);
    }

    public static void loadImageFromFile(ImageView imageView, File file, int size) {
        RequestBuilder<Drawable> requestBuilder = initGlideinstance(imageView.getContext(), file);

        if (size != -1)
            requestBuilder.override(size);

        requestBuilder.into(imageView);
    }


    public static void loadImageFromNetwork(ImageView imageView, String url) {
        loadImageFromNetwork(imageView, url, -1, -1, -1);
    }

    public static void loadImageFromNetwork(ImageView imageView, String url, @DrawableRes int placeHolder, @DrawableRes int error) {
        loadImageFromNetwork(imageView, url, placeHolder, error, -1);
    }

    public static void loadImageFromNetwork(ImageView imageView, String url, @DrawableRes int placeHolder, @DrawableRes int error, int size) {

        RequestBuilder<Drawable> requestBuilder = initGlideinstance(imageView.getContext(), url);

        if (size != -1)
            requestBuilder.override(size);
        if (placeHolder != -1)
            requestBuilder.placeholder(placeHolder);
        if (error != -1)
            requestBuilder.error(error);

        requestBuilder.into(imageView);
    }

}

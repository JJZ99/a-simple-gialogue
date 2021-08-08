package com.example.abcdialogue.Weibo.Util

import android.net.Uri
import android.widget.ImageView
import com.example.abcdialogue.R
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.view.SimpleDraweeView

object FrescoUtil {

    const val DEFAULT_URL = "https://www.baidu.com/img/PCtm_d9c8750bed0b3c7d089fa7d55720d6cf.png"

    fun loadImage(imageView: SimpleDraweeView, url: String?) {

        val uri =  if (url?.isNotEmpty() == true) Uri.parse(url) else Uri.parse(DEFAULT_URL)

        var controller = Fresco.newDraweeControllerBuilder()
            .setUri(uri)
            .setAutoPlayAnimations(true)
            .build()
        imageView.controller = controller;
    }
}
package com.example.abcdialogue.Weibo.Util

import android.graphics.Color
import android.net.Uri
import android.widget.ImageView
import com.example.abcdialogue.MyApplication
import com.example.abcdialogue.R
import com.example.abcdialogue.Util.DisplayUtil
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.drawable.ScalingUtils
import com.facebook.drawee.view.SimpleDraweeView
import com.facebook.drawee.generic.GenericDraweeHierarchy

import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder
import com.facebook.drawee.generic.RoundingParams


object FrescoUtil {

    const val DEFAULT_URL ="https://img.sj33.cn/uploads/202009/7-200913160953c5.jpg"
    const val DEFAULT_URL_BAIDU = "https://profile.csdnimg.cn/9/1/2/3_zzf0521"
    val builder = GenericDraweeHierarchyBuilder(MyApplication.context.resources)

    /**
     * 加载图片，这个用在xml中图片的加载
     */
    fun loadImage(imageView: SimpleDraweeView, url: String = DEFAULT_URL_BAIDU) {

        val uri =  if (url?.isNotEmpty()) Uri.parse(url) else Uri.parse(DEFAULT_URL_BAIDU)
        var controller = Fresco.newDraweeControllerBuilder()
            .setUri(uri)
            .setAutoPlayAnimations(true)
            .build()
        imageView.controller = controller
    }
    /**
     * 加载图片，这个用在xml中图片的加载
     */
    fun loadImageAddCircle(imageView: SimpleDraweeView, url: String = DEFAULT_URL_BAIDU) {
        val rp = RoundingParams()
        //设置图像是否为圆形
        rp.roundAsCircle = true
        //设置圆环的颜色和粗细
        //rp.setBorder(Color.Red,10F);
        imageView.hierarchy = builder.setRoundingParams(rp).build()
        val uri =  if (url?.isNotEmpty()) Uri.parse(url) else Uri.parse(DEFAULT_URL_BAIDU)
        var controller = Fresco.newDraweeControllerBuilder()
            .setUri(uri)
            .setAutoPlayAnimations(true)
            .build()
        imageView.controller = controller
    }

    /**
     * 删除图片
     */
    fun removeImageCache(url:String){
        val uri =  if (url?.isNotEmpty()) Uri.parse(url) else return
        var imagePipeline = Fresco.getImagePipeline()
        imagePipeline.also{

            it.evictFromMemoryCache(uri)
            it.evictFromDiskCache(uri)
            //这一行相当于上面的两行合并
            //it.evictFromCache(uri)
        }
    }
    fun loadImageAddSize(
        imageView: SimpleDraweeView,
        url: String = DEFAULT_URL,
        width: Int =112,
        ratio: Float = 1f,
    ) {
//        imageView.post{
//            imageView.layoutParams.apply {
//                this.width = DisplayUtil.dp2px(width)
//            }
//        }
        imageView.hierarchy = FrescoUtil.builder
            //淡入淡出的持续时间
            .setFadeDuration(300)
            .setPlaceholderImage(R.drawable.loading_image, null)
            .setFailureImage(R.mipmap.reload_click)
            //设置缩放类型
            .setActualImageScaleType(ScalingUtils.ScaleType.CENTER_CROP)
            //设置圆角
            .setRoundingParams(RoundingParams.fromCornersRadius(10F))
            .build()


        loadImage(imageView, url)
    }
}
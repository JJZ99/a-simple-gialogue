package com.example.abcdialogue.Weibo.Util

import android.content.Context
import android.graphics.Color
import android.widget.ImageView
import com.example.abcdialogue.MyApplication
import com.example.abcdialogue.MyApplication.Companion.context
import com.example.abcdialogue.R
import com.example.abcdialogue.Weibo.Util.ToastUtil.toastInfo
import com.hitomi.tilibrary.style.progress.ProgressPieIndicator
import com.hitomi.tilibrary.transfer.TransferConfig
import com.hitomi.tilibrary.transfer.Transferee
import com.vansz.glideimageloader.GlideImageLoader
import kotlinx.android.synthetic.main.activity_main2.transfer_image

/**
 * transferee工厂，仅生产transferee实例
 * from: https://github.com/Hitomis/transferee
 */
object TransfereeFactory {
    fun getTransfer(context: Context, imageView: ImageView, url: String): Transferee {
        val transfer = Transferee.getDefault(context)
        var config = TransferConfig.build()
            .setMissPlaceHolder(R.mipmap.loading_image) // 资源加载前的占位图
            .setErrorPlaceHolder(R.mipmap.reload_click) // 资源加载错误后的占位图
            .setProgressIndicator(ProgressPieIndicator()) // 资源加载进度指示器, 可以实现 IProgressIndicator 扩展
            .setImageLoader(GlideImageLoader.with(context)) // 图片加载器，可以实现 ImageLoader 扩展
            .setBackgroundColor(Color.parseColor("#FFFFFF")) // 背景色
            .setDuration(300) // 开启、关闭、手势拖拽关闭、显示、扩散消失等动画时长
            .setOffscreenPageLimit(2) // 第一次初始化或者切换页面时预加载资源的数量，与 justLoadHitImage 属性冲突，默认为 1
            .enableJustLoadHitPage(true) // 是否只加载当前显示在屏幕中的的资源，默认关闭
            .enableDragClose(true) // 是否开启下拉手势关闭，默认开启
            .enableDragHide(false) // 下拉拖拽关闭时，是否先隐藏页面上除主视图以外的其他视图，默认开启
            .enableDragPause(false) // 下拉拖拽关闭时，如果当前是视频，是否暂停播放，默认关闭
            .enableHideThumb(false) // 是否开启当 transferee 打开时，隐藏缩略图, 默认关闭
            .enableScrollingWithPageChange(false) // 是否启动列表随着页面的切换而滚动你的列表，默认关闭
            .setOnLongClickListener(object : Transferee.OnTransfereeLongClickListener {
                override fun onLongClick(imageView: ImageView?, imageUri: String?, pos: Int) {
                    "长按监听需要实现".toastInfo()
                }
            })
            .bindImageView(imageView, url) // 绑定一个 ImageView, 所有绑定方法只能调用一个

        return transfer.apply(config);


    }


}
package com.example.abcdialogue.Weibo.Util

import android.content.ContentValues
import android.content.Context
import android.content.DialogInterface
import android.graphics.Bitmap
import android.graphics.Color
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.abcdialogue.R
import com.example.abcdialogue.Weibo.Adapter.MyRecyclerAdapter
import com.example.abcdialogue.Weibo.Util.ToastUtil.toastError
import com.example.abcdialogue.Weibo.Util.ToastUtil.toastInfo
import com.example.abcdialogue.Weibo.Util.ToastUtil.toastSuccess
import com.hitomi.tilibrary.style.progress.ProgressPieIndicator
import com.hitomi.tilibrary.transfer.TransferConfig
import com.hitomi.tilibrary.transfer.Transferee
import com.vansz.glideimageloader.GlideImageLoader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * transferee工厂，生产transferee实例和设置长按的监听事件
 * from: https://github.com/Hitomis/transferee
 */
object TransfereeFactory {

    var transfersMap  = mutableMapOf<String,Transferee>()

    fun getTransfer(
        fragment: Fragment,
        imageView: ImageView,
        url: String,
        onDeleteImageListener: MyRecyclerAdapter.OnDeleteImageListener?,
        pos: Int,
        index: Int
    ): Transferee {
        Log.i("getTransfer imageViewUrl",url)
        if (transfersMap.containsKey(url)){
            return transfersMap.getValue(url)
        }else{
            val transfer = Transferee.getDefault(fragment.context)
            var config = TransferConfig.build()
                .setMissPlaceHolder(R.mipmap.loading_image) // 资源加载前的占位图
                .setErrorPlaceHolder(R.mipmap.reload_click) // 资源加载错误后的占位图
                .setProgressIndicator(ProgressPieIndicator()) // 资源加载进度指示器, 可以实现 IProgressIndicator 扩展
                .setImageLoader(GlideImageLoader.with(fragment.context)) // 图片加载器，可以实现 ImageLoader 扩展
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
                    override fun onLongClick(imageView: ImageView?, imageUrl: String?, pos: Int) {
                        var arr2 = arrayOf("保存到相册","从视图移除")
                        var builder2 : AlertDialog.Builder = AlertDialog.Builder(fragment.requireContext())
                        builder2.setTitle("矮油喂")
                        //设置不可被注销!!,只有点击了item才会消失
                        builder2.setCancelable(false)
                        builder2.setItems(arr2,object : DialogInterface.OnClickListener {
                            override fun onClick(dialog: DialogInterface?, which: Int) {
                                when(which){
                                    0 -> {
                                        "你触发了保存到本地事件".toastInfo()
                                        fragment.viewLifecycleOwner.lifecycleScope.launch {
                                            savePhoto(imageView,fragment.requireContext())
                                        }
                                    }
                                    1 -> {
                                        "你触发了图片删除事件".toastInfo()
                                        onDeleteImageListener.onDeleteImageListener()
                                        //移除map存储的内容
                                        transfersMap.remove(url)
                                    }
                                }
                            }
                        })
                        var dialog = builder2.create()
                        dialog.show()
                    }
                })
                .bindImageView(imageView, url) // 绑定一个 ImageView, 所有绑定方法只能调用一个
            transfersMap[url] = transfer
            return transfer.apply(config);
        }
    }

    private suspend fun savePhoto(imageView: ImageView?, context: Context) {
        withContext(Dispatchers.IO){
            val bitmap = imageView!!.drawable.toBitmap()
            val saveUri = context.contentResolver.insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                ContentValues()
            )?: kotlin.run {
                "存储失败".toastError()
                return@withContext
            }
            context.contentResolver.openOutputStream(saveUri).use {
                if (bitmap.compress(Bitmap.CompressFormat.JPEG,100,it)) {
                    MainScope().launch {"存储成功".toastSuccess()}
                } else {
                    MainScope().launch {"存储失败".toastError()}
                }
            }
        }

    }




}
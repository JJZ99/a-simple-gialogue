package com.example.abcdialogue.Weibo.Util

import android.content.ContentValues
import android.content.Context
import android.content.DialogInterface
import android.graphics.Bitmap
import android.graphics.Color
import android.provider.MediaStore
import android.util.Log
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.viewpager.widget.ViewPager
import com.example.abcdialogue.R
import com.example.abcdialogue.Weibo.Adapter.MyRecyclerAdapter
import com.example.abcdialogue.Weibo.Util.ToastUtil.toastError
import com.example.abcdialogue.Weibo.Util.ToastUtil.toastInfo
import com.example.abcdialogue.Weibo.Util.ToastUtil.toastSuccess
import com.hitomi.tilibrary.style.IIndexIndicator
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
        position: Int,
        index: Int
    ): Transferee {
      //  Log.i("infoRemoveGetTransfer",index.toString())

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
                .enableJustLoadHitPage(false) // 是否只加载当前显示在屏幕中的的资源，默认关闭
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
                        //builder2.setCancelable(false)
                        builder2.setItems(arr2,object : DialogInterface.OnClickListener {
                            override fun onClick(dialog: DialogInterface?, which: Int) {
                                when(which){
                                    0 -> {
                                        //"你触发了保存到本地事件".toastInfo()
                                        Log.i("线程aid",Thread.currentThread().id.toString())
                                        //CoroutineScope绑定到这个LifecycleOwner的生命周期,即作用域。 当生命周期被销毁时，这个范围将被取消
                                        //你可以简单的理解为在协程作用域中创建一个新的协程，然后执行挂起函数
                                        fragment.viewLifecycleOwner.lifecycleScope.launch {
                                            Log.i("线程bid",Thread.currentThread().id.toString())
                                            savePhoto(imageView,fragment.requireContext())
                                        }
                                    }
                                    1 -> {
                                        //"你触发了图片删除回调事件".toastInfo()
                                        onDeleteImageListener?.let {
                                            it.onDeleteImageListener(position,imageUrl)
                                        }
                                        //如果你不希望在移除后把当前的大图关闭，请把下面三行注释
                                        transfer.dismiss()   //先关闭transfer
                                        transfer.clear()   //然后清楚图片缓存
                                        transfer.destroy() //销毁资源
                                        //移除KV
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

    fun getTransferRecycler(
        fragment: Fragment,
        pictures: List<String>,
        onDeleteImageListener: MyRecyclerAdapter.OnDeleteImageListener?,
        position :Int,
        index: Int
    ): Transferee {
            val transfer = Transferee.getDefault(fragment.context)
            var config = TransferConfig.build()
                .setSourceUrlList(pictures)
                .setNowThumbnailIndex(index)
                .setMissPlaceHolder(R.mipmap.loading_image) // 资源加载前的占位图
                .setErrorPlaceHolder(R.mipmap.reload_click) // 资源加载错误后的占位图
                .setProgressIndicator(ProgressPieIndicator()) // 资源加载进度指示器, 可以实现 IProgressIndicator 扩展
                .setImageLoader(GlideImageLoader.with(fragment.context)) // 图片加载器，可以实现 ImageLoader 扩展
                .setBackgroundColor(Color.parseColor("#FFFFFF")) // 背景色
                .setDuration(300) // 开启、关闭、手势拖拽关闭、显示、扩散消失等动画时长
                .setOffscreenPageLimit(2) // 第一次初始化或者切换页面时预加载资源的数量，与 justLoadHitImage 属性冲突，默认为 1
                .enableJustLoadHitPage(false) // 是否只加载当前显示在屏幕中的的资源，默认关闭
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
                        //builder2.setCancelable(false)
                        builder2.setItems(arr2,object : DialogInterface.OnClickListener {
                            override fun onClick(dialog: DialogInterface?, which: Int) {
                                when(which){
                                    0 -> {
                                        //"你触发了保存到本地事件".toastInfo()
                                        Log.i("线程aid",Thread.currentThread().id.toString())
                                        //CoroutineScope绑定到这个LifecycleOwner的生命周期,即作用域。 当生命周期被销毁时，这个范围将被取消
                                        //你可以简单的理解为在协程作用域中创建一个新的协程，然后执行挂起函数
                                        fragment.viewLifecycleOwner.lifecycleScope.launch {
                                            Log.i("线程bid",Thread.currentThread().id.toString())
                                            savePhoto(imageView,fragment.requireContext())
                                        }
                                    }
                                    1 -> {
                                        //"你触发了图片删除回调事件".toastInfo()
                                        onDeleteImageListener?.let {
                                            if (pictures.size==1){
                                                transfer.dismiss()   //先关闭transfer
                                                transfer.clear()   //然后清楚图片缓存
                                                transfer.destroy() //销毁资源
                                            }
                                            it.onDeleteImageListener(position,imageUrl)

                                        }
                                    }
                                }
                            }
                        })
                        var dialog = builder2.create()
                        dialog.show()
                    }
                })
                .create()
            return transfer.apply(config);

    }
    /**
     * 挂起
     */
    private suspend fun savePhoto(imageView: ImageView?, context: Context) {
        //因为保存图片是磁盘或网络I/O操作，所以我们使用Dispatchers.IO调度器，在主线程之外的线程执行
        //用withContext 指定挂起
        withContext(Dispatchers.IO){
            val bitmap = imageView!!.drawable.toBitmap()
            //获取存储路径，为空则返回
            val saveUri = context.contentResolver.insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                ContentValues()
            )?: kotlin.run {
                "存储失败".toastError()
                return@withContext
            }
            //输出流，输出到本地相册，根据结果判读是否成功
            context.contentResolver.openOutputStream(saveUri).use {
                if (bitmap.compress(Bitmap.CompressFormat.JPEG,100,it)) {
                    MainScope().launch {"存储成功".toastSuccess()}
                } else {
                    MainScope().launch {"存储失败".toastError()}
                }
            }
            Log.i("线程cid",Thread.currentThread().id.toString())
        }

    }




}
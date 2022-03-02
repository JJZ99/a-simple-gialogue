package com.example.abcdialogue.Weibo

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.abcdialogue.MyApplication
import com.example.abcdialogue.Weibo.Prestener.IPresenter
import com.example.abcdialogue.Weibo.Util.ToastUtil.toast
import com.example.abcdialogue.Weibo.View.dialog.MyBottomSheetDialogFragment
import com.facebook.common.util.UriUtil
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.interfaces.DraweeController
import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.regex.Pattern


class LoginActivity : AppCompatActivity() {
    var count = 1
    val TAG = "LoginActivity"
    private var mPresenter = IPresenter(this, TAG)

    //向主进程发送消息的句柄
//    var handler: Handler = object : Handler(Looper.getMainLooper()){
//        //子类必须实现此功能才能接收消息
//        override fun handleMessage(msg: Message) {
//            super.handleMessage(msg)
//            next()
//        }
//    }
    /**
     * 自定义方法
     * 启动登录界面
     */
     fun next() {
        val sharedPref = this.getSharedPreferences(
            getString(com.example.abcdialogue.R.string.sp_access_token), Context.MODE_PRIVATE)
        val token = sharedPref.getString(ACCESS_TOKEN,"")
        //如果token为空就跳转登陆获取
        if (token.isNullOrEmpty()) {
            //"不存在Token：${token}跳转到登陆授权界面".toastInfo()
            var intent = Intent(this, InitSDK().javaClass)
            startActivity(intent)
            finish()
        } else {
            //不为空直接跳转到微博页
            //"已经存在Token：${token}直接跳到微博".toastInfo()
            Log.i("token has", token)
            //InitSDK.TOKEN = "2.00llrezFRMpNJDd3d5f9f262Ln9WYC"
            InitSDK.TOKEN = token
            var intent = Intent(this, WeiBoActivity().javaClass)
            startActivity(intent)
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.abcdialogue.R.layout.activity_login)
        //权限申请
        getPermission()
        val uri = Uri.Builder()
            .scheme(UriUtil.LOCAL_RESOURCE_SCHEME)
            .path(java.lang.String.valueOf(com.example.abcdialogue.R.drawable.loading))
            .build()
        val controller: DraweeController = Fresco.newDraweeControllerBuilder()
            .setUri(uri)
            .setAutoPlayAnimations(true)
            .build()
        baseContext.toast("Hello")
      //  textView4.lineSpacingExtra
        wei_bo_btn.setOnClickListener {

            open_weibo_anim_constrain.visibility = View.VISIBLE
            open_weibo_anim.visibility = View.VISIBLE
            open_weibo_anim.controller = controller
            lifecycleScope.launch(){
                delay(2000)
                open_weibo_anim.visibility = View.GONE
                wei_bo_btn.isEnabled = true
                //next()
                showBottomDialog()
            }
            Log.i("SingleTest:fromCallable",Thread.currentThread().name.toString())
//            Single.fromCallable {
//                Log.e("fromCallable","subscribeOn${Thread.currentThread().name.toString()}")
//                7
//            }.subscribeOn(Schedulers.io())
//                .observeOn(Schedulers.io())
//                .subscribe({ t ->
//                    Log.e("fromCallable", "onSuccess${Thread.currentThread().name.toString()}")
//                    Log.e("fromCallable",t.toString())
//                },{ t ->
//                    Log.e("fromCallable", "onError${Thread.currentThread().name.toString()}")
//                    Log.e("fromCallable", t.toString())
//                })
//            Log.i("SingleTest:just",Thread.currentThread().name.toString())
//            Single.defer {
//                Log.e("just","subscribeOn${Thread.currentThread().name.toString()}")
//                Single.just(7)
//            }.subscribeOn(Schedulers.computation())
//                .observeOn(Schedulers.io())
//                .subscribe({ t ->
//                    Log.e("just", "onSuccess${Thread.currentThread().name.toString()}")
//                    Log.e("just",t.toString())
//                },{ t ->
//                    Log.e("just", "onError${Thread.currentThread().name.toString()}")
//                    Log.e("just", t.toString())
//                })
        }
        Log.i(TAG + MyApplication.CON, "onCreate"+this.window.toString())
        lifecycle.addObserver(mPresenter)
    }

    private fun testRx() {
        val TAG = "RXJAVA"
        var a = 15
        //方法2
        Observable.create(object : ObservableOnSubscribe<Int> {
            override fun subscribe(emitter: ObservableEmitter<Int>) {
                //事件产生的地方，比如保存文件、请求网络等
                if (a % 2 == 1) {
                    emitter.onNext(1)
                } else {
                    emitter.onNext(0)
                    //error和complete是互斥的就算你这里都写了，但是只会回调用一个，前面的那个
//                emitter.onError(NullPointerException("空指针了，给老子爬"))
                    //emitter.onComplete()
                }
                Log.i("RXJAVA" ,"Emitter.onNext:"+Thread.currentThread().name.toString())

            }
        }).observeOn(Schedulers.newThread()).
        map {   //做一次转换，多余操作，就是想用一下
            Log.i("RXJAVA","map"+Thread.currentThread().name.toString())
            it.toString()
        }
            .subscribeOn(Schedulers.computation())//设置事件发生的线程

            .observeOn(Schedulers.newThread())
            .subscribe(object : Observer<String> {

                //这个方法在onNext之前被调用
                override fun onSubscribe(d: Disposable) {
                }
                override fun onNext(t: String) {
                    Log.i("RXJAVA" , "Observer.onNext:"+Thread.currentThread().name.toString())

                }
                override fun onError(e: Throwable) {
                    e.printStackTrace()
                }

                //这个方法在onNext之后被调用
                override fun onComplete() {

                }
            })
    }

    /**
     * 判断是否拥有读写权限。没有就申请
     */
    private fun getPermission() {
        //验证权限
        if (Build.VERSION.SDK_INT >= 23) {
            val REQUEST_CODE_CONTACT = 101
            val permissions = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE)
            //验证是否许可权限
            for (str in permissions) {
                if (checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                    //申请权限
                    requestPermissions(permissions, REQUEST_CODE_CONTACT)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
    }
    /***
     * 手机号码检测
     */
    fun checkPhoneNum(num: String): Boolean{
        val regExp = "^((13[0-9])|(15[^4])|(18[0-9])|(17[0-8])|(14[5-9])|(166)|(19[8,9])|)\\d{8}$"
        val p = Pattern.compile(regExp)
        val m = p.matcher(num)
        return m.matches()
    }
    override fun getResources(): Resources {
        val res: Resources = super.getResources()
        if (count == 1) {
            Log.i(TAG + MyApplication.CON, this.baseContext.toString())

            //Log.i(TAG + MyApplication.CON, this.window.toString())
            --count
        }
        return res
    }

    private fun showBottomDialog() {
        MyBottomSheetDialogFragment.newInstance(123L)
            .show(supportFragmentManager, "${MyBottomSheetDialogFragment.javaClass}")

    }
    /**
     * 在点击允许或者拒绝后会执行这个方法
     */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
//        mutableMapOf<Long,>()
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean("dasd",true)
    }

    companion object {
        const val ACCESS_TOKEN = "access_token"
    }
}
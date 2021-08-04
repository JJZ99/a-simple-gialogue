package com.example.abcdialogue.Weibo

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.abcdialogue.Util.Util.toastShort
import com.sina.weibo.sdk.auth.AuthInfo
import com.sina.weibo.sdk.auth.Oauth2AccessToken
import com.sina.weibo.sdk.auth.WbAuthListener
import com.sina.weibo.sdk.common.UiError
import com.sina.weibo.sdk.openapi.IWBAPI
import com.sina.weibo.sdk.openapi.WBAPIFactory

class InitSDK : AppCompatActivity(),WbAuthListener{
    private lateinit var mWBAPI: IWBAPI
    var token = MutableLiveData<String>()

    companion object {
        const val APP_KY:String = "3595354204"
        const val REDIRECT_URL: String = "https://api.weibo.com/oauth2/default.html"
        const val SCOPE : String = "all"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initSdk()
        startAuth()
        token.observe(this,{
            it.toastShort(this)
            var intent = Intent(this, WeiBoActivity().javaClass)
        intent.putExtra("token", token.value)
        startActivity(intent)
        })


    }

    private fun initSdk() {
        var authInfo = AuthInfo(this, APP_KY, REDIRECT_URL, SCOPE)
        mWBAPI = WBAPIFactory.createWBAPI(this)
        mWBAPI.registerApp(this, authInfo)
    }

    private fun startAuth(){
        Handler(Looper.getMainLooper()).postDelayed({
            mWBAPI.authorize(this as WbAuthListener)
        },1000)
    }

    override fun onComplete(p0: Oauth2AccessToken?) {
        p0?.accessToken?.let {
            Log.i("ZJJ", it)
            it.toastShort(this)
            token.value = it
        }
    }

    override fun onError(p0: UiError?) {
        "微博授权错误".toastShort(this)
    }

    override fun onCancel() {
        "微博授权取消".toastShort(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, @Nullable data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (mWBAPI != null) {
            mWBAPI.authorizeCallback(requestCode, resultCode, data)
        }
    }
}
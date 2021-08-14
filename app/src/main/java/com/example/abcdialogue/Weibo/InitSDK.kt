package com.example.abcdialogue.Weibo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import com.example.abcdialogue.Weibo.Util.ToastUtil.toastError
import com.example.abcdialogue.Weibo.Util.ToastUtil.toastInfo
import com.example.abcdialogue.Weibo.LoginActivity.Companion.ACCESS_TOKEN
import com.sina.weibo.sdk.auth.AuthInfo
import com.sina.weibo.sdk.auth.Oauth2AccessToken
import com.sina.weibo.sdk.auth.WbAuthListener
import com.sina.weibo.sdk.common.UiError
import com.sina.weibo.sdk.openapi.IWBAPI
import com.sina.weibo.sdk.openapi.WBAPIFactory

class InitSDK : AppCompatActivity(), WbAuthListener {
    private lateinit var mWBAPI: IWBAPI

    companion object {
        const val APP_KY: String = "3595354204"
        const val REDIRECT_URL: String = "https://api.weibo.com/oauth2/default.html"
        const val SCOPE: String = "all"
        var TOKEN: String = ""
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPref = this.getSharedPreferences(
            getString(com.example.abcdialogue.R.string.sp_access_token), Context.MODE_PRIVATE
        )
        initSdk()
        startAuth()
    }

    private fun initSdk() {
        var authInfo = AuthInfo(this, APP_KY, REDIRECT_URL, SCOPE)
        mWBAPI = WBAPIFactory.createWBAPI(this)
        mWBAPI.registerApp(this, authInfo)
    }

    private fun startAuth() {
        Handler(Looper.getMainLooper()).postDelayed({
            mWBAPI.authorize(this as WbAuthListener)
        }, 1500)
    }

    override fun onComplete(p0: Oauth2AccessToken?) {
        //获取后将token存到sp文件，然后跳转到微博页
        val sharedPref = this.getSharedPreferences(
            getString(com.example.abcdialogue.R.string.sp_access_token), Context.MODE_PRIVATE
        )
        p0?.accessToken?.let {
            val edit = sharedPref.edit()
            edit.putString(ACCESS_TOKEN, it)
            edit.commit()
            "微博授权成功getToken".toastInfo()
            TOKEN = it
            var intent = Intent(this, WeiBoActivity().javaClass)
            startActivity(intent)
        }
    }

    override fun onError(p0: UiError?) {
        "微博授权错误".toastError()
    }

    override fun onCancel() {
        "微博授权取消".toastInfo()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, @Nullable data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (mWBAPI != null) {
            mWBAPI.authorizeCallback(requestCode, resultCode, data)
        }
    }
}
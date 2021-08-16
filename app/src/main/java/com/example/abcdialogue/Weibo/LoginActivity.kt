package com.example.abcdialogue.Weibo

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.util.Log
import android.widget.EditText
import com.example.abcdialogue.Module.MainActivity2
import com.example.abcdialogue.R
import com.example.abcdialogue.Weibo.Util.ParseUtil.getFormatText
import com.example.abcdialogue.Weibo.Util.ParseUtil.getUri
import com.example.abcdialogue.Weibo.Util.ToastUtil.toast
import com.example.abcdialogue.Weibo.Util.ToastUtil.toastInfo
import kotlinx.android.synthetic.main.activity_login.login_btn
import kotlinx.android.synthetic.main.activity_login.username
import kotlinx.android.synthetic.main.activity_login.username_input
import kotlinx.android.synthetic.main.activity_login.wei_bo_btn
import java.util.regex.Pattern

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        baseContext.toast("Hello",)

        login_btn.setOnClickListener {

            //username_input.text = getFormatText("#行测##国考##省考#")
            username_input.text = getFormatText("【#商丘一院阳性产妇系救护车转运脱管#】8月14日，河南#商丘阳性产妇疑被授意隐瞒行程#持续引发关注。尹某系亲属有中风险接触史的隔离人员。8月6日，尹某突发妊娠期高血压，虞城县防疫部门派救护车转运中致其脱管。尹某称，救护车将其送到商丘市第一人民医院门口，授意其瞒报行程自行入院。@紧急呼叫 ...全文： http://m.weibo.cn/6124642021/4670225192328232 \u200B")
            username_input.movementMethod = LinkMovementMethod.getInstance()

//            var intent = Intent(this, MainActivity2().javaClass)
//            startActivity(intent)
//            "jump！jump！".toastInfo()
        }
        wei_bo_btn.setOnClickListener {
            val sharedPref = this.getSharedPreferences(
                getString(R.string.sp_access_token), Context.MODE_PRIVATE)
            val token = sharedPref.getString(ACCESS_TOKEN,"")
            //如果token为空就跳转登陆获取
            if(token.isNullOrEmpty()){
                "不存在Token：${token}跳转到登陆授权界面".toastInfo()
                var intent = Intent(this, InitSDK().javaClass)
                startActivity(intent)
                finish()
            }else{
                //不为空直接跳转到微博页
                "已经存在Token：${token}直接跳到微博".toastInfo()
                Log.i("token has",token)
                InitSDK.TOKEN = "2.00llrezFRMpNJDd3d5f9f262Ln9WYC"
                //InitSDK.TOKEN = token
                var intent = Intent(this, WeiBoActivity().javaClass)
                startActivity(intent)
                finish()

            }

        }
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

    companion object {
        const val ACCESS_TOKEN = "access_token"
    }
}
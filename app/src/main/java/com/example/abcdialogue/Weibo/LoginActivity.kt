package com.example.abcdialogue.Weibo

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.util.Log
import android.widget.EditText
import androidx.core.app.ActivityCompat
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

        getPermission()

        baseContext.toast("Hello",)

        login_btn.setOnClickListener {
            //#陈伟霆晒沉浸式刷牙#@William威廉陈伟霆 帅照🈶！陈伟霆就算是刷牙也能这么帅🆘果然靓仔什么姿势拍照都帅气满分！ ​
            //半导体产业与地缘政治的联系日益紧密，贸易保护主义使半导体供应链风险不断上升。据悉，韩国三星电子和SK 海力士都在研究各种半导体材料、零部件和设备，以降低对进口产品的依赖。http://t.cn/A6I9PdOc ​
            //2021年8月21日下午14:00，为搭建IC企业与应届毕业生之间的招聘桥梁，爱集微与西电微电子专业校友会联合举办的“西电微电子行业校友企业线上招聘宣讲会”将进行线上直播。http://t.cn/A6I9PFid 直播入口：http://t.cn/A6I2nXGY ​
            username_input.text = getFormatText("            //在过去五年里，英伟达的营收仅增长了233%，股价却从15美元上下飙升超过10倍至逼近200美元，已有声音认为，英伟达将成为下一只“永不出售”的股票。高估值背后，曾经的游戏显卡供应商何以超车众巨头？答曰：平台化。http://t.cn/A6IKzQaLv \u200B\n")
            //第一集熟了→ http://t.cn/A6ISgLwF
            //username_input.text = getFormatText("【#商丘一院阳性产妇系救护车转运脱管#】8月14日，河南#商丘阳性产妇疑被授意隐瞒行程#持续引发关注。尹某系亲属有中风险接触史的隔离人员。8月6日，尹某突发妊娠期高血压，虞城县防疫部门派救护车转运中致其脱管。尹某称，救护车将其送到商丘市第一人民医院门口，授意其瞒报行程自行入院。@紧急呼叫 ...全文： http://m.weibo.cn/6124642021/4670225192328232 \u200B")
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
                //"不存在Token：${token}跳转到登陆授权界面".toastInfo()
                var intent = Intent(this, InitSDK().javaClass)
                startActivity(intent)
                finish()
            }else{
                //不为空直接跳转到微博页
                //"已经存在Token：${token}直接跳到微博".toastInfo()
                Log.i("token has",token)
                //InitSDK.TOKEN = "2.00llrezFRMpNJDd3d5f9f262Ln9WYC"
                InitSDK.TOKEN = token
                var intent = Intent(this, WeiBoActivity().javaClass)
                startActivity(intent)
                finish()
            }
        }
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

    /***
     * 手机号码检测
     */
    fun checkPhoneNum(num: String): Boolean{
        val regExp = "^((13[0-9])|(15[^4])|(18[0-9])|(17[0-8])|(14[5-9])|(166)|(19[8,9])|)\\d{8}$"
        val p = Pattern.compile(regExp)
        val m = p.matcher(num)
        return m.matches()
    }

    /**
     * 在点击允许或者拒绝后会执行这个方法
     */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    companion object {
        const val ACCESS_TOKEN = "access_token"
    }
}
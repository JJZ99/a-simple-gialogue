package com.example.abcdialogue.Weibo.Util

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.net.Uri
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextPaint
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.text.style.URLSpan
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.text.clearSpans
import com.example.abcdialogue.MyApplication
import com.example.abcdialogue.MyApplication.Companion.context
import com.example.abcdialogue.R
import java.text.SimpleDateFormat
import java.util.Locale

/**
 * 用于文本的解析
 */
object ParseUtil {
    /**
     * 从<a href="http://app.weibo.com/t/feed/6vtZb0" rel="nofollow">微博 weibo.com</a>获取标签中的内容
     * @param string 微博来源的html
     * @return 内容
     */
    fun getSource(string: String):String{
        var beginIndex= string.indexOfFirst { it =='>' }
        var endIndex = string.indexOfLast { it == '<' }
        return string.substring(beginIndex+1,endIndex)
    }

    /**
     * 从<a href="http://app.weibo.com/t/feed/6vtZb0" rel="nofollow">微博 weibo.com</a>获取标签中的内容
     * @param string 微博来源
     * @return Uri
     */
    fun getUri(string: String): Uri {
        var beginIndex = string.indexOf("href=") + 6
        var endIndex = string.indexOf("rel=\"nofollow\"") - 2
        var uri = Uri.parse(string.substring(beginIndex, endIndex))
        Log.i("Uri",uri.toString())
        return uri
    }
    /**
     * 将Sun Nov 14 10:04:36 +0800 2010格式的时间，转化为分钟前小时前刚刚
     * @param string  Sun Nov 14 10:04:36 +0800 2010格式的字符串
     * @return 刚刚or某分钟前or某小时前
     */
    fun getTime(string: String):String{
        //格式化时间字符串
        var simpleDateFormat =  SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.CANADA).parse(string);
        //获取已经发表了多少分钟
        var time = (System.currentTimeMillis()-simpleDateFormat.time)/60000
        when {
            time < 1 -> return TYPE_NOW
            time<60 -> return time.toString()+TYPE_MIN
            time>=60 -> return (time/60).toString()+TYPE_HOUR
        }
        return (time/60).toString()+TYPE_HOUR
    }

    /**
     * 从text中获取超话信息
     */
    fun getFormatText(content: String): Spanned {

        val spannedStringBuilder = SpannableStringBuilder()
        var allTextIndex = content.indexOf("全文： ")
        var textSpanned = if (allTextIndex !=-1 )SpannableString(content.substring(0,allTextIndex+2)) else SpannableString(content)
        textSpanned.setSpan(ForegroundColorSpan(context.resources.getColor(R.color.textColor,null)), 0, textSpanned.length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)

        //用来标志下一个#是不是右边的
        var isTopic = false
        //上一个#的索引
        var left = 0
        var right = 0
        var list = mutableListOf<Pos>().apply {
            var index = -1
            for( char in  content){
                ++index
                if (char == WELL_CHAR){
                    if (!isTopic){
                        left = index
                        isTopic = true
                    }else{
                        right = index
                        isTopic = false
                        add(Pos(left, right))
                    }
                }
            }
        }
        list.forEach {
            textSpanned.setSpan(ForegroundColorSpan(context.resources.getColor(R.color.topicColor,null)), it.left, it.right+1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
        }
        if (allTextIndex!=-1){
            //链接的结尾有个ZWSP 不能截进来
            val url = content.substring(allTextIndex+4,content.length-1)
            textSpanned.setSpan(MURLSpan(url), allTextIndex, allTextIndex+2, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
            textSpanned.setSpan(StyleSpan(Typeface.BOLD), 0, textSpanned.length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
        }
        spannedStringBuilder.append(textSpanned)
        return spannedStringBuilder
    }
    private const val WELL ="#"
    private const val WELL_CHAR ='#'

    private const val TYPE_NOW = "刚刚"
    private const val TYPE_MIN="分钟前"
    private const val TYPE_HOUR="小时前"
}

class MURLSpan(private val url: String) : URLSpan(url){
    override fun updateDrawState(ds: TextPaint) {
        super.updateDrawState(ds)
        //设置超链接文本的颜色
        ds.color = context.resources.getColor(R.color.topicColor)
        //这里可以去除点击文本的默认的下划线
        ds.isUnderlineText = false
    }

    override fun onClick(widget: View) {
         super.onClick(widget)

        //去除点击后字体出现的背景色
        (widget as? TextView)?.highlightColor = Color.TRANSPARENT
        //自定义超链接动作
        Toast.makeText(context, "自定义超链接动作", Toast.LENGTH_SHORT).show()
    }
}
data class Pos(val left:Int,val right:Int)

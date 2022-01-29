package com.example.abcdialogue.Weibo.Util

import android.graphics.Color
import android.graphics.Typeface
import android.net.Uri
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.text.style.URLSpan
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.abcdialogue.MyApplication.Companion.context
import com.example.abcdialogue.R

import java.text.SimpleDateFormat
import java.util.Locale
import java.util.regex.Pattern

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
    fun getTime(string: String): String {
        //格式化时间字符串
        var simpleDateFormat =
            SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.CANADA).parse(string);
        //获取已经发表了多少分钟
        var time = (System.currentTimeMillis() - simpleDateFormat.time) / 60000
        when {
            time < 1 -> return TYPE_NOW
            time < 60 -> return time.toString() + TYPE_MIN
            time >= 60 -> return (time / 60).toString() + TYPE_HOUR
        }
        return (time / 60).toString() + TYPE_HOUR
    }

    /**
     * 解析文案内容，将超话部分高亮显示，将全文设置高亮并且设置跳转链接
     * @param content 文案内容
     * @return span富文本
     */
    fun getFormatText(content: String): Spanned {

        val spannedStringBuilder = SpannableStringBuilder()
        var allTextIndex = content.indexOf("全文： ")
        var textSpanned = if (allTextIndex !=-1 ) SpannableString(content.substring(0, allTextIndex + 2)) else SpannableString(content)
        textSpanned.setSpan(ForegroundColorSpan(context.resources.getColor(R.color.textColor, null)), 0, textSpanned.length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)

        //超话部分
        getTopicSpanUseRegex(textSpanned)
        //@部分
        getAtSpanUseRegex(textSpanned)
        if (allTextIndex!=-1){//结尾的全文部分
            //链接的结尾有个ZWSP 不能截进来
            val url = content.substring(allTextIndex + 4, content.length - 1)
            textSpanned.setSpan(MURLSpan(url),
                allTextIndex,
                allTextIndex + 2,
                Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
            textSpanned.setSpan(StyleSpan(Typeface.BOLD),
                allTextIndex,
                allTextIndex + 2,
                Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
        }else{//结尾的一些链接
            getUrlSpanUseRegex(textSpanned)
        }
        spannedStringBuilder.append(textSpanned)
        return spannedStringBuilder
    }

    fun getAtSpanUseRegex(textSpanned: SpannableString){
        //@部分使用正则表达式
        var pattern = Pattern.compile(AT_RE);
        var matcher = pattern.matcher(textSpanned);
        matcher.reset()
        while (matcher.find()){
            var at = matcher.group(0)
            if (at!= null) {
                var start = matcher.start()
                var end = matcher.end()
                textSpanned.setSpan(ForegroundColorSpan(context.resources.getColor(R.color.topicColor,
                    null)), start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
                textSpanned.setSpan(MyClickSpan(at), start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
            }
        }
    }
    fun getTopicSpanUseRegex(textSpanned: SpannableString){
        var pattern = Pattern.compile(TOPIC);
        var matcher = pattern.matcher(textSpanned);
        matcher.reset()
        while (matcher.find()){
            var topic = matcher.group(0)
            if (topic!= null) {
                var start = matcher.start()
                var end = matcher.end()
                textSpanned.setSpan(ForegroundColorSpan(context.resources.getColor(R.color.topicColor,
                    null)), start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
                textSpanned.setSpan(MyClickSpan(topic),
                    start,
                    end,
                    Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
            }
        }
    }

    fun getTopicSpanUseStringUtil(content: String, textSpanned: SpannableString) {
        //用来标志下一个#是不是右边的
        var isTopic = false
        //上一个#的索引
        var left = 0
        var right = 0
        var list = mutableListOf<Pos>().apply {
            for ((index, char) in content.withIndex()) {
                if (char == WELL_CHAR) {
                    if (!isTopic) {
                        left = index
                        isTopic = true
                    } else {
                        right = index
                        isTopic = false
                        add(Pos(left, right))
                    }
                }
            }
        }
        list.forEach {
            textSpanned.setSpan(ForegroundColorSpan(context.resources.getColor(R.color.topicColor,
                null)), it.left, it.right + 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
        }
    }
    fun getUrlSpanUseRegex(textSpanned: SpannableString){
        var pattern = Pattern.compile(URL3);
        var matcher = pattern.matcher(textSpanned);
        matcher.reset()
        while (matcher.find()){
            var url = matcher.group(0)
            if (url!= null) {
                var start = matcher.start()
                var end = matcher.end()
                textSpanned.setSpan(MURLSpan(url), start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
                textSpanned.setSpan(StyleSpan(Typeface.BOLD),
                    start,
                    end,
                    Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
            }
        }
    }

    fun getUrlSpanUseStringUtil(content: String, textSpanned: SpannableString) {
        var httpIndex = content.indexOf(HTTP)
        while (httpIndex != -1) {
            var blackIndex = content.indexOf(BLANK, httpIndex)
            val url = if (blackIndex == -1) content.substring(httpIndex,
                content.length) else content.substring(httpIndex, blackIndex)
            textSpanned.setSpan(MURLSpan(url),
                httpIndex,
                blackIndex,
                Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
            textSpanned.setSpan(StyleSpan(Typeface.BOLD),
                httpIndex,
                blackIndex,
                Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
            httpIndex = content.indexOf(HTTP, blackIndex)
        }
    }

    /**
     * @param url 缩略图的url
     * @return 大图的url
     */
    fun getMiddle2LargeUrl(url: String): String {
        return url.replace(MIDDLE_VALUE, LARGE_VALUE)
    }

    /**
     * @param url 缩略图的url
     * @return 中等图的url
     * */
    fun getSmall2MiddleUrl(url: String):String{
        return url.replace(SMALL_VALUE, MIDDLE_VALUE)
    }
    fun getLarge2MiddleUrl(url: String):String{
        return url.replace(LARGE_VALUE, MIDDLE_VALUE)
    }


    private const val AT ="@"
    private const val BLANK = " "
    private const val HTTP ="http"
    private const val WELL_CHAR ='#'
    private const val TYPE_NOW = "刚刚"
    private const val TYPE_MIN="分钟前"
    private const val TYPE_HOUR="小时前"

    private const val SMALL_VALUE ="/thumbnail"
    private const val MIDDLE_VALUE = "/bmiddle"
    private const val LARGE_VALUE = "/large"

    // 定义正则表达式
    private const val AT_RE = "@[0-9a-zA-Z\\u4e00-\\u9fa5-_]+" // @人
    private const val TOPIC = "#[0-9a-zA-Z\\u4e00-\\u9fa5]+#" // ##话题
    private const val EMOJI = "\\[[0-9a-zA-Z\\u4e00-\\u9fa5]+\\]" // 表情
    private const val URL = "\\b(([\\w-]+://?|www[.])[^\\s()<>]+(?:\\([\\w\\d]+\\)|([^[:punct:]\\s]|/)))" // url 前面有汉字时不可取
    private const val URL2 = "http://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]" // url,https时不可取
    private const val URL3 = "(https?|ftp|file)://[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]" // url 啥case都ok



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

class MyClickSpan(var text:String): ClickableSpan(){
    override fun updateDrawState(ds: TextPaint) {
    }
    override fun onClick(widget: View) {
        (widget as? TextView)?.highlightColor = Color.TRANSPARENT
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }
}
data class Pos(val left:Int,val right:Int)

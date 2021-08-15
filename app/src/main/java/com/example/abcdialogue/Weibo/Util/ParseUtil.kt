package com.example.abcdialogue.Weibo.Util

import android.net.Uri
import android.util.Log
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
     * @param string 微博来源的Uri
     * @return 内容
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
            time < 1 -> {
                return TYPE_NOW
            }
            time<60 -> {
                return time.toString()+TYPE_MIN
            }
            time>=60 -> {
                return (time/60).toString()+TYPE_HOUR
            }
        }
        return (time/60).toString()+TYPE_HOUR
    }

    /**
     * 从text中获取超话信息
     */
//    fun getFormatText(string: String): Spanned {
//        string.indexOf("https//")
//        val spannedStringBuilder = SpannableStringBuilder()
//
//        // 前缀
//        if (priceBuilder.prefixText?.isNotEmpty() == true) {
//            val prefixTextSpanned = SpannableString(priceBuilder.prefixText)
//            prefixTextSpanned.setSpan(ForegroundColorSpan(priceBuilder.prefixTextColor), 0, prefixTextSpanned.length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
//            prefixTextSpanned.setSpan(RelativeSizeSpan(priceBuilder.prefixTextSizeRatio), 0, prefixTextSpanned.length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
//            //前缀是否设置为粗体
//            if (priceBuilder.prefixTextBold) {
//                prefixTextSpanned.setSpan(StyleSpan(Typeface.BOLD), 0, prefixTextSpanned.length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
//            }
//            spannedStringBuilder.append(prefixTextSpanned)
//
//            val prefixTextSpaceSpanned = SpannableString(" ")
//            prefixTextSpaceSpanned.setSpan(SpaceSpan(priceBuilder.prefixMarginRight), 0, prefixTextSpaceSpanned.length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
//            spannedStringBuilder.append(prefixTextSpaceSpanned)
//        }
//
//        // ￥ 符号
//        if (!priceBuilder.hideYuanSymbol) {
//            val yuanSymbolSpanned = SpannableString("¥")
//            yuanSymbolSpanned.setSpan(ForegroundColorSpan(priceBuilder.priceTextColor), 0, yuanSymbolSpanned.length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
//            yuanSymbolSpanned.setSpan(
//                RelativeSizeSpan(priceBuilder.yuanSymbolTextSizeRatio),
//                0,
//                yuanSymbolSpanned.length,
//                Spanned.SPAN_INCLUSIVE_EXCLUSIVE
//            )
//            if (priceBuilder.yuanSymbolBold) {
//                yuanSymbolSpanned.setSpan(
//                    StyleSpan(Typeface.BOLD),
//                    0,
//                    yuanSymbolSpanned.length,
//                    Spanned.SPAN_INCLUSIVE_EXCLUSIVE
//                )
//            }
//            spannedStringBuilder.append(yuanSymbolSpanned)
//
//            val yuanSpaceSpanned = SpannableString(" ")
//            yuanSpaceSpanned.setSpan(
//                SpaceSpan(priceBuilder.yuanSymbolMarginRight),
//                0,
//                yuanSpaceSpanned.length,
//                Spanned.SPAN_INCLUSIVE_EXCLUSIVE
//            )
//            spannedStringBuilder.append(yuanSpaceSpanned)
//        }
//
//        // 价格
//        val priceInString = convertPriceFromLongToString(
//            priceBuilder.priceInFen,
//            priceBuilder.priceInFenMax
//        )
//        val priceIntegerInString = retrievePriceIntegerText(priceInString)
//        val priceDecimalInString = retrievePriceDecimalText(priceInString)
//
//        val priceIntegerSpanned = SpannableString(priceIntegerInString)
//        priceIntegerSpanned.setSpan(
//            ForegroundColorSpan(priceBuilder.priceTextColor),
//            0,
//            priceIntegerSpanned.length,
//            Spanned.SPAN_INCLUSIVE_EXCLUSIVE
//        )
//        priceIntegerSpanned.setSpan(
//            RelativeSizeSpan(1F),
//            0,
//            priceIntegerSpanned.length,
//            Spanned.SPAN_INCLUSIVE_EXCLUSIVE
//        )
//        priceBuilder.priceShadow?.let {
//            priceIntegerSpanned.setSpan(
//                ShadowSpan(it),
//                0,
//                priceIntegerSpanned.length,
//                Spanned.SPAN_INCLUSIVE_EXCLUSIVE
//            )
//        }
//        if (priceBuilder.priceBold) {
//            priceIntegerSpanned.setSpan(
//                StyleSpan(Typeface.BOLD),
//                0,
//                priceIntegerSpanned.length,
//                Spanned.SPAN_INCLUSIVE_EXCLUSIVE
//            )
//        }
//        spannedStringBuilder.append(priceIntegerSpanned)
//
//        if (priceDecimalInString.isNotEmpty()) {
//            val priceDecimalSpanned = SpannableString(priceDecimalInString)
//            priceDecimalSpanned.setSpan(ForegroundColorSpan(priceBuilder.priceTextColor), 0, priceDecimalSpanned.length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
//            priceDecimalSpanned.setSpan(RelativeSizeSpan(priceBuilder.priceDecimalTextSizeRatio), 0, priceDecimalSpanned.length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
//            if (priceBuilder.priceBold) {
//                priceDecimalSpanned.setSpan(StyleSpan(Typeface.BOLD), 0, priceDecimalSpanned.length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
//            }
//            spannedStringBuilder.append(priceDecimalSpanned)
//            priceBuilder.priceShadow?.let {
//                priceDecimalSpanned.setSpan(
//                    ShadowSpan(it),
//                    0,
//                    priceDecimalSpanned.length,
//                    Spanned.SPAN_INCLUSIVE_EXCLUSIVE
//                )
//            }
//        }
//
//        // 后缀
//        if (priceBuilder.suffixText?.isNotEmpty() == true) {
//            val suffixTextSpaceSpanned = SpannableString(" ")
//            suffixTextSpaceSpanned.setSpan(SpaceSpan(priceBuilder.suffixTextMarginLeft), 0, suffixTextSpaceSpanned.length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
//            spannedStringBuilder.append(suffixTextSpaceSpanned)
//
//            val suffixTextSpanned = SpannableString(priceBuilder.suffixText)
//            suffixTextSpanned.setSpan(ForegroundColorSpan(priceBuilder.suffixTextColor), 0, suffixTextSpanned.length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
//            suffixTextSpanned.setSpan(RelativeSizeSpan(priceBuilder.suffixTextSizeRatio), 0, suffixTextSpanned.length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
//            //后缀是否设置为粗体
//            if (priceBuilder.suffixTextBold) {
//                suffixTextSpanned.setSpan(StyleSpan(Typeface.BOLD), 0, suffixTextSpanned.length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
//            }
//            spannedStringBuilder.append(suffixTextSpanned)
//        }
//        return spannedStringBuilder
//    }
    private const val TYPE_NOW = "刚刚"
    private const val TYPE_MIN="分钟前"
    private const val TYPE_HOUR="小时前"
}
package com.example.abcdialogue.Weibo.View.Fragment

import androidx.fragment.app.Fragment

object FragmentFactory {
    fun getInstance(type:Int):Fragment {
        return when(type){
            TYPE_NEW -> NewFragment()
            TYPE_VIDEO -> VideoFragment()
            TYPE_GOOD -> GoodFragment()
            else-> GoodFragment()
        }
    }

    //新闻
    private const val TYPE_NEW = 1
    //视频
    private const val TYPE_VIDEO = 2
    //商品
    private const val TYPE_GOOD = 3



}
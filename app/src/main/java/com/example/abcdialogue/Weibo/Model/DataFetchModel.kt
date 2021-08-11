package com.example.abcdialogue.Weibo.Model

import android.util.Log
import com.example.abcdialogue.Weibo.Bean.WBAllDTO
import com.example.abcdialogue.Weibo.Util.Net.RetrofitHelper
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

object DataFetchModel {
    /**
     * 获取国家列表
     */
    fun getProvinceList(token: String): Observable<List<Map<String, String>>> =
        RetrofitHelper.getRetrofit().create(WeiBoApi::class.java).getProvinceList(token)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    /**
     * 获取微博
     */
    fun getStatusesList(token: String,page:Int): Observable<WBAllDTO> =
        RetrofitHelper.getRetrofit().create(WeiBoApi::class.java).getStatusesList(token,page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}
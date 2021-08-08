package com.example.abcdialogue.Weibo.Model

import android.util.Log
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
}
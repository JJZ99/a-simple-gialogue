package com.example.abcdialogue.Weibo.Model

import com.example.abcdialogue.Weibo.Bean.WBAllDTO
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface WeiBoApi {

    /**
     * 获取国家列表
     */
    @GET(COUNTRY)
    fun getProvinceList(@Query("access_token") accessToken: String): Observable<List<Map<String,String>>>



    /**
     * 获取国家列表
     */
    @GET(STATUES)
    fun getStatusesList(@Query("access_token") accessToken: String): Observable<WBAllDTO>


    companion object {
        const val COUNTRY = "https://api.weibo.com/2/common/get_country.json"
        const val STATUES = "https://api.weibo.com/2/statuses/home_timeline.json"
    }
}
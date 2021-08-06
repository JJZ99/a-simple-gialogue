package com.example.abcdialogue.Weibo.Model

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface WeiBoApi {

    /**
     * 获取国家列表
     */
    @GET(COUNTRY)
    fun getProvinceList(@Query("access_token") accessToken: String): Observable<List<Map<String,String>>>

    companion object {
        const val COUNTRY = "https://api.weibo.com/2/common/get_country.json"
    }
}
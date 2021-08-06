package com.example.abcdialogue.Weibo.Model

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface AppService {
    @GET("get_data.json")
    fun getAppData(): Call<List<String>>


    @GET("{page}/get_data.json")
    fun getData(@Path("page") page: Int): Call<Data>

    @GET("get_data.json")
    fun getData(@Query("u") user: String, @Query("t") token: String): Call<Data>

    @POST("data/create")
    fun createData(@Body data: Data): Call<ResponseBody>
    //data会被放到请求体中，ResponseBody代表能够接受任意类型的响应数据

}
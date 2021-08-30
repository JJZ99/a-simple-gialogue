package com.example.abcdialogue.Weibo.Util.Net

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitHelper {

    private const val BASE_URL = "http://www.weather.com.cn"

    @Volatile
    private var retrofit: Retrofit? = null

    private var retrofitBuilder: Retrofit.Builder? = null

    fun init(){
        if (retrofitBuilder == null){
            //Builder可以自定义连接设置
            val client = OkHttpClient.Builder()
                //设置连接超时
                .connectTimeout(20, TimeUnit.SECONDS)
                //设置读超时
                .readTimeout(20,TimeUnit.SECONDS)
                //设置写超时
                .writeTimeout(20,TimeUnit.SECONDS)
                .build()
            retrofitBuilder = Retrofit.Builder()
                //Set the API base URL
                .baseUrl(BASE_URL)
                //用于序列化和反序列化的转换器工厂
                .addConverterFactory(GsonConverterFactory.create())
                //call 适配
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                //设置用于请求http的客户端
                .client(client)
        }
        retrofit = retrofitBuilder!!.build()
    }

    fun getRetrofit():Retrofit{
        if (retrofit == null){
            synchronized(RetrofitHelper.javaClass){
                if (retrofit == null) {
                    init()
                }
            }
        }
        return retrofit!!
    }
}
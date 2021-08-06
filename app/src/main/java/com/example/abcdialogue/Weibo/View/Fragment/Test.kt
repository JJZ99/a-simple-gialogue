package com.example.abcdialogue.Weibo.View.Fragment

import com.example.abcdialogue.Weibo.Model.testApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Test {
    val retrofit = Retrofit.Builder()
        .baseUrl("www.baidu.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val appService = retrofit.create(testApi::class.java)
    fun arr(){
        appService.getAppData().enqueue(object : Callback<List<String>> {
            override fun onResponse(call: Call<List<String>>, response: Response<List<String>>) {
                //...
            }
            override fun onFailure(call: Call<List<String>>, t: Throwable) {
                //...
            }
        })
    }

}
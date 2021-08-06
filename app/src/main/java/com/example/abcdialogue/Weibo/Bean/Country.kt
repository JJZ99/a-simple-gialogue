package com.example.abcdialogue.Weibo.Bean

import com.google.gson.annotations.SerializedName

data class Country(
    @SerializedName("status_code")
    val statusCode: Int,
    val statusMsg: String?,
)

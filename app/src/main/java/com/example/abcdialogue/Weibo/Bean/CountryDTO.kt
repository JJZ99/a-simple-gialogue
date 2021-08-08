package com.example.abcdialogue.Weibo.Bean

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CountryDTO(
    val statusCode: String,
    val statusMsg: String?,
):Serializable

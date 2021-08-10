package com.example.abcdialogue.Weibo.Bean

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class WBGeo(
    /**
     * 经度坐标
     */
    @SerializedName("longitude")
    val longitude: String,
    /**
     * 维度坐标
     */
    @SerializedName("latitude")
    val latitude: String,
    /**
     * 所在城市的城市代码
     */
    @SerializedName("city")
    val city: String,
    /**
     * 所在省份的省份代码
     */
    @SerializedName("province")
    val province: String,
    /**
     * 所在城市的城市名称
     */
    @SerializedName("city_name")
    val cityName: String,
    /**
     * 所在省份的省份名称
     */
    @SerializedName("province_name")
    val provinceName: String,
    /**
     * 所在的实际地址，可以为空
     */
    @SerializedName("address")
    val address: String,
    /**
     * 地址的汉语拼音，不是所有情况都会返回该字段
     */
    @SerializedName("pinyin")
    val pinyin: String,
    /**
     * 更多信息，不是所有情况都会返回该字段
     */
    @SerializedName("more")
    val more: String,
) : Serializable

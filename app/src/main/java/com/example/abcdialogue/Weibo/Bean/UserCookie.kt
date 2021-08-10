package com.example.abcdialogue.Weibo.Bean

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity
data class UserCookie(
    @PrimaryKey @ColumnInfo(name = "uid") @SerializedName("uid") val uid: String,
    @ColumnInfo(name = "access_token") @SerializedName("access_token") val accessToken: String,
    @ColumnInfo(name = "expires_in") @SerializedName("expires_in") var expiresIn: Long?,
    @ColumnInfo(name = "refresh_token") @SerializedName("refresh_token") var refreshToken: String?,
    @ColumnInfo(name = "time") @SerializedName("time") val time:Long = System.currentTimeMillis()
    ):Serializable
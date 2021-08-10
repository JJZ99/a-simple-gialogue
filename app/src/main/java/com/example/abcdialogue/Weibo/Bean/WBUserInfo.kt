package com.example.abcdialogue.Weibo.Bean

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class WBUserInfo(
    /**
     * 用户UID
     */
    @SerializedName("id")
    val id: Long,

    /**
     * 字符串型的用户UID
     */
    @SerializedName("idstr")
    val idStr: String,

    /**
     * 用户昵称
     */
    @SerializedName("screen_name")
    val screenName: String,

    /**
     * 友好显示名称
     */
    @SerializedName("name")
    val name: String,

    /**
     * 用户所在省级ID
     */
    @SerializedName("province")
    val province: String,

    /**
     * 用户所在城市ID
     */
    @SerializedName("city")
    val city: String,

    /**
     * 用户所在地
     */
    @SerializedName("location")
    val location: String,

    /**
     * 用户个人描述
     */
    @SerializedName("description")
    val description: String,

    /**
     * 用户博客地址
     */
    @SerializedName("url")
    val url: String,

    /**
     * 用户头像地址（中图），50×50像素
     */
    @SerializedName("profile_image_url")
    val profileImageUrl: String,

    /**
     * 用户的微博统一URL地址
     */
    @SerializedName("profile_url")
    val profileUrl: String,

    /**
     * 用户的个性化域名
     */
    @SerializedName("domain")
    val domain: String,

    /**
     * 用户的微号
     */
    @SerializedName("weihao")
    val weihao: String,

    /**
     * 性别，m：男、f：女、n：未知
     */
    @SerializedName("gender")
    val gender: String,

    /**
     * 粉丝数
     */
    @SerializedName("followers_count")
    val followersCount: Int,

    /**
     * 关注数
     */
    @SerializedName("friends_count")
    val friendsCount: Int,

    /**
     * 微博数
     */
    @SerializedName("statuses_count")
    val statusesCount: Int,

    /**
     * 收藏数
     */
    @SerializedName("favourites_count")
    val favouritesCount: Int,

    /**
     * 用户创建（注册）时间
     */
    @SerializedName("created_at")
    val createdAt: String,

    /**
     * 暂未支持
     */
    @SerializedName("following")
    val following: Boolean,

    /**
     * 是否允许所有人给我发私信，true：是，false：否
     */
    @SerializedName("allow_all_act_msg")
    val allowAllActMsg: Boolean,

    /**
     * 是否允许标识用户的地理位置，true：是，false：否
     */
    @SerializedName("geo_enabled")
    val geoEnabled: Boolean,

    /**
     * 是否是微博认证用户，即加V用户，true：是，false：否
     */
    @SerializedName("verified")
    val verified: Boolean,

    /**
     * 暂未支持
     */
    @SerializedName("verified_type")
    val verifiedType: Int,

    /**
     * 用户备注信息，只有在查询用户关系时才返回此字段
     */
    @SerializedName("remark")
    val remark: String,

    /**
     * 用户的最近一条微博信息字段 详细
     */
    @SerializedName("status")
    val status: WBStatusDTO,

    /**
     * 是否允许所有人对我的微博进行评论，true：是，false：否
     */
    @SerializedName("allow_all_comment")
    val allowAllComment: Boolean,

    /**
     * 用户头像地址（大图），180×180像素
     */
    @SerializedName("avatar_large")
    val avatarLarge: String,

    /**
     * 用户头像地址（高清），高清头像原图
     */
    @SerializedName("avatar_hd")
    val avatarHd: String,

    /**
     * 认证原因
     */
    @SerializedName("verified_reason")
    val verifiedReason: String,

    /**
     * 该用户是否关注当前登录用户，true：是，false：否
     */
    @SerializedName("follow_me")
    val followMe: Boolean,

    /**
     * 用户的在线状态，0：不在线、1：在线
     */
    @SerializedName("online_status")
    val onlineStatus: Int,

    /**
     * 用户的互粉数
     */
    @SerializedName("bi_followers_count")
    val biFollowersCount: Int,

    /**
     * 用户当前的语言版本，zh-cn：简体中文，zh-tw：繁体中文，en：英语
     */
    @SerializedName("lang")
    val lang: String
) : Serializable

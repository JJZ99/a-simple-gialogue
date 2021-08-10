package com.example.abcdialogue.Weibo.Bean


data class WBStatusBean(
    //微博相关
    val createdAt: String,//微博创建时间
    val wId: Long,//微博ID
    val text: String,//微博信息内容
    val source: String,//微博来源 "<a href=\"http://app.weibo.com/t/feed/6vtZb0\" rel=\"nofollow\">微博 weibo.com</a>"
    val favorite: Boolean,//是否已收藏，true：是，false：否
    val thumbnailPic: String,//缩略图片地址，没有时不返回此字段
    val bmiddlePic: String,//中等尺寸图片地址，没有时不返回此字段
    val originalPic: String,//原始图片地址，没有时不返回此字段
    val repostsCount: Int,//转发数
    val commentsCount: Int,//评论数
    val attitudesCount: Int,//表态数

    //下面是微博作者个人信息部分
    val uId: Long,//* 用户id
    val screenName: String,//用户昵称
    val name: String,//友好显示名称
    val province: String,//用户所在省级ID
    val city: String,//用户所在城市ID
    val location: String,//用户所在地
    val description: String,//用户个人描述
    val uCreatedAt: String,//用户创建（注册）时间
    val profileImageUrl: String,//用户头像地址（中图），50×50像素
    val avatarLarge: String,//用户头像地址（大图），180×180像素
    val avatarHd: String,//用户头像地址（高清），高清头像原图
    val profileUrl: String,//用户的微博统一URL地址
    val gender: String,//性别，m：男、f：女、n：未知
    val followersCount: Int,//粉丝数
    val friendsCount: Int,//关注数
    val statusesCount: Int,//微博数
    val favouritesCount: Int,//收藏数
    val geoEnabled: Boolean,//是否允许标识用户的地理位置，true：是，false：否
    val verified: Boolean,//是否是微博认证用户，即加V用户，true：是，false：否
    //val status: WBStatusDTO,//用户的最近一条微博信息字段 详细
    val onlineStatus: Int,//用户的在线状态，0：不在线、1：在线
)

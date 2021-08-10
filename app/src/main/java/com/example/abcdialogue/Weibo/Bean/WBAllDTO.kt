package com.example.abcdialogue.Weibo.Bean

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class WBAllDTO(

    @SerializedName("statuses")
    val statuses: List<WBStatusDTO>?,
    @SerializedName("hasvisible")
    val hasvisible: Boolean?,
    @SerializedName("previous_cursor")
    val previousCursor: Int?,
    @SerializedName("next_cursor")
    val nextCursor: Long?,
    @SerializedName("previous_cursor_str")
    val previousCursorStr: String?,
    @SerializedName("next_cursor_str")
    val nextCursorStr: String?,
    @SerializedName("total_number")
    val totalNumber: Int?,
    @SerializedName("interval")
    val interval: Int?,
    @SerializedName("uve_blank")
    val uveBlank: Int?,
    @SerializedName("since_id_str")
    val sinceIdStr: String?,
    @SerializedName("max_id_str")
    val maxIdStr: String?,
    @SerializedName("has_unread")
    val hasUnread: Int?

):Serializable

data class WBStatusDTO(
    /** 微博创建时间 */
    @SerializedName("created_at")
    val createdAt: String,
    /** 微博ID */
    @SerializedName("id")
    val id: Long,
    /** 微博MID */
    @SerializedName("mid")
    val mid: Long,
    /** 字符串型的微博ID */
    @SerializedName("idstr")
    val idStr: String,
    /** 微博信息内容 */
    @SerializedName("text")
    val text: String,
    /** 微博来源 */
    @SerializedName("source")
    val source: String,
    /** 是否已收藏，true：是，false：否 */
    @SerializedName("favorited")
    val favorite: Boolean,
    /** 是否被截断，true：是，false：否 */
    @SerializedName("truncated")
    val truncated: Boolean,
    /** （暂未支持）回复ID */
    @SerializedName("in_reply_to_status_id")
    val inReplyToStatusId: String,
    /** （暂未支持）回复人UID */
    @SerializedName("in_reply_to_user_id")
    val inReplyToUserId: String,
    /** （暂未支持）回复人昵称 */
    @SerializedName("in_reply_to_screen_name")
    val inReplyToScreenName: String,
    /** 缩略图片地址，没有时不返回此字段 */
    @SerializedName("thumbnail_pic")
    val thumbnailPic: String,
    /** 中等尺寸图片地址，没有时不返回此字段 */
    @SerializedName("bmiddle_pic")
    val bmiddlePic: String,
    /** 原始图片地址，没有时不返回此字段 */
    @SerializedName("original_pic")
    val originalPic: String,
    /** 地理信息字段 详细 */
    @SerializedName("geo")
    val geo: WBGeo,
    /** 微博作者的用户信息字段 详细 */
    @SerializedName("user")
    val user: WBUserInfo,

    /** 被转发的原微博信息字段，当该微博为转发微博时返回 详细 */
    @SerializedName("retweeted_status")
    val retweetedStatus: WBStatusDTO,

    /** 转发数 */
    @SerializedName("reposts_count")
    val repostsCount: Int,
    /** 评论数 */
    @SerializedName("comments_count")
    val commentsCount: Int,
    /** 表态数 */
    @SerializedName("attitudes_count")
    val attitudesCount: Int,
    /** 暂未支持 */
    @SerializedName("mlevel")
    val mLevel: Int,
    /** 微博的可见性及指定可见分组信息。该object中type取值，0：普通微博，1：私密微博，3：指定分组微博，4：密友微博；list_id为分组的组号 */
    @SerializedName("visible")
    val visible: WBVisible,

///** 微博配图ID。多图时返回多图ID，用来拼接图片url。用返回字段thumbnail_pic的地址配上该返回字段的图片ID，即可得到多个图片url。 */
//@SerializedName("pic_ids")
//val pic_ids:Wb,

    /** 微博流内的推广微博ID */
    @SerializedName("ad")
    val ad: List<WBAd>

) : Serializable

data class WBVisible (
    /** 类型 */
    @SerializedName("type")
    val type: Int,
    /** 分组组号*/
    @SerializedName("list_id")
    val listId: Int,
)
data class WBAd(
    @SerializedName("id")
    val id: Long,
    @SerializedName("mark")
    val mark: String
) : Serializable

fun WBStatusDTO.transformToBean(): WBStatusBean = WBStatusBean(
    createdAt = createdAt ?: "",
    wId = id ?: 0L,
    text = text ?: "",
    source = source ?: "",
    favorite = favorite ?: false,
    thumbnailPic = thumbnailPic ?: "",
    bmiddlePic = bmiddlePic ?: "",
    originalPic = originalPic ?: "",
    repostsCount = repostsCount ?: 0,
    commentsCount = commentsCount ?: 0,
    attitudesCount = attitudesCount ?: 0,
    uId = user.id ?: 0L,
    screenName = user.screenName ?: "",
    name = user.name ?: "",
    province = user.province ?: "",
    city = user.city ?: "",
    location = user.location ?: "",
    description = user.description ?: "",
    uCreatedAt = user.createdAt ?: "",
    profileImageUrl = user.profileImageUrl ?: "",
    avatarLarge = user.avatarLarge ?: "",
    avatarHd = user.avatarHd ?: "",
    profileUrl = user.profileUrl ?: "",
    gender = user.gender ?: "",
    followersCount = user.followersCount ?: 0,
    friendsCount = user.friendsCount ?: 0,
    statusesCount = user.statusesCount ?: 0,
    favouritesCount = user.favouritesCount ?: 0,
    geoEnabled = user.geoEnabled ?: false,
    verified = user.verified ?: false,
    onlineStatus = user.onlineStatus ?: 0
)

package com.example.abcdialogue.Weibo.Bean

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class WBAllDTO(

    @SerializedName("statuses")
    val statuses: Int,
    @SerializedName("status_msg")
    val statusMsg: String?,
    @SerializedName("comments")
    val comments: List<String>?,
    @SerializedName("has_more")
    val hasMore: Boolean

):Serializable

data class WBStatusContent(
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
    val geo: WbGeo,
    /** 微博作者的用户信息字段 详细 */
    @SerializedName("user")
    val user: WBUserInfo,

    /** 被转发的原微博信息字段，当该微博为转发微博时返回 详细 */
    @SerializedName("retweeted_status")
    val retweetedStatus: WBStatusContent,

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

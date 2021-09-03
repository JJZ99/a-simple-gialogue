package com.example.abcdialogue.Weibo.Util

enum class LoadStatus {
    LoadMoreIn,             // 列表往下加载更多中
    LoadMoreSuccess,        // 列表往下加载成功
    LoadMoreError,          // 列表往下加载失败
    LoadMoreEnd             // 列表往下已全部加载完毕
}
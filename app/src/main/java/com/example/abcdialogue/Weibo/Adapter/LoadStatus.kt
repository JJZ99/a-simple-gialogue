package com.example.abcdialogue.Weibo.Adapter

enum class LoadStatus {
    Initialize,             // 初始化中
    InitializeSuccess,      // 初始化成功
    InitializeError,        // 初始化错误
    InitializeEmpty,        // 初始化列表空
    LoadMoreIn,             // 列表往下加载更多中
    LoadMoreSuccess,        // 列表往下加载成功
    LoadMoreError,          // 列表往下加载失败
    LoadMoreEnd             // 列表往下已全部加载完毕
}
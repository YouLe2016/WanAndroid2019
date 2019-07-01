package com.wyl.category.bean

data class NavigationBean(
    val articles: List<ArticleBean>?,
    val cid: Int,
    val name: String
)
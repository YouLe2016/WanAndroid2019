package com.wyl.category.vm

import com.wyl.category.bean.ArticleBean
import com.wyl.category.bean.CategoryBean
import com.wyl.category.bean.NavigationBean

class ChildItemModel(id: Int, content: String, position: Int) : StringItemModel(id, content, position) {
    constructor(article: ArticleBean, position: Int) : this(
        article.id,
        article.title,
        position
    )

    constructor(category: CategoryBean, position: Int) : this(
        category.id,
        category.name,
        position
    )
}
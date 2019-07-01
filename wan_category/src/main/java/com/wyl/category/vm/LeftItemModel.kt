package com.wyl.category.vm

import com.wyl.category.bean.CategoryBean
import com.wyl.category.bean.NavigationBean

class LeftItemModel(id: Int, content: String, position: Int) : StringItemModel(id, content, position) {
    constructor(navigation: NavigationBean, position: Int) : this(
        navigation.cid,
        navigation.name,
        position
    )

    constructor(category: CategoryBean, position: Int) : this(
        category.id,
        category.name,
        position
    )
}
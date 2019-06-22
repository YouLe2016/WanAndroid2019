package com.wyl.category.vm

import com.wyl.category.bean.CategoryBean

class LeftItemModel(category: CategoryBean, position: Int) : StringItemModel(
    category.id,
    category.name,
    position
)
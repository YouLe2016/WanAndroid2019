package com.wyl.category.vm

import com.wyl.category.bean.CategoryBean

class GroupItemModel(category: CategoryBean, position: Int) : StringItemModel(
    category.id,
    category.name,
    position
)




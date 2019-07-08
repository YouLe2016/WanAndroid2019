/**
 * created by 乐哥哥, 2019/7/8
 * Copyright (c) 2019, 270628297@qq.com All Rights Reserved.
 * #                   *********                            #
 * #                  ************                          #
 * #                  *************                         #
 * #                 **  ***********                        #
 * #                ***  ****** *****                       #
 * #                *** *******   ****                      #
 * #               ***  ********** ****                     #
 * #              ****  *********** ****                    #
 * #            *****   ***********  *****                  #
 * #           ******   *** ********   *****                #
 * #           *****   ***   ********   ******              #
 * #          ******   ***  ***********   ******            #
 * #         ******   **** **************  ******           #
 * #        *******  ********************* *******          #
 * #        *******  ******************************         #
 * #       *******  ****** ***************** *******        #
 * #       *******  ****** ****** *********   ******        #
 * #       *******    **  ******   ******     ******        #
 * #       *******        ******    *****     *****         #
 * #        ******        *****     *****     ****          #
 * #         *****        ****      *****     ***           #
 * #          *****       ***        ***      *             #
 * #            **       ****        ****                   #
 */
package com.wyl.base

import com.lzg.extend.toDisposables
import com.wyl.base.bean.ArticleData
import com.wyl.base.repository.ArticleRepository
import com.wyl.libbase.base.BaseViewModel

/**
 * 项目名称：WanAndroid2019
 * 创建人：乐哥哥
 * 创建时间：2019-07-08 17:08
 */
class CommonViewModel(private val repository: ArticleRepository) : BaseViewModel() {

    /** 收藏文章 */
    fun collect(data: ArticleData) {
        if (data.id == -1) {
            repository.collectOut(data.title, data.author, data.link)
        } else {
            repository.collectIn(data.id)
        }.doOnSubscribe { loading.set(true) }
            .doFinally { loading.set(false) }
            .subscribe({
                data.collect = !data.collect
                error.value = "收藏成功"
            }, {
                onError(it)
            }).toDisposables(disposables)
    }


    /** 取消收藏 */
    fun unCollect(data: ArticleData) {
        repository.unCollectHome(data.id)
            .doOnSubscribe { loading.set(true) }
            .doFinally { loading.set(false) }
            .subscribe({
                data.collect = !data.collect
                error.value = "取消收藏"
            }, {
                onError(it)
            }).toDisposables(disposables)
    }

}
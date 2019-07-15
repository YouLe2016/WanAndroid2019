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
package com.wyl.base.fragment

import android.databinding.ObservableArrayList
import com.lzg.extend.BaseResponse
import com.lzg.extend.JsonConvert
import com.lzg.extend.toDisposables
import com.lzy.okgo.OkGo
import com.lzy.okrx2.adapter.ObservableBody
import com.wyl.base.SEARCH
import com.wyl.base.bean.ArticleBean
import com.wyl.base.bean.ArticleListData
import com.wyl.libbase.base.PageViewModel

/**
 * 项目名称：WanAndroid2019
 * 创建人：乐哥哥
 * 创建时间：2019-7-15 09:18:12
 */
class ArticleSearchViewModel(var key: String) : PageViewModel() {
    val dataSource = ObservableArrayList<ArticleBean>()
    private var page = 0

    override fun refresh() {
        page = 0
        loadArticleList()
            .doFinally { refresh.set(false) }
            .doOnNext {
                dataSource.clear()
                page = it.curPage
            }.subscribe({
                it.datas?.let { data -> dataSource.addAll(data) }
                enableLoadMore.set(!it.over)
            }, {
                onError(it)
            }).toDisposables(disposables)
    }

    override fun loadMore() {
        loadArticleList()
            .doFinally { loadMore.set(false) }
            .doOnNext {
                page = it.curPage
            }.subscribe {
                it.datas?.let { data ->
                    dataSource.addAll(data)
                }
                enableLoadMore.set(!it.over)
            }.toDisposables(disposables)
    }


    private fun loadArticleList() =
        OkGo.post<BaseResponse<ArticleListData>>("$SEARCH$page/json")
            .params("k", key)
//            .isMultipart(true)
            .converter(object : JsonConvert<BaseResponse<ArticleListData>>() {})
            .adapt(ObservableBody<BaseResponse<ArticleListData>>())
            .map { it.data }
}
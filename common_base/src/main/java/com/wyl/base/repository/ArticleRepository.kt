package com.wyl.base.repository

import com.lzg.extend.BaseResponse
import com.lzg.extend.JsonConvert
import com.lzy.okgo.OkGo
import com.lzy.okrx2.adapter.ObservableBody
import com.wyl.base.COLLECT_IN
import com.wyl.base.COLLECT_OUT
import com.wyl.base.UNCOLLECT_HOME
import io.reactivex.Observable

/**
 * 项目名称：WanAndroid2019
 * 创建人：乐哥哥
 * 创建时间：2019-07-03 14:42
 */
class ArticleRepository {

    /**
     * 收藏站内文章
     */
    fun collectIn(id: Int): Observable<*> =
        OkGo.post<BaseResponse<*>>("$COLLECT_IN/$id/json")
            .converter(object : JsonConvert<BaseResponse<*>>() {})
            .adapt(ObservableBody<BaseResponse<*>>())

    /** 收藏站外文章 */
    fun collectOut(title: String, author: String, link: String): Observable<*> =
        OkGo.post<BaseResponse<*>>(COLLECT_OUT)
            .params("title", title)
            .params("author", author)
            .params("link", link)
            .converter(object : JsonConvert<BaseResponse<*>>() {})
            .adapt(ObservableBody<BaseResponse<*>>())

    /** 取消收藏(文章列表) */
    fun unCollectHome(id: Int): Observable<*> =
        OkGo.post<BaseResponse<*>>("$UNCOLLECT_HOME/$id/json")
            .converter(object : JsonConvert<BaseResponse<*>>() {})
            .adapt(ObservableBody<BaseResponse<*>>())


}
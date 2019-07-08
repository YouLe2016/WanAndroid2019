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
package com.wyl.base.bean

import android.databinding.BaseObservable
import android.databinding.Bindable
import com.wyl.base.BR

/**
 * 项目名称：WanAndroid2019
 * 创建人：乐哥哥
 * 创建时间：2019-07-08 15:59
 */
class ArticleData : BaseObservable() {
    /**
     * apkLink :
     * author : 郭霖
     * chapterId : 409
     * chapterName : 郭霖
     * collect : false
     * courseId : 13
     * desc :
     * envelopePic :
     * fresh : false
     * id : 7475
     * link : https://mp.weixin.qq.com/s/BT3a3TMm_mZu0hdVd1bvgA
     * niceDate : 2018-11-07
     * origin :
     * projectLink :
     * publishTime : 1541520000000
     * superChapterId : 408
     * superChapterName : 公众号
     * tags : [{"name":"公众号","url":"/wxarticle/list/409/1"}]
     * title : 一篇好文之Android数据库GreenDao的完全解析
     * type : 0
     * userId : -1
     * visible : 1
     * zan : 0
     */

    var apkLink: String = ""
    var author: String = ""
    var chapterId: Int = 0
    var chapterName: String? = null
    var courseId: Int = 0
    var desc: String? = null
    var envelopePic: String? = null
    var isFresh: Boolean = false
    var id: Int = -1
    lateinit var link: String
    var niceDate: String? = null
    var origin: String? = null
    var projectLink: String? = null
    var publishTime: Long = 0
    var superChapterId: Int = 0
    var superChapterName: String? = null
    var title: String = ""
    var type: Int = 0
    var userId: Int = 0
    var visible: Int = 0
    var zan: Int = 0
    var tags: List<TagsBean>? = null

    @Bindable
    var collect: Boolean = false
        set(value) {
            if (field != value) {
                field = value
                notifyPropertyChanged(BR.collect)
            }
        }
}
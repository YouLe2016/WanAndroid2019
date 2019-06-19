package com.wyl.home.bean

/**
 * desc :
 * id : 18
 * imagePath : http://www.wanandroid.com/blogimgs/00f83f1d-3c50-439f-b705-54a49fc3d90d.jpg
 * isVisible : 1
 * order : 1
 * title : 公众号文章列表强势上线
 * type : 0
 * url : http://www.wanandroid.com/wxarticle/list/408/1
 */
data class BannerData(
    var desc: String = "",
    var id: Int = 0,
    var imagePath: String = "",
    var isVisible: Int = 0,
    var order: Int = 0,
    var title: String = "",
    var type: Int = 0,
    var url: String = ""
)
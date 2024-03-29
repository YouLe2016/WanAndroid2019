package com.wyl.libbase.utils

import android.support.v4.app.Fragment
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.launcher.ARouter

/* ************* 针对Bundle封装的版本 ************** */
// fun openActivity(pathName: String, bundle: Bundle? = null) {
//     ARouter.getInstance().build(pathName)
//         .with(bundle)
//         .navigation()
// }
//
// fun getFragment(pathName: String, bundle: Bundle? = null): Fragment =
//     ARouter.getInstance().build(pathName)
//         .with(bundle)
//         .navigation() as Fragment


fun openActivity(pathName: String, block: (Postcard.() -> Postcard)? = null) {
    var postcard = ARouter.getInstance().build(pathName)
    if (block != null) postcard = block.invoke(postcard)
    postcard.navigation()
}


fun getFragment(pathName: String, block: (Postcard.() -> Postcard)? = null): Fragment {
    var postcard = ARouter.getInstance().build(pathName)
    if (block != null) postcard = block.invoke(postcard)
    return postcard.navigation() as Fragment
}
package com.wyl.common_lib.binding.banner

import android.databinding.BindingAdapter
import com.youth.banner.Banner
import com.youth.banner.BannerConfig
import com.youth.banner.listener.OnBannerListener

@BindingAdapter(value = ["urls", "titles", "onItemClick"], requireAll = false)
fun onBanner(banner: Banner, urls: List<String>?, titles: List<String>?, onClick: OnBannerListener?) {
    banner.setImages(urls)
        .setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE)
        .setBannerTitles(titles)
        .setImageLoader(GlideImageLoad())
        .setOnBannerListener(onClick)
        .start()
}
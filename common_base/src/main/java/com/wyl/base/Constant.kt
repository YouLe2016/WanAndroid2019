package com.wyl.base

/* EventBus */
/**
 * 登录成功的事件回调
 */
const val EVENT_LOGIN = "登录成功的事件回调"

/* ACache */
const val USERNAME = "username"

/* url */
const val BASE_URL = "https://www.wanandroid.com/"

/* **************** 登录注册 **********************/
const val LOGIN_LOGIN = "${BASE_URL}user/login"
const val LOGIN_REGISTER = "${BASE_URL}user/register"
const val LOGIN_LOGOUT = "${BASE_URL}user/logout/json"


/* **************** 1. 首页相关 **********************/
const val HOME_BANNER = "${BASE_URL}banner/json"
const val HOME_HOTKEY = "${BASE_URL}hotkey/json"
const val HOME_FRIEND = "${BASE_URL}friend/json"
// https://www.wanandroid.com/article/list/0/json
const val HOME_ARTICLE_LIST = "${BASE_URL}article/list/"

/* **************** 2.体系 **********************/
/**
 * 体系数据
 */
const val TREE_CATEGORY = "${BASE_URL}tree/json"


/* **************** 3. 导航 **********************/
/**
 * 导航数据
 */
const val NAV_CATEGORY = "${BASE_URL}navi/json"


/* **************** 6.收藏 **********************/
/**
 * 收藏站内文章
 */
// https://www.wanandroid.com/lg/collect/1165/json
const val COLLECT_IN = "${BASE_URL}lg/collect/"
/**
 * 收藏站外文章
 */
const val COLLECT_OUT = "${BASE_URL}lg/collect/add/json"

/**
 * 取消收藏(文章列表)
 */
const val UNCOLLECT_HOME = "${BASE_URL}lg/uncollect_originId/"
/**
 * 取消收藏(我的收藏页面)
 */
const val UNCOLLECT_MINE = "${BASE_URL}lg/uncollect/"
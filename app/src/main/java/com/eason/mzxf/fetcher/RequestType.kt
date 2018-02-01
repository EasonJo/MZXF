package com.eason.mzxf.fetcher

/**
 * 当前 Fetcher 的请求类型
 * @author Eason
 */
enum class RequestType {
    /**
     * 登录
     */
    LOGIN,
    /**
     * 签到
     */
    CHECKIN,
    /**
     * 获取用户信息
     */
    GET_INFO,
    /**
     * None
     */
    TYPE_NONE
}
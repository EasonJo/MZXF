package com.eason.mzxf.http.service

import com.eason.mzxf.bean.BaseJson
import com.eason.mzxf.bean.SignData
import com.eason.mzxf.bean.UserInfo
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * 网络访问接口
 * Created by Eason on 2018/1/15.
 */
interface ApiService {
    /**
     * 登录接口
     */
    @GET("login.xhtml")
    fun login(@Query("userName") userName: String, @Query("passWord") password: String): Single<BaseJson<UserInfo>>

    /**
     * 签到接口
     */
    @GET("sign/sign.xhtml")
    fun checkIn(@Query("keyCode") keyCode: String): Single<BaseJson<SignData>>

    /**
     * 获取用户信息
     */
    @GET("user/getInfo.xhtml")
    fun getInfo(@Query("keyCode") keyCode: String): Single<BaseJson<UserInfo>>
}
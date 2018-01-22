package com.eason.mzxf.http.service

import com.eason.mzxf.bean.BaseJson
import com.eason.mzxf.bean.SignData
import com.eason.mzxf.bean.UserInfo
import io.reactivex.Single
import retrofit2.http.*

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
    @FormUrlEncoded
    @POST("user/getInfo.xhtml")
    fun getInfo(@Field("keyCode") keyCode: String): Single<BaseJson<UserInfo>>
}
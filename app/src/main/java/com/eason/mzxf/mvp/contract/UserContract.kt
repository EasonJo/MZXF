package com.eason.mzxf.mvp.contract

import android.app.Activity
import com.eason.mzxf.bean.BaseJson
import com.eason.mzxf.bean.SignData
import com.eason.mzxf.bean.UserInfo
import com.jess.arms.mvp.IModel
import com.jess.arms.mvp.IView
import io.reactivex.Single


interface UserContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View : IView {

        fun loading()
        fun getActivity(): Activity
        fun loginResult(msg: String)
        fun setDefaultUserNameAndPassword(map: HashMap<String, String>)
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model : IModel {
        /**
         * login
         */
        fun login(userName: String, passWord: String): Single<BaseJson<UserInfo>>

        /**
         * set default userName and Password
         */
        fun getDefaultUserNameAndPassword(): HashMap<String, String>

        fun isLogin(): Boolean

        fun setLogined(boolean: Boolean)

        fun getInfo(keyCode: String): Single<BaseJson<UserInfo>>

        fun checkIn(keyCode: String): Single<BaseJson<SignData>>
    }

    interface UserInfoView : IView {
        fun showInfo(message: String)
    }

}

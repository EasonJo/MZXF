package com.eason.mzxf.mvp.model

import android.app.Application
import android.text.TextUtils
import com.eason.mzxf.bean.BaseJson
import com.eason.mzxf.bean.SignData
import com.eason.mzxf.bean.UserInfo
import com.eason.mzxf.http.service.ApiService
import com.eason.mzxf.mvp.contract.UserContract
import com.eason.mzxf.utils.PASSWORD
import com.eason.mzxf.utils.Preferences
import com.eason.mzxf.utils.USERNAME
import com.google.gson.Gson
import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel
import com.jess.arms.utils.DataHelper
import io.reactivex.Single
import io.reactivex.SingleSource
import io.reactivex.functions.BiFunction
import java.util.*
import javax.inject.Inject


@ActivityScope
class LoginModel @Inject constructor(repositoryManager: IRepositoryManager,
                                     private var mGson: Gson?,
                                     private var mApplication: Application?,
                                     private var preferences: Preferences)
    : BaseModel(repositoryManager), UserContract.Model {

    override fun getInfo(keyCode: String): Single<BaseJson<UserInfo>> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java).getInfo(keyCode)
    }

    override fun isLogin(): Boolean = preferences.isUserLoggedIn

    override fun getDefaultUserNameAndPassword(): HashMap<String, String> {
        val user = HashMap<String, String>()
        Single.zip(getValueFromSp(USERNAME), getValueFromSp(PASSWORD), BiFunction<String, String, HashMap<String, String>> { u, p ->
            val map = HashMap<String, String>()
            if (!TextUtils.isEmpty(u) && !TextUtils.isEmpty(p)) {
                map[USERNAME] = u
                map[PASSWORD] = p
            }
            map
        }).subscribe { t1, _ -> user.putAll(t1) }
        return user
    }


    override fun login(userName: String, passWord: String): Single<BaseJson<UserInfo>> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java).login(userName, passWord)
    }

    override fun setLogined(boolean: Boolean) {
        if (boolean) preferences.saveUserLoggedIn() else preferences.saveUserLoggedOut()
    }

    override fun checkIn(keyCode: String): Single<BaseJson<SignData>> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java).checkIn(keyCode)
    }

    override fun onDestroy() {
        super.onDestroy()
        this.mGson = null
        this.mApplication = null
    }

    /**
     * get value form sp
     */
    private fun getValueFromSp(key: String): SingleSource<String> {

        return Single.fromCallable {
            //Can't be null String
            val value = DataHelper.getStringSF(mApplication, key)
            if (TextUtils.isEmpty(value) || "null" == value)
                ""
            else
                value

        }
    }
}
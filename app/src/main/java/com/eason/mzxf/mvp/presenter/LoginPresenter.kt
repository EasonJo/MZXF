package com.eason.mzxf.mvp.presenter

import android.app.Application
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import android.content.Intent
import com.eason.mzxf.fetcher.RequestType
import com.eason.mzxf.mvp.contract.UserContract
import com.eason.mzxf.mvp.ui.activity.UserActivity
import com.eason.mzxf.utils.KEYCODE
import com.eason.mzxf.utils.PASSWORD
import com.eason.mzxf.utils.Preferences
import com.eason.mzxf.utils.USERNAME
import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.http.imageloader.ImageLoader
import com.jess.arms.integration.AppManager
import com.jess.arms.utils.DataHelper
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import timber.log.Timber
import javax.inject.Inject


@ActivityScope
class LoginPresenter @Inject constructor(model: UserContract.Model, rootView: UserContract.View,
                                         private var mErrorHandler: RxErrorHandler?,
                                         private var mApplication: Application?, private var mImageLoader: ImageLoader?,

                                         private var mAppManager: AppManager?) : ApiPresenter<UserContract.Model, UserContract.View>(model, rootView) {
    @Inject
    lateinit var preferences: Preferences

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        //mRxPermissions.request()
        setDefaultUserNameAndPassword()
    }

    fun doLogin(userName: String, passWord: String) {

        fetch(mModel.login(userName, passWord), RequestType.LOGIN) {
            mRootView.hideLoading()
            if (it.isSuccess) {
                DataHelper.setStringSF(mApplication, USERNAME, userName)
                DataHelper.setStringSF(mApplication, PASSWORD, passWord)
                DataHelper.setStringSF(mApplication, KEYCODE, it.data?.keyCode)
                mRootView.loginResult(it.data.toString())
                mModel.setLogined(true)
                mRootView.launchActivity(Intent(mApplication,UserActivity::class.java))
            } else {
                mRootView.loginResult(it.message!!)
                mModel.setLogined(false)
            }
        }
    }

    fun setDefaultUserNameAndPassword() {
        val map = mModel.getDefaultUserNameAndPassword()
        if (map.size > 0) {
            mRootView.setDefaultUserNameAndPassword(map)
        }
    }

    fun isLogin(): Boolean = mModel.isLogin()

    override fun onRequestError(errorMessage: String?) {
        Timber.e(errorMessage)
        mRootView.showMessage(errorMessage)
    }

    override fun onRequestStart(requestType: RequestType) {
        Timber.i("Start request: $requestType")
    }

    override fun onDestroy() {
        super.onDestroy()
        this.mErrorHandler = null
        this.mAppManager = null
        this.mImageLoader = null
        this.mApplication = null
    }
}

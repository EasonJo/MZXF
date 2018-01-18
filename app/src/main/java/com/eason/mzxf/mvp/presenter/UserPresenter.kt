package com.eason.mzxf.mvp.presenter

import android.app.Application
import com.eason.mzxf.fetcher.RequestType
import com.eason.mzxf.mvp.contract.UserContract
import com.eason.mzxf.utils.KEYCODE
import com.eason.mzxf.utils.Preferences
import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.http.imageloader.ImageLoader
import com.jess.arms.integration.AppManager
import com.jess.arms.utils.DataHelper
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import timber.log.Timber
import javax.inject.Inject

/**
 * @author Created by Eason on 2018/1/16.
 */
@ActivityScope
class UserPresenter @Inject constructor(model: UserContract.Model, userInfoView: UserContract.UserInfoView,
                                        private var mErrorHandler: RxErrorHandler?,
                                        private var mApplication: Application?,
                                        private var mImageLoader: ImageLoader?,
                                        private var mAppManager: AppManager?,
                                        val preferences: Preferences) :
        ApiPresenter<UserContract.Model, UserContract.UserInfoView>(model, userInfoView) {


    fun getInfo() {

        if (!preferences.isUserLoggedIn) return

        val keyCode = DataHelper.getStringSF(mApplication, KEYCODE)

        keyCode?.let {
            fetch(mModel.getInfo(it), RequestType.GET_INFO) {
                if (it.isSuccess) mRootView.showInfo(it.data.toString()) else mRootView.showMessage(it.message)
            }
        }
    }

    fun checkIn() {
        if (!preferences.isUserLoggedIn) return

        val keyCode = DataHelper.getStringSF(mApplication, KEYCODE)
        keyCode?.let {
            fetch(mModel.checkIn(it), RequestType.CHECKIN) {
                if (it.isSuccess) mRootView.showInfo(it.data.toString()) else mRootView.showMessage(it.message)
            }
        }
    }

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
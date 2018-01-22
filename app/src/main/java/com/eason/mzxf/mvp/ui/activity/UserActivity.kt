package com.eason.mzxf.mvp.ui.activity

import android.content.Intent
import android.os.Bundle
import com.eason.mzxf.R
import com.eason.mzxf.di.component.DaggerUserInfoComponent
import com.eason.mzxf.di.module.UserInfoModule
import com.eason.mzxf.mvp.contract.UserContract
import com.eason.mzxf.mvp.presenter.UserPresenter
import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils
import kotlinx.android.synthetic.main.activity_user.*

class UserActivity : BaseActivity<UserPresenter>(), UserContract.UserInfoView {

    override fun setupActivityComponent(appComponent: AppComponent?) {
        DaggerUserInfoComponent
                .builder()
                .appComponent(appComponent)
                .userInfoModule(UserInfoModule(this))
                .build()
                .inject(this)
    }

    override fun initData(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_user)

        userInfo.setOnClickListener {
            mPresenter.getInfo()
        }

        checkIn.setOnClickListener {
            mPresenter.checkIn()
        }
    }

    override fun initView(savedInstanceState: Bundle?): Int = 0

    override fun showLoading() {
    }

    override fun launchActivity(intent: Intent?) {
        ArmsUtils.startActivity(intent)
        finish()
    }

    override fun hideLoading() {
    }

    override fun killMyself() {
        finish()
    }

    override fun showMessage(message: String?) {
        ArmsUtils.snackbarText(message)
    }

    override fun showInfo(message: String) {
        info.text = message
    }
}

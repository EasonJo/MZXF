package com.eason.mzxf.di.module

import android.app.Application
import com.eason.mzxf.mvp.contract.UserContract
import com.eason.mzxf.mvp.model.LoginModel
import com.eason.mzxf.utils.Preferences
import com.jess.arms.di.scope.ActivityScope
import dagger.Module
import dagger.Provides

/**
 * Created by Eason on 2018/1/16.
 */
@Module
class UserInfoModule(var view: UserContract.UserInfoView) {


    @ActivityScope
    @Provides
    internal fun provideUserInfoView(): UserContract.UserInfoView {
        return this.view
    }

    @ActivityScope
    @Provides
    internal fun provideLoginModel(model: LoginModel): UserContract.Model {
        return model
    }

    @ActivityScope
    @Provides
    internal fun providePreferences(app: Application): Preferences {
        return Preferences(app)
    }
}
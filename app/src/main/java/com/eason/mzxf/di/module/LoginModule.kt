package com.eason.mzxf.di.module

import android.app.Application
import com.eason.mzxf.mvp.contract.UserContract
import com.eason.mzxf.mvp.model.LoginModel
import com.eason.mzxf.utils.Preferences
import com.jess.arms.di.scope.ActivityScope
import com.tbruyelle.rxpermissions2.RxPermissions
import dagger.Module
import dagger.Provides

/**
 * 构建MainModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
 *
 * @param view
 */
@Module
class LoginModule(val view: UserContract.View) {

    @ActivityScope
    @Provides
    internal fun provideLoginView(): UserContract.View {
        return this.view
    }

    @ActivityScope
    @Provides
    internal fun provideLoginModel(model: LoginModel): UserContract.Model {
        return model
    }

    @ActivityScope
    @Provides
    internal fun provideRxPermissions(): RxPermissions {
        return RxPermissions(view.getActivity())
    }

    @ActivityScope
    @Provides
    internal fun providePreferences(app: Application): Preferences {
        return Preferences(app)
    }

}
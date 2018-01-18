package com.eason.mzxf.di.component

import com.eason.mzxf.di.module.LoginModule
import com.eason.mzxf.mvp.ui.activity.LoginActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.di.scope.ActivityScope
import dagger.Component

@ActivityScope
@Component(modules = [LoginModule::class], dependencies = [AppComponent::class])
interface LoginComponent {
    fun inject(activity: LoginActivity)
}
package com.eason.mzxf.di.component

import com.eason.mzxf.di.module.UserInfoModule
import com.eason.mzxf.mvp.ui.activity.UserActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.di.scope.ActivityScope
import dagger.Component

@ActivityScope
@Component(modules = [(UserInfoModule::class)], dependencies = [AppComponent::class])
interface UserInfoComponent {
    fun inject(activity: UserActivity)
}
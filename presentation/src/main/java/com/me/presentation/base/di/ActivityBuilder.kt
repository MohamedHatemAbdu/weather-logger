package com.me.presentation.base.di

import com.me.presentation.base.view.activity.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class ActivityBuilder {


    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity

}


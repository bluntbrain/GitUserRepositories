package com.shivamsatija.gituserrepositories.di.module

import android.app.Activity
import android.content.Context
import com.shivamsatija.gituserrepositories.di.ActivityContext
import com.shivamsatija.gituserrepositories.di.PerActivity
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(var activity: Activity) {

    @Provides
    @PerActivity
    @ActivityContext
    fun provideActivityContext(): Context = activity
}
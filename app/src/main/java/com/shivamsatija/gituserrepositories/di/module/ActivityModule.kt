package com.shivamsatija.gituserrepositories.di.module

import android.app.Activity
import android.content.Context
import com.shivamsatija.gituserrepositories.di.ActivityContext
import com.shivamsatija.gituserrepositories.di.PerActivity
import com.shivamsatija.gituserrepositories.ui.listing.RepositoriesMvpPresenter
import com.shivamsatija.gituserrepositories.ui.listing.RepositoriesMvpView
import com.shivamsatija.gituserrepositories.ui.listing.RepositoriesPresenter
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(var activity: Activity) {

    @Provides
    @PerActivity
    @ActivityContext
    fun provideActivityContext(): Context = activity

    @Provides
    @PerActivity
    fun provideRepositoriesPresenter(presenter: RepositoriesPresenter<RepositoriesMvpView>)
            : RepositoriesMvpPresenter<RepositoriesMvpView>
            = presenter
}
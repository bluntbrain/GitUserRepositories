package com.shivamsatija.gituserrepositories.di.component

import android.content.Context
import com.shivamsatija.gituserrepositories.GitUserRepositoriesApplication
import com.shivamsatija.gituserrepositories.data.DataManager
import com.shivamsatija.gituserrepositories.di.ApplicationContext
import com.shivamsatija.gituserrepositories.di.BaseUrl
import com.shivamsatija.gituserrepositories.di.module.ApplicationModule
import dagger.Component
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Singleton

@Singleton
@Component(
    modules = [ApplicationModule::class]
)
interface ApplicationComponent {

    fun inject(application: GitUserRepositoriesApplication)

    @ApplicationContext
    fun applicationContext(): Context

    fun compositeDisposable(): CompositeDisposable

    fun dataManager(): DataManager

    @BaseUrl
    fun baseUrl(): String
}
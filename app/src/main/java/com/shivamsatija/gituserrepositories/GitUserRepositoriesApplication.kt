package com.shivamsatija.gituserrepositories

import android.app.Application
import com.shivamsatija.gituserrepositories.di.component.ApplicationComponent
import com.shivamsatija.gituserrepositories.di.component.DaggerApplicationComponent
import com.shivamsatija.gituserrepositories.di.module.ApplicationModule

class GitUserRepositoriesApplication : Application() {

    private lateinit var _applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()

        _applicationComponent = DaggerApplicationComponent.builder()
            .applicationModule(ApplicationModule(this))
            .build()
    }

    fun getApplicationComponent() = _applicationComponent
}
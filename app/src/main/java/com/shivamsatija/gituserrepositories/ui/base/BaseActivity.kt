package com.shivamsatija.gituserrepositories.ui.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.shivamsatija.gituserrepositories.GitUserRepositoriesApplication
import com.shivamsatija.gituserrepositories.di.component.ActivityComponent
import com.shivamsatija.gituserrepositories.di.component.DaggerActivityComponent
import com.shivamsatija.gituserrepositories.di.module.ActivityModule

abstract class BaseActivity : AppCompatActivity() {

    @LayoutRes
    abstract fun getLayoutResource() : Int

    abstract fun injectDependencies(activityComponent: ActivityComponent)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResource())

        val activityComponent = DaggerActivityComponent.builder()
            .applicationComponent((application as GitUserRepositoriesApplication).getApplicationComponent())
            .activityModule(ActivityModule(this))
            .build()

        injectDependencies(activityComponent)
    }
}
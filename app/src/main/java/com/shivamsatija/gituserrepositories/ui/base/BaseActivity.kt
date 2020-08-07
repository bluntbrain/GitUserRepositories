package com.shivamsatija.gituserrepositories.ui.base

import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.shivamsatija.gituserrepositories.GitUserRepositoriesApplication
import com.shivamsatija.gituserrepositories.di.component.ActivityComponent
import com.shivamsatija.gituserrepositories.di.component.DaggerActivityComponent
import com.shivamsatija.gituserrepositories.di.module.ActivityModule

abstract class BaseActivity : AppCompatActivity(), BaseMvpView {

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

    override fun hideLoading() {

    }

    override fun showLoading() {

    }

    override fun showToast(message: String) {
        if (!TextUtils.isEmpty(message)) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT)
                .show()
        }
    }
}
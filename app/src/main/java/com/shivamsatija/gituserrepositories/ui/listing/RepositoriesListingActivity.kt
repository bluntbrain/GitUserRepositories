package com.shivamsatija.gituserrepositories.ui.listing

import android.os.Bundle
import com.shivamsatija.gituserrepositories.R
import com.shivamsatija.gituserrepositories.di.component.ActivityComponent
import com.shivamsatija.gituserrepositories.ui.base.BaseActivity

class RepositoriesListingActivity
    : BaseActivity() {

    override fun getLayoutResource(): Int = R.layout.activity_repositories_listing

    override fun injectDependencies(activityComponent: ActivityComponent) {
        activityComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }
}
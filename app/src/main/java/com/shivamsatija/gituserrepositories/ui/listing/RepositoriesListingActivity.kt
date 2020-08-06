package com.shivamsatija.gituserrepositories.ui.listing

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.shivamsatija.gituserrepositories.GitUserRepositoriesApplication
import com.shivamsatija.gituserrepositories.R
import com.shivamsatija.gituserrepositories.di.BaseUrl
import com.shivamsatija.gituserrepositories.di.component.DaggerActivityComponent
import com.shivamsatija.gituserrepositories.di.module.ActivityModule
import javax.inject.Inject

class RepositoriesListingActivity
    : AppCompatActivity(R.layout.activity_repositories_listing) {

    @Inject
    @field:BaseUrl
    lateinit var baseUrl: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DaggerActivityComponent.builder()
            .applicationComponent(
                (application as GitUserRepositoriesApplication).getApplicationComponent()
            )
            .activityModule(ActivityModule(this))
            .build()
            .inject(this)

        println("Base URL: $baseUrl")
    }
}
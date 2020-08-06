package com.shivamsatija.gituserrepositories.di.component

import com.shivamsatija.gituserrepositories.di.PerActivity
import com.shivamsatija.gituserrepositories.di.module.ActivityModule
import com.shivamsatija.gituserrepositories.ui.listing.RepositoriesListingActivity
import dagger.Component

@PerActivity
@Component(modules = [ActivityModule::class], dependencies = [ApplicationComponent::class])
interface ActivityComponent {

    fun inject(activity: RepositoriesListingActivity)
}
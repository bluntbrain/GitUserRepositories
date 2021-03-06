package com.shivamsatija.gituserrepositories.ui.listing

import com.shivamsatija.gituserrepositories.data.model.Repository
import com.shivamsatija.gituserrepositories.data.model.User
import com.shivamsatija.gituserrepositories.ui.base.BaseMvpView

interface RepositoriesMvpView : BaseMvpView {

    fun setUser(user: User)

    fun onUserSearchFailed()

    fun updateUserRepositories(repositories: ArrayList<Repository>, toClear: Boolean)

    fun onFetchUserRepositoriesFailed()
}
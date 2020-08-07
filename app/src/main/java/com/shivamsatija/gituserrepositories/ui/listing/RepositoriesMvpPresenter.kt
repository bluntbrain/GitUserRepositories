package com.shivamsatija.gituserrepositories.ui.listing

import com.shivamsatija.gituserrepositories.ui.base.BaseMvpPresenter

interface RepositoriesMvpPresenter<V : RepositoriesMvpView> : BaseMvpPresenter<V> {

    fun searchUser(username: String)

    fun fetchUserRepositories(username: String)
}